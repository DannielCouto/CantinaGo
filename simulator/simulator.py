"""CantinaGo fleet telemetry simulator.

Publishes one reading per e-bike every TICK_SECONDS on cantinago/telemetry/<code>.
Batteries slowly drain while "riding" and recharge while docked, so the backend sees
e-bikes cross the BR1 minimum (20 %) in both directions.
"""

import json
import os
import random
import signal
import sys
import time
from datetime import datetime, timezone

import paho.mqtt.client as mqtt

BROKER_HOST = os.getenv("MQTT_HOST", "localhost")
BROKER_PORT = int(os.getenv("MQTT_PORT", "1883"))
TICK_SECONDS = float(os.getenv("TICK_SECONDS", "5"))
FLEET_SIZE = int(os.getenv("FLEET_SIZE", "30"))
SEED = os.getenv("SEED")

running = True


def stop(*_):
    global running
    running = False


def make_fleet(rng):
    return {
        f"EB-{i:02d}": {"battery": rng.randint(15, 100), "riding": rng.random() < 0.3}
        for i in range(1, FLEET_SIZE + 1)
    }


def step(bike, rng):
    if bike["riding"]:
        bike["battery"] = max(0, bike["battery"] - rng.randint(1, 4))
    else:
        bike["battery"] = min(100, bike["battery"] + rng.randint(0, 2))
    if rng.random() < 0.1:
        bike["riding"] = not bike["riding"]


def connect():
    client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, client_id="cantinago-simulator")
    while running:
        try:
            client.connect(BROKER_HOST, BROKER_PORT)
            client.loop_start()
            print(f"connected to {BROKER_HOST}:{BROKER_PORT}", flush=True)
            return client
        except OSError as e:
            print(f"broker not reachable ({e}), retrying in 2 s", flush=True)
            time.sleep(2)
    sys.exit(0)


def main():
    signal.signal(signal.SIGTERM, stop)
    signal.signal(signal.SIGINT, stop)
    rng = random.Random(SEED)
    fleet = make_fleet(rng)
    client = connect()
    while running:
        now = datetime.now(timezone.utc).isoformat().replace("+00:00", "Z")
        for code, bike in fleet.items():
            step(bike, rng)
            payload = {
                "code": code,
                "battery": bike["battery"],
                "locked": not bike["riding"],
                "timestamp": now,
            }
            client.publish(f"cantinago/telemetry/{code}", json.dumps(payload), qos=1)
        time.sleep(TICK_SECONDS)
    client.loop_stop()
    client.disconnect()


if __name__ == "__main__":
    main()

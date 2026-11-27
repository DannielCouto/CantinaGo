CREATE TABLE stations (
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(100)     NOT NULL UNIQUE,
    latitude  DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    capacity  INT              NOT NULL CHECK (capacity >= 0)
);

CREATE TABLE ebikes (
    id                BIGSERIAL PRIMARY KEY,
    code              VARCHAR(20) NOT NULL UNIQUE,
    type              VARCHAR(20) NOT NULL CHECK (type IN ('NORMAL', 'CARGO')),
    status            VARCHAR(20) NOT NULL
        CHECK (status IN ('AVAILABLE', 'RESERVED', 'IN_USE', 'MAINTENANCE', 'RETIRED')),
    battery           INT         NOT NULL CHECK (battery BETWEEN 0 AND 100),
    latitude          DOUBLE PRECISION,
    longitude         DOUBLE PRECISION,
    locked            BOOLEAN     NOT NULL DEFAULT TRUE,
    last_telemetry_at TIMESTAMP WITH TIME ZONE,
    station_id        BIGINT REFERENCES stations (id)
);

CREATE INDEX idx_ebikes_station ON ebikes (station_id);

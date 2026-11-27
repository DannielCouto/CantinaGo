# AGENTS.md — CantinaGo

Instructions for AI coding agents (and humans) working in this repository. Keep in sync with the
Quality Manual (sections 3.1, 4.1, 5.2, 5.3, 6.1): a PR that changes one updates the other.

## Product

University meal delivery by student couriers using the UA e-bike fleet. Specs live in
`deliverables/` (Product Specs report): user stories (US*), business rules (BR1–BR17),
lifecycles (3.3), reference data (3.4), SLOs (4.2).

## Layout

| Path         | What                                                                 |
|--------------|----------------------------------------------------------------------|
| `backend/`   | Spring Boot 4 / Java 21 modular monolith (`pt.ua.cantinago.<module>`) |
| `frontend/`  | React + Vite + TypeScript SPA                                        |
| `simulator/` | Python fleet telemetry simulator, publishes to MQTT                  |
| `infra/`     | Config for infrastructure containers (Mosquitto)                     |

Backend modules (one package each): `accounts`, `fleet`, `rentals`, `maintenance`, `catalog`,
`orders`, `payments`, `telemetry`, `backoffice`. Modules talk through Java interfaces/events,
never HTTP. External systems (MQTT, payment gateway) sit behind adapters.

## Commands

```bash
cd backend && mvn verify          # unit (*Test, Surefire) + integration/Cucumber (*IT, Failsafe; needs Docker)
cd backend && mvn test            # unit tests only, no Docker
cd frontend && npm ci && npm test # Vitest
cd frontend && npm run lint       # oxlint
docker compose up --build         # full stack at http://localhost:8000
```

## Rules

- Code, identifiers, commits and comments in **English**; docs and reports in **Portuguese**.
  Gherkin features are in Portuguese (`# language: pt`).
- Java: Google Java Style. Frontend: functional components with hooks.
- Commits: Conventional Commits with the story key, e.g. `feat(SCRUM-54): reserve e-bike`.
- Branches: `feature/SCRUM-XX-short-name` from `dev`; PR into `dev`, then `dev` into `main`; merge only via PR.
- REST: plural resources under `/api`, correct status codes, errors as RFC 9457 problem details.
- Schema changes only through new Flyway migrations (`backend/src/main/resources/db/migration`);
  never edit an applied migration.
- Every business rule (BRx) gets unit tests at its boundary values from the rules table.
  Calculation rules (BR5, BR9, BR13, BR15) are written test-first.
- Gherkin features carry the Jira story key (e.g. `@SCRUM-7`) and scenarios the rule tag (`@BRx`);
  once Xray has created a Test for a scenario, add its Test key tag too. Features are the source
  of truth for acceptance; CI imports results into Xray (project `SCRUM`) on every push to `main`.
- Time-dependent logic uses the injected `java.time.Clock`, never `Instant.now()` directly.
- Test data is synthetic. Never put real personal data, secrets or credentials in code, tests,
  config or prompts.

## Definition of done

Acceptance criteria met and Gherkin green; unit tests for new logic; integration tests for
adapters/persistence; CI and quality gate green; PR approved by another team member with AI
review findings answered; tests linked in Xray; merged and deployed; PO accepted in Jira.

## Agents must not

- Approve or merge PRs.
- Weaken or delete tests to make CI pass.
- Add tests that do not fail when the behaviour is wrong.

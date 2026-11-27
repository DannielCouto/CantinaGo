# Contribuir para o CantinaGo

As regras completas estão no Manual de Qualidade (secções 3 e 4) e, para agentes de IA, no
[AGENTS.md](AGENTS.md). Resumo:

1. Move a história para **In Progress** no Jira.
2. Cria o ramo a partir de `main`: `feature/SCRUM-54-reserve-ebike`.
3. Commits pequenos, em inglês, com Conventional Commits e a chave da história:
   `feat(SCRUM-54): reserve e-bike`, `test(SCRUM-54): ...`.
4. Abre um PR cedo (draft se ainda não estiver pronto), usando o template.
5. A CI tem de passar; responde aos achados do revisor de IA com `ACEITE` ou `REJEITADO: <motivo>`.
6. Outro membro revê e aprova. Merge por squash; o ramo é apagado.

## Correr localmente

Requisitos: Java 21+, Maven 3.9+, Node 22+, Docker.

```bash
docker compose up --build          # stack completa em http://localhost:8000

# ou, em desenvolvimento:
docker compose up -d db broker simulator
cd backend && mvn spring-boot:run  # API em http://localhost:8080
cd frontend && npm ci && npm run dev  # http://localhost:5173 (proxy de /api para :8080)
```

## Testes

```bash
cd backend && mvn test     # unitários (sem Docker)
cd backend && mvn verify   # + integração e Cucumber com Testcontainers (precisa de Docker)
cd frontend && npm test
```

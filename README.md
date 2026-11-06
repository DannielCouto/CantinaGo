# CantinaGo
Trabalho para a disciplina TQS - UA


O CantinaGo é um serviço de entregas da universidade. Os estudantes usam e-bikes da universidade para levar refeições da cantina e dos bares ao campus e às zonas à volta da universidade. Quem encomenda pode ser estudante, docente, investigador ou funcionário, quer esteja numa sala de aula, num laboratório, numa residência ou em casa, perto do campus. Quem entrega é um estudante que quer ganhar algum dinheiro nos tempos livres, com horários que se ajustam às aulas.


## 3.2 Atores candidatos
 
| Ator | Principais preocupações |
|------|-------------------------|
| **Cliente** (utilizador final) | Consulta as ementas da cantina e dos bares, faz e paga pedidos, indica o local de entrega (sala no campus ou morada nas redondezas), acompanha a entrega em tempo real, avalia o estafeta |
| **Estafeta** (estudante) | Regista-se com o número de estudante, define a sua disponibilidade, desbloqueia uma e-bike numa estação, aceita e entrega pedidos, reporta problemas nas bicicletas, acompanha as suas entregas e ganhos |
| **Fornecedor** (cantina / bar) | Gere as ementas e a disponibilidade dos produtos, recebe e prepara os pedidos, marca-os como prontos para recolha |
| **Operador do serviço** | Regista e-bikes e estações, define zonas e taxas de entrega, gere o ciclo de vida das bicicletas, trata da manutenção dentro do SLA, redistribui as bicicletas entre estações |
| **Técnico de campo** | Inspeciona e repara e-bikes, troca baterias, fecha pedidos de manutenção (faz parte do papel de operador no MVP) |
| **Cliente** (institucional) | A universidade (por exemplo, os serviços de ação social). Define as regras de utilização e os requisitos para ser estafeta, acompanha os KPIs (tempo de entrega, volume de pedidos, ganhos dos estafetas) e o cumprimento do SLA |
| **Administrador da plataforma** | Gere contas de utilizador e perfis, monitoriza o estado da plataforma |
| **Sistemas externos** | Gateway de pagamento (sandbox, por exemplo cartão/MB WAY), simulador de telemetria da frota (ver [4.4](#44-simulador-de-telemetria)), fornecedor de identidade (SSO da universidade, opcional), serviço de mapas/geocodificação (moradas de entrega e rotas), consumidores de dados abertos (feed GBFS) |


### 4.3 Out of scope

- Faturação ao cliente institucional (faturação B2B).
- Integração com hardware real de veículos ou IoT.
- Aplicações móveis nativas (uma interface web responsiva é suficiente).
- Telemetria dos hubs.
- Pagamentos reais.
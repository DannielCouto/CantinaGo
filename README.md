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


## Épicos
 
| ID  | Épico                                   | Objetivo                                                            |
|-----|-----------------------------------------|---------------------------------------------------------------------|
| E1  | Contas, autenticação e perfis           | Login com a conta UA e papéis de cliente, estafeta, staff e admin.  |
| E2  | Catálogo e ementas                      | O que cada ponto de venda tem disponível, e quando.                 |
| E3  | Encomenda e pagamento                   | Do carrinho à confirmação, com local e hora de entrega.             |
| E4  | Gestão de pedidos no ponto de venda     | Preparar, marcar como pronto e passar ao estafeta.                  |
| E5  | Disponibilidade e turnos dos estafetas  | Entregas encaixadas nos tempos livres entre aulas.                  |
| E6  | Atribuição e despacho de entregas       | Juntar cada pedido ao estafeta e à e-bike certos.                   |
| E7  | Gestão da frota de e-bikes              | Reserva, bateria e manutenção das bicicletas da UA.                 |
| E8  | Localização e zonas de entrega          | Mapa do campus com edifícios e salas, e a área de serviço.          |
| E9  | Acompanhamento e notificações           | Estado do pedido em tempo real até à entrega.                       |
| E10 | Ganhos e pagamentos aos estafetas       | Quanto cada estafeta ganha e como recebe.                           |
| E11 | Avaliações, suporte e incidentes        | Resolver o que corre mal, para clientes e estafetas.                |
| E12 | Backoffice e análise                    | Indicadores e configuração do serviço.                              |
 


### 4.3 Out of scope

- Faturação ao cliente institucional (faturação B2B).
- Integração com hardware real de veículos ou IoT.
- Aplicações móveis nativas (uma interface web responsiva é suficiente).
- Telemetria dos hubs.
- Pagamentos reais.

## 6.1 Papéis

| Papel | Membro | Responsabilidades |
|-------|--------|-------------------|
| **Team Coordinator** | Daniel Couto | Garante uma distribuição justa das tarefas e que os membros trabalham de acordo com o plano; promove a colaboração e age cedo perante problemas; garante que os resultados são entregues a tempo |
| **Product Owner** | Daniel Couto | Representa os stakeholders e conhece o domínio; clarifica as funcionalidades esperadas para a equipa; aceita os incrementos das histórias |
| **QA Engineer** | Alan Marques | Promove as práticas de QA em conjunto com os outros papéis; configura as ferramentas que medem a qualidade; verifica que a equipa segue as práticas acordadas, incluindo as regras de uso de IA |
| **DevOps Master** | Ricardo Rebelo | Responsável pela infraestrutura de desenvolvimento e produção: repositório, pipelines, contentores, ambiente de deployment, bases de dados |
| **Developer** | Todos (Daniel Couto, Ricardo Rebelo, Alan Marques) | Implementa e testa histórias; as contribuições são registadas através de pull requests e commits |

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
| E3  | Encomenda e acompanhamento do pedido    | Do carrinho à entrega, com pagamento e estado em tempo real.        |
| E4  | Gestão de pedidos no ponto de venda     | Preparar, marcar como pronto e passar ao estafeta.                  |
| E5  | Disponibilidade e turnos dos estafetas  | Entregas encaixadas nos tempos livres entre aulas.                  |
| E6  | Atribuição e despacho de entregas       | Juntar cada pedido ao estafeta e à e-bike certos.                   |
| E7  | Gestão da frota de e-bikes              | Reserva, bateria e manutenção das bicicletas da UA.                 |
| E8  | Localização e zonas de entrega          | Mapa do campus com edifícios e salas, e a área de serviço.          |
| E9  | Ganhos e pagamentos aos estafetas       | Quanto cada estafeta ganha e como recebe.                           |
| E10 | Avaliações, suporte e incidentes        | Resolver o que corre mal, para clientes e estafetas.                |
| E11 | Análise de indicadores                  | Indicadores do serviço para o avaliar e planear.                    |

## Personas
 
### Inês Carvalho — cliente (estudante)
- 20 anos, 2.º ano de Biologia, vive na residência de Santiago.
- Tem aulas e laboratórios espalhados pelo dia e muitas vezes não tem tempo para ir à cantina.
- Usa o telemóvel para tudo e paga com MB Way.
- **Objetivo:** comer uma refeição barata e quente sem perder tempo em filas.
- **Frustração:** chegar à cantina e ter uma fila interminável.
### Prof. Rui Matos — cliente (docente)
- 47 anos, docente no DETI, gabinete no edifício 4.
- Dá aulas seguidas e tem reuniões à hora de almoço.
- **Objetivo:** receber o almoço no gabinete à hora certa, sem ter de pensar nisso.
- **Frustração:** serviços de entrega externos que não encontram o edifício nem o gabinete ou não podem entrar até a porta do edifício.
### Tiago Almeida — estafeta (estudante)
- 21 anos, 3.º ano de Engenharia Mecânica, vive num quarto alugado perto do campus.
- Tem furos no horário e quer ganhar dinheiro sem um emprego de horário fixo.
- Não tem carro nem bicicleta própria.
- **Objetivo:** fazer entregas nos tempos livres e saber quanto vai ganhar.
- **Frustração:** trabalhos que não respeitam o horário das aulas e das épocas de exames ou que necessitam de veiculo proprio.
### Fernanda Sousa — staff (cantina do Crasto)
- 52 anos, trabalha na cantina do Crasto há 15 anos.
- À hora de almoço tem fila ao balcão e pouco tempo para olhar para ecrãs.
- **Objetivo:** preparar os pedidos por ordem e entregá-los ao estafeta certo, sem confusões.
- **Frustração:** pedidos que chegam todos ao mesmo tempo ou de pratos que já acabaram.
### Marta Ribeiro — administração (SASUA)
- 38 anos, responsável pelo CantinaGo nos Serviços de Ação Social.
- Gere a frota de e-bikes, os estafetas e os pagamentos.
- **Objetivo:** manter o serviço fiável e sustentável, com bicicletas operacionais e estafetas suficientes nas horas de ponta.
- **Frustração:** não ter dados para justificar o serviço junto da reitoria.
---
 


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

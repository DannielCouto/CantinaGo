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


## Histórias de utilizador
 
### E1 — Contas, autenticação e perfis
- **US1.1** (SCRUM-28) Como membro da UA, quero entrar com a minha conta universitária, para não ter de criar outra conta.
- **US1.2** (SCRUM-29) Como estudante, quero poder criar um perfil de estafeta, para poder fazer entregas.
- **US1.3** (SCRUM-30) Como administradora, quero aprovar estafetas, para garantir que só fazem entregas pessoas preparadas.
### E2 — Catálogo e ementas
- **US2.1** (SCRUM-31) Como cliente, quero ver as ementas do dia de cada ponto de venda, com preços e descrições, para escolher o que comer.
- **US2.2** (SCRUM-32) Como staff, quero marcar um prato como esgotado, para que não seja encomendado.
- **US2.3** (SCRUM-33) Como staff, quero definir o horário em que aceito encomendas, para não receber mais pedidos passado certo horário.
### E3 — Encomenda e acompanhamento do pedido
- **US3.1** (SCRUM-34) Como cliente, quero escolher o local de entrega (sala, laboratório, residência ou morada perto do campus), para receber onde estou.
- **US3.2** (SCRUM-35) Como cliente, quero agendar a entrega para uma hora específica, para a receber quando tenho disponibilidade.
- **US3.3** (SCRUM-36) Como cliente, quero pagar com MB Way ou cartão, para usar o meio de pagamento que me é mais conveniente.
- **US3.4** (SCRUM-37) Como cliente, quero cancelar o pedido antes de a preparação começar, para ser reembolsado caso mude de planos.
- **US3.5** (SCRUM-38) Como cliente, quero ver o estado do pedido em tempo real, para acompanhar a entrega.
- **US3.6** (SCRUM-39) Como cliente, quero ser avisado quando o estafeta estiver a chegar, para estar à porta.
- **US3.7** (SCRUM-40) Como estafeta, quero confirmar a entrega com um código dado pelo cliente, para provar que entreguei à pessoa certa.
### E4 — Gestão de pedidos no ponto de venda
- **US4.1** (SCRUM-41) Como staff, quero ver os pedidos ordenados pela hora de recolha, para organizar a preparação.
- **US4.2** (SCRUM-42) Como staff, quero marcar um pedido como pronto, para avisar o estafeta.
- **US4.3** (SCRUM-43) Como staff, quero confirmar a entrega ao estafeta lendo um QR, para não trocar sacos.
### E5 — Disponibilidade e turnos dos estafetas
- **US5.1** (SCRUM-44) Como estafeta, quero ligar e desligar o estado "disponível", para só receber pedidos quando posso.
- **US5.2** (SCRUM-45) Como estafeta, quero indicar os meus tempos livres na semana, para que a app não me proponha turnos durante as aulas.
- **US5.3** (SCRUM-46) Como administradora, quero ver quantos estafetas estão previstos por hora, para saber quando faltam pessoas.
### E6 — Atribuição e despacho de entregas
- **US6.1** (SCRUM-47) Como estafeta, quero aceitar ou recusar um pedido num tempo limite, para decidir se o consigo fazer.
- **US6.2** (SCRUM-48) Como estafeta, quero conseguir agrupar vários pedidos para o mesmo destino, para os fazer numa só viagem.
- **US6.3** (SCRUM-49) Como sistema, quero propor pedidos tendo em conta a distância e a bateria da e-bike, para que as entregas sejam rápidas e possíveis.
- **US6.4** (SCRUM-50) Como administradora, quero reatribuir um pedido à mão, para resolver casos em que o estafeta já não pode efetuar a entrega.
### E7 — Gestão da frota de e-bikes
- **US7.1** (SCRUM-54) Como estafeta, quero reservar e desbloquear uma e-bike pela app, para começar o turno sem depender de terceiros.
- **US7.2** (SCRUM-55) Como estafeta, quero reportar uma avaria e trocar de bicicleta, para não perder o tempo de trabalho.
- **US7.3** (SCRUM-56) Como administradora, quero ver a localização, a bateria e o estado de cada bicicleta, para planear a manutenção.
### E8 — Localização e zonas de entrega
- **US8.1** (SCRUM-51) Como cliente, quero escolher o edifício e a sala a partir de uma lista (caso a entrega seja dentro da Universidade), para não ter de escrever moradas.
- **US8.2** (SCRUM-52) Como estafeta, quero indicações até à entrada do edifício, para não perder tempo à procura.
- **US8.3** (SCRUM-53) Como administradora, quero definir a área de serviço no mapa, para que só sejam aceites pedidos dentro dela.
### E9 — Ganhos e pagamentos aos estafetas
- **US9.1** (SCRUM-57) Como estafeta, quero ver quanto ganhei por entrega, por dia e por mês, para controlar os meus rendimentos.
- **US9.2** (SCRUM-58) Como cliente, quero ter a opção de dar gorjeta ao estafeta, para agradecer um bom serviço.
- **US9.3** (SCRUM-59) Como administradora, quero gerar os pagamentos mensais/semanais dos estafetas, para pagar a tempo e sem erros.
### E10 — Avaliações, suporte e incidentes
- **US10.1** (SCRUM-60) Como cliente, quero avaliar a entrega, para ajudar a melhorar o serviço.
- **US10.2** (SCRUM-61) Como cliente, quero reportar um problema com fotografia, para pedir reembolso.
- **US10.3** (SCRUM-62) Como estafeta, quero reportar um incidente (cliente ausente, acidente), para ficar registado e ter apoio.
### E11 — Análise de indicadores
- **US11.1** (SCRUM-63) Como administradora, quero ver o número de entregas, o tempo médio e a taxa de cancelamento, para avaliar o serviço.
- **US11.2** (SCRUM-64) Como responsável da cantina, quero ver os pratos mais pedidos por entrega, para planear a produção.

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
### Marta Ribeiro — gestora do serviço (SASUA, cliente institucional)
- 38 anos, responsável pelo CantinaGo nos Serviços de Ação Social.
- Define as regras do serviço, aprova os estafetas e acompanha os KPIs e o cumprimento do SLA pelo operador.
- **Objetivo:** manter o serviço fiável e sustentável, com bicicletas operacionais e estafetas suficientes nas horas de ponta.
- **Frustração:** não ter dados para justificar o serviço junto da reitoria.
### Sofia Martins — coordenadora de operações (MoveLeve, operador)
- 34 anos, coordena a operação da frota da MoveLeve no campus da UA.
- Planeia a manutenção, a redistribuição de e-bikes entre estações e o trabalho dos técnicos.
- **Objetivo:** cumprir o SLA de disponibilidade com o menor número de deslocações.
- **Frustração:** só saber de uma avaria quando um estafeta se queixa.
### João Pereira — técnico de campo (MoveLeve)
- 29 anos, mecânico de bicicletas, faz a ronda das estações numa carrinha.
- **Objetivo:** saber que reparações tem e por que ordem, sem telefonemas.
- **Frustração:** chegar a uma estação e não encontrar a bicicleta ou a avaria descrita.
---
 

## Cenários
 
### C1 — Almoço entre aulas (Prof. Rui) · E1, E2, E3
Às 10h50, no intervalo, o Rui abre a app com a conta UA e vê a ementa da cantina do Crasto. Escolhe o prato do dia, seleciona "DETI — Gabinete 4.2.15" e agenda a entrega para as 13h05, quando acaba a aula. Paga com MB Way. Às 13h00 recebe uma notificação de que o estafeta está a chegar. Ao chegar, dá o código de 4 dígitos ao estafeta e a entrega fica confirmada.
 
### C2 — O primeiro turno do Tiago · E1, E5, E6, E7
O Tiago ativa o perfil de estafeta e é aprovado depois de fazer a formação de segurança. Indica que está livre às terças entre as 12h e as 14h. Na terça, liga o estado "disponível", reserva uma e-bike na doca da biblioteca e desbloqueia-a com a app. Recebe um pedido da cantina do Crasto para o edifício 4, aceita-o e recebe logo um segundo pedido para o mesmo edifício. Faz as duas entregas numa só viagem.
 
### C3 — Hora de ponta na cantina do Crasto · E2, E4
Às 12h30, a Fernanda vê no tablet do balcão a fila de pedidos ordenada pela hora de recolha. O prato de peixe acaba, e ela marca-o como esgotado para que deixe de aparecer na app. Quando um pedido fica pronto, marca-o como "pronto". O estafeta chega, mostra o QR e ela entrega o saco certo.
 
### C4 — Jantar na residência que corre mal · E3, E8, E10
A Inês encomenda o jantar para a residência de Santiago, bloco B. O estafeta segue as indicações até à entrada do bloco e faz a entrega, mas falta a sopa. A Inês reporta o problema na app com uma fotografia, e o reembolso parcial é aprovado no próprio dia.
 
### C5 — Fecho do mês · E7, E9, E11
No fim do mês, a Marta abre o painel de indicadores: vê o número de entregas, o tempo médio de entrega e as horas com falta de estafetas. Duas e-bikes estão com a bateria degradada e ela marca-as para manutenção. Gera os pagamentos dos estafetas, e o Tiago vê na app o total do mês com as gorjetas incluídas.
 


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
| **Product Owner** | Edivaldo Bonfim | Representa os stakeholders e conhece o domínio; clarifica as funcionalidades esperadas para a equipa; aceita os incrementos das histórias |
| **QA Engineer** | Alan Marques | Promove as práticas de QA em conjunto com os outros papéis; configura as ferramentas que medem a qualidade; verifica que a equipa segue as práticas acordadas, incluindo as regras de uso de IA |
| **DevOps Master** | Ricardo Rebelo | Responsável pela infraestrutura de desenvolvimento e produção: repositório, pipelines, contentores, ambiente de deployment, bases de dados |
| **Developer** | Todos (Daniel Couto, Ricardo Rebelo, Alan Marques) | Implementa e testa histórias; as contribuições são registadas através de pull requests e commits |

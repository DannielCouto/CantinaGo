# language: pt
@SCRUM-7 @US7.9 @US7.4
Funcionalidade: Telemetria da frota e e-bikes reserváveis

  @BR1 @SCRUM-66
  Cenário: Bateria baixa torna a e-bike não reservável
    Dado que a e-bike "EB-07" está "disponível" com 25 % de bateria
    Quando o simulador envia uma leitura de 18 % para a e-bike "EB-07"
    Então a e-bike "EB-07" não aparece nas e-bikes reserváveis da estação

  @BR1 @SCRUM-67
  Cenário: Leitura acima do mínimo volta a tornar a e-bike reservável
    Dado que a e-bike "EB-07" está "disponível" com 15 % de bateria
    Quando o simulador envia uma leitura de 20 % para a e-bike "EB-07"
    Então a e-bike "EB-07" aparece nas e-bikes reserváveis da estação

  @BR1 @SCRUM-68
  Cenário: E-bike em manutenção não é reservável
    Dado que a e-bike "EB-08" está "manutenção" com 90 % de bateria
    Então a e-bike "EB-08" não aparece nas e-bikes reserváveis da estação

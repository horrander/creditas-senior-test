## System Design - Serviços de Simulação de Empréstimo

### Objetivo

Descrever de maneira detalhada todos os fluxos necessários para que seja realizado a simulação de empréstimo.

[Diagrama completo de arquitetura](https://github.com/horrander/creditas-senior-test/blob/main/docs/diagrama_geral.drawio)

### Orientações de arquitetura

A arquitetura do projeto foi pensado para contemplar solicitações vindas de múltiplos frontend (mobile, web), onde cada uma destas origens possui seu próprio BFF (Backend for Frontend), de forma que as necessidades de cada ambiente possam ser atendidas, permitindo controles de segurança específicos. O BFF será responsável por orquestrar as chamadas para os diferentes serviços internos (Dados de cliente, autenticação, parâmetros, motor de cálculo, etc.)

O fluxo de simulação deverá ser distribuído em diferentes micro-serviços, cada um com uma responsabilidade clara organizados em contextos distintos, informações de clientes, parametros, motor de cálculo, entre outros, de forma que o desenvolvimento possa ser paralelizado em diversas equipes.

Os micro-serviços devem possuir uma arquitetura simples, separação clara de responsabilidades de forma que possuam fácil manutenção, sejam altamente testáveis, com pouco ou nenhum acoplamento, permitindo substituição de tecnologias caso seja necessário.

A separação dos micro-serviços permite que sejam facilmente escaláveis, garantindo a performance do sistema como um todo.

### Diagramas

#### Fluxo geral de simulação

O diagrama abaixo demonstra de forma resumida a separação de responsabilidades entre as diferentes etapas de simulação, tudo orquestrado pelo BFF, de forma que os fluxos podem ser facilmente reordenados e seus retornos podem ser tratados de forma específica para cadas tipo de frontend.

![Desenho simplificado de arquitetura](./geral.jpg)

#### BFF

BFF é um conceito onde um frontend possui um backend específico pra atender suas necessidades, dessa forma o frontend não é responsável pela orquestração da chamada de serviços, isso ajuda a garantir a segurança de todo fluxo, já que os serviços internos não são expostos ao ambiente de externo, além disso qualquer necessidade de reordenação ou acréscimo de algum serviço pode ser feito de forma transparente ao frontend, resumidamente o BFF funciona com um gateway que ordena, orquestra e serve ao frontend.

![Backend for Frontend](./bff.jpg)

Neste diagrama é possível identificar como o BFF orquestra a solicitação de simulação de empréstimo, onde são realizados solicitações em duas etapas distintas

1. Chamada simultânea ao serviço de informações de clientes e parâmetros:
    - **Informações de Clientes:**
        - Retorna infamações relacionadas ao cliente, isso permitirá realizar uma simulação de empréstimo customizado. Exemplos de dados obtidos nessa solicitação
            - Dados Cadastrais: Nome da mãe, idade, endereço, correntista? Agência e banco que possui conta.
            - Dados de riscos de crédito: Score do cliente junto ao mercado, inadimplência, fraudes.
            - Dados financeiros: Financiamentos e empréstimos anteriores, caso o cliente já possua um empréstimo será possível oferecer uma oportunidade de refinanciamento, reduzindo risco de inadimplência.
        - Exemplos de endpoint:
            - GET: /cliente/{cpf}/dados
            - Retorno:
                ```
                    {
                        "nome": "Cliente João",
                        "data_nascimento": "1978-05-24",
                        "cpf": "34556798400",
                        "dados_bancarios": {
                            "correntista": true,
                            "agencia": 5678,
                            "conta": 2353466,
                            "cliente_desde": "2015-09-01"
                        },
                        contratos_emprestimos: [
                            {
                                "numero_contrato": 2465734557544,
                                "data_contrato" "2024-09-23"
                                "valor_total" 345678.56,
                                "numero_parcelas": 60
                                "tipo": "financiamento_veiculo"
                            }
                        ],
                        "risco_financeiro": {
                            "possui_restricao_financeira": false,
                            "politicamente_exposto": false,
                            "score": 782
                        }
                    }
    - **Parâmetros de Sistema:** Dados de parametrização relacionados ao fluxo, como por exemplo tempo limite para aguardar solicitação, features toggles, ativar ou desativar fluxos, etc.
        - Exemplo de endpoint:
            - GET: /parametros/simulacao_emprestimo
            - Retorno:
                ```
                {
                    "tempo_maximo_aguardar_simulacao": 5,
                    "habilitar_notificacao_push: true,
                    "habilitar_notificacao_email: false,
                    "obter_simulacao_cache":  true
                }
        - Obs: Estes dados devem ser obtidos de serviços de outras equipes já expostos.
    
2. Após as duas chamadas anteriores "retornarem" com sucesso, o BFF deverá postar uma mensagem em alguma fila disponível (RabbitMq, Kafka ou outro serviço cloud), com um compilado de informações. Configurar conexão com fila para "aguardar" um tempo pré determinado o retorno da simulação:
    - Caso a simulação retorne sucesso no prazo estipulado o BFF deverá retornar a simulação para o frontend
    - Caso contrário, deverá retornar uma mensagem amigável, solicitando que o cliente aguarde o retorno da solicitação via notificação ou email.
    - Essa abordagem de uso de fila garante que mesmo em caso de falha em algum ponto do fluxo a simulação não será "perdida", já que poderá ser processada em um segundo momento pelos serviços de cálculo, entretanto, adiciona uma camada a mais de custo, configuração e manutenção.

#### Informações de Cliente

![Backend for Frontend](./cliente.jpg)

Partindo do pressuposto que outras equipes já expuseram serviços relacionados a dados de clientes, segue sugestão de diagrama para obter dados cadastrais e financeiros. Estes dados seriam importantes para disponibilizar uma simulação personalizada para o cliente.

#### Motor de Cálculo

![Backend for Frontend](./motor_calculo.jpg)

O motor de cálculo é coração do sistema de simulação de empréstimo, responsável por abrigar toda lógica de validação, cálculo e organização dos dodos.

O módulo está divido nos seguintes micro-serviços/tecnologias:

1. Fila (RabbitMq, Kafka, Amazon SQS, IBM MQ) responsável por realizar a orquestração de entrada e saída de simulações de empréstimo, a abordagem de fila garante a resiliência do sistema por um todo devido sua capacidade de reprocessar chamadas com falha ou que não responderam no tempo esperado, entretanto, essa abordagem costuma aumentar os custos do projeto, além de demandar uma infra-estrutura e mão de obra especializada em alguns casos, um trade off a ser discutido com a equipe.
2. Orquestrador: Responsável por se comunicar com a fila . Essa abordagem de fila garante que a solicitação de simulação seja temporariamente armazenada e processada de acordo com o demanda ou capacidade do sistema, que a qualquer momento podem ter suas réplicas escaladas para garantir a disponibilidade do serviço. O orquestrador é responsável por "remover" uma solicitação da fila e direciona-la para os serviços mais internos do módulo de simulação, ao final do processo, deverá em caso de sucesso devolver uma mensagem de sucesso para a fila.
3. Serviço de Cache: Deverá ser utilizado algum serviço ou banco de dados de cache para armazenar o resultado da simulação, dessa forma o orquestrador deverá primeiramente consultar o banco de cache, caso esteja disponível, para obter uma simulação feita recentemente com as mesmas condições, por exemplo, nos últimos 15 minutos foi feita uma simulação para o mesmo cpf, com o mesmo valor de empréstimo, mesma quantidade de parcelas? Se sim, devolva essa ao invés de iniciar uma outra simulação.
4. Partindo do pressuposto que não há uma simulação recente para os dados recebidos, deve-se iniciar a orquestração dos seguintes fluxos:
    1. Api de Taxas e Faixas: Deverá possuir um banco de dados único com a seguinte estrutura:
        - Tabelas de "Peso dos dados cliente" com uma estrutura de chave-valor (informação-peso)
            - cliente_correntista: 20
            - score_credito_positivo: 10
            - inadimplente: 30
            - pessoa_politicamente_exposta: 15
        - Tabela de faixas de juros de acordo com o peso
            - peso_inicial: int
            - peso_final: int
            - valor_taxa_juros_anual
        - Dessa forma é possível somar e "pesar" os dados do cliente, de forma que quanto maior o peso, menor seria sua taxa de juros, pois de certa forma representa um cliente menos propenso a inadimplência
        - Obs.: Outros pesos e faixas de taxas podem ser adicionados para uma simulação mais precisa, além de permitir uma calibragem a partir do cruzamento de informações futuras, como por exemplo faixas por idade, tempo como cliente, entre outros.
    2. Motor de cálculo: Esse micro-serviço é o coração do módulo de simulação, deverá a partir dos dados do cliente e dos dados obtidos da api de taxas realizar o cálculo dos valores de empréstimo
        - Observações:
            - Utilizar 4 casas decimais para uma maior precisão dos cálculos.
            - Utilizar logs bem estruturados para permitir localizar possíveis erros de cálculo
            - Ter alta cobertura de tests unitários que explorem exaustivamente todos os cenários levantados para garantir que qualquer mudança realizada não impacte a precisão dos serviço.
            - Ser altamente documentada, principalmente as fórmulas matemáticas usadas.
    3. Serviço de persistência de dados de simulação em cache e em storage permanente, essa abordagem permite que os dados sejam armazenados temporariamente para evitar que uma nova simulação para os mesmos dados de entrada seja feita, e ao persistir os dados permanentemente é possível usar estas informações para geração de relatórios e cruzamento de informações, a fim de melhorar cadas vez mais a "calibração" das faixas e taxas aplicadas
        - Obs: Nenhum dos dois tipos de persistência deve ser bloqueante, caso não seja possível armazenar os dados a simulação deverá ser encaminhada ao cliente para evitar que o mesmo desista de contratar o empréstimo devido ao um erro técnico.
    4. Consumir serviço disponível para notificar o cliente via email ou push sobre o resultado de sua simulação, dessa forma mesmo que a simulação leve mais tempo que o esperado ou que o cliente interrompa a simulação ele será notificado o mais breve possível que sua simulação foi concluída.

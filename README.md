## Teste para desenvolvedor Sênior - Creditas

### Objetivo

Aplicação responsável por realizar simulações de empréstimo com base nos dados do cliente.

### Como executar a aplicação

Docker/Podman


1. Via o terminal de sua escolha (Powershell, bash) vá para a pasta raiz do projeto, onde existe o arquivo Dockerfile
2. Build da imagem:
    - docker build -t creditas-test .
3. Executar o container
    - docker run -d -p 8080:8080 --name creditas-test creditas-test:latest
- Obs: Verifique antes se não existe nenhuma outra aplicação em execução "ocupando" a porta 8080.
4. Verifique se o container está em execução através do comando.
    - docker ps -a
5. Obs.: Caso tenha o podman instalado ao invés do docker, substitua o comando docker por podman.

Via IDE ou editor de texto (Injellij/Vs Code)

1. Abra a IDE ou editor de texto de sua escolha e execute o projeto.

Abra o navegador na seguinte url

http://localhost:8080/swagger-ui/index.html

### Exemplos de uso

A aplicação possui os seguintes endpoints:

1. POST: /lote (Simula em lote empréstimos com base nos dados de cliente fornecidos)
    - Realiza uma simulação com base em um array de dados dos clientes, conforme abaixo:

- ```[
        {
            "valorSolicitado": 10000,
            "cpfCliente": "12345678901",
            "numeroParcelas": 12,
            "codigoAlfabeticoMoeda": "BRL"
        },
        {
            "valorSolicitado": 10000,
            "cpfCliente": "98765432100",
            "numeroParcelas": 12,
            "codigoAlfabeticoMoeda": "BRL"
        },
        {
            "valorSolicitado": 10000,
            "cpfCliente": "11223344556",
            "numeroParcelas": 12,
            "codigoAlfabeticoMoeda": "BRL"
        },
        {
            "valorSolicitado": 10000,
            "cpfCliente": "99887766554",
            "numeroParcelas": 12,
            "codigoAlfabeticoMoeda": "BRL"
        }
      ]

2. POST: / (Simula apenas empréstimo com base nos dados fornecidos)

- ```
        {
            "valorSolicitado": 10000,
            "cpfCliente": "99887766554",
            "numeroParcelas": 12,
            "codigoAlfabeticoMoeda": "BRL"
        }

3.  Para que o cálculo funcione, é necessário que o cpf do cliente exista no arquivo data.sql presente na pasta main/resources, pois, para que a simulação seja mais real, foi adicionada uma consulta ao banco de dados dos dados do cliente.

### Arquitetura do projeto

O projeto possui uma arquitetura bem simples, de forma a ser prática e funcional, e se baseia na seguinte separação de responsabilidades:

1. Entrada de dados (Porta de comunicação com o Frontend)
    - Composto por: Controllers, Dtos.
    - Objetivo: Servir de porta de "entrada" e "saída" de comunicação com o frontend, não possui regras de negócio, apenas faz a interlocução das chamadas.

2. Domínio.

    - Composto por: Models, Services, Exceptions.
    - Objetivo: Implementar as regras de negócio da aplicação, responsável por realizar cálculos e validações de negócio, é totalmente agnóstico com relação à tecnologia.

3. Camada de Infraestrutura:

    - Composto por: Repositories, Entities.
    - Objetivo: Abstrair toda a comunicação com o mundo exterior, por exemplo, banco de dados, outras APIs, leitura de arquivos, entre outros. Não deve possuir regras de negócio.

As regras de negócio se concentram nas classes contidas na pasta Model, obedecendo ao princípio da Orientação a Objetos, onde uma classe deve possuir propriedades e comportamentos, tendo os serviços como um orquestrador de fluxo.

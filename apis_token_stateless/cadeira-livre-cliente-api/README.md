# Projeto Cadeira Livre Cliente API

Projeto de Back-end do aplicativo Cadeira Livre Cliente.

## Resumo

O projeto é subdividido em módulos, utiliza arquitetura de API REST e microsserviços e arquitetura em multi-camadas.

### Tecnologias

- **Javascript**
- **ES6 Modules**
- **Express.js**
- **REST API**
- **JWT**
- **MongoDB**
- **RabbitMQ**
- **Mongoose**
- **Arquitetura Multicamadas**
- **Docker**
- **Docker-compose**
- **Jest.js**

### Pré-requisitos

É possível inicializar o projeto localmente via Docker ou rodando a aplicação completa na máquina.

#### Para rodar via Docker com _docker-compose_

Processo de criação:

O projeto está composto por um `Dockerfile`. Serão criados os seguintes `containers`:

```
* cadeira-livre-cliente-db
* cadeira-livre-cliente-api
* cadeira-livre-rabbit
```

Processo de execução:

Para realizar o build das imagens e inicializar todos os containers, basta executar:

`docker-compose up --build`

Para não acompanhar os logs, apenas execute com a flag `-d` ao fim do comando. Para não realizar o build, apenas remova a flag `--build`.

Para parar todos os containers:

`docker-compose stop`

Para apagar todos os containers parados:

`docker container prune`

Em seguida, aperte `y` para confirmar a exlcusão.

Para apagar todas as imagens geradas (caso os containers estejam parados):

`docker image prune`

Em seguida, aperte `y` para confirmar a exlcusão.

Para acompanhar os logs de qualquer container, basta executar:

`docker logs --follow NOME_DO_CONTAINER`

#### Para rodar localmente via Yarn

É necessário ter as seguintes ferramentas para inicializar o projeto:

```
Node
Yarn
Instância local do MongoDB
Instância local do RabbitMQ
```

### Instalação

Primeiramente, rode a instalação através do `yarn`:

```
yarn
```

## Iniciando a aplicação

Após instalar a aplicação, execute:

```
yarn startLocal
```

Existem os seguintes comandos:

`yarn startLocal` -> inicia a aplicação no ambiente local de desenvolvimento
`yarn start` -> inicia a aplicação no ambiente de produção ou de containers. Neste caso, é necessário informar variáveis de ambiente relacionadas às URLs de conexão, são elas:

- **NODE_ENV**: ambiente a ser utilizado.
- **RABBIT_MQ_CONNECTION**: URL de conexão com o RabbitMQ.
- **MONGO_DB_CONNECTION**: URL de conexão com o MongoDB.
- **APPLICATION_SECRET**: secret do JWT da aplicação em base64.

A aplicação estará disponível em:

```
http://localhost:8096
```

## Testes automatizados

A aplicação conta com testes unitários e testes de integração utilizando
a biblioteca Jest.js. Para rodar os testes, basta executar o comando:

`yarn test`

## Deployment

O deploy da aplicação está sendo realizado no Heroku

## Documentação

A API estará sendo documentada com Swagger e poderá ser consultada no endereço:

http://localhost:8096/swagger-ui (local)

https://cadeira-livre-cliente-api.herokuapp.com/swagger-ui (produção)

### Endpoints

![Documentação do Swagger - Endpoints](https://github.com/vhnegrisoli/cadeira-livre-cliente-api/blob/main/imagens/Documenta%C3%A7%C3%A3o%20Swagger.png)

## Autores

- **Victor Hugo Negrisoli** - Desenvolvedor Back-End - [vhnegrisoli](https://github.com/vhnegrisoli)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE](LICENSE)

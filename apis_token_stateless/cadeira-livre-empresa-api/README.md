# Projeto Cadeira Livre Empresa API

[![Build Status](https://travis-ci.com/vhnegrisoli/cadeira-livre-empresa-api.svg?branch=master)](https://travis-ci.com/vhnegrisoli/cadeira-livre-empresa-api)

Projeto de Back-end do aplicativo Cadeira Livre Empresa.

## Resumo

O projeto é subdividido em módulos, utiliza arquitetura de API REST e microsserviços e conta com testes automatizados.

### Tecnologias

* **Java 11**
* **Spring Boot 2**
* **Spring Security**
* **OAuth 2.0**
* **Spring Data JPA**
* **Spring Cloud Open Feign**
* **RabbitMQ**
* **QueryDSL**
* **Redis**
* **PostgreSQL 11**
* **REST API**
* **Testes de Integração e Unitários**
* **JUnit5**
* **Mockito**
* **Banco de dados em memória HSQL para testes de integração**
* **Docker**
* **Docker-compose**

### Pré-requisitos

É possível inicializar o projeto localmente via Docker ou rodando a aplicação completa na máquina.

#### Para rodar via Docker com *docker-compose*

Processo de criação:

O projeto está composto por um `Dockerfile` que contém um `Multi-Stage Build` da aplicação, que roda primeiramente 
uma imagem do `Maven`, que por fim instala todas as dependências necessárias, roda os testes automatizados (unitários e de integração) e
por fim gera um `jar` de execução.
 
O segundo estágio do `Dockerfile` copia o `jar` gerado pelo primeiro estágio ao segundo estágio, que por fim expõe
a porta `8095` e o executa. Há também no projeto um arquivo `docker-compose.yml`, que, ao ser executado, executa os containers:

```   
* cadeira-livre-db
* cadeira-livre-api
* cadeira-livre-redis
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

#### Para rodar localmente via IDE ou Maven

É necessário ter as seguintes ferramentas para inicializar o projeto:

```
Java 11.0.3
Maven
mvn
RabbitMQ
Redis Server
PostgreSQL 11
```

### Code Style

O projeto utiliza o checkstyle da Google e o plugin PMD, sendo assim, cada comando do maven irá rodar o checkstyle.

### Instalação

Primeiramente, rode a instalação através da mvn, sem os testes:

```
mvn clean install -DskipTests
```

***Obs.: a branch master é usada para deploy em produção no Heroku, portanto, é setada a flag skipTests como false no arquivo pom.xml, 
impedindo que os testes sejam ignorados.**

Para realizar a instalação das dependências com os testes, execute apenas:

```
mvn clean install
```

Para construir o jar de execução, execute:

```
mvn package
```

## Iniciando a aplicação

Após instalar a aplicação, dar o build e gerar o jar, basta, na raiz, executar:

```
mvn spring-boot:run
```

Ou então:

```
cd target/java -jar nome_do_jar.jar
```

A aplicação estará disponível em:

```
http://localhost:8095
```

## Executando testes automatizados

Foram escritos testes de integração (banco de dados em memória HQSL) e testes unitários
utilizando o JUnit5 e o Mockito.

Para rodar apenas os testes:

```
mvn test
```

## Deployment

O deploy da aplicação está sendo realizado no Heroku

## Documentação

A API estará sendo documentada com Swagger e poderá ser consultada no endereço:

https://cadeira-livre-empresa-api.herokuapp.com/swagger-ui.html

### Endpoints

![Documentação do Swagger - Endpoints](https://uploaddeimagens.com.br/images/002/773/100/original/Swagger_Docs_Cadeira_Livre.png?1594866289)

### Especificação dos requests e responses

![Documentação do Swagger - Requests e Responses](https://uploaddeimagens.com.br/images/002/773/102/original/Swagger_Docs_Cadeira_Livre_2.png?1594866388)

## Autores

* **Victor Hugo Negrisoli** - *Desenvolvedor Back-End Pleno* - [vhnegrisoli](https://github.com/vhnegrisoli)

## Licença

Este projeto possui a licença do MIT. Veja mais em: [LICENSE.txt](LICENSE.txt)


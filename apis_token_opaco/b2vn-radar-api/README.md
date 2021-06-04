# b2vn-radar-api
Back-end de autenticação do projeto B2VN utilizando Java 11, Spring Boot, Spring Security, OAuth2.0, Spring Cloud OpenFeign, QueryDSL e PostgreSQL 11.


## Exigências

Para o desenvolvimento, você precisará apenas do JAVA instalado em seu ambiente de trabalho.

### Java

O [Java](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) é realmente fácil de instalar.
Você poderá executar o seguinte comando após o procedimento de instalação abaixo.

    $ java -version
    java version "11.0.5"

## Instalação (Local)

    $ git clone https://github.com/vhnegrisoli/b2vn-radar-api.git
    $ cd b2vn-radar-api
    
### Build no projeto localmente

    $ mvn clean install

### Rodando o projeto localmente

Na raiz da aplicação: 

    $ mvn spring-boot:run

ou

    $ cd /target
    $ java -jar b2vn-radar-api.jar



## Instalação (Docker)

Para começar, como são microsserviços, é necessário ter todos os projetos.

    $ git clone https://github.com/vhnegrisoli/b2vn-auth-api.git
    $ git clone https://github.com/vhnegrisoli/b2vn-radar-api.git
    $ git clone https://github.com/Noninus/b2vn-front.git

###  Criar network b2vn
    $ docker network create b2vn

### Build dos projetos
    $ docker image build -t b2vn-auth-api .
    $ docker image build -t b2vn-radar-api .
    $ docker image build -t b2vn-front .

### Criar o container com o parametro --name para especificar o network
    $ docker container run --network b2vn --name auth -p 8080:8080 -d b2vn-auth-api
    $ docker container run --network b2vn --name radar -p 8081:8081 -d b2vn-radar-api
    $ docker container run --network b2vn --name front -p 3000:80 b2vn-front



### Criar base de dados PostgreSQL para B2VN Auth API

    $ docker pull postgres

    $ docker pull dpage/pgadmin4

    $ docker run --name b2vn-db --network b2vn -e "POSTGRES_PASSWORD=b2vn-auth" -p 5432:5432 -v C:\db -d postgres

    $ docker run --name pgadmin4 --network b2vn -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=victorhugonegrisoli.ccs@gmail.com" -e "PGADMIN_DEFAULT_PASSWORD=b2vn-auth" -d dpage/pgadmin4]
    
### Acesso à base de dados

    Acesso em: localhost:15432

    host: b2vn-db
    port: 5432
    db: postgres
    username: postgres
    password: b2vn-auth

### Script de criação da base de dados e inserção dos dados iniciais

    CREATE DATABASE b2vn
        WITH 
        OWNER = postgres
        ENCODING = 'UTF8'
        LC_COLLATE = 'en_US.utf8'
        LC_CTYPE = 'en_US.utf8'
        TABLESPACE = pg_default
        CONNECTION LIMIT = -1;

    CREATE TABLE public.log (
        id int4 NOT NULL,
        usuario_id int4 NOT NULL,
        usuario_nome varchar(120) NOT NULL,
        usuario_email varchar(120) NOT NULL,
        usuario_permissao varchar(120) NOT NULL,
        usuario_descricao varchar(120) NOT NULL,
        tipo_operacao varchar(15) NOT NULL,
        data_acesso timestamp NOT NULL,
        metodo varchar(10) NOT NULL,
        url_acessada varchar(255) NOT NULL,
        servico_nome varchar(20) NOT NULL,
        servico_descricao varchar(20) NOT NULL,
        CONSTRAINT log_pkey PRIMARY KEY (id)
    );

    CREATE TABLE public.permissao (
        id int4 NOT NULL,
        descricao varchar(255) NOT NULL,
        codigo varchar(10) NULL,
        rate_limit int4 NULL,
        CONSTRAINT permissao_pkey PRIMARY KEY (id)
    );

    CREATE TABLE public.usuario (
        id int4 NOT NULL,
        cpf varchar(14) NULL,
        data_cadastro timestamp NOT NULL,
        email varchar(255) NOT NULL,
        nome varchar(120) NOT NULL,
        senha varchar(255) NOT NULL,
        ultimo_acesso timestamp NOT NULL,
        fk_permissao int4 NOT NULL,
        CONSTRAINT usuario_pkey PRIMARY KEY (id),
        CONSTRAINT fkqa04ym7nxtsl5oyyw205c4l2q FOREIGN KEY (fk_permissao) REFERENCES permissao(id)
    );

    INSERT INTO public.permissao (id,descricao,codigo,rate_limit) VALUES 
    (1,'Administrador','ADMIN',5)
    ,(2,'Usuário','USER',2)
    ;

    INSERT INTO public.usuario (id,cpf,data_cadastro,email,nome,senha,ultimo_acesso,fk_permissao) VALUES 
    (1,'103.324.589-54','2019-09-19 00:00:00.000','admin@admin.com','Usuário Administrador','$2a$10$NL.sPHyZeR/4Iu8hgbbWSej6qe89F96vwXLnagIDROvS8OShM5ase','2019-11-17 21:21:26.928',1);

    CREATE SEQUENCE public.hibernate_sequence
        INCREMENT BY 1
        MINVALUE 1
        MAXVALUE 9223372036854775807
        START 10038
        CACHE 1
        NO CYCLE;


## Funcionalidades
 - Paginação nas consultas para melhoras a eficiência  
 - Token de segurança (Oauth2)
 - Log para controle de acesso dos usuários
 - Sistema [RBAC](https://docs.microsoft.com/pt-br/azure/role-based-access-control/overview) (Usuários, administradores, configurações de acesso, permissões)
 - Rate Limiting configurável e baseado no RBAC
 - API baseada em microsserviços: Autenticação, Radares, Trajetos e Controles
 - Saídas em JSON e XML
 - Exportação para CSV
 - Visualização dos radares por lote no Mapa
 - Visualização das consultas em formato de tabelas paginadas


## Arquitetura

A arquitetura utilizada é a [REST](http://www.matera.com/blog/post/quais-os-beneficios-da-arquitetura-rest), um protocolo utilizado na integração de Web Services, e estes, são soluções utilizadas para integração e comunicação entre sistemas. Como uma abstração da arquitetura HTTP

![RESTful example](http://www.matera.com/br/wp-content/uploads/2018/06/RESTful-Service-Client-Example-Crunchify-Tutorial.png)

### Microsserviços

[Microsserviços](https://www.redhat.com/pt-br/topics/microservices/what-are-microservices) são uma abordagem de arquitetura para a criação de aplicações. O que diferencia a arquitetura de microsserviços das abordagens monolíticas tradicionais é como ela decompõe a aplicação por funções básicas. Cada função é denominada um serviço e pode ser criada e implantada de maneira independente. Isso significa que cada serviço individual pode funcionar ou falhar sem comprometer os demais.

![microservices](https://www.redhat.com/cms/managed-files/monolithic-vs-microservices.png)

Portanto, um microsserviço é uma função essencial de uma aplicação e é executado independentemente dos outros serviços. Nós escolhemos sua utilização pois torna o projeto mais seguro e eficaz na questão da escalabilidade do mesmo.


## Documentos da API

### Diagrama de caso de uso

O [Diagrama de Caso de Uso](https://medium.com/operacionalti/uml-diagrama-de-casos-de-uso-29f4358ce4d5) descreve as funcionalidades propostas pelo PhiloPDV e é uma excelente ferramenta para o levantamento dos requisitos funcionais do sistema.interações com elementos externos e entre si.

![Caso de Uso - B2VN](https://uploaddeimagens.com.br/images/002/513/934/full/caso-uso.jpeg?1574080556)

### Diagrama de implantação

O [Diagrama de Implantação](https://www.lucidchart.com/pages/pt/o-que-e-diagrama-de-implementacao-uml) descreve a implementação física de informações geradas pelo programa de software em componentes de hardware.

![Implantação - B2VN](https://i.ibb.co/JByjgDR/Diagrama-de-Implanta-o.png)

## Documentação (Swagger)

### Documentação da autenticação
http://localhost:8080/swagger-ui.html

### Documentação dos radares
http://localhost:8081/swagger-ui.html


## Teste de Estresse

O [JMeter](https://jmeter.apache.org/download_jmeter.cgi) é uma ferramenta desenvolvida totalmente em Java pelo grupo Apache com o objetivo de realizar testes de carga e stress, a seguir faço um roteiro para realização de um teste de stress simples.

1- Primeiramente devemos baixar o JMeter 5.2, para isso [clique aqui](https://jmeter.apache.org/download_jmeter.cgi).

2- Descompactar o conteúdo do arquivo em uma pasta de sua preferência.

3- Inicie a ferramenta executando o jar $pastajmeter/bin/ApacheJMeter.jar.

![Stress Test - B2VN](https://i.ibb.co/hWwYtWx/stress-test.jpg)


## Linguagens & Ferramentas

### JAVA

- [Spring Boot](https://spring.io/projects/spring-boot) é o framework do projeto.
- [Spring Security](https://spring.io/projects/spring-security) é uma estrutura que fornece autenticação, autorização e outros recursos de segurança para aplicativos corporativos
- [Oauth2](https://oauth.net/2/) é o protocolo para autorização. 
- [QueryDSL](http://www.querydsl.com/) é utilizado para simplificação de consultas ao banco de dados.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) implementa facilmente o JPA.
- [PostgreSQL](https://www.postgresql.org/) como banco de dados.
- [OpenFeign](https://github.com/OpenFeign/feign) para realizar os feign clients.
- [Spring Cloud](https://spring.io/projects/spring-cloud) para ferramentas voltadas a microsserviços.

### JavaScript

- [React Map GL](https://uber.github.io/react-map-gl/) é utilizado para visualização do mapa.
- [React](http://facebook.github.io/react) é usado para a interface do usuário.
- [axios](https://github.com/axios/axios) é utilizado para o client HTTP.
- [sweetalert2](https://sweetalert2.github.io/) é utilizado para os alertas e mensagens ao usuário.
- [react-table](https://www.npmjs.com/package/react-table) para a visualizações dos dados nas tabelas.
- [react-chartjs-2](https://github.com/jerairrest/react-chartjs-2) utilizado para gerar gráficos dinâmicos.


## Apresentação

### Telas (site)

Nessa seção é possível visualizar algumas interfaces e algumas funcionalidades já implementadas no site (front-end).
Na API é possível executar serviços de comparações, com visualização gráfica, e outras relações.

#### Tela de Login

![login](/imagens/login.jpeg)

#### Tela de Cadastro

![cadastro](/imagens/cadastro.jpeg)

#### Tela Inicial

![home](/imagens/home.jpeg)

#### Tela do passo a passso para conseguir um Token (Oauth2)

![token](/imagens/token.jpeg)

#### Tela do mapa de radares separados por cores

É possível navegar no mapa e escolher um radar para ver mais informações.

![mapa](/imagens/mapa.jpeg)

Ao clicar sobre um radar, aparecerá um popup indicando os parâmetros do mesmo.

![mapa_clicado](/imagens/mapa_clicado.jpeg)

#### Tela consulta das localizações

![localizacoes](/imagens/localizacoes.jpeg)

#### Tela consulta da acurácia dos radares

![acuracia](/imagens/acuracia.jpeg)

#### Tela consulta das velocidades médias

![velocidade_media](/imagens/velocidade_media.jpeg)

#### Tela consulta da distância entre radares

![distancia_radares](/imagens/distancia_radares.jpeg)

#### Tela para exportação de CSV e PDF

![exportacao](/imagens/exportacao.jpeg)

#### Tela para visualização dos usuários do sistema e suas permissões

Nessa tela é possível ver todos os usuários e também conceder a permissão de administrador pelo CPF.

![usuarios_list](/imagens/usuarios_list.jpeg)

### Telas (swagger)

Nessa seção é possível visualizar algumas URLs desenvovildas do sistema e mostrar, seu retorno e suas exeções.

#### Tela para visualização dos métodos da API de radares

![api_radares](/imagens/api_radares.jpeg)

#### Tela para visualização dos métodos da API de radares

![api_radares_2](/imagens/api_radares_2.jpeg)

### Autores

- [Rafael Nonino Filho](https://www.linkedin.com/in/rafael-nonino-filho-17977b15b) - Desenvolvedor Front-end
- [Victor Hugo Negrisoli](https://www.linkedin.com/in/victorhugonegrisoli) - Desenvolvedor Back-end
 

### Licença de uso

Este projeto está licenciado sob a licença MIT(Instituto de Tecnologia de Massachusetts).
Para mais informações consulte nossa ![LICENSE](LICENSE)

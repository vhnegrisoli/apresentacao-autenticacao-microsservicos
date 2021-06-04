# b2vn-auth-api
Back-end de autenticação do projeto B2VN utilizando Java 11, Spring Boot, Spring Security, OAuth2.0, Spring Cloud OpenFeign, QueryDSL e PostgreSQL 11.

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

## Exigências

Para o desenvolvimento, você precisará apenas do JAVA instalado em seu ambiente de trabalho.

### Java

O [Java](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) é realmente fácil de instalar.
Você poderá executar o seguinte comando após o procedimento de instalação abaixo.

    $ java -version
    java version "11.0.5"

## Instalação (Local)

    $ git clone https://github.com/vhnegrisoli/b2vn-auth-api.git
    $ cd b2vn-auth-api
    
### Build no projeto localmente

    $ mvn clean install

### Rodando o projeto localmente

Na raiz da aplicação: 

    $ mvn spring-boot:run

ou

    $ cd /target
    $ java -jar b2vn-auth-api.jar
    

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


### Funcionalidades
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

### Autores

- [Rafael Nonino Filho](https://www.linkedin.com/in/rafael-nonino-filho-17977b15b) - Desenvolvedor Front-end
- [Victor Hugo Negrisoli](https://www.linkedin.com/in/victorhugonegrisoli) - Desenvolvedor Back-end
 

### Licença de uso

Este projeto está licenciado sob a licença MIT(Instituto de Tecnologia de Massachusetts).
Para mais informações consulte nossa ![LICENSE](LICENSE)
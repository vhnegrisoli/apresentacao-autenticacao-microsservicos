# Apresentação: Autenticação Stateful vs Stateless entre Microsserviços

Projeto para usar como base para a apresentação "Autenticação Stateful vs Stateless na Comunicação entre Microsserviços"

### Conteúdo geral abordado

* **Conceitos**
* **Vantagens e desvantagens**
* **Arquitetura de cada modelo**
* **Quando usar cada modelo**
* **Exemplos práticos**

### 01 - Breve Resumo

O estado de uma aplicação (ou de qualquer outra coisa, na verdade) é a condição ou a qualidade dela em um determinado momento: é seu estado de existência. Para determinar se algo é stateful ou stateless, basta considerar o tempo em que seu estado de interação é registrado e como essas informações precisam ser armazenadas. 

### 02 - Autenticação stateful

A autenticação stateful é comumente usada em muitas aplicações, especialmente para as que não exigem muita escalabilidade. 

A sessão com estado é criada no lado do back-end e a ID de referência da sessão correspondente é enviada ao cliente. 

Cada vez que o cliente faz uma solicitação ao servidor, o servidor localiza a memória da sessão usando o ID de referência do cliente e encontra as informações de autenticação. 

Nesse modelo, você pode facilmente imaginar que, se a memória da sessão for excluída no lado do back-end, o ID de referência da sessão, que o cliente está segurando, não terá mais sentido, bloqueando seu acesso. 

#### 2.1 - O Token Opaco da Autenticação Stateful

Os tokens de acesso opacos são tokens em um formato proprietário, em que não é possível acessar, e, normalmente, contêm algum identificador para acessar informações persistidas em algum servidor de armazenamento. Para validar um token opaco, o destinatário do token precisa chamar o servidor que emitiu o token.

#### 2.2 - Vantagens do uso de um Token Opaco

* Lógica de implementação apenas em um local.
* Gestão e controle de acessos simplificados.
* Excelente para aplicações MVC, monolíticas, processos internos.
* Maior complexidade de tentativas de autenticações por fora da aplicação.
* Terceiros mal-intencionados possuem maior dificuldade para encontrar brechas de segurança.

A facilidade em controlar acessos é definida pelo pressuposto de que os dados do usuário estão em algum sistema de armazenamento (ex: Redis, MongoDB, SGBDs, etc), e não codificados no token. Neste modelo, o token é apenas um identificador.

#### 2.3 - Desvantagens do uso de um Token Opaco

* Pode ocorrer estresse da API responsável por realizar a validação do token dependendo do número de serviços que necessitam validação.
* Falha no quesito escalabilidade. 
* Maior dificuldade ao distribuir a autenticação entre microsserviços.
* Em uma aplicação distribuída, se a API de autenticação cai, todos os serviços ficam indisponíveis.
* Maior complexidade de implementação, pois exige, além da lógica de segurança e validação do token, um servidor de armazenamento para os dados (ex: Redis).
* Maior complexidade de integração com sistemas terceiros.

#### 2.4 - Exemplo de uma arquitetura de microsserviços com autenticação stateful:

![Exemplo Arquitetura Stateful](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Arquitetura%20Token%20Opaco.png)

### 03 - Autenticação stateless

* Uma aplicação ou processo stateless são recursos isolados. Nenhuma referência ou informação sobre transações antigas são armazenadas, e cada uma delas é feita do zero. 

* A autenticação stateless armazena os dados no lado do cliente (navegador). Os dados são assinados por uma chave chave para garantir a integridade e autoridade dos dados da sessão.

* Como a sessão do usuário é armazenada no lado do cliente, o servidor só tem a capacidade de verificar sua validade verificando se o payload e a assinatura correspondem.

#### 3.1 - O Token JWT da Autenticação Stateless

Os tokens de acesso JSON Web Token (JWT) estão em conformidade, segundo os padrões estabelecidos pelo JWT na RFC-7519, e contêm informações sobre uma entidade na forma de declarações. Eles são independentes, portanto, não é necessário que o destinatário chame um servidor para validar o token.

#### 3.2 - Vantagens do uso de um Token JWT

* Baixo consumo de memória do servidor.
* Excelente no quesito escalabilidade.
* Excelente para aplicações distribuídas, APIs, microsserviços.
* Maior facilidade para uso da aplicação por integrações de sistemas terceiros.
* A geração e distribuição de tokens fica em uma aplicação isolada, que não é uma dependência de outras aplicações ao validar este token.
* Facilidade de validação dos dados do usuário pertencente ao token em cada aplicação, não necessitando um cliente com a API de autenticação ou de implementação de um servidor de armazenamento em cada aplicação para recuperar os dados do usuário.

#### 3.3 - Desvantagens do uso de um Token JWT

* Dificuldade de controle de acessos.
* Não é possível revogar o token com facilidade ou a qualquer momento.
* Pode tornar mais fácil a entrada de terceiros mal-intencionados caso alguém tenha contato com o token, ex: manter dados sensíveis no payload do token.
* A sessão não pode ser alterada até o fim de seu tempo de expiração. Em caso de um vazamento do token, não será possível proibir o acesso até que ele fique inválido.
* Mais complexo e desnecessário de se implementar para aplicações centralizadas como MVC ou monolíticas.

#### 3.4 Exemplo de uma arquitetura de microsserviços com autenticação stateless:

![Exemplo Arquitetura Stateless](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Autentica%C3%A7%C3%A3o%20Token%20JWT.png)

### 04 - Quando utilizar cada abordagem?

Token Opaco:

* Preciso ter total controle dos acessos dos meus usuários, principalmente definir hierarquia de acessos. Exemplo: Roles de Vendedor, Coordenador, Gerente, Diretor.
* Preciso, sempre que necessário, deslogar um usuário.
* Minha aplicação é centralizada, apenas um back-end e um front-end se comunicando, sem serviços distribuídos e sem integrações terceiras.
* Estou desenvolvendo uma aplicação para acesso interno dos funcionários para definir o que cada um poderá acessar dos sistemas internos da organização.* 

Token JWT:

* Preciso apenas identificar qual usuário está realizando uma determinada ação no sistema nos diferentes serviços.
* Tenho várias comunicações distribuídas entre serviços.
* Quero ter maior performance sem me preocupar com estresse na API por conta de técnicas de autenticação.
* Não pretendo nunca persistir informações relativas ao usuário, apenas seu registro inicial.
* Preciso gerar acessos externos a outros serviços.
* Preciso limitar um tempo determinado de acesso através de um token.
* Preciso de facilidade para manipular os dados de quem está realizando tal ação com o mínimo de impacto.

### 05 - Questões discutidas na comunidade

* Validar autenticação a cada request via endpoint (stateful) ou na própria aplicação (stateless)?
* Quem deve assumir a responsabilidade de verificar os credenciais do token? Cada aplicação individual (ou biblioteca criada para isso) ou a própria API de autenticação?
* Controle de sessão, login  e acessos com JWT é possível, mesmo que parcialmente?
* Vemos muitas pessoas implementando black-lists com JWT (eu já fiz isso) para bloquear alguma ação. Isso não estaria quebrando o conceito de stateless? Nesse caso, por quê utilizar o JWT?
* Tenho uma aplicação distribuída que, a cada request, valida o token na API de autenticação. Terei problemas por isso? Estou ferindo o conceito do REST?

### 06 - Exemplo prático de autenticação stateful e stateless com arquitetura de microsserviços

#### 6.1 - Arquitetura da aplicação B2VN Radares São Paulo

![B2VN Radares Arquitetura](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/B2VN%20Arquitetura%20Token%20Opaco.png)

#### 6.2 - Arquitetura dos apps mobile Cadeira Livre Cliente e Cadeira Livre Empresa:

![Cadeira Livre Arquitetura](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Cadeira%20Livre%20App%20Arquitetura.png)

### Referências

* https://auth0.com/docs/tokens/access-tokens
* https://tools.ietf.org/html/rfc7519
* https://jwt.io/introduction
* https://docs.mashery.com/connectorsguide/GUID-3812EE8B-3770-445C-83F2-FB6D1D54C18A.html
* https://medium.com/@piyumimdasanayaka/json-web-token-jwt-vs-opaque-token-984791a3e715
* https://fusionauth.io/learn/expert-advice/tokens/pros-and-cons-of-jwts/
* https://www.redhat.com/pt-br/topics/cloud-native-apps/stateful-vs-stateless
* https://github.com/facg3/Stateless-vs-stateful-authentication
* https://medium.com/@kennch/stateful-and-stateless-authentication-10aa3e3d4986

### URLs de teste das aplicações:

URLs para servirem de exemplo prático dos tipos de autenticação.

#### B2VN Radares

Auth-API:

https://b2vn-auth-api.herokuapp.com/

Radar-API:

https://b2vn-radar-api.herokuapp.com/

#### Cadeira Livre

Cadeira-Livre-Empresa-API:

https://cadeira-livre-empresa-api.herokuapp.com/

Cadeira-Livre-Cliente-API:

https://cadeira-livre-cliente-api.herokuapp.com/

Integração-Pagarme-API:

https://integracaopagarme-api.herokuapp.com/

### Gerar token nas aplicações via cURL

Para gerar um token na API B2VN-Auth-API, basta executar o seguinte cURL:

```shell
curl -i -X POST \
   -H "Content-Type:application/x-www-form-urlencoded" \
   -d "grant_type=password" \
   -d "client_id=b2vn-auth-api-client" \
   -d "client_secret=b2vn-auth-api-secret" \
   -d "username=victorhugonegrisoli.ccs@gmail.com" \
   -d "password=123456" \
 'https://b2vn-auth-api.herokuapp.com/oauth/token'
```

Para gerar um token na API Cadeira-Livre-Empresa-API, basta executar o seguinte cURL:

```shell
curl -i -X POST \
   -H "Content-Type:application/x-www-form-urlencoded" \
   -d "grant_type=password" \
   -d "client_id=cadeira-livre-empresa-api-client" \
   -d "client_secret=cadeira-livre-empresa-api-secret" \
   -d "username=victorhugonegrisoli.ccs@gmail.com" \
   -d "password=123456" \
 'https://cadeira-livre-empresa-api.herokuapp.com/oauth/token'
 ```
 
Para gerar um token na API Cadeira-Livre-Cliente-API, basta executar o seguinte cURL:

```shell
curl -i -X POST \
   -H "Content-Type:application/json" \
   -d \
'{
  "email":"vhnegrisoli@gmail.com",
  "senha":"123456"
}' \
 'https://cadeira-livre-cliente-api.herokuapp.com/api/auth/token'
```

### Gerar token nas aplicações via Swagger

B2VN Auth API:

https://b2vn-auth-api.herokuapp.com/swagger-ui.html#/auth-controller/generateAccessTokenUsingPOST

Cadeira-Livre-Empresa-API:

https://cadeira-livre-empresa-api.herokuapp.com/swagger-ui.html#/auth-controller/generateAccessTokenUsingPOST

Cadeira-Livre-Cliente-API:

https://cadeira-livre-cliente-api.herokuapp.com/swagger-ui.html/#/Autentica%C3%A7%C3%A3o/autenticarUsuario

### Autor

#### Victor Hugo Negrisoli
#### Desenvolvedor Back-End

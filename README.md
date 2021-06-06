# Apresentação: Autenticação Stateful vs Stateless entre Microsserviços

Projeto para usar como base para a apresentação "Autenticação Stateful vs Stateless na Comunicação entre Microsserviços"

### Conteúdo geral abordado

##### Tokens JWT e Token Opaco

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

#### 2.4 - Exemplo de uma arquitetura:

![Exemplo Arquitetura Stateful](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Arquitetura%20Token%20Opaco.png)

### 03 - Autenticação stateless

Exemplo de uma arquitetura:

![](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Autentica%C3%A7%C3%A3o%20Token%20JWT.png)

### Gerar token nas aplicações

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

### Autor

#### Victor Hugo Negrisoli
#### Desenvolvedor Back-End

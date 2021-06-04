# Apresentação: Autenticação Stateful vs Stateless entre Microsserviços

Projeto para usar como base para a apresentação "Autenticação Stateful vs Stateless na Comunicação entre Microsserviços"

### Comunicação Stateful - Autenticação com Token Opaco

Exemplo de uma arquitetura:

![](https://github.com/vhnegrisoli/apresentacao-autenticacao-microsservicos/blob/main/imagens/Arquitetura%20Token%20Opaco.png)

### Comunicação Stateless - Autenticação com Token JWT

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

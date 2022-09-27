# API-TODO
API Rest de Gerenciamento de tarefas, utilizando para JWT.

Contatos:
- Linkedin: https://www.linkedin.com/in/welson-júnior-25b190193/
- Email : welsongt9@live.com


### Tecnologias utilizadas
- Java
- Banco de dados SQL
- Framework Spring

### Como utlizar o projeto
- O projeto está configurado para rodar com o banco de dados MySQL, como o nome de db_tarefas.
- A porta do banco é a padrão do MySQL localhost:3306.
- A o configurar o banco de dados no arquivo application.properties, as tabelas e relacionamentos serão criados pela JPA.
- Alguns dados já serão persistidos do banco de dados ao iniciar a aplicação.
- A o iniciar a aplicação alguns dados já estão sendo

  ![](https://github.com/welsonjnr/API-TODO/blob/main/img/Sem%20título.png)
  > Dados persistidos.

### Como autenticar para fazer a requisição
- O projeto utiliza para a autenticação o JWT com sistema de token de acesso e refresh Token.
- O Acess Token expira a cada 30 minutos, mas o refresh token a cada um ano.
- Para conseguir o seu acess_token e o refresh_token. Você precisa enviar uma requisição com o email e senha do usuário.
- URL para fazer o login: "http://localhost:8080/api/login"
  
  ![](https://github.com/welsonjnr/API-TODO/blob/main/img/tokens_usuario.png)
  > Tokens de acesso.

- Com os tokens do usuário para fazer as requisições, você deve colocar o token no cabeçalho das requições com a Key Authorization.
- Colocando no valor da key "Bearer acess_token".
  
  ![](https://github.com/welsonjnr/API-TODO/blob/main/img/Cabecalho%20campo%20Authorization.png)
  > Exemplo.

  - Quando o acess_token expirar, com os dados do refresh token em maõs, você deve enviar uma requisição para conseguir seu novo de token de acesso.
  - URL para fazer o refresh: "http://localhost:8080/api/usuario/token/refresh"
  - Enviar no cabeçalho a Key Authorization e no valor da key "Bearer refresh_token".

  ![](https://github.com/welsonjnr/API-TODO/blob/main/img/Cabecalho%20refresh%20token.png)
  > Exemplo refresh token.

- Após conseguir os tokens do usuário, você está livre para fazer as requisições na API(ver os endpoints no Swagger).

### Swagger
- O projeto conta com a API do Swagger para ter acessos documentado aos endpoints.
- No Swagger é possível visualizar todos os endpoints criados e os exemplos de como enviar as requisições.
- link para acessar o swagger : "http://localhost:8080/swagger-ui.html#/"

![](https://github.com/welsonjnr/API-TODO/blob/main/img/Exemplo%20Swagger.png)
> Exemplo Swagger.

- No Swagger também é possível colocar o token de acesso para fazer as requisições autenticadas.
- Sendo necessário apenas colocar no campo de Authorize "Bearer acess_token".

![](https://github.com/welsonjnr/API-TODO/blob/main/img/Authorize%20para%20requisicao%20autenticada.png)
> Exemplo Requisição Swagger.




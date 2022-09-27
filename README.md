# API-TODO
API Rest de Gerenciamento de tarefas, utilizando para JWT.

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
  
  ![](https://github.com/welsonjnr/API-TODO/blob/main/img/Sem%20título.png)
  > Tokens de acesso.

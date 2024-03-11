# tech-challenge-product-api

### Subindo o ambiente
Para rodar o ambiente completo (banco e aplicação) é necessário apenas rodar o comando `docker compose up -d`.
Caso já tenha as imagens criadas e queira atualizar o build para uma nova versão, execute o mesmo comando passando o parâmetro --build no final. Ex: `docker compose up -d --build`.

Dessa forma o docker irá subir 2 containers rodando a aplicação e o banco de dados. Os containers são acessível através da network do docker.

### Rodando a aplicação isoladamente
A imagem utilizada para executar a aplicação foi baseada na JDK 21. Para criar a imagem docker, deve-se abrir um terminal na raiz do projeto e executar o comando:
`docker build -t <TAG_DA_IMAGEM> . `
O dockerfile da aplicação irá copiar todo o conteúdo do repositório para a imagem e irá fazer o build da aplicação, gerando o executável app.jar

O build da aplicação é feito dentro do container para garantir que o ambiente de build seja o mais próximo possível do ambiente no qual a aplicação irá ser executada.

Para rodar a aplicação, basta executar o comando
`docker run -d -p 8080:8080 --name <NOME_DO_CONTAINER> <TAG_DA_IMAGEM>`.
Dessa forma um container será criado já com a aplicação rodando na porta 8080.

Para testar a execução, faça a seguinte chamada http:
`curl --location --request GET 'http://localhost:8080/tech-challenge-product/health'`.
A aplicação deve responder com um status 200.

### Rodando o banco de dados isoladamente
A imagem utilizada para executar a aplicaçao foi baseada na imagem oficial do mariaDb. Para criar a imagem docker, deve-se abrir um terminal e navegar até a pasta **/database** do projeto e executar o seguinte comando:
`docker build -t <TAG_DA_IMAGEM> . `.

O dockerfile irá configurar a senha do usuário root e também irá copiar o arquivo **setup.sql** para a pasta **/docker-entrypoint-initdb.d** dentro do container. Todos os arquivos **.sql**, **.sh**, etc... localizados na pasta **docker-entrypoint-initdb.d** são executados automaticamente quando o container é iniciado.

No arquivo **setup.sql** estão as configurações básicas necessárias para a aplicação conectar no banco de dados. Ao executar o arquivo, será criado a database utilizada pela aplicação, o usuário/senha que a aplicação utilizará para acessar o banco e as permissões necessárias para que o usuário acesse a database da aplicação.

Para rodar o container, basta executar o comando `docker run -d -p 3606:3606 --name <NOME_DO_CONTAINER> <TAG_DA_IMAGEM>`.

### Imagens no dockerhub
**Aplicacão**: docker pull gcamargo1997/tech-challenge-product-api:latest
**Banco de dados**: docker pull gcamargo1997/tech-challenge-product-db:latest
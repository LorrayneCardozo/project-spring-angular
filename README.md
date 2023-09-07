# Projeto Fullstack - Spring + Angular

## Backend
### Tecnologias utilizadas
- MySQL
- Maven 3.6.3
- Spring Boot 2.6.1
- Swagger

### Como rodar
Primeiramente, criar a base de dados no MySQL com o comando `CREATE DATABASE base_de_dados;` e definir no `application.properties` o nome da sua base, assim como o usuário e a senha.
```
spring.datasource.url=jdbc:mysql://localhost:3306/base_de_dados
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.mvc.pathmatch.matching-strategy=ant-path-matcher 

jwt.expiration=3600000
```
Em seguida, executar o projeto na pasta do backend utilizando maven com o comando:
```bash
cd backend
mvn spring-boot: run
```
O projeto será executado na porta `8080`, e a API pode ser testada com o Swagger na URL `http://localhost:8080/swagger-ui.html#/`.

## Frontend
### Tecnologias utilizadas
- Node 18.10.0
- NPM 8.19.2
- Angular 16.2.0

### Como rodar 
No arquivo `environment.ts`, defina a URL da api:
```ts
export const environment = {
    production: false,
    apiUrl: 'URL_DA_API'
  };
```

Após executar o projeto no terminal na pasta do frontend com o comando abaixo, o projeto estará na porta `http://localhost:4200`:
```bash
cd frontend
ng serve
```

## Utilizando Docker
Para executar o projeto utilizando docker, basta executar na raiz do projeto:
```bash
docker-compose up --build
```
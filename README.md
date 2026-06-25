# Kurs_5_Grupp_Projekt_K5 - Microservices-arkitektur
Deltagande i Grupp-Projektet:

*Jonathan Isaksson, Konstantinos Nascos, Felix Lidén, Rasha Knifdi, Jonatan Emil Dahl, Markus Nikolic*

### För att starta programmen:

#### Manuellt
1. Starta service-registry
2. sen starta microservices: `author-service`, `book-service`, `loan-service`
3. sist starta api-gateway(alla går att starta självständigt)

#### Docker
Bygg microservices till Docker
```Bash
docker compose up --build
```

### Vi har använt:
- OpenFeign
- Swagger
- JWT
- Ratelimiting
- H2
- Eureka
- Github Repository
- Github Actions
- Service Registry
- API Gateway
- Spring Security
- Spring Boot
- Spring Web
- MockMvc
- Docker
- TestContainers

---

# Kort om varje microservice visar:

## Api-gateway - Jonathan Isaksson:
#### port: `localhost:8080`
Exempel:
```http
http://localhost:8080/api/v1
```
Api-gateway är en frontdörr till att komma åt alla microservices. Man behöver inte komma ihåg varje microservice egna port, istället ska allt skötas automatiskt härifrån. Säkerhet hanteras central härifrån med ratelimiting och jwt som skickar tokens till microservices vid behov.

## JWT - Jonathan Isaksson
#### port: `localhost:8080`
```http
POST http://localhost:8080/auth/login
```
Logga in via länken ovan. Det finns **Admin** och **User**, du kan logga in via antingen Swagger eller Postman. Genererar en token som ger dig åtkomst till tjänsterna. Olika roller ger olia privilegier. Auth Token krävs vid användning av Authors, Books och Loans.

Användarnamn och Lösenord i JSON format:
```JSON
{
  "username": "admin",
  "password": "password"
}
```
```JSON
{
"username": "user",
"password": "password"
}
```

## author-microservice - Rasha Knifdi:
#### port: `loaclhost:8081`
Author har frikopplats som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet..

## book-service - Markus Nikolic:
#### port: `localhost:8083`
Book ska frikopplas som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet..

## loan-service - Konstantinos Nascos:
#### port: `loaclhost:8082`
Loan har frikopplats som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet..

## service-registry - Felix Lidén:
#### port: `loaclhost:8761`
Det är mellansteget som hjälper microservice kommunicera med varandra. Det är också den som har hand om eureka server och startar den. Man kan checka vilka microservices är uppe med att kolla länken:
```http
GET http://localhost:8761
```

## Tester för alla microservices - Jonatan Emil Dahl:
```Bash
createLoan_withoutBookId_returnsBadRequest()
getAllLoans_returnsOk()
contextLoads()
createAuthor_andGetAuthorById_returnsAuthor()
createAuthor(String name)
```

---

## Arbetsflöde:
Agilt med gitflow, och också kommunicerat direkt med varandra via discord för när man skapat en Pull Request så den blir gjord snabbt och effektivt. Haft 2 möten i veckan under projektets gång för att säkerställa att vi jobbar gemensamt mot samma mål.

Vi har delat upp arbetet så alla fick vars en del för att streamline arbetet.

Jonathan Isaksson har jobbat huvudsakligen på API-Gateway. Rasha Knifdi har jobbat huvudsakligen på author-microservice. Konstantinos Nascos har jobbat huvudsakligen på loan-microservice. Felix Liden har jobbat huvudsakligen på service-registry. Markus Nikolic har jobbat huvudsakligen på book-microservice. Jonatan Dahl har jobbat i alla microservices med huvudsakligen tester och Github Actions(IOM vi inte hade behov av 6 microservice).

Vi har alla hjälpts åt med Pull Request och Issues.

---

## Checklista:
När allt är igång, för att testa med postman:

### Som Admin:

HEADERS: Content-Type: application/json
POST http://localhost:8080/auth/login -klar

```JSON
{
  "username": "admin",
  "password": "password"
}
```

kopiera token och använd som Authorization bearer-token UTAN CITATTECKEN

POST http://localhost:8080/api/v1/authors -klar
```JSON
{
  "name": "Tolkien"
}
```

POST http://localhost:8080/api/v1/books -klar
```JSON
{
  "title": "LotR",
  "author": "Tolkien",
  "authorId": 1,
  "publicationYear": 1950
}
```

GET http://localhost:8080/api/v1/books/author/1 -klar


POST http://localhost:8080/api/v1/loans -klar
```JSON
{
  "bookId": 1,
  "borrowerName": "Test User"
}
```

GET http://localhost:8080/api/v1/authors/1 -klar

GET http://localhost:8080/api/v1/authors -klar

PUT http://localhost:8080/api/v1/books/1 -klar
```JSON
{
  "title": "LotR del1/3",
  "author": "Tolkien",
  "authorId": 1,
  "publicationYear": 1950
}
```

DELETE http://localhost:8080/api/v1/books/1 -klar

GET http://localhost:8080/api/v1/books/author/1 -klar

### Som User:

POST http://localhost:8080/auth/login -klar

HEADERS: Content-Type: application/json

BODY:
```JSON
{
"username": "user",
"password": "password"
}
```

GET http://localhost:8080/api/v1/books -klar

DELETE http://localhost:8080/api/v1/books/1 -403 klar(utgår från att resterande fungerar då också) med fel nyckel blir det 401 istället.

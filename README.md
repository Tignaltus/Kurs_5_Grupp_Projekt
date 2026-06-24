# Kurs_5_Grupp_Projekt
För att starta programmen:
Starta service registry, sen starta microservices, sist starta gateway(alla går att starta självständigt)

Vi har använt:
OpenFeign
Swagger
JWT
Ratelimiting
H2
Eureka
Github Repository
Github Actions
Service Registry
API Gateway
Spring Security
Spring Boot
Spring Web
MockMvc
TestContainers

kort om varje microservice visar:

Api-gateway:
port: 8080
url: http://localhost:8080/api/v1/
Api-gateway är en frontdörr till att komma åt alla microservices. Man behöver inte komma ihåg varje microservice egna port, istället ska allt skötas automatiskt härifrån. Säkerhet hanteras central härifrån med ratelimiting och jwt som skickar tokens till microservices vid behov.


author-microservice:
port: 8081
Author har frikopplats som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet.. 

book-service:
port: 8083
Book ska frikopplas som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet..


loan-service:
port: 8082
Loan har frikopplats som fristående program från tidigare monolit, har nu endast tillgång till sin egna service/model/repo/controller. Vid behov kopplas till andra med en Client med hjälp av dto för säkerhet..


service-registry:
port: 8761
Det är mellansteget som hjälper microservice kommunicera med varandra. Det är också den som har hand om eureka server och startar den.

Testerna visar:
createLoan_withoutBookId_returnsBadRequest()
getAllLoans_returnsOk()
contextLoads()
createAuthor_andGetAuthorById_returnsAuthor()
createAuthor(String name)


Vi har arbetat:
Agilt med gitflow, och också kommunicerat direkt med varandra via discord för när man skapat en Pull Request så den blir gjord snabbt och effektivt. Haft 2 möten i veckan under projektets gång för att säkerställa att vi jobbar gemensamt mot samma mål.
Vi har delat upp arbetet så alla fick vars en del för att streamline arbetet.
Jonathan Isaksson har jobbat huvudsakligen på API-Gateway. Rasha Knifdi har jobbat huvudsakligen på author-microservice. Konstantinos Nascos har jobbat huvudsakligen på loan-microservice. Felix Liden har jobbat huvudsakligen på service-registry. Markus Nikolic har jobbat huvudsakligen på book-microservice. Jonatan Dahl har jobbat i alla microservices med huvudsakligen tester och Github Actions(IOM vi inte hade behov av 6 microservice).
Vi har alla hjälpts åt med Pull Request och Issues.

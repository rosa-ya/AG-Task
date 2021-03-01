# AG Task: Backend 
#General info
* It is Java  Micro-Service Application, utilizing Spring Boot
* It has test automation for unit testing and integration testing
* Spring data jpa (JpaRepository) is used to perform save and searching .
* Update the access keys in application.properties file

# Swagger api  
* SwaggerConfiguration.java can be connected on http://localhost:8080/swagger-ui/index.html# when application is running.

#Technologies
1. Spring boot jpa, starter, tomcat etc.
2. Junit, Mockito for testing
3. H2 for database.
4. Springfox for swagger api.

#How it builds
* mvn package OR mvn clean compile package install.
It will create exchangeRate-0.0.1.jar in target folder.
* java -jar exchangeRate-0.0.1.jar
* It is a SpringBoot project and can be simply executed as a java project from ExchangeRateApplication.java class. 

 
# Methods
## Get exchange rate for given Date and Currencies
### localhost:8080/api/exchange-rate/{date}/{baseCurrency}/{targetCurrency}

## Get exchange rate daily history
### localhost:8080/api/exchange-rate/history/daily/{yyyy}/{MM}/{dd}
 
## Get exchange rate  monthly history
### localhost:8080/api/exchange-rate/history/monthly/{yyyy}/{MM}

 
# H2 DATABASE  
### JDBC URL - jdbc:h2:mem:exchange_rate_db
* DB configuration is available in application.properties.

#TESTING   
Mockito is used fot unit testing.




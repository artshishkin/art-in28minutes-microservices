# art-in28minutes-microservices
Master Microservices with Spring Boot and Spring Cloud - Tutorial on Udemy - in28minutes

####  Section 3: Restful Web Services with Spring Boot

#####  33. Step 18 - Internationalization for RESTful Services

Testing:
1.  Use HttpHeader `Accept-Language` for setting Locale
    -  `en` - English
    -  `ru` - Rus
    -  `uk` - Ukraine
    -  `<without it>` - default - `en`

#####  38. Step 20 - Configuring Auto Generation of Swagger Documentation

-  `http://localhost:8080/v2/api-docs`
-  `http://localhost:8080/swagger-ui/index.html`

#####  42. Step 23 - Monitoring APIs with Spring Boot Actuator

HAL Browser:
-  `localhost:8080` -> will redirect to HAL Explorer
-  insert `/actuator`

#####  46. Step 27 - Versioning RESTful Services - Header and Content Negotiation Approach

 - Media type versioning (a.k.a “content negotiation” or “accept header”)
   - GitHub
 - (Custom) headers versioning
   - Microsoft
 - URI Versioning
   - Twitter
 - Request Parameter versioning 
   - Amazon
 - Factors
  - URI Pollution
  - Misuse of HTTP Headers
  - Caching
  - Can we execute the request on the browser?
  - API Documentation
 - No Perfect Solution 

####  Section 6: Microservices with Spring Cloud - V2

#####  129. Step 05 - Installing Git and Creating Local Git Repository - V2

-  `cd C:\Users\Admin\IdeaProjects\Study\in28minutes\MicroservicesTutorial\art-in28minutes-microservices\git-localconfig-repo`
-  `git init`
-  `git status`
-  `git add *`
-  `git status`
-  `git commit -m "adding limits-service.yml"`

#####  131. Step 06 - Connect Spring Cloud Config Server to Local Git Repository - V2

-  curl to `http://localhost:8888/limits-service/default`

#####  138. Step 11 - Create a simple hard coded currency exchange service - V2

-  URL: `http://localhost:8000/currency-exchange/from/USD/to/INR`
-   Response Structure
```json
{
   "id":10001,
   "from":"USD",
   "to":"INR",
   "conversionMultiple":65.00,
   "environment":"8000 instance-id"
}
```

#####  139. Step 12 - Setting up Dynamic Port in the the Response - V2

-  Edit Configuration
-  Duplicate config `currency-exchange8000`
-  VM options:
    -  `-Dserver.port=8001`

#####  145. Step 16 - Creating a service for currency conversion - V2

-  URL: `http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10`
-  Response Structure
```json
{
  "id": 10001,
  "from": "USD",
  "to": "INR",
  "conversionMultiple": 65.00,
  "quantity": 10,
  "totalCalculatedAmount": 650.00,
  "environment": "8000 instance-id"
}
```

#####  155. Step 23 - Enabling Discovery Locator with Eureka for Spring Cloud Gateway

Initial

-  `http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR`
-  `http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/from/USD/to/INR/quantity/10`

Lower Case

-  `http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR`
-  `http://localhost:8765/currency-conversion/currency-conversion/from/USD/to/INR/quantity/10`



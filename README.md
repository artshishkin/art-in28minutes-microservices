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


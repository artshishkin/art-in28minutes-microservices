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


#####  157. Step 24 - Exploring Routes with Spring Cloud Gateway

Custom Routes

-  `http://localhost:8765/currency-exchange/from/USD/to/UAH`
-  `http://localhost:8765/currency-conversion/from/USD/to/UAH/quantity/10`
-  `http://localhost:8765/currency-conversion-new/from/USD/to/UAH/quantity/10`
-  `http://localhost:8765/currency-conversion-new-with-segment/from/USD/to/UAH/quantity/10`

#####  161. Step 28 - Playing with Circuit Breaker Features of Resilience4j

-  [CircuitBreaker Documentation](https://resilience4j.readme.io/docs/circuitbreaker)
-  Run this to curl 10 requests per second
    -  `watch -n 0.1 curl http://localhost:8000/sample-api`
-  If you run Windows than use another host, wsl (I used Ubuntu WSL) and curl to you IP (find through `ipconfig`)
    -  `watch -n 0.1 curl http://192.168.1.154:8000/sample-api`
-  Make sure Firewall settings allow access to your host (create inbound rule or temporarily disable Firewall).
-  After 10 unsuccessfully calls circuit breaker switches to the OPEN state and does not call method just return fallback result.
-  In 60 seconds it will try another 10 calls and so on.
-  Curl to 
    -  `http://localhost:8000/actuator/health`
    
#####  163. Step 29 - Exploring Rate Limiting and BulkHead Features of Resilience4j - V2

RateLimiter
-  `watch -n 0.7 curl http://192.168.1.154:8000/rate-limit`
-  without fallbackMethod
-  `io.github.resilience4j.ratelimiter.RequestNotPermitted: RateLimiter 'default' does not permit further calls`
BulkHead
-  `watch -n 0.2 curl http://192.168.1.154:8000/bulk-head`
Set maxConcurrentCalls to 2 and run watch command from 3 separate windows to make 3 threads of call
     
####  Section 7: Docker with Microservices using Spring Boot and Spring Cloud - V2

#####  172. Step 07 - Playing with Docker Images

To create new tag of an image
-  `docker image tag nginx:1.13 artarkatesoft/mynginx:1.0.13`
-  `docker image tag artarkatesoft/mynginx:1.0.13 artarkatesoft/mynginx:latest`
To inspect
-  `docker image inspect artarkatesoft/mynginx:1.0.13`
To search existing images in registry hub.docker.com
-  `docker search mynginx`
-  `docker search --limit 100 artarkatesoft`
View image history
-  `docker image history artarkatesoft/beer-order-service`
Remove image from local repository
-  `docker image rm  artarkatesoft/mynginx:1.0.13`
Remove unused images
-  `docker image prune`

#####  173. Step 08 - Playing with Docker Containers

1.  Pause the container
    -  `docker container pause <container-name/ID>`
    -  `docker container inspect 890`
        -  "Status": "paused"
2.  Unpause the container
    -  `docker container unpause <container-name/ID>`
        -  "Status": "running"
3.  Remove all stopped containers
    -  `docker container prune`
4.  Stop
    -  `docker container stop <ID/name>` -> SIGTERM -> graceful shutdown
    -  `{"@timestamp":"2021-03-24T15:41:33.680Z","@version":"1","level":"INFO","message":"Shutting down ExecutorService 'applicationTaskExecutor'","logger_name":"org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor","thread_name":"SpringContextShutdownHook"}`
5.  Kill
    -  `docker container kill <ID/name>` -> SIGKILL -> immediately terminates the process
6.  Starting container at startup
    - add `--restart=always`
    -  **or** update
    -  `docker container update --restart always 890`
    -  `docker container inspect 890`
        -  `"HostConfig": "RestartPolicy": {"Name": "always","MaximumRetryCount": 0}`
    -  Restart Docker Desktop
    -  `docker ps` - must be running
    -  `docker container update --restart no 890` - to not restart                 

#####  174. Step 09 - Playing with Docker Commands - stats, system

1.  Docker Events
    -  `docker events`
    -  we have one container is running with healthchecks
    -  so we see events from healthchecks
    -  in another window `docker container stop 890`
    -  in events tab we see
        -  container exec_die
        -  container kill
        -  container die
        -  network disconnect
        -  container stop
2.  Docker top
    -  `docker top`        
    -  UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
    -  root                12148               12128               39                  16:48               ?                   00:00:40            java -Djava.security.egd=file:/dev/./urandom org.springframework.boot.loader.JarLauncher
3.  Docker stats
    -  `docker stats`
    -  CONTAINER ID   NAME            CPU %     MEM USAGE / LIMIT    MEM %     NET I/O       BLOCK I/O   PIDS
    -  89091762b554   crazy_hawking   1.64%     371.7MiB / 9.73GiB   3.73%     1.12kB / 0B   0B / 0B     75
4.  Limit containers
    -  start another container with limit of 768MB and CPU 15% (100000 - 100%)
    -  `docker container run -d -p 8762:8761 -m 768m --cpu-quota 15000  artarkatesoft/art-sfg-mssc-brewery-eureka`
5.  Docker system
    -  `docker system df` - disk usage
    -  `docker system df -v` - disk usage --verbose with detailed information
    
#####  181. Step 15 - Creating Container Image for Currency Exchange Microservice

Buildpacks are a tool that provides framework and application dependencies. 
Given a Spring Boot fat jar, a buildpack would provide the Java runtime for us. 
This allows us to skip the Dockerfile and get a sensible Docker image automatically.
-  `./mvnw spring-boot:build-image`

#### Section 8: Kubernetes with Microservices using Docker, Spring Boot and Spring Cloud

#####  193. Step 05 - Deploy Your First Spring Boot Application to Kubernetes Cluster

1.  In Digital Ocean create Kubernetes Cluster `my-first-k8s-cluster`
2.  Download configuration file `my-first-k8s-cluster-kubeconfig.yaml`
3.  Deploy application
    -  `kubectl --kubeconfig="my-first-k8s-cluster-kubeconfig.yaml" create deployment hello-world-rest-api --image=in28min/hello-world-rest-api:0.0.1.RELEASE`
    -  got the response
        -  `deployment.apps/hello-world-rest-api created`
4.  Expose App to the outside world
    -  `kubectl --kubeconfig="my-first-k8s-cluster-kubeconfig.yaml" expose deployment hello-world-rest-api --type=LoadBalancer --port=8080`
        -  `service/hello-world-rest-api exposed`
5.  Kubernetes Dashboard
    -  Services
    -  hello-world-rest-api
        -  curl to  `http://144.126.246.224:8080/`
            -  {healthy:true}
        -  curl to  `http://144.126.246.224:8080/hello-world`
            -  `Hello World V1 7c4sc`
        -  curl to  `http://144.126.246.224:8080/hello-world-bean`
            -  `{"message":"Hello World"}`
               
#####  195. Step 06 - Quick Look at Kubernetes Concepts - Pods, Replica Sets and Deployment

-  To simplify connection to Digital Ocean Kubernetes 
    -  modify `my-first-k8s-cluster-kubeconfig.yaml` -> `my-first-k8s-cluster-kubeconfig/config`
    -  `cd ./my-first-k8s-cluster-kubeconfig` 
    -  `kubectl --kubeconfig="config" version` - test connection
    -  add `--kubeconfig="config"` to every request
    -  `kubectl --kubeconfig="config" get nodes`
-  Get events
    -  `kubectl --kubeconfig="config" get events`
        -  `No resources found in default namespace` 
    -  `kubectl --kubeconfig="config" get events -A`
    -  `kubectl --kubeconfig="config" get events --all-namespaces=true`
        -  **BUT**
        -  `No resources found` - WTF?
    -  `kubectl --kubeconfig="config" delete deployments --all`
        -  `deployment.apps "hello-world-rest-api" deleted`
    -   recreate deployment "hello-world-rest-api"
    -  `kubectl --kubeconfig="config" get events -A`
    
| NAMESPACE |   LAST SEEN   | TYPE |     REASON |              OBJECT                                       | MESSAGE |
| --- | --- | --- | --- | --- | --- |
| default |     2m1s        | Normal |   Killing |             pod/hello-world-rest-api-687d9c7bc7-7c4sc    | Stopping container hello-world-rest-api |
| default |     52s         | Normal |   Scheduled |           pod/hello-world-rest-api-687d9c7bc7-qrwpq    | Successfully assigned default/hello-world-rest-api-687d9c7bc7-qrwpq to pool-657zl26t9-8qodd |
| default |     51s         | Normal |   Pulled |              pod/hello-world-rest-api-687d9c7bc7-qrwpq    | Container image "in28min/hello-world-rest-api:0.0.1.RELEASE" already present on machine |
| default |     51s         | Normal |   Created |             pod/hello-world-rest-api-687d9c7bc7-qrwpq    | Created container hello-world-rest-api |
| default |     51s         | Normal |   Started |             pod/hello-world-rest-api-687d9c7bc7-qrwpq    | Started container hello-world-rest-api |
| default |     52s         | Normal |   SuccessfulCreate |    replicaset/hello-world-rest-api-687d9c7bc7   | Created pod: hello-world-rest-api-687d9c7bc7-qrwpq |
| default |     52s         | Normal |   ScalingReplicaSet |   deployment/hello-world-rest-api              | Scaled up replica set hello-world-rest-api-687d9c7bc7 to 1 |

-  Get pods
    -  `kubectl --kubeconfig="config" get pods`        
        -  NAME                                    READY   STATUS    RESTARTS   AGE
        -  hello-world-rest-api-687d9c7bc7-qrwpq   1/1     Running   0          7m35s    
-  Get ReplicaSet
    -  `kubectl --kubeconfig="config" get replicasets` - plural or singular
    -  `kubectl --kubeconfig="config" get replicaset` 
        -  NAME                              DESIRED   CURRENT   READY   AGE
        -  hello-world-rest-api-687d9c7bc7   1         1         1       8m26s
-  Get deployment
    -  `kubectl --kubeconfig="config" get deployments` - plural or singular
        -  NAME                   READY   UP-TO-DATE   AVAILABLE   AGE
        -  hello-world-rest-api   1/1     1            1           11m
-  Get service
    -  `kubectl --kubeconfig="config" get services` - plural or singular
    -  `kubectl --kubeconfig="config" get service` 
        -  NAME                   TYPE           CLUSTER-IP     EXTERNAL-IP       PORT(S)          AGE
        -  hello-world-rest-api   LoadBalancer   10.245.75.88   144.126.246.224   8080:30867/TCP   11h
        -  kubernetes             ClusterIP      10.245.0.1     <none>            443/TCP          12h

#####  196. Step 07 - Understanding Pods in Kubernetes

-  `kubectl --kubeconfig="config" get pods -o wide`

| NAME                                    | READY   | STATUS    | RESTARTS   | AGE    | IP             | NODE                   | NOMINATED NODE   | READINESS GATES |
| hello-world-rest-api-687d9c7bc7-qrwpq   | 1/1     | Running   | 0          | 134m   | 10.244.1.230   | pool-657zl26t9-8qodd   | <none>           | <none> |

    -  READY - 1/1 - 1 container in 1 pod / containers talk to each other using localhost
    -  IP - address of pod  

-  `kubectl --kubeconfig="config" explain pods`
-  Pods can be from the different applications or the same app
-  `kubectl --kubeconfig="config" describe pod hello-world-rest-api-687d9c7bc7-qrwpq`    
    
#####  197. Step 08 - Understanding ReplicaSets in Kubernetes

1.  Get ReplicaSets
    -  `kubectl --kubeconfig="config" get replicaset`
    -  `kubectl --kubeconfig="config" get replicasets`
    -  `kubectl --kubeconfig="config" get rs`
2.  ReplicaSet monitors number of Pods 
    -  `kubectl --kubeconfig="config" get pods -o wide`
        -  `hello-world-rest-api-687d9c7bc7-qrwpq   1/1     Running   0          3h56m   10.244.1.230   pool-657zl26t9-8qodd   <none>           <none>`
    -  `kubectl --kubeconfig="config" delete pods hello-world-rest-api-687d9c7bc7-qrwpq`
        - `pod "hello-world-rest-api-687d9c7bc7-qrwpq" deleted`
        -  **BUT**
    -  `kubectl --kubeconfig="config" get pods -o wide`
        -  `hello-world-rest-api-687d9c7bc7-bnnkg   1/1     Running   0          2m14s   10.244.0.98   pool-657zl26t9-8qod0   <none>           <none>`
        -  **another pod has been started**
3.  Scaling Pods
    -  `kubectl --kubeconfig="config" scale  deployment hello-world-rest-api --replicas=3`
    -  `kubectl --kubeconfig="config" get pods`
        -  NAME                                    READY   STATUS    RESTARTS   AGE
        -  hello-world-rest-api-687d9c7bc7-bnnkg   1/1     Running   0          11m
        -  hello-world-rest-api-687d9c7bc7-gsx5w   1/1     Running   0          27s
        -  hello-world-rest-api-687d9c7bc7-lmbvd   1/1     Running   0          27s        
    -  test it
        -  curl `http://144.126.246.224:8080/hello-world` many times
            -  Hello World  V1 gsx5w
            -  Hello World  V1 lmbvd
            -  Hello World  V1 bnnkg
        -  `watch -n 0.1 curl http://144.126.246.224:8080/hello-world`
    -  `kubectl --kubeconfig="config" get rs`
        -  NAME                              DESIRED   CURRENT   READY   AGE
        -  hello-world-rest-api-687d9c7bc7   3         3         3       4h31m
    -  `kubectl --kubeconfig="config" get event --sort-by=.metadata.creationTimestamp`
4.  ReplicaSet Description
    -  `kubectl --kubeconfig="config" explain replicaset`              

#####  198. Step 09 - Understanding Deployment in Kubernetes        

1.  View ReplicaSet
    -  `kubectl --kubeconfig="config" get rs -o wide`
        -  NAME                              DESIRED   CURRENT   READY   AGE     CONTAINERS             IMAGES                                       SELECTOR
        -  hello-world-rest-api-687d9c7bc7   3         3         3       5h53m   hello-world-rest-api   in28min/hello-world-rest-api:0.0.1.RELEASE   app=hello-world-rest-api,pod-template-hash=687d9c7bc7
2.  Deploy fake image
    -  `kubectl --kubeconfig="config" set image deployment hello-world-rest-api hello-world-rest-api=DUMMY_IMAGE:TEST`
    -  `deployment.apps/hello-world-rest-api image updated`
    -  `kubectl --kubeconfig="config" get rs -o wide`    
        -  NAME                              DESIRED   CURRENT   READY   AGE     CONTAINERS             IMAGES                                       SELECTOR
        -  hello-world-rest-api-687d9c7bc7   3         3         3       6h20m   hello-world-rest-api   in28min/hello-world-rest-api:0.0.1.RELEASE   app=hello-world-rest-api,pod-template-hash=687d9c7bc7
        -  hello-world-rest-api-84d8799896   1         1         0       16m     hello-world-rest-api   DUMMY_IMAGE:TEST                             app=hello-world-rest-api,pod-template-hash=84d8799896        
    -  `kubectl --kubeconfig="config" get pods`
        -  3 pods OK and 1 InvalidImageName
        -  `hello-world-rest-api-84d8799896-sj8kz   0/1     InvalidImageName   0          19m`
    -  `kubectl --kubeconfig="config" describe pod hello-world-rest-api-84d8799896-sj8kz`
        -  `Failed to apply default image tag "DUMMY_IMAGE:TEST": couldn't parse image reference "DUMMY_IMAGE:TEST": invalid reference format: repository name must be lowercase`
3.  Events while deploy fake image
    -  ` kubectl --kubeconfig="config" get event --sort-by=.metadata.creationTimestamp`
        -  `5m7s        Warning   Failed              pod/hello-world-rest-api-84d8799896-sj8kz    Error: InvalidImageName`
    -  Events
        1.  Deployment said ReplicaSet to scale up: `Scaled up replica set hello-world-rest-api-84d8799896 to 1`
        2.  ReplicaSet created Pod: `Created pod: hello-world-rest-api-84d8799896-sj8kz`
        3.  Pod was assigned to pool: `Successfully assigned default/hello-world-rest-api-84d8799896-sj8kz to pool-657zl26t9-8qod1`
        4.  Pod InspectFailed: `Failed to apply default image tag "DUMMY_IMAGE:TEST": couldn't parse image reference "DUMMY_IMAGE:TEST": invalid reference format: repository name mus`  
        5.  Pod failed to deploy: `Error: InvalidImageName`
4.  Deploy correct image
    -  `kubectl --kubeconfig="config" set image deployment hello-world-rest-api hello-world-rest-api=in28min/hello-world-rest-api:0.0.2.RELEASE`
        -  `deployment.apps/hello-world-rest-api image updated`
        -  NAME                              DESIRED   CURRENT   READY   AGE     CONTAINERS             IMAGES                                       SELECTOR
        -  hello-world-rest-api-687d9c7bc7   0         0         0       6h55m   hello-world-rest-api   in28min/hello-world-rest-api:0.0.1.RELEASE   app=hello-world-rest-api,pod-template-hash=687d9c7bc7
        -  hello-world-rest-api-7ddff5dfc6   3         3         3       55s     hello-world-rest-api   in28min/hello-world-rest-api:0.0.2.RELEASE   app=hello-world-rest-api,pod-template-hash=7ddff5dfc6
        -  hello-world-rest-api-84d8799896   0         0         0       51m     hello-world-rest-api   DUMMY_IMAGE:TEST                             app=hello-world-rest-api,pod-template-hash=84d8799896
    -  `kubectl --kubeconfig="config" get pods`
        -  NAME                                    READY   STATUS              RESTARTS   AGE
        -  hello-world-rest-api-687d9c7bc7-8jwmm   1/1     Running             0          3s
        -  hello-world-rest-api-687d9c7bc7-l9h94   0/1     ContainerCreating   0          1s
        -  hello-world-rest-api-7ddff5dfc6-jfhgz   1/1     Running             0          4m21s
        -  hello-world-rest-api-7ddff5dfc6-m8svc   0/1     Terminating         0          4m4s
        -  hello-world-rest-api-7ddff5dfc6-n4gng   1/1     Running             0          4m11s
5.  Default deployment strategy is **ROLLING UPDATE**
    -  1 pod at a time
    -  guaranties Zero-time downtime update  

#####  200. Step 11 - Understanding Services in Kubernetes

-  `kubectl --kubeconfig="config" get pods -o wide`
    -  every pod has IP
-  ` kubectl --kubeconfig="config" delete pod hello-world-rest-api-7ddff5dfc6-lbppd`
    -  New Pod: ContainerCreating -> Running
    -  Old Pod: Terminating -> <disapear>
    -  new pod has new IP
-  In Google Cloud Platform LoadBalancer was created for us to balance load among pods
-  `kubectl --kubeconfig="config" get services`
    -  NAME                   TYPE           CLUSTER-IP     EXTERNAL-IP       PORT(S)          AGE
    -  hello-world-rest-api   LoadBalancer   10.245.75.88   144.126.246.224   8080:30867/TCP   18h
    -  kubernetes             ClusterIP      10.245.0.1     <none>            443/TCP          19h    
        
#####  202. Step 13 - Understanding Kubernetes Architecture - Master Node and Nodes

-  `kubectl --kubeconfig="config" get componentstatuses`
    -  NAME                 STATUS    MESSAGE             ERROR
    -  controller-manager   Healthy   ok
    -  scheduler            Healthy   ok
    -  etcd-0               Healthy   {"health":"true"}
-  etcd - Distributed Database

#####  208. Step 16 - Deploy Microservices to Kubernetes & Understand Service Discovery

1.  Create deployment `currency-exchange`
    -  `kubectl --kubeconfig="config" create deployment currency-exchange --image=artarkatesoft/art-in28m-currency-exchange-service:0.0.1-k8s-SNAPSHOT`
2.  Open port 8000
    -  `kubectl --kubeconfig="config" expose deployment currency-exchange --type=LoadBalancer --port=8000`
3.  List running services
    -  `kubectl --kubeconfig="config" get svc` / `get services`
4.  List pods
    -  `kubectl --kubeconfig="config" get po` / `get pods`    
5.  List ReplicaSets
    -  `kubectl --kubeconfig="config" get rs` / `get replicaset`
6.  List all that was created
    -  `kubectl --kubeconfig="config" get all`
7.  Curl to service by using external IP
    -  `curl http://64.225.94.53:8000/currency-exchange/from/USD/to/UAH`    
        -  `curl http://64.225.94.53:8000/currency-exchange/from/USD/to/UAH
            {"id":10002,"from":"USD","to":"UAH","conversionMultiple":27.69,"environment":"8000 currency-exchange v1-k8s currency-exchange-66b7664d5d-l75dr "}`
8.  Deploy `currency-conversion` service
    -  `kubectl --kubeconfig="config" create deployment currency-conversion --image=artarkatesoft/art-in28m-currency-conversion-service:0.0.1-k8s-SNAPSHOT`
    -  `kubectl --kubeconfig="config" expose deployment currency-conversion --type=LoadBalancer --port=8100`
    -  `kubectl --kubeconfig="config" get svc`
    -  `kubectl --kubeconfig="config" get svc --watch`
    -  `curl http://174.138.102.120:8100/currency-conversion/from/USD/to/UAH/quantity/10`
        -  `{"id":10002,"from":"USD","to":"UAH","conversionMultiple":27.69,"quantity":10,"totalCalculatedAmount":276.90,"environment":"8000 currency-exchange v1-k8s currency-exchange-66b7664d5d-l75dr "}`
9.  Matching Environment variables
    -  service `currency-exchange` -> Kubernetes creates CURRENCY_EXCHANGE_SERVICE_HOST            
    -  service `currency-conversion` -> Kubernetes creates CURRENCY_CONVERSION_SERVICE_HOST
    -  service `any-name` -> Kubernetes creates ANY_NAME_SERVICE_HOST
                
#####  209. Step 17 - Creating Declarative Configuration Kubernetes YAML for Microservices

1.  Read current configurations
    -  `kubectl --kubeconfig="config" get deployment currency-exchange -o yaml >> currency-exchange-deployment-read.yaml`
    -  got content for [currency-exchange-deployment-read.yaml](docker/digital-ocean-k8s/currency-exchange-deployment-read.yaml)
    -  `kubectl --kubeconfig="config" get service currency-exchange -o yaml >> currency-exchange-service.yaml`
    -  got [currency-exchange-service.yaml](docker\digital-ocean-k8s\currency-exchange-service.yaml)
2.  Create new deployment configuration
    -  merge two files into [currency-exchange-deployment.yaml](docker/digital-ocean-k8s/currency-exchange-deployment.yaml)
    -  modify replicas to **2**
3.  Compare configurations
    -  cd to the location of [currency-exchange-deployment.yaml](docker/digital-ocean-k8s/currency-exchange-deployment.yaml)
        -  `cd C:\Users\Admin\IdeaProjects\Study\in28minutes\MicroservicesTutorial\art-in28minutes-microservices\docker\digital-ocean-k8s`
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" diff -f currency-exchange-deployment.yaml`
        -  `error: failed to run "diff": executable file not found in %PATH%`
    -  `choco install diffutils`
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" diff -f currency-exchange-deployment.yaml`
        -  ...
        -  `-  replicas: 1`
        -  `+  replicas: 2`
        -  ...
4.  Deploy new configuration
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" apply -f currency-exchange-deployment.yaml`
        -  `Warning: kubectl apply should be used on resource created by either kubectl create --save-config or kubectl apply 
            deployment.apps/currency-exchange configured`
        -  `Warning: kubectl apply should be used on resource created by either kubectl create --save-config or kubectl apply
            service/currency-exchange configured`
        -  Do not worry about them right now
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" get pods`  
        -  currency-exchange-66b7664d5d-l75dr     1/1     Running   0          4h21m
        -  currency-exchange-66b7664d5d-ql664     1/1     Running   0          3m20s    
5.  Test it
    -  `watch -n 0.5 curl http://174.138.102.120:8100/currency-conversion/from/USD/to/UAH/quantity/10` (through Ubuntu WSL)
    -  view different hostname in environment field

#####  212. Step 20 - Deploying Microservices using Kubernetes YAML Configuration

1.  Delete old deployments 
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" delete all -l app=currency-exchange`
        -  pod "currency-exchange-66b7664d5d-l75dr" deleted
        -  pod "currency-exchange-66b7664d5d-ql664" deleted
        -  service "currency-exchange" deleted
        -  deployment.apps "currency-exchange" deleted
        -  replicaset.apps "currency-exchange-66b7664d5d" deleted
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" delete all -l app=currency-conversion`
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" get all` - ensure there is nothing present 
2.  Deploy microservice
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" apply -f currency-exchange-deployment.yaml`   
        -  deployment.apps/currency-exchange created
        -  service/currency-exchange created
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" get all`
    -  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" get svc --watch`

#####  213. Step 21 - Playing with Kubernetes Declarative YAML Configuration

-  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" apply -f currency-conversion-deployment.yaml`
-  `kubectl --kubeconfig="C:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml" get svc --watch` - wait until currency-conversion receives External-IP
-  `watch -n 0.1 curl http://68.183.240.180:8100/currency-conversion/from/USD/to/UAH/quantity/11.21`
    -  load balancer does not switch very often

#####  Setting Default Configuration to Digital Ocean

In order to run KubeCtl commands without `--kubeconfig` parameter we can set default config in Environment Variables
-  KUBECONFIG = `c:\Users\Admin\.kube\my-first-k8s-cluster-kubeconfig.yaml`

#####  214. Step 22 - Creating Environment Variables to enable Microservice Communication

1.  Set Environment Variable to Exchange host
    -  Ranga uses Env.Variable `CURRENCY_EXCHANGE_URI`. I have created earlier property `exchange-service.host`.
    -  So I will be using environment variable `EXCHANGE_SERVICE_HOST`. **But** with a port added (:8000).
2.  Deploy config
    -  `kubectl apply -f currency-conversion-deployment.yaml`
    -  **OR**
    -  By using Intellij Kubernetes plugin (makes the same).
3.  Check everything is OK
    -  `kubectl get po`
    -  `kubectl logs currency-conversion-5969fb884b-x4xqj`    
    -  `kubectl logs -f currency-conversion-5969fb884b-x4xqj` - to follow
    -  from ubuntu terminal
        -  `curl http://68.183.240.180:8100/currency-conversion/from/USD/to/UAH/quantity/11.21`
        -  all ok
        
#####  215. Step 23 - Understanding Centralized Configuration in Kubernetes - Config Maps

-  `kubectl create configmap currency-conversion --from-literal=EXCHANGE_SERVICE_HOST=http://currency-exchange:8000`
    -  `configmap/currency-conversion created`
-  `kubectl get configmap` - or `get cm`
-  `kubectl get cm currency-conversion`
-  `kubectl get cm currency-conversion -o yaml`
-  `kubectl get cm currency-conversion -o yaml >> currency-conversion-configmap.yaml` -> to get [currency-conversion-configmap.yaml](docker\digital-ocean-k8s\currency-conversion-configmap.yaml)
-  delete unnecessary fields
-  add it to [currency-conversion-deployment.yaml](docker\digital-ocean-k8s\currency-conversion-deployment.yaml)
-  `kubectl apply -f currency-conversion-deployment.yaml`
    -  Warning: kubectl apply should be used on resource created by either kubectl create --save-config or kubectl apply

































                           
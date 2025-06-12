<h1>Offer Aggregator Service</h1>
Backend service for the financing application aggregator


<h2>Installation and running</h2>
<h3>Tools</h3>
JDK 21  
Gradle 8.14  
Docker engine 27.5.1  

```
./gradlew clean build
```

```
./gradlew localDevRun
```


Collection for testing API: [test.http](src/test/resources/test.http).
()

<h3>Short decision log</h3>

* Reactive approach + streaming option
* Postgres JDBC (not R2DBC because of limited transaction maturity and ORM support) 
* PII data encryption to simulate sensitive data protection
* Automatic context propagation (trace id propagation during reactive execution)  
* No unit tests. DB layer integration tests provided. Full scenarios are covered in test.http collection 
* Prometheus added for checking average response time

<h3>Out of scope</h3>

<b>Production readiness<b>
* Monitoring
* Authentication and authorization
* Rate limiting (probably responsibility of API Gateway, but still)
* Secret management

<b>Cross-cutting concerns<b>
* Common approach to avoid sensitive data in logs (masking patterns)
* Common approach for logging or/and saving raw request and response from third party (if required)
* Common approach for storing PII data (Vault)
* Fallback options (some/all integrations are down)

<b>Testing<b>
* Performance testing
* Test coverage metrics

<h3>Possible improvements</h3>

* Generate clients for financing institutions from provided open-api doc.  
* Swagger UI support

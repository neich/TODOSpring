# TODOjavaee

This is a Spring Boot 3.3 application that implements an pure API REST for a TODO appication. It is used for educational purposes and the goal is to show good practices when building multi-tier applications with JavaEE (and in general). It works with the the [TODOandroid](https://github.com/neich/TODOAndroid) Android application as client.

The main three tiers used are:

* REST tier (```@Controller```)
* Business tier (```@Service```)
* Persistence tier (JPA) (`@Entity`, `@Repository`)

Other Java APIs used:

* Bean validation
* Exception Mappers
* Dependency injection

It uses [Spring Boot 3.3](https://spring.io/projects/spring-boot) to produce an jar file than can be executed standalone without an application server:

```
./gradlew clean bootRun -PjvmArgs="-Dspring.profiles.active=dev"
```

#### Collaborations are welcome!
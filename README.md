# TODOjavaee

This is a Spring Boot application that implements an pure API REST for a TODO appication. It is used for educational purposes and the goal is to show good practices when building multi-tier applications with JavaEE (and in general). It works with the the [TODOandroid](https://github.com/neich/TODOAndroid) Android application as client.

The main three tiers used are:

* REST tier (```@Controller```)
* Business tier (```@Service```)
* Persistence tier (JPA)

Other Java APIs used:

* Bean validation
* Exception Mappers
* Dependency injection

It uses [Spring Boot 2](https://spring.io/projects/spring-boot) to produce an jar file than can be executed standalone without an application server:

```
gradle bootRun
```

## Image uploading

In order to store files, the app uses a private object storage server: [minio      ](https://www.minio.io/). The minio configuration has to be passed to the application as properties via command line:

```
gradlew bootJar
java -Dswarm.project.minio.ulr=http://your-minio-host.com -Dswarm.project.minio.access-key=your-access-key -Dswarm.project.minio.secret-key=your-secret-key -Dswarm.project.minio.bucket=your-bucket -jar ./build/libs/todo-spring-0.1.0.jar
```



## Heroku

The app is ready to deploy into [Heroku](http://heroku.com) with the ```web``` profile. There is a file ```Procfile``` with the command line arguments to start the jar.
#### Collaborations are welcome!
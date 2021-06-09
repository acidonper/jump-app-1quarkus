# Jump App Quarkus

## Introduction

_Jump App Quarkus_ is one of a set of microservices, named Jumps, developed to generate a microservice communication test tool in order to support multi hands-on and webinars around microservices in Kubernetes.

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

It is possible run this application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `code-with-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Test Jump App Quarkus API Locally

- GET method to reach /

```$bash
$ curl -X GET localhost:8080/
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>code-with-quarkus - 1.0.0-SNAPSHOT</title>
    <style>
...
```

- GET method to reach /jump

```$bash
$ curl -X GET localhost:8080/jump
{"code":200,"message":"/jump - Greetings from Quarkus!"}
```

- POST method with JUMP Object in the body to make multi jumps through _Jump App Quarkus_

```$bash
$ curl -XPOST -H "Content-type: application/json" -d '{
    "message": "Hello",
    "last_path": "/jump",
    "jump_path": "/jump",
    "jumps": [
        "http://localhost:8080",
        "http://localhost:8080",
        "http://localhost:8080",
        "http://localhost:8080",
        "http://localhost:8080",
        "http://localhost:8080"
    ]
}' 'localhost:8080/jump'
{"code":200,"message":"/jump - Greetings from Quarkus!"}
```

- GET method to reach /load

```$bash

## Generate a load 
# cpu - A number which is used to obtain the biggest primer number (Max - 999999999)
# wait - The number of miliseconds to wait (Max - 999999999)
# mem - The number of megabites loaded (Max - Depends on the resources)

$ curl -X GET localhost:8080/load?cpu=999999999&wait=1000&mem=10
{"code":200,"message":"/load - Greetings from Quarkus!"}
```


## Author Information

Asier Cidon

asier.cidon@gmail.com

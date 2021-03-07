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

### Creating a native executable on MacOS

- Install Dependencies
```shell script
brew install --cask graalvm/tap/graalvm-ce-java11
export GRAALVM_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.0.0/Contents/Home/
xattr -r -d com.apple.quarantine ${GRAALVM_HOME}/../.. # Temporally
${GRAALVM_HOME}/bin/gu install native-image
```

- Verify App
```shell script
mvn verify -Pnative
mvn package -Pnative 
```

- Build and run container
```shell script
docker build -f src/main/docker/Dockerfile.native -t jump-app-quarkus:dev .
docker run -i --rm -p 8080:8080 jump-app-quarkus:dev 
```
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

## Using s2i to build _Jump App Quarkus_ native image in Openshift

It is possible to build Quarkus Native images using Openshift integrated tools in a simple manner. 

Please, follow next steps to build an image in Openshift using s2i: 

- Create an _ImageStream_ in Openshift to reference official Quarkus s2i GraalVM image

```$bash
cat << EOF > ubi-quarkus-native-s2i.yaml
apiVersion: image.openshift.io/v1
kind: ImageStream
metadata:
  name: ubi-quarkus-native-s2i
spec:
  lookupPolicy:
    local: false
  tags:
  - annotations: null
    from:
      kind: DockerImage
      name: quay.io/quarkus/ubi-quarkus-native-s2i:20.2.0-java11
    generation: 1
    importPolicy: {}
    name: 20.2.0-java11
    referencePolicy:
      type: Source
EOF
oc apply -f ubi-quarkus-native-s2i.yaml
```

- Create an _ImageStream_ to allocate builds output

```$bash
cat << EOF > jump-app-quarkus.yaml
apiVersion: image.openshift.io/v1
kind: ImageStream
metadata:
  name: jump-app-quarkus
spec:
  lookupPolicy:
    local: false
EOF
oc apply -f jump-app-quarkus.yaml
```

- Create a _BuildConfig_ to build the _Jump App Quarkus_ image

```$bash
cat << EOF > quarkus-bc.yaml
apiVersion: build.openshift.io/v1
kind: BuildConfig
metadata:
  name: quarkus
spec:
  nodeSelector: null
  output:
    to:
      kind: ImageStreamTag
      name: jump-app-quarkus:latest
  postCommit: {}
  resources: {}
  source:
    git:
      uri: https://github.com/acidonper/jump-app-quarkus.git
      ref: develop
    type: Git
  strategy:
    sourceStrategy:
      from:
        kind: ImageStreamTag
        name: ubi-quarkus-native-s2i
    type: Source
EOF
oc apply -f quarkus-bc.yaml
```

- Trigger the _build_ process

```$bash
oc new-build quarkus
```

When this process is finished, a new image is available in the openshift project **image-registry.openshift-image-registry.svc:5000/<user_namespace>/jump-app-quarkus:latest**.

## Author Information

Asier Cidon

asier.cidon@gmail.com

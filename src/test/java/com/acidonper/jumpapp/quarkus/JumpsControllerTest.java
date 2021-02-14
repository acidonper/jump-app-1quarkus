package com.acidonper.jumpapp.quarkus;

import com.acidonper.jumpapp.quarkus.entities.Jump;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(WiremockJump.class)
public class JumpsControllerTest {

    @Test
    public void getJump() {
        given()
          .when().get("/jump")
          .then()
             .statusCode(200)
             .body(is("{\"code\":200,\"message\":\"/jump - Greetings from Quarkus!\"}"));
    }

    @Test
    public void postEmptyJumps() {
        // Jump Object
        Jump jump = new Jump("test","/jump", "/jump", new String[] {});

        given()
                .when().contentType("application/json").body(jump).post("/jump")
                .then()
                .statusCode(200)
                .body(is("{\"code\":400,\"message\":\"/jump - Farewell from Spring Boot! Bad Request!\"}"));
    }

    @Test
    public void postJump() {
        // Jump Object
        Jump jump = new Jump("test","/jump", "/jump", new String[] {"http://localhost:8080"});

        given()
                .when().contentType("application/json").body(jump).post("/jump")
                .then()
                .statusCode(200)
                .body(is("{\"code\":200,\"message\":\"/jump - Greetings from Jump Testing!\"}"));
    }

    @Test
    public void postMultiJumps() {
        // Jump Object
        Jump jump = new Jump("test","/jump", "/jump", new String[] {"http://localhost:8080", "http://localhost:8080"});

        given()
                .when().contentType("application/json").body(jump).post("/jump")
                .then()
                .statusCode(200)
                .body(is("{\"code\":200,\"message\":\"/jump - Greetings from Multi Jump Testing!\"}"));
    }

}
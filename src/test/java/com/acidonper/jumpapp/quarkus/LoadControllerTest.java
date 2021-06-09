package com.acidonper.jumpapp.quarkus;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(WiremockJump.class)
public class LoadControllerTest {

    @Test
    public void load() {
        given()
          .when().get("/load?{cpu}&{wait}&{mem}", 7, 500, 1)
          .then()
             .statusCode(200)
             .body(is("{\"code\":200,\"message\":\"/load - Greetings from Quarkus!\"}"));
    }

}
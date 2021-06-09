package com.acidonper.jumpapp.quarkus.web;

import com.acidonper.jumpapp.quarkus.entities.Jump;
import com.acidonper.jumpapp.quarkus.entities.Response;
import com.acidonper.jumpapp.quarkus.services.JumpClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Arrays;

@Path("/jump")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JumpsController {

    @GET
    public Response jump() throws InterruptedException {
        // Logging
        System.out.println("Received GET /jump");

        // Generate Response Object
        Response response = new Response("/jump - Greetings from Quarkus!", 200);

        // sleep
        Thread.sleep(500);

        // Logging
        System.out.println("Sending GET Response /jump - " + response.toString());
        return response;
    }

    @POST
    public Response jumps(Jump jump) {
        // Logging
        System.out.println("Received POST /jump");
        System.out.println(jump.toString());

        // Create Response Object
        Response response = new Response();
        response = new Response("/jump - Greetings from Quarkus!", 200);

        // Extract last jump
        if (jump.jumps.length == 0) {
            response.message = "/jump - Farewell from Spring Boot! Bad Request!";
            response.code = 400;
        }
        else if (jump.jumps.length == 1) {
            String url = jump.jumps[0] + jump.last_path;
            String content = "application/json";
            JumpClient jumpClient = RestClientBuilder.newBuilder()
            .baseUri(URI.create(url))
            .build(JumpClient.class);
            response = jumpClient.getJump(url, content);
        }
        else if (jump.jumps.length > 1) {
            String url = jump.jumps[0] + jump.last_path;
            Jump nextJumpObj = new Jump(jump.message, jump.last_path, jump.jump_path, Arrays.copyOfRange(jump.jumps, 1, jump.jumps.length));
            String content = "application/json";
            JumpClient jumpClient = RestClientBuilder.newBuilder()
                    .baseUri(URI.create(url))
                    .build(JumpClient.class);
            response = jumpClient.postJump(url, content, nextJumpObj);
        }

        System.out.println("Sending POST Response /jump - " + response.toString());
        return response;
    }

}
package com.acidonper.jumpapp.quarkus.web;

import com.acidonper.jumpapp.quarkus.entities.Response;
import com.acidonper.jumpapp.quarkus.services.LoadCPU;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/load")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoadController {

    @GET
    public Response load(@QueryParam("cpu") int cpu, @QueryParam("wait") int wait,
                         @QueryParam("mem") int mem) throws InterruptedException {

        // Logging
        System.out.println("Received GET /load");

        // Sleep
        if (wait != 0) {
            System.out.println("Sleeping miliseconds:" + wait);
            Thread.sleep(wait);
        }

        // Gen Memory consumption
        if (mem != 0) {
            System.out.println("Generating memory load mb:" + mem);
            byte[] b = new byte[mem];
            b[0] = 1;
            b[b.length - 1] = 1;

        }

        // Gen CPU Load
        if (cpu <= 1) {
            System.out.println("Generating CPU load obtaining the biggest Prime of the number:" + cpu);
            int bigPrime = LoadCPU.biggestPrime(cpu);
        }

        // Generate Response Object
        Response response = new Response("/load - Greetings from Quarkus!", 200);

        // Logging
        System.out.println("Sending GET Response /load - " + response.toString());
        return response;
    }


}

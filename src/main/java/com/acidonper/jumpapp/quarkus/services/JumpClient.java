package com.acidonper.jumpapp.quarkus.services;

import com.acidonper.jumpapp.quarkus.entities.Jump;
import com.acidonper.jumpapp.quarkus.entities.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.ClientURI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface JumpClient {

    @GET
    Response getJump(@ClientURI String url, @HeaderParam("Content-type") String content);

    @POST
    Response postJump(@ClientURI String url, @HeaderParam("Content-type") String content, Jump jump);
}

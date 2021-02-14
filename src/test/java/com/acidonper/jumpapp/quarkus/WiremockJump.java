package com.acidonper.jumpapp.quarkus;

import com.acidonper.jumpapp.quarkus.entities.Response;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import com.github.tomakehurst.wiremock.WireMockServer;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class WiremockJump implements QuarkusTestResourceLifecycleManager {

        private WireMockServer wireMockServer;

        @Override
        public Map<String, String> start() {

            wireMockServer = new WireMockServer();
            wireMockServer.start();

            Response responseExpected = new Response("/jump - Greetings from Jump Testing!",200);
            Response responseMultiExpected = new Response("/jump - Greetings from Multi Jump Testing!",200);

            Jsonb jsonb = JsonbBuilder.create();
            String jsonString = jsonb.toJson(responseExpected);

            Jsonb jsonbMulti = JsonbBuilder.create();
            String jsonMultiString = jsonbMulti.toJson(responseMultiExpected);

            stubFor(post(urlEqualTo("/jump"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(jsonMultiString)));

            stubFor(get(urlEqualTo("/jump"))
                    .willReturn(aResponse()
                            .withHeader("Content-Type", "application/json")
                            .withBody(jsonString)));

            return Collections.singletonMap("com.acidonper.jumpapp.quarkus.services.JumpClient/mp-rest/url", wireMockServer.baseUrl());
        }

        @Override
        public void stop() {
            if (null != wireMockServer) {
                wireMockServer.stop();
            }
        }
}

package com.acidonper.jumpapp.quarkus.entities;

public class Response {

    public String message;
    public Integer code;

    public Response() {
        super();
    }

    public Response(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format(
                "Response[message='%s', code='%s']", message, code.toString());
    }
}

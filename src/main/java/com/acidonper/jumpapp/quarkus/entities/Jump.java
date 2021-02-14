package com.acidonper.jumpapp.quarkus.entities;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

public class Jump {
    @NotEmpty
    public String message;
    @NotEmpty
    public String last_path;
    @NotEmpty
    public String jump_path;

    public String[] jumps;

    public Jump() {
        super();
    }

    public Jump(String message, String last_path, String jump_path, String[] jumps) {
        this.message = message;
        this.last_path = last_path;
        this.jump_path = jump_path;
        this.jumps = jumps;
    }

    @Override
    public String toString() {
        return String.format(
                "Jump[message='%s', last_path='%s', jump_path='%s', jumps='%s']", message, last_path, jump_path, Arrays.toString(jumps));
    }

}



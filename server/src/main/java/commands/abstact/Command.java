package commands.abstact;

import network.Request;
import network.Response;

import java.io.IOException;

public abstract class Command implements Executable {
    private final String name;
    private final String description;

    public abstract Response execute(Request request) throws IOException;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + description;
    }
}


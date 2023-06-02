package commands.available;

import network.Request;
import network.Response;

public abstract class Command implements Executable {
    private final String name;
    private final String description;

    public abstract Response execute(Request request);

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


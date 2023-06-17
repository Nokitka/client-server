package commands.available;

import commands.abstact.Command;
import network.Request;
import network.Response;
import network.Status;

public class Ping extends Command {
    public Ping() {
        super("ping", ": проверить сервер");
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        return new Response(Status.OK, "pong");
    }
}

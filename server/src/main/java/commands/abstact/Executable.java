package commands.abstact;

import network.Request;
import network.Response;

import java.io.IOException;

public interface Executable {
    Response execute(Request request) throws IOException;
}

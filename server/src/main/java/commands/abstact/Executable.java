package commands.abstact;

import network.Response;
import network.Request;

import java.io.IOException;

public interface Executable {
    Response execute(Request request) throws IOException;
}

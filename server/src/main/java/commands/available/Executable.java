package commands.available;

import network.Response;
import network.Request;

public interface Executable {
    Response execute(Request request);
}

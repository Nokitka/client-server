package server;

import common.network.CommandResult;
import common.network.Request;

public interface Executable {
    CommandResult execute(Request<?> request);
}

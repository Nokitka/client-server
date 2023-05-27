package common.network;

import server.DragonCollection;

import java.io.Serializable;

/**
 * the class that is responsible for requests
 * @param <T>
 */
public class Request<T> implements Serializable {
    public String command;
    public DragonCollection dragons;
    public T type;

    public Request(String command, T type, DragonCollection dragons) {
        this.command = command;
        this.type = type;
        this.dragons = dragons;
    }
}
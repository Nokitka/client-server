package network;

import data.Dragon;

import java.io.Serializable;

/**
 * Класс запроса клиента
 */
public class Request implements Serializable {
    private String command;
    private String arg;
    private Dragon dragon;

    public Request(String command, String arg, Dragon dragon) {
        this.command = command;
        this.dragon = dragon;
        this.arg = arg;
    }

    public Request(String command, String arg) {
        this.command = command;
        this.arg = arg;
    }

    public Request(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public boolean isEmpty() {
        return command.isEmpty() && arg.isEmpty() && dragon == null;
    }
}
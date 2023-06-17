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
    private User user;

    public Request(String command, String arg, Dragon dragon, User user) {
        this.command = command;
        this.dragon = dragon;
        this.arg = arg;
        this.user = user;
    }

    public Request(String command, String arg, User user) {
        this.command = command;
        this.arg = arg;
        this.user = user;
    }

    public Request(String command, User user) {
        this.command = command;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public boolean isEmpty() {
        return command.isEmpty() && arg.isEmpty() && dragon == null;
    }
}
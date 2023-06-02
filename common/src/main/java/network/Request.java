package network;

import data.Dragon;
import data.DragonHead;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String arg;
    private Dragon dragon;

    public Request(String command, String arg, Dragon dragon) {
        this.command = command;
        this.dragon = dragon;
        this.arg = arg;
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
}
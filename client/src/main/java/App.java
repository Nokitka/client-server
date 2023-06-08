import managers.RuntimeManager;
import network.Configuration;
import utils.Client;
import utils.Console;
import utils.ConsoleInput;

import java.io.IOException;
import java.net.InetAddress;

public class App {
    private static final int port = Configuration.PORT;

    public static void main(String[] args) {
        Console console = new Console();
        try {
            Client client = new Client(InetAddress.getLocalHost(), port, console);
            new RuntimeManager(console, client, new ConsoleInput()).interactiveMode();
        } catch (IOException e) {
            console.println("Невозможно подключиться к серверу!");
        }
    }

}
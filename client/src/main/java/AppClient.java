import managers.RuntimeManager;
import network.Configuration;
import utils.Client;
import utils.Console;
import utils.ConsoleInput;
import utils.UserInput;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class AppClient {
    private static final int port = Configuration.PORT;
    private static Console console;
    public static void main(String[] args) {
        console = new Console();
        try {
            Client client = new Client(InetAddress.getLocalHost(), port, console);
            new RuntimeManager(console, client, new ConsoleInput()).interactiveMode();
        } catch (IOException e) {
            console.println("Невозможно подключиться к серверу!");
        }
    }

}
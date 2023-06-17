package main;

import commands.available.*;
import exceptions.ExitObligedException;
import managers.CollectionManager;
import managers.CommandCollection;
import managers.DatabaseManager;
import managers.Parser;
import network.Configuration;
import utils.Console;
import utils.DatabaseHandler;
import utils.DatagramServer;
import utils.Printable;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class App {

    //-------------------------------КОНФИГУРАЦИОННЫЕ ПЕРЕМЕННЫЕ-----------------------------------------

    public static final int CONNECTION_TIMEOUT = 60 * 1000;

    public static final String HASHING_ALGORITHM = "MD5";
    public static final String DATABASE_URL = "jdbc:postgresql://localhost:5433/studs";
    public static final String DATABASE_URL_HELIOS = "jdbc:postgresql://pg:5433/studs";
    public static final String DATABASE_CONFIG_PATH = "C:\\Users\\mad_duck\\Documents\\GitHub\\client-server\\server\\dbconfig.cfg";

    //--------------------------------------------------------------------------------------------------

    public static int port = Configuration.PORT;
    public static final int connection_timeout = 60 * 1000;
    private static final Printable console = new Console();

    public static void main(String[] args) {
        /*if (args.length != 1) {
            console.printError("Поместите путь в аргументы командной строки!");
            return;
        }*/

        Parser parser = new Parser(console);
        CollectionManager collectionManager = new CollectionManager(parser);

        try {
            collectionManager.setDragons(parser.convertToDragons());
        } catch (FileNotFoundException e) {
            console.printError("Файл не найден");
        } catch (ExitObligedException e) {

        }

        CommandCollection commandCollection = new CommandCollection();

        commandCollection.addCommand(List.of(

                new Add(collectionManager),
                new AddIfMax(collectionManager),
                new AddIfMin(collectionManager),
                new Clear(collectionManager),
                new ExecuteScript(),
                new Exit(),
                new FilterContainsName(collectionManager),
                new FilterStartsWithDescription(collectionManager),
                new Help(commandCollection),
                new Info(collectionManager),
                new RemoveById(collectionManager),
                new RemoveGreater(collectionManager),
                //new Save(collectionManager, commandCollection),
                new Show(collectionManager),
                new UpdateId(collectionManager),
                new Register(DatabaseHandler.getDatabaseManager()),
                new Ping()
        ));

        DatagramServer server = null;
        try {
            server = new DatagramServer(InetAddress.getLocalHost(), port, connection_timeout, commandCollection, DatabaseHandler.getDatabaseManager());
        } catch (UnknownHostException e) {
            console.printError("Неизвестный хост");
        } catch (SocketException e) {
            console.printError("Случилась ошибка сокета");
        }
        server.run();

    }
}
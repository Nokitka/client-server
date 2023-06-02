import managers.CollectionManager;
import network.Configuration;
import network.DataManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class MainServer {

    private static int port = Configuration.PORT;

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception exception) {
                System.out.println("Не получается получить порт. Используется: " + port);
            }
        }

        DataManager dataManager;
        CollectionManager dragons = new CollectionManager();
        dataManager = dragons;

        DatagramChannel datagramChannel;

        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(port));
            datagramChannel.configureBlocking(false);

            System.out.println("Сервер запущен. Порт: " + port);
        } catch (IOException exception) {
            System.out.println("Ошибка запуска сервера!");
            System.out.println(exception.getMessage());
        }
    }

}

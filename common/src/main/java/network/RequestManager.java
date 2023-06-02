package network;

import data.Dragon;
import network.Response;
import network.Configuration;
import network.ObjectSerializer;
import network.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * The class creates requests for the server
 */
public class RequestManager {
    private DatagramChannel client;
    private int port = Configuration.PORT;
    protected final int max_attempts = 5;

    public RequestManager() {

    }

    public RequestManager(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        DatagramChannel client = DatagramChannel.open();
        SocketAddress clientAddress = new InetSocketAddress(Configuration.IP, Configuration.PORT);
        try {
            client.bind(clientAddress);
        } catch (BindException e) {
            System.out.println("Клиент с таким портом на данном устройстве уже существует.\n" +
                    "Поменяйте порт клиента.");
            System.exit(0);
        }
        SocketAddress serverAddress = new InetSocketAddress(Configuration.IP, port);
        client.connect(serverAddress);
        client.socket().setSoTimeout(3000);
        System.out.println("Клиент запущен:\nАдрес клиента - " + clientAddress + "\nАдрес сервера: " + serverAddress);
        this.client = client;
    }

    /*public Dragon sendDragon(Request request) throws IOException, ClassNotFoundException {

        client.configureBlocking(false);
        byte[] serializedRequest = ObjectSerializer.serializeObject(request);
        ByteBuffer buffer = ByteBuffer.wrap(serializedRequest);

        client.send(buffer, client.getRemoteAddress());
        client.configureBlocking(true);
        DatagramPacket inputPacket = new DatagramPacket(new byte[1024 * 1024], 1024 * 1024);

        client.socket().receive(inputPacket);
        Dragon result = (Dragon) ObjectSerializer.deserializeObject(inputPacket.getData());
        return result;
    }*/

    /**
     * The method gets the command and the arguments that the client entered the console, passes it to the server
     * for execution, tries to connect for 30 seconds
     *
     * @param request request - command entered by the client and its arguments
     * @return result
     */
    public Response sendRequest(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Запрос является null");
        }

        int attempt = 0;
        while (attempt < max_attempts) {
            try {
                Socket socket = new Socket(Configuration.IP, port);

                client.configureBlocking(false);
                byte[] serializedRequest = ObjectSerializer.serializeObject(request);
                ByteBuffer buffer = ByteBuffer.wrap(serializedRequest);

                client.send(buffer, client.getRemoteAddress());
                client.configureBlocking(true);
                DatagramPacket inputPacket = new DatagramPacket(new byte[1024 * 1024], 1024 * 1024);

                client.socket().receive(inputPacket);
                Response result = (Response) ObjectSerializer.deserializeObject(inputPacket.getData());

                attempt = max_attempts;
                return result;

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Не удалось подключиться к серверу. Пробуем еще.");
                e.printStackTrace();
                attempt++;
                try {
                    Thread.sleep(6000);
                } catch (Exception ignored) {
                }
            }
        }
        return new Response(Status.ERROR, "Прошло 30 секунд, сервер не отвечает.");
    }
}
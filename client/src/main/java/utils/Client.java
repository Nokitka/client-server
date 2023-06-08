package utils;

import network.ObjectSerializer;
import network.Request;
import network.Response;
import network.Status;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Client {
    private DatagramChannel client;
    private SocketAddress addr;
    private InetAddress host;
    private int port;
    private Console console;

    private ByteArrayOutputStream bStream;
    private ObjectInputStream iStream;

    public Client(InetAddress host, int port, Console console) throws IOException {
        this.host = host;
        this.port = port;
        this.console = console;

        this.addr = new InetSocketAddress(host, port);
        this.client = DatagramChannel.open().bind(null).connect(addr);
        this.client.configureBlocking(false);
    }

    /**
     * Отправка запроса и получение ответа от сервера
     * @param request запрос от клиента
     * @return ответ, который прислал сервер
     * @throws IOException
     */
    public Response sendAndAskResponse(Request request) throws IOException {
        if (request.isEmpty()) return new Response(Status.WRONG_ARGUMENTS, "Запрос пустой!");

        byte[] serializedMessage = ObjectSerializer.serializeObject(request);
        ByteBuffer buf = ByteBuffer.wrap(serializedMessage);
        client.send(buf, this.addr);
        buf.clear();

        ByteBuffer receiveBuf = ByteBuffer.allocate(65000);
        SocketAddress address = null;
        while (address == null) {
            address = client.receive(receiveBuf);
        }

        receiveBuf.flip();
        byte[] bytes = new byte[receiveBuf.remaining()];
        receiveBuf.get(bytes);

        byte[] toDeserialize = receiveBuf.array();
        Response response = null;

        try {
             response = (Response) ObjectSerializer.deserializeObject(toDeserialize);
        } catch (ClassNotFoundException ex) {
            console.printError(String.valueOf(ex));
        }
        //iStream.close();
        return response;
    }
}
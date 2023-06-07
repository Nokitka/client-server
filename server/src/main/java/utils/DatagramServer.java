package utils;

import managers.CollectionManager;
import managers.Parser;

import java.io.IOException;
import java.net.*;

public class DatagramServer extends Server {
    private final DatagramSocket socket;
    public DatagramServer(InetAddress address, int port, int soTimeout, RequestHandler requestHandler, Parser parser, CollectionManager collectionManager, Printable console) throws SocketException {
        super(new InetSocketAddress(address, port), soTimeout, requestHandler, parser, collectionManager, console);
        this.socket = new DatagramSocket(getAddr());
        this.socket.setSoTimeout(1);
    }


    @Override
    public Pair receiveData() throws IOException {
        byte[] res = new byte[0];
        SocketAddress addr = null;
        Pair pair = new Pair(res, addr);
        byte[] buffer = new byte[1024 * 1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);
        addr = packet.getSocketAddress();
        res = buffer;

        pair.setDataAndAddr(res, addr);
        return pair;
    }


    @Override
    public void sendData(byte[] data, SocketAddress addr) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, addr);
        socket.send(packet);
    }


    @Override
    public void close() {
        socket.close();
    }
}
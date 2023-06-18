package managers;

import network.ObjectSerializer;
import network.Request;
import network.Response;
import network.Status;
import utils.Pair;
import utils.RequestHandler;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager implements Runnable {
    private final CommandCollection commandCollection;
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);   //!!!!!!!!!!!!!!!!!!!!!
    private DatagramSocket socket;
    private static SocketAddress clientAddr;
    private final DatabaseManager databaseManager;

    public ConnectionManager(CommandCollection commandCollection, DatagramSocket socket, DatabaseManager databaseManager) {
        this.commandCollection = commandCollection;
        this.socket = socket;
        this.databaseManager = databaseManager;
    }

//    public static SocketAddress getClientAddr() {
//        return clientAddr;
//    }

    @Override
    public void run() {
        Pair pair = null;
        try{
            pair = receiveData();
        } catch (IOException e){
            return;
        }

        byte[] dataFromClient = pair.getData();
        clientAddr = pair.getAddr();

        try {
            System.out.println("Соединение установлено. Адрес: " + clientAddr.toString());
        } catch (Exception e) {
            System.out.println("Ошибка подключения");
        }

        Request request = null;
        try {
            /*ByteArrayInputStream bais = new ByteArrayInputStream(dataFromClient);
            ObjectInputStream ois = new ObjectInputStream(bais);
            request = (Request) ois.readObject();*/
            request = (Request) ObjectSerializer.deserializeObject(dataFromClient);
            System.out.println("Получен ответ с командой " + request.getCommand());
        } catch (IOException e) {
//                continue;
        } catch (ClassNotFoundException e) {
            System.out.println("Произошла ошибка при чтении полученных данных!");
        }

        Response response;
        if(!databaseManager.confirmUser(request.getUser()) && !request.getCommand().equals("register")){
            System.out.println("Пользователь не одобрен");
            response = new Response(Status.LOGIN_FAILED, "Неверный пользователь!");
            submitNewResponse(new ConnectionManagerPool(response, socket, pair.getAddr()));
        } else {
            FutureManager.addNewFixedThreadPoolFuture(fixedThreadPool.submit(new RequestHandler(commandCollection, request, socket, pair.getAddr())));
        }
    }

    public static void submitNewResponse(ConnectionManagerPool connectionManagerPool) {
        new Thread(() -> {
            byte[] data = null;
            try {
                data = ObjectSerializer.serializeObject(connectionManagerPool.response());
            } catch (IOException e) {
                //e.printStackTrace();
            }
            /*ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            try {
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(connectionManagerPool.response());
                oo.flush();
                oo.close();
            }catch (IOException e) {
//                System.out.println(e);
            }
            byte[] data = bStream.toByteArray();*/

            try {
                sendData(data, connectionManagerPool.socketAddress(), connectionManagerPool.socket());
                System.out.println("Отправлен ответ. IP: " + connectionManagerPool.socketAddress().toString());
            } catch (Exception e) {
            }
        }).start();
    }

    public Pair receiveData() throws IOException {
        byte[] res = new byte[0];
        SocketAddress addr = null;
        Pair pair = new Pair(res, addr);
        byte[] buffer = new byte[65507];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        addr = packet.getSocketAddress();
        res = buffer;
        pair.setDataAndAddr(res, addr);
        return  pair;
    }

    public static void sendData(byte[] data, SocketAddress addr, DatagramSocket socket) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, addr);
        socket.send(packet);
    }

}
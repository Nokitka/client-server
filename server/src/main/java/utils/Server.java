/*
package utils;

import managers.CollectionManager;
import managers.Parser;
import network.ObjectSerializer;
import network.Request;
import network.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

abstract class Server {
    private Printable console;
    private final RequestHandler requestHandler;
    private int soTimeout;
    private Parser parser;
    private CollectionManager collectionManager;

    private InetSocketAddress addr;
    private boolean running = true;

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));

    public Server(InetSocketAddress addr, int soTimeout, RequestHandler requestHandler, Parser parser, CollectionManager collectionManager, Printable console) { //InetAddress host, int port, int soTimeout, RequestHandler handler, parser parser) {
        this.addr = addr;
        this.requestHandler = requestHandler;
        this.soTimeout = soTimeout;
        this.parser = parser;
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public InetSocketAddress getAddr() {
        return addr;
    }

    public abstract Pair receiveData() throws IOException;

    */
/**
     * Отправляет данные клиенту
     *//*

    public abstract void sendData(byte[] data, SocketAddress addr) throws IOException;


    public abstract void close();

    public void stop() {
        running = false;
    }

    public void run() throws IOException {
        while (running) {
            if (scanner.ready()) {
                String line = scanner.readLine();
                if (line.equals("save") || line.equals("s")) {
                    console.println("Коллекция успешно сохранена по пути: " + parser.convertToCSV(collectionManager));
                }
            }
            Pair pair;
            try {
                pair = receiveData();
            } catch (Exception e) {
//                disconnectFromClient();
                continue;
            }

            byte[] dataFromClient = pair.getData();
            SocketAddress clientAddr = pair.getAddr();

            try {
//                connectToClient(clientAddr);
                console.println("Соединение установлено. Адрес: " + clientAddr.toString());
            } catch (Exception e) {
                console.println("Ошибка подключения");
            }

            Request request = null;
            try {
                request = (Request) ObjectSerializer.deserializeObject(dataFromClient);
                console.println("Получен запрос с командой " + request.getCommand());
            } catch (IOException e) {
                continue;
            } catch (ClassNotFoundException e) {
                console.println("Произошла ошибка при чтении полученных данных!");
            }

            Response response = null;
            try {
                response = requestHandler.handler(request);
            } catch (Exception e) {
            }

            byte[] data = ObjectSerializer.serializeObject(response);

            try {
                sendData(data, clientAddr);
                console.println("Отправлен ответ. IP: " + clientAddr.toString());
            } catch (Exception e) {
            }
        }

        close();
    }
}*/

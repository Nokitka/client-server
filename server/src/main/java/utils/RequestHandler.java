package utils;

import exceptions.CommandRuntimeException;
import exceptions.NoSuchCommandException;
import managers.CommandCollection;
import managers.ConnectionManagerPool;
import network.Request;
import network.Response;
import network.Status;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<ConnectionManagerPool> {
    private CommandCollection commandCollection;
    private Request request;
    private DatagramSocket socket;
    private SocketAddress socketAddress;

    public RequestHandler(CommandCollection commandCollection, Request request, DatagramSocket socket, SocketAddress socketAddress) {
        this.commandCollection = commandCollection;
        this.request = request;
        this.socket = socket;
        this.socketAddress = socketAddress;
    }

    @Override
    public ConnectionManagerPool call() {
        try {
            return new ConnectionManagerPool(commandCollection.executeCommand(request), socket, socketAddress);
        } catch (NoSuchCommandException e) {
            return new ConnectionManagerPool(new Response(Status.ERROR, "Такой команды нет в списке"), socket, socketAddress);
        } catch (IllegalArgumentException e) {
            return new ConnectionManagerPool(new Response(Status.WRONG_ARGUMENTS, "Неверное использование аргументов команды"), socket, socketAddress);
        } catch (CommandRuntimeException e) {
            return new ConnectionManagerPool(new Response(Status.ERROR, "Ошибка при исполнении программы"), socket, socketAddress);
        }
    }
}
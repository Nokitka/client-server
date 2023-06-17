package utils;

import managers.CommandCollection;
import managers.ConnectionManager;
import managers.DatabaseManager;
import managers.FutureManager;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ForkJoinPool;

public class DatagramServer {
    private final DatagramSocket socket;
    private final InetSocketAddress addr;
    private int soTimeout;
    private CommandCollection commandCollection;
    private DatabaseManager databaseManager;
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
    public DatagramServer(InetAddress address, int port, int soTimeout, CommandCollection commandCollection, DatabaseManager databaseManager) throws SocketException {
        this.addr = new InetSocketAddress(address, port);
        this.socket = new DatagramSocket(getAddr());
        this.socket.setSoTimeout(1);
        this.soTimeout = soTimeout;
        this.commandCollection = commandCollection;
        this.databaseManager = databaseManager;
    }


    public InetSocketAddress getAddr() {
        return addr;
    }
    public DatagramSocket getSocket() {
        return socket;
    }

    public void run(){
        while (true){
            FutureManager.checkAllFutures();
            forkJoinPool.submit(new ConnectionManager(commandCollection, socket, databaseManager));
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void close() {
        socket.close();
    }

    public void disconnectFromClient() {
        socket.disconnect();
    }
}
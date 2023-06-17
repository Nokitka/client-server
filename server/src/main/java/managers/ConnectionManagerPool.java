package managers;

import network.Response;

import java.net.DatagramSocket;
import java.net.SocketAddress;

public record ConnectionManagerPool(Response response, DatagramSocket socket, SocketAddress socketAddress){
}
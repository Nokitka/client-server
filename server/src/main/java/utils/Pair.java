package utils;

import java.net.SocketAddress;

/**
 * Класс, который хранит в себе адрес и данные в виде байтового массива
 */
public class Pair {
    private byte[] data;
    private SocketAddress addr;

    public Pair(byte[] data, SocketAddress addr) {
        this.data = data;
        this.addr = addr;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public SocketAddress getAddr() {
        return addr;
    }

    public void setAddr(SocketAddress addr) {
        this.addr = addr;
    }

    public void setDataAndAddr(byte[] data, SocketAddress addr) {
        setData(data);
        setAddr(addr);
    }


}
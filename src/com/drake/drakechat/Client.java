package com.drake.drakechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

class Client {
    private static final long serialVersionUID = 1L;

    private String name;

    private String address;
    private int port;

    private int ID = -1;

    //Socket for TCP
    //UDP
    private DatagramSocket socket;
    private InetAddress ip;

    private Thread send;

    Client(String name, String address, int port) {

        this.name = name;
        this.address = address;
        this.port = port;

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    boolean openConnection(String address) {

        try {
            socket = new DatagramSocket();
            ip = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // prevent concurrency
    // send data to server
    void send(final byte[] data) {

        send = new Thread("send") {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        send.start();

    }

    public void close() {
        new Thread(() -> {
            // no one can access socket
            synchronized (socket)

            {
                socket.close();
            }
        }).start();
    }

    // Receive message from server
    String receive() {

        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            // thread, runs until gets data
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message = "";

        try {
            message = new String(packet.getData(), "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

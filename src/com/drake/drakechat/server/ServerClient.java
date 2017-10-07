package com.drake.drakechat.server;

import java.net.InetAddress;

public class ServerClient {

    String name;
    InetAddress address;
    int port;
    private final int ID;
    // amount of unsuccess tries
    public int attempt = 0;


    ServerClient(String name, InetAddress address, int port, final int ID) {

        this.ID = ID;
        this.port = port;
        this.address = address;
        this.name = name;

    }

    int getID() {
        return ID;
    }
}

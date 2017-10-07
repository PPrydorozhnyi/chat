package com.drake.drakechat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private List<ServerClient> clients = new ArrayList<>();

    private DatagramSocket socket;
    // <1000 problems
    private int port;
    private boolean running = false;
    private Thread run;
    private Thread manage;
    private Thread send;
    private Thread receive;

    public Server(int port) {

        this.port = port;

        try {
            // open server on port
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        run = new Thread(this, "Server");
        run.start();
    }

    public void run() {
        running = true;

        System.out.println("Server started on port " + port);

        manageClients();
        receive();

    }

    private void manageClients() {
        manage = new Thread("Manage") {
            @Override
            public void run() {
                while (running) {
                    // managing
                }
            }
        };

        manage.start();
    }

    private void receive() {

        receive = new Thread("Receive") {
            @Override
            public void run() {

                byte[] data = new byte[1024];
                DatagramPacket packet = new DatagramPacket(data, data.length);

                while (running) {
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                    //TODO check
                    System.out.println(clients.get(0).address + ":" + clients.get(0).port);
                }
            }
        };
        receive.start();

    }

    private void process(DatagramPacket packet) {
        String string = new String(packet.getData()).trim();

        if (string.startsWith("/c/")) {

            int id = UniqueIdentifier.getIdentifier();

            clients.add(new ServerClient(string.substring(3, string.length()), packet.getAddress(),
                    packet.getPort(), id));

            System.out.println(string.substring(3, string.length()));
            System.out.println(clients.get(clients.size() - 1).getID());
            String ID = "/c/" + id;
            send(ID.getBytes(), packet.getAddress(), packet.getPort());

        } else if (string.startsWith("/m/")) {

            sendToAll(string);

        } else {
            System.out.println(string);
        }
    }

    private void send(byte[] data, InetAddress address, int port) {

        send = new Thread("Send") {

            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(data, data.length,
                        address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        send.start();
    }

    private void sendToAll(String message) {

        for (ServerClient client : clients) {

            send(message.getBytes(), client.address, client.port);
        }

    }


}

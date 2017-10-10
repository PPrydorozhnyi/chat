package com.drake.drakechat.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    private List<ServerClient> clients = new ArrayList<>();
    private List<Integer> clientResponse = new ArrayList<>();

    private DatagramSocket socket;
    // <1000 problems
    private int port;
    private boolean running = false;
    private Thread run;
    private Thread manage;
    private Thread send;
    private Thread receive;

    private final int MAX_ATTEMPTS = 5;

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

                    //check client connection
                    sendToAll("/i/server");

                    try {
                        //kind of ping
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // foreach makes more exceptions in multi-threading
                    for (int i = 0; i < clients.size(); i++) {
                        ServerClient c = clients.get(i);
                        if (!clientResponse.contains(c.getID()))
                            if (c.attempt >= MAX_ATTEMPTS) {
                                disconnect(c.getID(), false);
                            } else
                                c.attempt++;
                        else {
                            //because of overloading methods by int
                            clientResponse.remove(new Integer(c.getID()));
                            c.attempt = 0;
                        }
                    }

                }
            }
        };

        manage.start();
    }

    private void receive() {

        receive = new Thread("Receive") {
            @Override
            public void run() {

                byte[] data;
                DatagramPacket packet;

                while (running) {
                    data = new byte[1024];
                    packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                    //TODO check
                    //System.out.println(clients.get(0).address + ":" + clients.get(0).port);
                }
            }
        };
        receive.start();

    }

    private void process(DatagramPacket packet) {
        String string = "";

        try {
            string = new String(packet.getData(), "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
            System.out.println("Server: " + string.substring(3, string.length()) + "\n\r");
        } else if (string.startsWith("/d/")) {
            String id = string.split("/d/")[1];
            disconnect(Integer.parseInt(id), true);
        } else if (string.startsWith("/i/")) {
            clientResponse.add(Integer.parseInt(string.split("/i/")[1]));
        } else {
            System.out.println(string);
        }
    }

    private void disconnect(int id, boolean status) {
        ServerClient c = null;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() == id) {
                c = clients.get(i);
                clients.remove(i);
                break;
            }
        }

        String message;
        if (status)
            message = "Client " + c.name + " (" + c.getID() + ")" +
                    c.address + ":" + c.port + " disconnected";
        else
            message = "Client " + c.name + " (" + c.getID() + ")" +
                    c.address + ":" + c.port + " timed out";

        System.out.println(message);
        sendToAll("/m/" + message);

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

package com.drake.drakechat.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server implements Runnable {

    private List<ServerClient> clients = new ArrayList<>();
    private List<Integer> clientResponse = new ArrayList<>();

    private DatagramSocket socket;
    // <1000 problems
    private int port;
    private boolean running = false;

    private final int MAX_ATTEMPTS = 5;

    public Server(int port) {

        this.port = port;

        try {
            // open server on port
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(this, "Server").start();
    }

    public void run() {
        running = true;

        System.out.println("Server started on port " + port);

        manageClients();
        receive();

        Scanner scanner = new Scanner(System.in);
        String text;
        ServerClient c;

        // server commands
        while (running) {
            text = scanner.nextLine();
            if (!text.startsWith("/")) {
                sendToAll("/m/Server: " + text);
            } else {
                text = text.substring(1);

                if (text.equals("clients")) {
                    System.out.println("Clients: ");
                    System.out.println("-----------");
                    for (int i = 0; i < clients.size(); i++) {
                        c = clients.get(i);
                        System.out.println(c.name + "(" + c.getID() + "): " + c.address + ":" + c.port);
                    }
                    System.out.println("-----------");
                } else if (text.startsWith("kick")) {
                    kick(text);
                } else if (text.equals("quit")) {
                    quit();
                } else if (text.equals("help")) {
                    printHelp();
                } else {
                    System.out.println("Unknown command");
                    printHelp();
                }
            }

        }

    }

    private void printHelp() {
        System.out.println("List of all available commands: ");
        System.out.println("--------------------------------");
        System.out.println("/clients - show clients list");
        System.out.println("/kick [name OR id] - kick client with this name or id");
        System.out.println("/help - show help message");
        System.out.println("/quit - shut down the server");
        System.out.println("--------------------------------");
    }

    private void kick(String text) {

        String name;
        boolean num;
        int id;
        ServerClient c;

        name = text.split(" ", 2)[1];
        num = true;
        id = -1;
        try {
            id = Integer.parseInt(name);
        } catch (NumberFormatException e) {
            num = false;
        }

        if (num) {
            for (int i = 0; i < clients.size(); i++) {
                c = clients.get(i);
                if (c.getID() == id) {
                    disconnect(id, DisconnectFlags.KICKED);
                    return;
                }
            }

            System.out.println("Client " + id + "does not exist. Check id");

        } else {

            for (int i = 0; i < clients.size(); i++) {
                c = clients.get(i);
                if (name.equals(c.name)) {
                    disconnect(c.getID(), DisconnectFlags.KICKED);
                    return;
                }
            }

            System.out.println("Client with this name does not exist. Check name");

        }

    }

    private void manageClients() {
        new Thread("Manage") {
            @Override
            public void run() {
                while (running) {

                    //check client connection
                    sendToAll("/s/server");

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
                                disconnect(c.getID(), DisconnectFlags.INCORRECT);
                                System.out.println("how");
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
        }.start();
    }

    // connected users
    private void sendStatus() {
        if (clients.size() == 0)
            return;

        StringBuilder users = new StringBuilder();
        users.append("/u/");

        for (int i = 0; i < clients.size() - 1; i++) {
            users.append(clients.get(i).name);
            users.append("/n/");
        }

        users.append(clients.get(clients.size() - 1).name);
        sendToAll(users.toString());
    }

    private void receive() {

        new Thread("Receive") {
            @Override
            public void run() {

                byte[] data;
                DatagramPacket packet;

                while (running) {
                    data = new byte[1024];
                    packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (SocketException e) {
                        System.out.println("The server has been shut down");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    process(packet);
                    //TODO check
                    //System.out.println(clients.get(0).address + ":" + clients.get(0).port);
                }
            }
        }.start();

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

            sendStatus();

            System.out.println(string.substring(3, string.length()));
            System.out.println(clients.get(clients.size() - 1).getID());
            String ID = "/c/" + id;
            send(ID.getBytes(), packet.getAddress(), packet.getPort());
            sendToAll("/h/" + clients.get(clients.size() - 1).name);

        } else if (string.matches("(/m/|/i/).*")) {                                                  //message || icon
            sendToAll(string);
            System.out.println("on Server: " + string.substring(3, string.length()));
        } else if (string.startsWith("/d/")) {                                                 //disconnect
            String id = string.split("/d/")[1];
            disconnect(Integer.parseInt(id), DisconnectFlags.CORRECT);
        } else if (string.startsWith("/s/")) {                                               //status
            clientResponse.add(Integer.parseInt(string.split("/s/")[1]));
            //System.out.println(Integer.parseInt(string.split("/s/")[1]));
        } else {
            System.out.println(string);
        }
    }

    private void quit() {
        for (int i = 0; i < clients.size(); i++) {
            disconnect(clients.get(i).getID(), DisconnectFlags.CORRECT);
        }

        running = false;
        socket.close();

    }

    private void disconnect(int id, DisconnectFlags status) {
        ServerClient c = null;
        boolean isFound = false;
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() == id) {
                isFound = true;
                c = clients.get(i);
                clients.remove(i);
                break;
            }
        }

        if (!isFound) {
            return;
        }

        String message = "";

        switch (status) {
            case CORRECT:
                message = "Client " + c.name + " (" + c.getID() + ")" +
                        c.address + ":" + c.port + " disconnected";
                break;
            case INCORRECT:
                message = "Client " + c.name + " (" + c.getID() + ")" +
                        c.address + ":" + c.port + " timed out";
                break;
            case KICKED:
                message = "Client " + c.name + " (" + c.getID() + ")" +
                        c.address + ":" + c.port + " kicked";
                try {
                    send("/k/You have been kicked from this server".getBytes("UTF-8"), c.address, c.port);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("default case in disconnect method");
        }


        System.out.println(message);
        sendStatus();
        sendToAll("/m/" + message);

    }

    private void send(byte[] data, InetAddress address, int port) {

        new Thread("Send") {

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

        }.start();

    }

    private void sendToAll(String message) {

        for (ServerClient client : clients) {

            send(message.getBytes(), client.address, client.port);
        }

    }


}

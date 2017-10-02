package com.drake.drakechat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

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
		} catch(SocketException e) {
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
				while (running){
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
				while (running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
					socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String string = new String(packet.getData());
					
					System.out.println(string);
				}
			}
		};
		receive.start();
		
	}
	
	
}

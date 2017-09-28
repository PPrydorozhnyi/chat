package com.drake.drakechat.server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

	private DatagramSocket socket;
	private int port;
	private boolean running = false;
	private Thread run;
	private Thread manage;
	private Thread send;
	private Thread receive;
	
	public Server(int port) {
		
		this.port = port;
		
		try {
			socket = new DatagramSocket();
		} catch(SocketException e) {
			e.printStackTrace();
		}
		run = new Thread(this, "Server");
	}
	
	public void run() {
		running = true;
		
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
		
		receive = new Thread("Receve") {
			@Override
			public void run() {
				while (running) {
					
				}
			}
		};
		
	}
	
}
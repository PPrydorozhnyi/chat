package com.drake.drakechat.server;

import java.net.InetAddress;

public class ServerClient {
	
	public String name;
	public InetAddress address;
	public int port;
	private final int ID;
	// amount of unsuccess tries
	public int attempt = 0;

	
	public ServerClient(String name, InetAddress address, int port, final int ID) {
		
		this.ID = ID;
		this.port = port;
		this.address = address;
		this.name = name;
		
	}
	
	public int getID() {
		return ID;
	}
}

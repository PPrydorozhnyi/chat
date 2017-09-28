package com.drake.drakechat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String name;
	private String address;
	private int port;
	private JTextField txtMessage;
	private JTextArea history;
	
	//Socket for TCP
	//UDP
	private DatagramSocket socket;
	private InetAddress ip;

	public Client(String name, String address, int port) {
		
		this.name = name;
		this.address = address;
		this.port = port;
		
		boolean connect = openConnection(address, port);
		
		if (!connect) {
			System.out.println("Connection failed!");
			console("Connection failed!");
		}
		
		createWindow();
		console("Attempting a connection to " + address + ":" + port + ", user:" + name);
	}
	
	private boolean openConnection(String address,int port) {
		
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
	
private String recieve() {
		
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try{
			// thread, runs until gets data
		socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String message = new String(packet.getData());
		
		return message;
	}
	
	private void createWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Drake`s chat client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{15, 678, 25, 25, 7}; // SUM = 750
		gbl_contentPane.rowHeights = new int[]{20,430, 50};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		history = new JTextArea();
		history.setEditable(false);
		JScrollPane scroll = new JScrollPane(history);
		GridBagConstraints scrollConstrains = new GridBagConstraints();
		scrollConstrains.insets = new Insets(0, 0, 5, 5);
		scrollConstrains.fill = GridBagConstraints.BOTH;
		scrollConstrains.gridx = 0;
		scrollConstrains.gridy = 0;
		// amount of cells which it contains of
		scrollConstrains.gridwidth = 4;
		scrollConstrains.gridheight = 2;
		scrollConstrains.insets = new Insets(0, 5, 0, 0);
		contentPane.add(scroll, scrollConstrains);
		
		txtMessage = new JTextField();
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(txtMessage.getText());
				}
			}
		});
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 0;
		gbc_txtMessage.gridy = 2;
		gbc_txtMessage.gridwidth = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(txtMessage.getText());
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		setVisible(true);
		
		txtMessage.requestFocusInWindow();
		//TODO: add smiles button(7)
	}
	
	private void send(String message) {
		
		if ("".equals(message)) return;
		
		message = name + ": " + message;
		console(message);
		txtMessage.setText("");
	}
	
	public void console(String message) {
		//TODO: add color
		//TODO add image
		history.append(message + "\n\r");
		// to update caret position
		history.setCaretPosition(history.getDocument().getLength());
	}

}
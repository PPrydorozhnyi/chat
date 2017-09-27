package com.drake.drakechat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String name;
	private String address;
	private int port;
	private JTextField txtMessage;

	public Client(String name, String address, int port) {
		
		this.name = name;
		this.address = address;
		this.port = port;
		
		createWindow();
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
		
		JTextArea textHistory = new JTextArea();
		textHistory.setEditable(false);
		GridBagConstraints gbc_textHistory = new GridBagConstraints();
		gbc_textHistory.insets = new Insets(0, 0, 5, 5);
		gbc_textHistory.fill = GridBagConstraints.BOTH;
		gbc_textHistory.gridx = 1;
		gbc_textHistory.gridy = 1;
		// amount of cells which it contains of
		gbc_textHistory.gridwidth = 3;
		gbc_textHistory.insets = new Insets(0, 5, 0, 0);
		contentPane.add(textHistory, gbc_textHistory);
		
		txtMessage = new JTextField();
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.insets = new Insets(0, 0, 0, 5);
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		contentPane.add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);
		setVisible(true);
		
		txtMessage.requestFocus();
		//TODO: add smiles button(7)
	}

}

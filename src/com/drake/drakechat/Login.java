package com.drake.drakechat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField textField;
	private JLabel lblAddress;
	private JLabel lblPort;
	private JTextField textField_1;

	public Login() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 380);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setName("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(67, 67, 165, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(122, 40, 55, 15);
		contentPane.add(lblName);
		
		textField = new JTextField();
		textField.setBounds(67, 142, 165, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblAddress = new JLabel("Address:");
		lblAddress.setBounds(115, 117, 70, 15);
		contentPane.add(lblAddress);
		
		lblPort = new JLabel("Port:");
		lblPort.setBounds(129, 193, 41, 15);
		contentPane.add(lblPort);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(67, 215, 165, 28);
		contentPane.add(textField_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(90, 280, 117, 25);
		contentPane.add(btnLogin);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

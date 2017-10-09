package com.drake.drakechat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName;
    private JTextField txtAddress;
    private JLabel lblAddress;
    private JLabel lblPort;
    private JTextField txtPort;

    private Login() {

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
        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendInfo();
                }
            }
        });
        txtName.setBounds(67, 67, 165, 28);
        contentPane.add(txtName);
        txtName.setColumns(10);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(122, 40, 55, 15);
        contentPane.add(lblName);

        txtAddress = new JTextField();
        txtAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendInfo();
                }
            }
        });
        txtAddress.setBounds(67, 142, 165, 28);
        txtAddress.setText("77.47.190.50");
        contentPane.add(txtAddress);
        txtAddress.setColumns(10);

        lblAddress = new JLabel("Address:");
        lblAddress.setBounds(115, 117, 70, 15);
        contentPane.add(lblAddress);

        lblPort = new JLabel("Port:");
        lblPort.setBounds(129, 193, 41, 15);
        contentPane.add(lblPort);

        txtPort = new JTextField();
        txtPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendInfo();
                }
            }
        });
        txtPort.setColumns(10);
        txtPort.setBounds(67, 215, 165, 28);
        txtPort.setText("8000");
        contentPane.add(txtPort);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendInfo();
            }
        });
        btnLogin.setBounds(90, 280, 117, 25);
        contentPane.add(btnLogin);

    }

    private void sendInfo() {
        String name = txtName.getText();
        String address = txtAddress.getText();
        int port = Integer.parseInt(txtPort.getText());

        login(name, address, port);
    }


    private void login(String name, String address, int port) {
        dispose();
        new ClientWindow(name, address, port);
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

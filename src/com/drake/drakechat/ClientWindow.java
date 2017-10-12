package com.drake.drakechat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by drake on 07/10/17.
 */


public class ClientWindow extends JFrame implements Runnable {

    private JPanel contentPane;
    private JTextField txtMessage;
    private JTextArea history;

    private Client client;
    private Thread run;

    private Thread listen;
    private boolean running = false;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmOnlineUsers;
    private JMenuItem mntmExit;

    private OnlineUsers users;

    public ClientWindow(String name, String address, int port) {
        client = new Client(name, address, port);

        boolean connect = client.openConnection(address);

        if (!connect) {
            System.out.println("Connection failed!");
            console("Connection failed!");
        }

        createWindow();
        console("Attempting a connection to " + address + ": " + port + ", user: " + name);
        String connection = "/c/" + name;
        client.send(connection.getBytes());

        users = new OnlineUsers();

        run = new Thread(this, "Running");
        run.start();
    }

    //TODO: font for text (windows sucks)
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
        setMinimumSize(new Dimension(750, 500));

        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        //mnFile.setBackground(Color.BLACK);
        mnFile.setForeground(Color.BLACK);
        menuBar.add(mnFile);

        mntmOnlineUsers = new JMenuItem("Online users");
        mntmOnlineUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                users.setVisible(true);
            }
        });
        mnFile.add(mntmOnlineUsers);

        mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{15, 678, 25, 25, 7}; // SUM = 750
        gbl_contentPane.rowHeights = new int[]{20, 430, 50}; // SUM = 500
        contentPane.setLayout(gbl_contentPane);

        history = new JTextArea();
        history.setEditable(false);
        history.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(history);
        GridBagConstraints scrollConstrains = new GridBagConstraints();
        scrollConstrains.insets = new Insets(0, 0, 5, 5);
        scrollConstrains.fill = GridBagConstraints.BOTH;
        scrollConstrains.gridx = 0;
        scrollConstrains.gridy = 0;
        // amount of cells which it contains of
        scrollConstrains.gridwidth = 4;
        scrollConstrains.gridheight = 2;
        scrollConstrains.weightx = 1;
        scrollConstrains.weighty = 1;
        scrollConstrains.insets = new Insets(0, 5, 0, 0);
        contentPane.add(scroll, scrollConstrains);

        txtMessage = new JTextField("", 500);
        txtMessage.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (txtMessage.getText().length() == 500) {
                    e.consume();
                }
            }
        });
        txtMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send(txtMessage.getText(), true);
                }
            }
        });
        txtMessage.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        GridBagConstraints gbc_txtMessage = new GridBagConstraints();
        gbc_txtMessage.insets = new Insets(0, 5, 0, 0);
        gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMessage.gridx = 0;
        gbc_txtMessage.gridy = 2;
        gbc_txtMessage.gridwidth = 2;
        gbc_txtMessage.weightx = 1;
        contentPane.add(txtMessage, gbc_txtMessage);
        txtMessage.setColumns(10);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send(txtMessage.getText(), true);
            }
        });
        GridBagConstraints gbc_btnSend = new GridBagConstraints();
        gbc_btnSend.insets = new Insets(0, 0, 0, 5);
        gbc_btnSend.gridx = 3;
        gbc_btnSend.gridy = 2;
        //stay the same size
        gbc_btnSend.weightx = 0;
        gbc_btnSend.weighty = 0;
        contentPane.add(btnSend, gbc_btnSend);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String disconnect = "/d/" + client.getID();
                send(disconnect, false);
                running = false;
                //client.close();
            }
        });

        setVisible(true);

        txtMessage.requestFocusInWindow();
        //TODO: add smiles button(7)
    }

    private void send(String message, boolean text) {

        if ("".equals(message)) return;

        //TODO: check if implements TCP
        //console(message);
        if (text) {
            message = client.getName() + ": " + message;
            message = "/m/" + message;
            txtMessage.setText("");
        }

        try {
            client.send(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void console(String message) {
        //TODO: add color
        //TODO add image
        history.append(message + "\n\r");
        // to update caret position
        history.setCaretPosition(history.getDocument().getLength());
        txtMessage.requestFocusInWindow();
    }

    private void listen() {
        listen = new Thread("Listen") {
            @Override
            public void run() {
                while (running) {
                    String message = client.receive();
                    //System.out.println(message);
                    if (message.startsWith("/c/")) {
                        client.setID(Integer.parseInt(message.substring(3, message.length())));
                        console("Successfully connected to the server ID: " + client.getID());
                    } else if (message.startsWith("/m/")) {
                        String text = message.substring(3);
                        console(text);
                    } else if (message.startsWith("/i/")) {
                        send("/i/" + client.getID(), false);
                    } else if (message.startsWith("/h/")) {
                        console(message.substring(3, message.length()) + " successfully connected to the server");
                    } else if (message.startsWith("/k/")) {
                        String text = message.substring(3);
                        console(text);
                        txtMessage.setText("See ya...");
                        txtMessage.setEditable(false);
                    } else if (message.startsWith("/u/")) {
                        System.out.println(message);
                        String[] u = message.split("/u/|/n/");
                        users.update(Arrays.copyOfRange(u, 1, u.length));
                    }
                }
            }

        };
        listen.start();
    }

    @Override
    public void run() {
        running = true;
        listen();
    }

}
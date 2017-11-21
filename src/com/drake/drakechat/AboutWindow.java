package com.drake.drakechat;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

class AboutWindow extends JFrame {

    AboutWindow() {

        setTitle("About");
        setSize(420, 420);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(AboutWindow.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
        getContentPane().setLayout(null);

        URL resource = AboutWindow.class.getClassLoader().getResource("me.jpg");

        JLabel lblK = new JLabel();
        lblK.setIcon(new ImageIcon(resource));
        lblK.setBounds(0, 0, 160, 160);
        getContentPane().add(lblK);


        JTextArea textArea = new JTextArea("The application was created by Peter Pridorozhny," +
                " the 2nd year student of Igor Sikorsky Kyiv Polytechnic Institute");
        //textArea.setFont(new Font("Serif", Font.ITALIC, 16))
        //textArea.setFont(new Font("Serif", Font.ITALIC, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.ITALIC, 14));
        textArea.setBounds(197, 33, 211, 90);

        getContentPane().add(textArea);

        JTextArea textArea_1 = new JTextArea("Simple chat application with animated emoji" +
                " based on server-client architecture");
        textArea_1.setLineWrap(true);
        textArea_1.setWrapStyleWord(true);
        textArea_1.setOpaque(false);
        textArea_1.setEditable(false);
        textArea_1.setBounds(12, 257, 396, 50);
        getContentPane().add(textArea_1);

        JLabel lblAboutApplication = new JLabel("About application");
        lblAboutApplication.setFont(new Font("Serif", Font.BOLD, 14));
        lblAboutApplication.setBounds(134, 230, 141, 15);
        getContentPane().add(lblAboutApplication);
    }
}

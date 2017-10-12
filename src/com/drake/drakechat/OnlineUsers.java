package com.drake.drakechat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by drake on 12/10/17.
 */
public class OnlineUsers extends JFrame {

    private JPanel contentPane;
    private JList list;

    public OnlineUsers() {
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200, 320);
        setMinimumSize(new Dimension(200, 300));
        setTitle("Online users");
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        list = new JList();
        GridBagConstraints gbc_list = new GridBagConstraints();
        gbc_list.fill = GridBagConstraints.BOTH;
        gbc_list.gridx = 0;
        gbc_list.gridy = 0;
        JScrollPane p = new JScrollPane();
        p.setViewportView(list);
        contentPane.add(p, gbc_list);
        list.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
    }

    public void update(String[] users) {
        list.setListData(users);
    }
}

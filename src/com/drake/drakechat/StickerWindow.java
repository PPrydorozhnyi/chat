package com.drake.drakechat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * stickers window.
 */
class StickerWindow extends JWindow {

    private static final long serialVersionUID = 1L;
    private ClientWindow clientWindow;

    StickerWindow(ClientWindow clientWindow, Stickers stickers) {

        this.clientWindow = clientWindow;

        setAlwaysOnTop(true);
        setType(Window.Type.UTILITY);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(420, 420);
        setMinimumSize(new Dimension(420, 420));
        //setTitle("Stickers");
        setLocationRelativeTo(clientWindow);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{2, 132, 132, 132, 2};  // SUM 400
        gbl_contentPane.rowHeights = new int[]{2, 132, 132, 132, 2}; // SUM 400
//        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
//        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(stickers.getIcon("kot"));
        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("kot");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 1;
        contentPane.add(lblNewLabel, gbc_lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(stickers.getIcon("runner"));
        lblNewLabel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("runner");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 2;
        contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(stickers.getIcon("robot"));
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("robot");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 3;
        contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(stickers.getIcon("ridin"));
        lblNewLabel_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("ridin");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 2;
        gbc_lblNewLabel_3.gridy = 1;
        contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(stickers.getIcon("walk"));
        lblNewLabel_4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("walk");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 2;
        gbc_lblNewLabel_4.gridy = 2;
        contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(stickers.getIcon("hi"));
        lblNewLabel_5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("hi");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_5.gridx = 2;
        gbc_lblNewLabel_5.gridy = 3;
        contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("");
        lblNewLabel_6.setIcon(stickers.getIcon("fish"));
        lblNewLabel_6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("fish");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_6.gridx = 3;
        gbc_lblNewLabel_6.gridy = 1;
        contentPane.add(lblNewLabel_6, gbc_lblNewLabel_6);

        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setIcon(stickers.getIcon("kot2"));
        lblNewLabel_7.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("kot2");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_7.gridx = 3;
        gbc_lblNewLabel_7.gridy = 2;
        contentPane.add(lblNewLabel_7, gbc_lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("");
        lblNewLabel_8.setIcon(stickers.getIcon("komp"));
        lblNewLabel_8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clientWindow.sendSticker("komp");
                clientWindow.setShowStickers();
                dispose();
            }
        });
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.gridx = 3;
        gbc_lblNewLabel_8.gridy = 3;
        contentPane.add(lblNewLabel_8, gbc_lblNewLabel_8);


    }

    void updateLocation() {
        setLocationRelativeTo(clientWindow);
    }
}
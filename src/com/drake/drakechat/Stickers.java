package com.drake.drakechat;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by drake on 22/10/17.
 */
public class Stickers {
    private static Stickers ourInstance;
    private Map<String, Icon> icons;

    public static Stickers getInstance() {
        if (ourInstance == null)
            ourInstance = new Stickers();

        return ourInstance;
    }

    private Stickers() {
        icons = new HashMap<>();
    }

    private void load(String resorcePath, String name) {
        URL resource = Stickers.class.getClassLoader().getResource(resorcePath);
        //System.out.println(resource);
        Icon icon = null;
        try {
            icon = new ImageIcon(resource);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        icons.put(name, icon);
    }

    public Icon getIcon(String name) {
        return icons.get(name);
    }

    public void loadAll() {
        load("sticker.png", "angry");
        load("kot.gif", "kot");
    }
}

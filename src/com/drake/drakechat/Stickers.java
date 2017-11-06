package com.drake.drakechat;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Stickers loader
 */
class Stickers {
    private static Stickers ourInstance;
    private Map<String, Icon> icons;

    static Stickers getInstance() {
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

    Icon getIcon(String name) {
        return icons.get(name);
    }

    void loadAll() {
        //load("sticker.png", "angry");
        load("kot.gif", "kot");
        load("runner.gif", "runner");
        load("robot.gif", "robot");
        load("ridin.gif", "ridin");
        load("walkingMan.gif", "walk");
        load("hi.gif", "hi");
        load("fish.gif", "fish");
        load("giphy.gif", "kot2");
        load("komp.gif", "komp");
    }
}

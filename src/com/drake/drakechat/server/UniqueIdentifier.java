package com.drake.drakechat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueIdentifier {

    private static List<Integer> IDS = new ArrayList<Integer>();
    private static final int RANGE = 10000;

    private static int INDEX = 0;

    static {
        for (int i = 0; i < RANGE; i++)
            IDS.add(i);
        Collections.shuffle(IDS);
    }

    private UniqueIdentifier() {
    }

    //TODO return index when client disconnects

    public static int getIdentifier() {

        if (INDEX > IDS.size() - 1) INDEX = 0;

        return IDS.get(INDEX++);

    }
}

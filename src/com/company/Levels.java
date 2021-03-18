package com.company;

import java.util.ArrayList;


public class Levels {
    public boolean shouldShowFlask = false;

    public ArrayList<ArrayList<String>> initialiseTwoDArray(int length, int height) {
        ArrayList<ArrayList<String>> TwoD = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            TwoD.add(new ArrayList<>(length));
            for (int j = 0; j < height; j++) {
                TwoD.get(i).add("X");
            }


        }

        return TwoD;
    }
}

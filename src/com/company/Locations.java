package com.company;

import java.util.ArrayList;

public class Locations {
    int x;
    int y;
    boolean showOb = false;
    String name;

    public void moveX(int moveXModifier){
        this.x += moveXModifier;
        System.out.println(this.x);
    }

    public void moveY(int moveYModifier){
        this.y += moveYModifier;
        System.out.println(this.y);
    }

    public void isShow(ArrayList<ArrayList<String>> TwoD){
        if (showOb){
            TwoD.get(this.x).set(this.y,this.name);
        }
    }

    public void setlocatio(ArrayList<ArrayList<String>> TwoD){
        TwoD.get(this.x).set(this.y, this.name);
    }

    public void removeLocation(ArrayList<ArrayList<String>> TwoD){
        TwoD.get(this.x).set(this.y, "X");
    }
}
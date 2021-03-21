package com.company;

import java.util.ArrayList;

public class Locations {
    int x;
    int y;
    boolean showOb = false;
    boolean testing = true;
    String name;


    public void moveX(int moveXModifier){
        this.x += moveXModifier;
    }

    public void moveY(int moveYModifier){
        this.y += moveYModifier;
    }

    public void isShow(ArrayList<ArrayList<String>> TwoD){
        if (this.showOb || this.testing){
            TwoD.get(this.x).set(this.y,this.name);
        }
    }

    public void setlocation(ArrayList<ArrayList<String>> TwoD){
        TwoD.get(this.x).set(this.y, this.name);
    }

    public void removeLocation(ArrayList<ArrayList<String>> TwoD){
        TwoD.get(this.x).set(this.y, "X");
    }
}
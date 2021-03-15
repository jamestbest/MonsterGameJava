package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to the monster game!");

        // create a 2d array to store the map with 'x' for area and 'M'.'P'
        // create a 3d array to store the co-ordinates so they can be selected by each object
        // create an object array to store the x,y and moving methods

        int height = 5;
        int width = 5;


        ArrayList<ArrayList<String>> TwoDMap = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<ArrayList<Integer>>> ThreeDMap = new ArrayList<ArrayList<ArrayList<Integer>>>(height);

        for (int i = 0; i < height; i++) {
            ThreeDMap.add(new ArrayList<>(height));
            for (int j = 0; j < width; j++) {
                ThreeDMap.get(i).add(new ArrayList<>(width));
                ThreeDMap.get(i).get(j).add(i);
                ThreeDMap.get(i).get(j).add(j);
            }
        }


        System.out.println(ThreeDMap.get(2).get(3));
        System.out.println(ThreeDMap.get(4).get(1));


    }
}

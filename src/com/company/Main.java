package com.company;

import javax.sound.sampled.AudioInputStream;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main {


    private static Locations monster = new Locations();
    private static Locations flask = new Locations();
    private static Locations trapOne = new Locations();
    private static Locations trapTwo = new Locations();
    private static Locations player = new Locations();

    private static int length = 10;
    private static int height = 10;
    private static ArrayList<ArrayList<String>> TwoD = initialiseTwoDArray(length, height);


    public static void main(String[] args) {
        System.out.println("Welcome to the monster game");

        int length = 10;
        int height = 10;

        ArrayList<ArrayList<ArrayList<Integer>>> ThreeD = initialiseThreeDArray(length, height);


        int[] temp = Createlocation(ThreeD);
        monster.x = temp[0];
        monster.y = temp[1];
        monster.name = "M";

        temp = Createlocation(ThreeD);
        flask.x = temp[0];
        flask.y = temp[1];
        flask.name = "F";

        temp = Createlocation(ThreeD);
        trapOne.x = temp[0];
        trapOne.y = temp[1];
        trapOne.name = "T";

        temp = Createlocation(ThreeD);
        trapTwo.x = temp[0];
        trapTwo.y = temp[1];
        trapTwo.name = "T";

        temp = Createlocation(ThreeD);
        player.x = temp[0];
        player.y = temp[1];
        player.name = "P";


        boolean end = false;
        player.showOb = true;

        player.isShow(TwoD);
        flask.isShow(TwoD);
        monster.isShow(TwoD);
        trapOne.isShow(TwoD);
        trapTwo.isShow(TwoD);

        while (!end) {
            showTwoDRows(TwoD);
            end = Choice();


        }


    }

    public static int[] Createlocation(ArrayList<ArrayList<ArrayList<Integer>>> ThreeD) {
        Random random = new Random();
        int x = random.nextInt(ThreeD.size());
        int y = random.nextInt(ThreeD.get(x).size());

        int[] coords;
        coords = new int[]{ThreeD.get(x).get(y).get(0), ThreeD.get(x).get(y).get(1)};
        ThreeD.get(x).remove(y);


        return coords;
    }

    public static void showThreeDIndividual(ArrayList<ArrayList<ArrayList<Integer>>> ThreeD) {
        for (int i = 0; i < ThreeD.size(); i++) {
            for (int j = 0; j < ThreeD.get(i).size(); j++) {
                System.out.println(ThreeD.get(i).get(j));
            }
        }
    }

    public static void showThreeDRows(ArrayList<ArrayList<ArrayList<Integer>>> ThreeD) {
        for (int i = 0; i < ThreeD.size(); i++) {
            System.out.println(ThreeD.get(i));
        }
    }


    public static ArrayList<ArrayList<ArrayList<Integer>>> initialiseThreeDArray(int length, int height) {
        ArrayList<ArrayList<ArrayList<Integer>>> ThreeD = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            ThreeD.add(new ArrayList<>(length));
            for (int j = 0; j < height; j++) {
                ThreeD.get(i).add(new ArrayList<>(height));
                ThreeD.get(i).get(j).add(i);
                ThreeD.get(i).get(j).add(j);
            }
        }

        return ThreeD;

    }

    public static ArrayList<ArrayList<String>> initialiseTwoDArray(int length, int height) {
        ArrayList<ArrayList<String>> TwoD = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            TwoD.add(new ArrayList<>(length));
            for (int j = 0; j < height; j++) {
                TwoD.get(i).add("X");
            }


        }

        return TwoD;
    }

    public static void showTwoDRows(ArrayList<ArrayList<String>> TwoD) {
        for (int i = 0; i < TwoD.size(); i++) {
            System.out.println(TwoD.get(i));
        }
    }

    public static boolean Choice() {
        System.out.println("Please enter WASD to move or F to exit the program");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();

        if (choice.toLowerCase().equals("f")) {
            return true;
        } else if ((choice.equalsIgnoreCase("w")) || (choice.equalsIgnoreCase("a")) || (choice.equalsIgnoreCase("s")) || (choice.equalsIgnoreCase("d"))) {
            movingPlayer(choice.toLowerCase());
            return false;
        } else {
            System.out.println("invalid input");
            Choice();
        }
        return false;
    }

    public static void movingPlayer(String movement) {
        player.removeLocation(TwoD);
        if (movement.equals("w")) {
            player.moveX(-1);
        }
        else if (movement.equals("d")){
            player.moveY(1);
        }
        else if(movement.equals("s")){
            player.moveX(1);
        }
        else if (movement.equals("a")){
            player.moveY(-1);
        }
        player.setlocatio(TwoD);
    }


}

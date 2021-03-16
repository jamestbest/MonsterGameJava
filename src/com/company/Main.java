package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {


    private static final Locations monster = new Locations();
    private static final Locations flask = new Locations();
    private static final Locations trapOne = new Locations();
    private static final Locations trapTwo = new Locations();
    private static final Locations player = new Locations();

    private static final int length = 5;
    private static final int height = 5;
    private static int waitTurns = 2;
    private static final ArrayList<ArrayList<String>> TwoD = initialiseTwoDArray(length, height);
    private static final ArrayList<ArrayList<ArrayList<Integer>>> ThreeD = initialiseThreeDArray(length, height);

    public static void main(String[] args) {
        System.out.println("Welcome to the monster game");

        initialiseAllObjects();

        boolean end = false;
        boolean flaskWin = false;
        boolean monsterWin = false;

        player.showOb = true;

        player.isShow(TwoD);
        flask.isShow(TwoD);
        monster.isShow(TwoD);
        trapOne.isShow(TwoD);
        trapTwo.isShow(TwoD);

        while (!end) {

            showTwoDRows(TwoD);
            end = Choice();

            updateObjectsShown();

            if (checkIfPlayerOnMonster() || monster.showOb){
                monster.showOb = true;

            }
            if (checkIfPlayerOnFlask()){
                flaskWin = true;
                end = true;
            }
            if (checkIfPlayerOnTrap() && !trapOne.showOb){
                System.out.println("You have sprung one of the monsters traps, beware!");
                monster.showOb = true;
                trapOne.showOb = true;
                trapTwo.showOb = true;
                updateObjectsShown();

                //when the player lands on a trap the player needs to move away from it
                //as they are currently hidden when it spawns
                //or place the player ontop of the flask when it spawns.

            }
            if (monster.showOb){
                if (waitTurns == 0){
                    movingMonster();
                }
                else{
                    waitTurns--;
                }
                if(checkIfPlayerOnMonster()){
                    end = true;
                    monsterWin = true;
                }


            }


        }
        showTwoDRows(TwoD);
        if(flaskWin){
            System.out.println("You managed to escape with the flask in hand, Well done!");
        }
        else if(monsterWin){
            System.out.println("Whilst searching for the magical flask you perished to the great beast below!");
        }


    }

    public static void updateObjectsShown(){
        monster.isShow(TwoD);
        trapOne.isShow(TwoD);
        trapTwo.isShow(TwoD);
        flask.isShow(TwoD);
        player.isShow(TwoD);
    }

    public static void movingMonster(){
        int movement = 2;
        for (int i = 0; i < movement; i++) {
            monster.removeLocation(TwoD);
            if (player.x < monster.x){
                monster.moveX(-1);
            }
            else if(player.x > monster.x){
                monster.moveX(1);
            }
            if (player.y < monster.y){
                monster.moveY(-1);
            }
            else if(player.y > monster.y){
                monster.moveY(1);
            }
            monster.setlocatio(TwoD);
            movement--;
        }

    }

    public static boolean checkIfPlayerOnMonster(){
        if ((player.x == monster.x) && (player.y == monster.y)){
            if (!monster.showOb){
                System.out.println("You landed on the monster");
                player.removeLocation(TwoD);
                monster.setlocatio(TwoD);
                movePlayerRandomly(1);
                player.setlocatio(TwoD);
            }
            return true;

        }
        else{
            return false;
        }
    }

    public static void movePlayerRandomly(int numberOfMoves){
        for (int i = 0; i < numberOfMoves; i++) {
            if(player.x > 0){
                player.moveX(-1);
            }
            else if(player.x < height-1){
                player.moveX(1);
            }
            else if(player.y > 0){
                player.moveY(-1);
            }
            else if (player.y < length -1){
                player.moveY(1);
            }

        }
    }

    public static boolean checkIfPlayerOnTrap(){
        if (player.x == trapOne.x && player.y == trapOne.y){
            return true;
        }
        else return player.x == trapTwo.x && player.y == trapTwo.y;
    }

    public static boolean checkIfPlayerOnFlask(){
        return player.x == flask.x && player.y == flask.y;
    }

    public static void initialiseAllObjects(){
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
        for (ArrayList<ArrayList<Integer>> arrayLists : ThreeD) {
            for (ArrayList<Integer> arrayList : arrayLists) {
                System.out.println(arrayList);
            }
        }
    }

    public static void showThreeDRows(ArrayList<ArrayList<ArrayList<Integer>>> ThreeD) {
        for (ArrayList<ArrayList<Integer>> arrayLists : ThreeD) {
            System.out.println(arrayLists);
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
        for (ArrayList<String> strings : TwoD) {
            System.out.println(strings);
        }
    }

    public static boolean Choice() {
        System.out.println("Please enter WASD to move or F to exit the program");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();

        if (choice.equalsIgnoreCase("f")) {
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
        switch (movement) {
            case "w":
                if (player.x > 0) {
                    player.moveX(-1);
                }
                break;
            case "d":
                if (player.y < length - 1) {
                    player.moveY(1);
                }

                break;
            case "s":
                if (player.x < height - 1) {
                    player.moveX(1);
                }
                break;
            case "a":
                if (player.y > 0) {
                    player.moveY(-1);
                }
                break;
        }
        player.setlocatio(TwoD);
    }


}

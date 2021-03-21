package com.company;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.SplittableRandom;

public class Main {


    private static final Locations monster = new Locations();
    private static final Locations flask = new Locations();
    private static final Locations trapOne = new Locations();
    private static final Locations trapTwo = new Locations();
    private static final Locations player = new Locations();
    private static final Locations ladderDown = new Locations();
    private static final Locations ladderUp = new Locations();

    private static Levels LevelsArray[] = new Levels[6];

    // to go back up the ladder the map must be saved to a location
    public static int length = 10;
    public static int height = 10;
    private static int waitTurns = 2;
    private static int numberOfLevelsLoaded = 0;
    private static int currentLevel = 0;
    private static ArrayList<ArrayList<String>> TwoD;
    private static ArrayList<ArrayList<ArrayList<Integer>>> ThreeD = initialiseThreeDArray(length, height);
    private static boolean end = false;
    private static boolean flaskWin = false;
    private static boolean monsterWin = false;
    private static boolean fullend = false;



    // TODO: 18/03/2021 also need to add the ability to move back up the ladder to load an old level with the same info 
    // TODO: 18/03/2021 also need to add the monster[] to have a different amount of monster on each level 
    // TODO: 18/03/2021 maybe have the player grab the flask and then have to travel back up the levels
    // TODO: 19/03/2021 bug: when you win the down will appear. (May have been fixed)
    // FIXED: 19/03/2021 when you trigger the monster in an old level the monster won't give you any chance to escape on the next level, you will touch it and then the game ends
    // TODO: 19/03/2021 when loading through a 'U' the locations are not saved
    // TODO: 19/03/2021 either change the class to save the objects or go through each item in the Array and check for
    // TODO: 19/03/2021 'M','P' ect.. and then save them as the new locations
    // FIXED: 19/03/2021 the height and length also don't change as you move up as they are based on the number of levels loaded
    // FIXED: 19/03/2021 instead have the height and length = to base + currentLevel


    public static void main(String[] args) {
        LevelsArray[numberOfLevelsLoaded] = new Levels();
        TwoD = LevelsArray[numberOfLevelsLoaded].initialiseTwoDArray(length,height);
        System.out.println("Welcome to the monster game");
        createNewLevel();
        while (!fullend){
            while (!end) {
                System.out.println("checking ladder" + currentLevel);
                if (currentLevel > 3){
                    System.out.println("hiding ladder");
                    ladderDown.showOb = false;
                    ladderDown.removeLocation(TwoD);
                    updateObjectsShown();
                }

                updateObjectsShown();

                showTwoDRows(TwoD);
                end = Choice();
                System.out.println(LevelsArray.length);

                updateObjectsShown();

                if (checkIfPlayerOnMonster() || monster.showOb){
                    monster.showOb = true;

                }
                if (checkIfPlayerOnFlask()&& currentLevel == 4){
                    flaskWin = true;
                    end = true;
                    fullend = true;
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




                if(checkIfPlayerOnLadderDown() && ladderDown.showOb){
                    end = true;
                    if (!fullend && currentLevel != 4){
                        if (currentLevel != 4){
                            System.out.println("creating new level");
                            currentLevel++;
                            System.out.println("\n\n\n");
                            System.out.println("You venture on to find the flask!");
                            createNewLevel();
                            end = false;
                        }


                    }

                }
                if(checkIfPlayerOnLadderUp()){
                    end = true;
                    if (currentLevel != 0){
                        System.out.println("going up");
                        TwoD = LevelsArray[currentLevel-1].initialiseTwoDArray(length,height);
                    }

                }


            }
            showAllObjects();
            updateObjectsShown();
            showTwoDRows(TwoD);
            if(flaskWin){
                System.out.println("You managed to escape with the flask in hand, Well done!");
            }
            else if(monsterWin && !checkIfPlayerOnLadderDown()){
                fullend = true;
                System.out.println("Whilst searching for the magical flask you perished to the great beast below!");
            }
            else{
                end = false;
            }



        }

    }

    public static void saveLevel(){
        System.out.println("saving the level information");
        //create a new file and then add the information about the x and y's of each location
        //create a new method to read the information
        //don't save the level if it has already been saved
        //create a new class for the levels and save the array to it <---
        //this will require changing alot of code but will make it much easier to check if the
        //level has already been loaded, stopping it from being overwritten.

        //Most likely not going to use this method, instead the levels are stored inside the
        //LevelsArray where you can easily index the next one needed and also check how many
        //have been stored, however it may also be a good idea to create a save for all the levels
        //and allow players to save and load the game at any time, however this may create some
        //issues with how the game is currently creating new levels and so info such as no. levels
        //loaded must also be saved.
    }


    public static void showAllObjects(){
        player.showOb = true;
        monster.showOb = true;
        flask.showOb = true;
        trapOne.showOb = true;
        trapTwo.showOb = true;
        ladderUp.showOb = true;
        ladderDown.showOb = true;
    }

    public static void hideAllObjects(){
        player.showOb = false;
        monster.showOb = false;
        flask.showOb = false;
        trapOne.showOb = false;
        trapTwo.showOb = false;
        ladderUp.showOb = false;
        ladderDown.showOb = false;
    }

    public static void createNewLevel(){
        waitTurns = 2;
        numberOfLevelsLoaded++;
        TwoD.clear();
        ThreeD.clear();
        LevelsArray[numberOfLevelsLoaded] = new Levels();
        TwoD = LevelsArray[numberOfLevelsLoaded].initialiseTwoDArray(length+currentLevel,height+currentLevel);
        ThreeD = initialiseThreeDArray(length+currentLevel,height+currentLevel);
        hideAllObjects();
        initialiseAllObjects();
        player.showOb = true;
        ladderDown.showOb = true;
        ladderUp.showOb = currentLevel > 1;
        updateObjectsShown();

    }

    public static void updateObjectsShown(){
        monster.isShow(TwoD);
        trapOne.isShow(TwoD);
        trapTwo.isShow(TwoD);
        flask.isShow(TwoD);
        player.isShow(TwoD);
        ladderDown.isShow(TwoD);
        ladderUp.isShow(TwoD);
    }

    public static void movingMonster(){
        int movement = 2;
        for (int i = 0; i < movement; i++) {
            System.out.println("Monster moving");
            monster.removeLocation(TwoD);
            if (player.x < monster.x){
                monster.moveX(-1);
            }
            else if(player.x > monster.x){
                monster.moveX(1);
            }
            else if (player.y < monster.y){
                monster.moveY(-1);
            }
            else if(player.y > monster.y){
                monster.moveY(1);
            }
            monster.setlocation(TwoD);
        }

    }

    public static boolean checkIfPlayerOnLadderUp(){
        if(player.x == ladderUp.x && player.y == ladderUp.y && currentLevel > 1) {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkIfPlayerOnMonster(){
        if ((player.x == monster.x) && (player.y == monster.y)){
            if (!monster.showOb){
                System.out.println("You landed on the monster");
                player.removeLocation(TwoD);
                monster.setlocation(TwoD);
                movePlayerRandomly(1);
                player.setlocation(TwoD);
            }
            return true;

        }
        else{
            return false;
        }
    }

    public static boolean checkIfPlayerOnLadderDown(){
        return player.x == ladderDown.x && player.y == ladderDown.y;
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
        if (currentLevel == 4){
            return player.x == flask.x && player.y == flask.y;
        }
        else{
            return false;
        }
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

        temp = Createlocation(ThreeD);
        ladderDown.x = temp[0];
        ladderDown.y = temp[1];
        ladderDown.name = "D";

        temp = Createlocation(ThreeD);
        ladderUp.x = temp[0];
        ladderUp.y = temp[1];
        ladderUp.name = "U";

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
            fullend = true;
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
        player.setlocation(TwoD);
    }

}

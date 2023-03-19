package com.company;

import java.util.Scanner;

public class App {
    public static boolean xWon = false;
    public static boolean oWon = false;
    public static boolean finished = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean xTurn = true;
        boolean oTurn = false;
        String input = "_________";
        int emptyCells = 0;


        // A multidimensional array is created using the 3 groups
        String[][] multiDArray = createMultiDArray(input.substring(0,3), input.substring(3,6), input.substring(6));

        print(multiDArray);

        int firstCooridinate = 0;
        int secondCoordinate = 0;

        while (true) {
            boolean nextIteration = false;
            boolean gameOver = false;


            for (int i = 0; i < multiDArray.length; i++) {
                for (int j = 0; j < multiDArray[i].length; j++) {
                    if (multiDArray[i][j].equals("_")) {
                        emptyCells ++;
                    }
                }
            }

            if (emptyCells <= 1) {
                for (int i = 0; i < multiDArray.length; i++) {
                    for (int j = 0; j < multiDArray[i].length; j++) {
                        if (multiDArray[i][j].equals("_")) {
                            if (xTurn && !oTurn) {
                                multiDArray[i][j] = "X";
                                checkConditions(multiDArray);
                            } else {
                                multiDArray[i][j] = "X";
                                checkConditions(multiDArray);
                            }
                        }
                    }
                }
            }

            // System.out.println("Enter coordinates");
            if (scanner.hasNextInt()) {
                firstCooridinate = scanner.nextInt() - 1;
                secondCoordinate = scanner.nextInt() - 1;
                if (firstCooridinate > 2 || firstCooridinate < 0 || secondCoordinate > 2 || secondCoordinate < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                else if (multiDArray[firstCooridinate][secondCoordinate].equalsIgnoreCase("x") || multiDArray[firstCooridinate][secondCoordinate].equalsIgnoreCase("o")) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
            } else {
                System.out.println("Numbers Only");
                nextIteration = true;
                scanner.next();
//                scanner.next();
                continue;
            }

            if (nextIteration) {
                continue;
            }

            if (xTurn && !oTurn) {
                multiDArray[firstCooridinate][secondCoordinate] = "X";
                xTurn = false;
                oTurn = true;
                firstCooridinate = 0;
                secondCoordinate = 0;
                print(multiDArray);
            } else {
                multiDArray[firstCooridinate][secondCoordinate] = "O";
                xTurn = true;
                oTurn = false;
                firstCooridinate = 0;
                secondCoordinate = 0;
                print(multiDArray);
            }

            checkConditions(multiDArray);

            if (App.finished) {
                gameOver = true;
            }

            if (gameOver) {
                break;
            }


        }

    }

    // MAIN ENDS HERE
    // ==========================================================================

    // ******** METHODS ********
    //        ----------

    // Check win conditions:
    public static void checkConditions(String[][] input){

        int xCount = 0;
        int oCount = 0;
        boolean hasEmptyCells = false;
        int diff;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j].equalsIgnoreCase("x")) {
                    xCount ++;
                } else if (input[i][j].equalsIgnoreCase("o")) {
                    oCount ++;
                } else if (input[i][j].equals("_")) {
//                    input[i][j] = " ";

                    hasEmptyCells = true;
                }
            }
        }

        if (!hasEmptyCells) {
            App.finished = true;
        }

        diff = Math.abs(xCount - oCount);

        checkDiagonal(input);
        checkHorizontal(input);
        checkVertical(input);

        if ((App.xWon && App.oWon) || diff >= 2) {
            System.out.println("Impossible");
            App.finished = true;
        }
        else if (App.xWon) {
            System.out.println("X wins");
            App.finished = true;
        }
        else if (App.oWon) {
            System.out.println("O wins");
            App.finished = true;
        }
        else if (!App.xWon && !App.oWon && !hasEmptyCells) {
            System.out.println("Draw");
            App.finished = true;
        }
        else if (!App.xWon && !App.oWon && hasEmptyCells) {
            // System.out.println("Game not finished");
        }


    }


    // ===========================================================================

    // GAME STATE METHODS:

    // Check Diagonal win states
    public static void checkDiagonal(String[][] multiDArray){
        // CREATE A METHOD FOR THE FOLLOWING CODE:
        String diagonalLeftToRight = "";
        String[] arrayDiagonalLeftToRight = {multiDArray[0][0], multiDArray[1][1], multiDArray[2][2]};

        for (int i = 0; i < arrayDiagonalLeftToRight.length; i++) {
            diagonalLeftToRight += arrayDiagonalLeftToRight[i];
        }

        String diagonalRightToLeft = "";
        String[] arrayDiagonalRightToLeft = {multiDArray[0][2], multiDArray[1][1], multiDArray[2][0]};

        for (int i = 0; i < arrayDiagonalRightToLeft.length; i++) {
            diagonalRightToLeft += arrayDiagonalRightToLeft[i];
        }

        if (diagonalLeftToRight.equalsIgnoreCase("xxx") || diagonalRightToLeft.equalsIgnoreCase("xxx")) {

            App.xWon = true;
        } else if (diagonalLeftToRight.equalsIgnoreCase("ooo") || diagonalRightToLeft.equalsIgnoreCase("ooo")) {
            App.oWon = true;
        }

    }

    // Used to check Horizontal win states
    public static void checkHorizontal(String[][] multiDArray){
        boolean finished = false;
        // This loop converts each inner array into a string
        for (int i = 0; i < multiDArray.length; i++) {
            String row = "";
            for(int j = 0; j < multiDArray.length; j++) {
                row += multiDArray[i][j];
            }
            if(row.equalsIgnoreCase("xxx")){
                App.xWon = true;
            }
            if (row.equalsIgnoreCase("ooo")) {
                App.oWon = true;
            }
        }
    }

    // Used to check Vertical win states
    public static void checkVertical(String[][] multiDArray){
        for(int i = 0; i < multiDArray.length; i++){
            String col = "";
            for(int j = 0; j < multiDArray.length; j++){
                col += multiDArray[j][i];
            }
            if (col.equalsIgnoreCase("xxx")) {
                App.xWon = true;
            }
            if (col.equalsIgnoreCase("ooo")) {
                App.oWon = true;
            }
        }

    }

    // ==========================================

    // METHODS FOR CREATING ARRAYS:

    // Create a multidimensional array of [3][3] length representing a grid
    public static String[][] createMultiDArray(String first, String second, String        third){
        String[][] multiDArray = new String[3][3];

        String[] firstRow = createArray(first);
        String[] secondRow = createArray(second);
        String[] thirdRow = createArray(third);

        multiDArray[0] = firstRow;
        multiDArray[1] = secondRow;
        multiDArray[2] = thirdRow;

        return multiDArray;
    }

    // Creates a 1d array (Only used in createMultiDArray())
    public static String[] createArray(String str){
        String[] arr = new String[str.length()];

        for (int i = 0; i < arr.length; i++) {
            String temp = str.valueOf(str.charAt(i));
            arr[i] = temp;
        }
        return arr;
    }

    // ============================================================================
    // PRINTING METHODS:

    public static void print(String[][] multiDArray) {
        System.out.println("---------");
        for (int i = 0; i < multiDArray.length; i++) {
            String row = "";
            for (int j = 0; j < multiDArray[i].length; j++) {
//                if (multiDArray[i][j].equals("_")) {
//                    multiDArray[i][j] = " ";
//                }

                row += multiDArray[i][j];
                row += " ";
            }
            System.out.print("| ");
            System.out.print(row);
            System.out.println("|");
        }
        System.out.println("---------");
    }
}
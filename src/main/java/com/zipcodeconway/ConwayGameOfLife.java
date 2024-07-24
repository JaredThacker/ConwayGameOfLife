package com.zipcodeconway;

import java.util.Random;

public class ConwayGameOfLife {
    public SimpleWindow displayWindow;
    public int dimension;
    public int[][] currentState;

    public ConwayGameOfLife(Integer dimension) {
        this.displayWindow = new SimpleWindow(dimension);
        this.dimension = dimension;
        currentState = createRandomStart(dimension);
     }

    public ConwayGameOfLife(Integer dimension, int[][] startMatrix) {
        this.displayWindow = new SimpleWindow(dimension);
        this.dimension = dimension;
        this.currentState = startMatrix;
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        sim.createRandomStart(50);
        sim.simulate(100);
    }

    private int[][] createRandomStart(Integer dimension) {
        Random random = new Random(System.currentTimeMillis());
        int[][] randomSeed = new int[dimension][dimension];
        for (int i = 0; i < randomSeed.length; i++){
            for (int j = 0; j < randomSeed.length; j++){
                int randomNum = random.nextInt(2);
                randomSeed[i][j] = randomNum;
            }
        }
        return randomSeed;
    }

    public int[][] simulate(Integer maxGenerations) {
        int[][] nextGen = new int[dimension][dimension];
        for(int i = 0; i <= maxGenerations; i++){
            for(int j = 0; j < dimension; j++){
                for(int k = 0; k < dimension; k++){

                    int updatedStatus = isAlive(j, k, currentState);
                    nextGen[j][k] = updatedStatus;
                }
            }
            copyAndZeroOut(nextGen, currentState);
            this.displayWindow.display(currentState, i);
            this.displayWindow.sleep(100);
        }
        return nextGen;
    }

    public void copyAndZeroOut(int [][] next, int[][] current) {
        for (int i = 0; i < current.length; i++) {
            System.arraycopy(next[i], 0, current[i], 0, current[i].length);
        }
    }

    private int isAlive(int row, int col, int[][] world) {
        int sum = 0;
        int[][] coordinates = new int[][] {{ row - 1, col }, { row, col + 1}, { row + 1, col }, { row, col - 1},
                { row - 1, col + 1}, { row + 1, col + 1 }, { row + 1, col - 1}, { row - 1, col - 1}};

        for(int[] eachCoordinate : coordinates){
            try {
                sum += world[eachCoordinate[0]][eachCoordinate[1]];
            } catch (Exception ignored) {} //ignores out of bounds exception
        }

        boolean isCellAlive = world[row][col] == 1;

//         if cell is alive, check sum < 2 OR > 3 then turn dead (0), otherwise cell is alive (1)
//         if cell is dead, check if neighbors == 3, revive (1)
        return isCellAlive ? ((sum < 2 || sum > 3) ? 0 : 1) : (sum == 3 ? 1 : 0);
    }
}

/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Assignment 2
  Author: Vo Van Thanh
  ID: TA
  Created  date: 11/12/2019
  Last modified: 17/12/2019
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package vietnamfc.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import vietnamfc.controller.MainController;

import java.util.ArrayList;

// The class to manage the state of the game
public class GameBoard {
    private final int ROWS = 4;
    private final int COLUMNS = 5;
    private final int CELL_NUM = ROWS * COLUMNS;
    private final int MAX_TIME = 120;
    private ArrayList<Cell> cells;
    private ArrayList<Cell> openCells;
    private int waitTime;
    private int score;
    private int totalScore;
    private int foundPairs = 0;
    private MainController controller;

    public GameBoard() {
        cells = new ArrayList<Cell>();
        openCells = new ArrayList<Cell>();
        waitTime = 3;
        score = 0;
        score();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void findNewPair() {
        foundPairs++;
    }

    // Check if the user wins the game
    public boolean win() {
        return foundPairs == 10;
    }

    // Win the game
    public void winTheGame() {
        totalScore += score;
        controller.winTheGame();
    }

    public void setLevel(int i) throws Exception{
        switch(i) {
            case 1:
                waitTime = 3;
                break;
            case 2:
                waitTime = 2;
                break;
            case 3:
                waitTime = 1;
                break;
            default:
                throw new Exception("Wrong option for level!");
        }
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void openCell(int row, int col) {
        Cell cell = getCell(row, col);
        openCells.add(cell);
    }

    public void closeCell(int row, int col) {
        Cell cell = getCell(row, col);
        openCells.remove(cell);
    }

    public int getOpenCellNum() {
        return openCells.size();
    }

    public ArrayList<Cell> getOpenCells() {
        return openCells;
    }

    public Cell getCell(int row, int col) {
        return cells.get(row * COLUMNS + col);
    }

    public void addCell(Cell cell) {
        cell.setGameBoard(this);
        cells.add(cell);
    }

    // Check if the open pictures are the same
    public boolean checkOpenCells() {
        return (openCells.size() == 2 && openCells.get(0).getPlayer() == openCells.get(1).getPlayer());
    }

    public void reset() {
        shuffleCards();
        score = MAX_TIME;
        openCells.clear();
        foundPairs = 0;
        for (Cell cell : cells) {
            cell.reset();
        }
    }

    // Initialize the members of the class
    public void intialize() {
        for (int i = 0; i < CELL_NUM; i++) {
            int row = i / COLUMNS;
            int col = i % COLUMNS;
            Cell cell = new Cell(row, col);
            addCell(cell);
        }
        shuffleCards();
    }

    // Score the game by counting the seconds playing the game.
    private void score() {
        score = MAX_TIME;
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            score--;
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(MAX_TIME);
        clock.play();
    }

    private void swap(int firstIdx, int secondIdx, int[] arr) {
        int tmp = arr[firstIdx];
        arr[firstIdx] = arr[secondIdx];
        arr[secondIdx] = tmp;
    }

    private int getRandomNumber(int num) {
        return (int)(Math.random() * num);
    }

    // Randomly populate the card board.
    private void shuffleCards() {
        int[] indexes = new int[CELL_NUM];
        int[] players = new int[CELL_NUM/2];
        for (int i = 0; i < CELL_NUM; i++) {
            indexes[i] = i;
        }
        for (int i =0; i < CELL_NUM/2; i++) {
            players[i] = i;
        }
        int num = CELL_NUM;
        while (num > 0) {
            int playerIdx = getRandomNumber(num/2);
            int playerId = players[playerIdx];
            swap(playerIdx, num/2 -1, players);
            int firstIdx = getRandomNumber(num);
            Cell firstCell = cells.get(indexes[firstIdx]);
            firstCell.setPlayer(playerId);
            swap(firstIdx, --num, indexes);
            int secondIdx = getRandomNumber(num);
            Cell secondCell = cells.get(indexes[secondIdx]);
            secondCell.setPlayer(playerId);
            swap(secondIdx, --num, indexes);
        }
    }

}

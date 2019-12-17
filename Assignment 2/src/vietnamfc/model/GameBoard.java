package vietnamfc.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import vietnamfc.controller.Controller;
import vietnamfc.model.Cell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private Controller controller;

    public GameBoard() {
        cells = new ArrayList<Cell>();
        openCells = new ArrayList<Cell>();
        waitTime = 3;
        score = 0;
        score();
    }

    public void setController(Controller controller) {
        this.controller = controller;
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

    public void findNewPair() {
        foundPairs++;
    }

    // Check if the user wins the game
    public boolean win() {
        return foundPairs == 1;
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

    private void swap(int firstIdx, int secondIdx, int[] arr) {
        int tmp = arr[firstIdx];
        arr[firstIdx] = arr[secondIdx];
        arr[secondIdx] = tmp;
    }

    private int getRandomNumber(int num) {
        return (int)(Math.random() * num);
    }

    // Check if the open pictures are the same
    public boolean checkOpenCells() {
        return (openCells.size() == 2 && openCells.get(0).getPlayer() == openCells.get(1).getPlayer());
    }

    public void reset() {
        shuffleCards();
        score = 0;
        openCells.clear();
        foundPairs = 0;
        for (Cell cell : cells) {
            cell.faceDown();
            cell.setView();
        }
    }

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

    // Randomly populate the board
    public void intialize() {
        for (int i = 0; i < CELL_NUM; i++) {
            int row = i / COLUMNS;
            int col = i % COLUMNS;
            Cell cell = new Cell(row, col);
            addCell(cell);
        }
        shuffleCards();
    }
}

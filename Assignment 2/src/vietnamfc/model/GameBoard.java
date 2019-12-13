package vietnamfc.model;

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
    private ArrayList<Cell> cells;
    private Map<Integer, Pair> table;
    private ArrayList<Cell> openCells;

    public GameBoard() {
        cells = new ArrayList<Cell>();
        table = new HashMap<Integer, Pair>();
        openCells = new ArrayList<Cell>();
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

    public boolean checkOpenCells() {
        return (openCells.size() == 2 && openCells.get(0).getPlayer() == openCells.get(1).getPlayer());
    }

    // Randomly populate the board
    public void intialize() {
        for (int i = 0; i < CELL_NUM; i++) {
            int row = i / COLUMNS;
            int col = i % COLUMNS;
            Cell cell = new Cell(row, col);
            addCell(cell);
        }

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
            table.put(playerId, new Pair(firstCell, secondCell));
        }

    }
}

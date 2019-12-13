package vietnamfc.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import vietnamfc.model.Cell;
import vietnamfc.model.GameBoard;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final int PLAYER_NUMBER = 10;
    private final int ROWS = 4;
    private final int COLUMNS = 5;
    private final int IMAGE_SIZE = 20;
    @FXML
    private GridPane gridPane;
    private ArrayList<Cell> cells;
    GameBoard gameBoard;

    private int getPlayerId(int row, int col) {
        return (row * COLUMNS + col) % PLAYER_NUMBER + 1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cells = new ArrayList<Cell>();
        gameBoard = new GameBoard();
        gameBoard.intialize();
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                gridPane.add(gameBoard.getCell(row, col).getButton(),
                        col * IMAGE_SIZE, row * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
            }
        }
    }
}

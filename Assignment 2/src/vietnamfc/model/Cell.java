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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
    This class to represent the information of a cell on the board
 */
public class Cell {
    private static final int BUTTON_SIZE = 200;
    private int row;
    private int column;
    // To check if we show the image or not
    private boolean faceup;
    // The button to represent the cell in the UI
    private Button button;
    private Background logoBackground;
    private Background playerBackground;
    private int playerId;
    private GameBoard gameBoard;
    private boolean stay;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.faceup = false;
        this.stay = false;
        this.playerId = playerId;
        button = new Button();
        button.setPrefHeight(BUTTON_SIZE);
        button.setPrefWidth(BUTTON_SIZE);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                if (!stay && gameBoard.getOpenCellNum() < 2) {
                    flip();
                    gameBoard.openCell(row, column);
                    if (gameBoard.checkOpenCells()) {

                        ArrayList<Cell> openCells = gameBoard.getOpenCells();
                        openCells.get(0).stay();
                        openCells.get(1).stay();
                        openCells.remove(1);
                        openCells.remove(0);

                        gameBoard.findNewPair();
                        // If the user wins the game, update the score
                        if (gameBoard.win()) {
                            gameBoard.winTheGame();
                        }
                    } else {
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.seconds(gameBoard.getWaitTime()), event -> {
                                    if (!stay) {
                                        flip();
                                        gameBoard.closeCell(row, column);
                                    }
                                })
                        );
                        timeline.play();
                    }
                }
            }
        });
        logoBackground = new Background(new BackgroundImage(new Image(
                new File("media/images/logo.jpg").toURI().toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(button.getWidth(), button.getHeight(), true, true, true, false)));
        setView();
    }

    public void reset() {
        stay = false;
        faceup = false;
        setView();
    }

    public void stay() {
        stay = true;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setPlayer(int playerId) {
        this.playerId = playerId;
        playerBackground = new Background(new BackgroundImage(
                new Image(new File("media/images/" + (playerId + 1) + ".jpg").toURI().toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(button.getWidth(), button.getHeight(), true, true, true, false)));
    }

    public int getPlayer() {
        return playerId;
    }

    public void flip() {
        faceup = !faceup;
        setView();
    }

    public void setView() {
        if (faceup) {
            button.setBackground(playerBackground);
        } else {
            button.setBackground(logoBackground);
        }
    }

    public Button getButton() {
        return button;
    }

}

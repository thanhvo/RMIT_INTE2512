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
package vietnamfc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.System.exit;

public class CloseGameController {
    @FXML
    private DialogPane dialogPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button playButton;
    @FXML
    private Text messageText;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setMessage(String message) {
        messageText.setText(message);
    }

    @FXML
    public void resetGame() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        mainController.resetGame();
    }

    @FXML
    public void quit() {
        exit(0);
    }
}

package vietnamfc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CloseGameController {
    @FXML
    private DialogPane dialogPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button playButton;
    @FXML
    private Text messageText;

    public void setMessage(String message) {
        messageText.setText(message);
    }
}

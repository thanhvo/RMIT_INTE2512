package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private TextField inputText;
    @FXML
    private TextArea outputText;

    public void printOutput(ActionEvent actionEvent) {
        if (outputText.getText().isEmpty()) {
            outputText.setText(inputText.getText());
        } else {
            outputText.setText(outputText.getText() + "\n" + inputText.getText());
        }
    }


    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
                printOutput(new ActionEvent());
        }
    }
}

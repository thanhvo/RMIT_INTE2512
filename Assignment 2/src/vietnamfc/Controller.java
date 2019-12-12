package vietnamfc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final int PLAYER_NUMBER = 10;
    private final int ROWS = 4;
    private final int COLUMNS = 5;
    private final int IMAGE_SIZE = 10;
    @FXML
    private GridPane gridPane;
    private ImageView logo;
    private ArrayList<ImageView> players;
    private ArrayList<Button> buttons;

    private Button getButton(int row, int col) {
        return buttons.get(row * COLUMNS + col);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            // Create the logo
            File logoFile = new File("media/images/logo.jpg");
            Image image = new Image(logoFile.toURI().toString());
            logo.setImage(image);

            // Create images of the players
            players = new ArrayList<ImageView>();
            for (int i = 1; i <= PLAYER_NUMBER; i++) {
                ImageView view = new ImageView();
                File file = new File("media/images/logo" + i + ".jpg");
                Image playerImage = new Image(file.toURI().toString());
                view.setImage(playerImage);
                players.add(view);
            }

            buttons = new ArrayList<Button>();

            for (int i = 0; i < ROWS * COLUMNS; i++) {
                Button button = new Button();
                button.setGraphic(logo);
                buttons.add(button);
            }

            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    gridPane.add(getButton(row, col), row * IMAGE_SIZE, col * IMAGE_SIZE);
                }
            }

    }
}

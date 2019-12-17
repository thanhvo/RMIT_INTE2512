package vietnamfc.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;
import vietnamfc.model.GameBoard;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {

    private final int PLAYER_NUMBER = 10;
    private final int ROWS = 4;
    private final int COLUMNS = 5;
    private final int IMAGE_SIZE = 20;
    private final int MAX_TIME = 120; // maximum time in seconds
    private final int SOUND_BUTTON_SIZE = 10;
    @FXML
    private GridPane boardPane;
    private Text scoreText;
    private ProgressBar timeBar;
    private GameBoard gameBoard;
    private AtomicInteger totalTime;
    private static MediaPlayer mediaPlayer;
    private Button soundButton;
    private boolean music;
    private Background soundBackground;
    private Background muteBackground;

    private void addLevelBox() {
        ComboBox levelComboBox = new ComboBox();
        levelComboBox.getItems().add("1");
        levelComboBox.getItems().add("2");
        levelComboBox.getItems().add("3");
        levelComboBox.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String selection = (String) levelComboBox.getSelectionModel().getSelectedItem();
                try {
                    gameBoard.setLevel(Integer.parseInt(selection));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Label levelLabel = new Label("LEVEL:");
        boardPane.add(levelLabel, COLUMNS * IMAGE_SIZE, 0);
        boardPane.add(levelComboBox,COLUMNS * IMAGE_SIZE + 4, 0);
    }

    private void addScore() {
        Label scoreLabel = new Label("SCORE");
        boardPane.add(scoreLabel, COLUMNS * IMAGE_SIZE, ROWS * IMAGE_SIZE);
        scoreText = new Text(String.format("%06d",0));
        boardPane.add(scoreText, COLUMNS * IMAGE_SIZE + 4, ROWS * IMAGE_SIZE);
    }

    private void addTime() {
        Label timeLabel = new Label ("TIME:");
        boardPane.add(timeLabel, 0, ROWS * IMAGE_SIZE);
        timeBar = new ProgressBar(1.0);
        boardPane.add(timeBar, IMAGE_SIZE, ROWS * IMAGE_SIZE);
        GridPane.setColumnSpan(timeBar, IMAGE_SIZE * 3);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeBar.progressProperty(), 1.0)),
                new KeyFrame(Duration.seconds(MAX_TIME), e-> {
                    System.out.println("Time is up!");
                }, new KeyValue(timeBar.progressProperty(), 0))
        );
        timeline.play();
    }

    private void resetTime() {
        totalTime.set(MAX_TIME * 100);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(timeBar.progressProperty(), 1.0)),
                new KeyFrame(Duration.seconds(MAX_TIME), e-> {
                    System.out.println("Time is up!");
                }, new KeyValue(timeBar.progressProperty(), 0))
        );
        timeline.play();
    }

    private void addClock() {
        Text timeText = new Text("02:00:00");
        AtomicInteger mins = new AtomicInteger(2);
        AtomicInteger seconds = new AtomicInteger();
        AtomicInteger hundredthSeconds = new AtomicInteger();
        totalTime = new AtomicInteger(MAX_TIME * 100);
        boardPane.add(timeText, 2 * IMAGE_SIZE, ROWS * IMAGE_SIZE);
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                totalTime.getAndDecrement();
                mins.set(totalTime.intValue() / (60 * 100));
                seconds.set((totalTime.intValue() - mins.intValue() * 60 * 100) / 100);
                hundredthSeconds.set(totalTime.intValue() - mins.intValue() * 60 * 100 - seconds.intValue() * 100);
                timeText.setText(String.format("%02d:%02d:%02d",mins.intValue(), seconds.intValue(), hundredthSeconds.intValue()));
            }), new KeyFrame(Duration.millis(10))
        );
        clock.setCycleCount(MAX_TIME * 100);
        clock.play();
    }

    public void winTheGame() {
        scoreText.setText(String.format("%06d", gameBoard.getTotalScore()));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Congratulation! You won the game! Do you want to play another game?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            resetGame();
        }
    }

    private void handleSound() {
        if (music) {
            mediaPlayer.play();
        } else {
            mediaPlayer.stop();
        }
    }

    private void setSoundButton() {
        if (music) {
            soundButton.setBackground(soundBackground);
        } else {
            soundButton.setBackground(muteBackground);
        }
    }
    private void addSound() {
        music = false;
        String musicFile = "media/sound/sound.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        handleSound();
        soundButton = new Button();
        soundButton.setPrefHeight(SOUND_BUTTON_SIZE);
        soundButton.setPrefWidth(SOUND_BUTTON_SIZE);
        soundBackground= new Background(new BackgroundImage(new Image(
                new File("media/images/sound.jpg").toURI().toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE, true, true, true, false)));
        muteBackground= new Background(new BackgroundImage(new Image(
                new File("media/images/mute.png").toURI().toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(SOUND_BUTTON_SIZE, SOUND_BUTTON_SIZE, true, true, true, false)));
        setSoundButton();
        soundButton.setOnAction(e -> {
            music = !music;
            handleSound();
            setSoundButton();
        });
        boardPane.add(soundButton, 3 * IMAGE_SIZE, ROWS * IMAGE_SIZE);
    }

    public void resetGame() {
        gameBoard.reset();
        resetTime();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameBoard = new GameBoard();
        gameBoard.setController(this);
        gameBoard.intialize();

        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Button button = gameBoard.getCell(row, col).getButton();
                boardPane.add(button, col * IMAGE_SIZE, row * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
            }
        }
        addLevelBox();
        addScore();
        addTime();
        addClock();
        addSound();
    }
}

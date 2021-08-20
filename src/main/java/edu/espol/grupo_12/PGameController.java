/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.espol.grupo_12;

import ai.MiniMax;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author josue
 */
public class PGameController implements Initializable {
    
    private static GridPane gameBoard;
    public static Board boardPlaying;
    private AnimationTimer gameTimer;
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        gridPanePrincipal.add(generateGridPaneGame(), 1, 1);
        borderPane.setCenter(generateGridPaneGame());
        runGameLoop();
    }    
    
    private static GridPane generateGridPaneGame() {
        gameBoard = new GridPane();
        boardPlaying = new Board();
        for (int row = 0; row < boardPlaying.getWidth(); row++) {
            for (int col = 0; col < boardPlaying.getWidth(); col++) {
                Celda celda = new Celda(row, col, boardPlaying.getMarkAt(row, col));
                GridPane.setConstraints(celda, col, row);
                gameBoard.getChildren().add(celda);
            }
        }
        gameBoard.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        return gameBoard;
    }
    /**
     * Runs the main game loop which is responsible for playing the AI's turn 
     * as long as the game is still ongoing.
     */
    private void runGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (boardPlaying.isGameOver()) {
                    endGame();
                } else {
                    if (boardPlaying.isCrossTurn()) {
                        try {
                            playAI();
                        } catch (CloneNotSupportedException ex) {
                            System.out.println(ex);
//                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }
        };
        gameTimer.start();
    }

    /**
     * Analyses the current state of the board and plays the move best for the X
     * player. Updates the tile it places a mark on also.
     */
    private static void playAI() throws CloneNotSupportedException {
        MiniMax move = new MiniMax(boardPlaying);
        int row = move.getRow();
        int col = move.getCol();
        boardPlaying.placeMark(row, col);
        for (Node child : gameBoard.getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                Celda t = (Celda) child;
                t.update();
                return;
            }
        }
    }

    private void resetGame() {
//        gridPanePrincipal.getChildren().clear();
        borderPane.setCenter(generateGridPaneGame());
//        gridPanePrincipal.add(generateGridPaneGame(), 1, 1);
        runGameLoop();
    }

    /**
     * Stops the game loop and displays the result of the game.
     */
    private void endGame() {
        gameTimer.stop();
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION, "", new ButtonType("New Game"));
        Mark winner = boardPlaying.getWinningMark();

        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText(null);
        if (winner == Mark.BLANK) {
            gameOverAlert.setContentText("Draw!");
        } else {
            gameOverAlert.setContentText(winner + " wins!");
        }
        gameOverAlert.setOnHidden(e -> {
            gameOverAlert.close();
            resetGame();
        });
        gameOverAlert.show();
    }
}

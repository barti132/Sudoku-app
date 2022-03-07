package pl.bartoszsredzinski.sudokuapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuApp extends Application{
    private static SudokuGame game;

    @Override
    public void start(Stage stage){
        Scene scene = new Scene(game.createContent());
        scene.getStylesheets().add("sudoku.css");
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args){
        game = new SudokuGame();

        int[][] board = {{5, 3, 0, 0, 7, 0, 0, 0, 0}, {6, 0, 0, 1, 9, 5, 0, 0, 0}, {0, 9, 8, 0, 0, 0, 0, 6, 0}, {8, 0, 0, 0, 6, 0, 0, 0, 3}, {4, 0, 0, 8, 0, 3, 0, 0, 1}, {7, 0, 0, 0, 2, 0, 0, 0, 6}, {0, 6, 0, 0, 0, 0, 2, 8, 0}, {0, 0, 0, 4, 1, 9, 0, 0, 5}, {0, 0, 0, 0, 8, 0, 0, 7, 9}};

        launch();
    }
}
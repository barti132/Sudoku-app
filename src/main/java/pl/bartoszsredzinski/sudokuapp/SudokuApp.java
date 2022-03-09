package pl.bartoszsredzinski.sudokuapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class SudokuApp extends Application{
    private static SudokuGame game;

    @Override
    public void start(Stage stage){
        Scene scene = new Scene(game.createContent());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add("sudoku.css");
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.getIcons().add(new Image("logo.png"));
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args){
        game = new SudokuGame();
        launch();
    }
}
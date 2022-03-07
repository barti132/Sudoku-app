package pl.bartoszsredzinski.sudokuapp;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pl.bartoszsredzinski.sudokuapp.sudokualg.Sudoku;
import pl.bartoszsredzinski.sudokuapp.uicomponents.MenuButton;
import pl.bartoszsredzinski.sudokuapp.uicomponents.SudokuTextField;

/**
 * Main sudoku application class
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */
public class SudokuGame{
    private HBox pane;
    private GridPane sudokuGrid;
    private TextArea messageBox;
    private Sudoku sudoku;

    public SudokuGame(){
        sudoku = new Sudoku();
    }

    public Parent createContent(){
        pane = new HBox();

        sudokuGrid = createSudokuGrid();
        pane.getChildren().add(sudokuGrid);

        pane.getChildren().add(generateMenuPanel());

        return pane;
    }

    private VBox generateMenuPanel(){
        MenuButton generateSudoku = new MenuButton("Generate new sudoku");
        generateSudoku.setOnMouseClicked(e -> generateSudokuAction());

        MenuButton solveSudoku = new MenuButton("Solve this sudoku");
        solveSudoku.setOnMouseClicked(e -> solveSudokuAction());

        MenuButton validSudoku = new MenuButton("Valid this sudoku");
        validSudoku.setOnMouseClicked(e -> solveSudokuAction());

        messageBox = new TextArea();
        messageBox.setPrefWidth(180);
        messageBox.setPrefHeight(20);
        messageBox.setEditable(false);

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().add(generateSudoku);
        vBox.getChildren().add(solveSudoku);
        vBox.getChildren().add(validSudoku);
        vBox.getChildren().add(messageBox);
        VBox.setMargin(generateSudoku, new Insets(10, 10, 10, 10));
        return vBox;
    }

    private GridPane createSudokuGrid(){
        GridPane sudokuPane = new GridPane();
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");
                cell.pseudoClassStateChanged(right, col == 2 || col == 5);
                cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);

                cell.getChildren().add(new SudokuTextField());

                sudokuPane.add(cell, col, row);
            }
        }
        return sudokuPane;
    }

    private void solveSudokuAction(){
        System.out.println("solve sudoku");
    }

    private void generateSudokuAction(){

        System.out.println("generate sudoku");
    }
}

package pl.bartoszsredzinski.sudokuapp;

import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pl.bartoszsredzinski.sudokuapp.sudokualg.Sudoku;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */
public class SudokuGame{
    private Pane pane;
    private GridPane sudokuGrid;
    private Sudoku sudoku;

    public SudokuGame(){
        sudoku = new Sudoku();
    }

    public Parent createContent(){
        pane = new Pane();
        pane.setPrefSize(720, 480);
        sudokuGrid = createSudokuGrid();

        pane.getChildren().add(sudokuGrid);
        return pane;
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

                cell.getChildren().add(createTextField());

                sudokuPane.add(cell, col, row);
            }
        }
        return sudokuPane;
    }

    private TextField createTextField(){
        TextField textField = new TextField();

        textField.setTextFormatter(new TextFormatter<Integer>(c -> {
            if(c.getControlNewText().matches("[1-9]")){
                return c;
            }
            else{
                return null;
            }
        }));
        return textField;
    }
}

package pl.bartoszsredzinski.sudokuapp;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private final int GRID_WIDTH = 9;
    private final int GRID_HEIGHT = 9;

    private HBox pane;
    private GridPane sudokuGrid;
    private TextArea messageBox;
    private ComboBox levelBox;
    private final Sudoku sudoku;

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
        levelBox = new ComboBox(FXCollections.observableArrayList(
                "Easy", "Medium", "Hard")
        );
        levelBox.setPrefWidth(180);
        levelBox.setValue("Medium");

        MenuButton generateSudoku = new MenuButton("Generate new sudoku");
        generateSudoku.setOnMouseClicked(e -> generateSudokuAction());

        MenuButton solveSudoku = new MenuButton("Solve sudoku");
        solveSudoku.setOnMouseClicked(e -> solveSudokuAction());

        MenuButton validSudoku = new MenuButton("Valid sudoku");
        validSudoku.setOnMouseClicked(e -> solveSudokuAction());

        messageBox = new TextArea();
        messageBox.setPrefWidth(180);
        messageBox.setPrefHeight(20);
        messageBox.setEditable(false);

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().add(generateSudoku);
        vBox.getChildren().add(levelBox);
        vBox.getChildren().add(solveSudoku);
        vBox.getChildren().add(validSudoku);
        vBox.getChildren().add(messageBox);
        VBox.setMargin(generateSudoku, new Insets(0, 10, 0, 10));
        return vBox;
    }

    private GridPane createSudokuGrid(){
        GridPane sudokuPane = new GridPane();
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

        for(int col = 0; col < GRID_WIDTH; col++){
            for(int row = 0; row < GRID_HEIGHT; row++){
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
        sudoku.generate(getLevelAsInteger());
        for(Node node : sudokuGrid.getChildren()){
            StackPane stack = (StackPane) node;
            SudokuTextField field = (SudokuTextField) stack.getChildren().get(0);
            String val = String.valueOf(sudoku.getBoard()[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]);

            field.setStyle(null);
            field.setText(val);
            field.setEditable(true);
            field.setMouseTransparent(false);
            field.setFocusTraversable(true);
            field.setStyle("-fx-display-caret: false;");

            if(!val.equals("0")){
                field.setEditable(false);
                field.setMouseTransparent(true);
                field.setFocusTraversable(false);
                field.setStyle("-fx-background-color: #d1d1d1;");
            }
        }

    }

    private int getLevelAsInteger(){
        switch(levelBox.getSelectionModel().getSelectedItem().toString()){
            case "Easy":
                return 4;
            case "Hard":
                return 8;
            default:
                return 6;
        }
    }
}

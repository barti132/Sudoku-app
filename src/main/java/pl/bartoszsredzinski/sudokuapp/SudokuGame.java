package pl.bartoszsredzinski.sudokuapp;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
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
//TODO
// wywalić vallid przycisk,
// dodać wskazówkę(sudoku zapisać rozwiązane sudoku),
// timer,
// labele dla pól,
// automatyczne sprawdzanie(opcjonalnie)
// refactor
public class SudokuGame{
    private static final int GRID_WIDTH = 9;
    private static final int GRID_HEIGHT = 9;

    private GridPane sudokuGrid;
    private TextArea messageBox;
    private ComboBox<String> levelBox;
    private final Sudoku sudoku;

    public SudokuGame(){
        sudoku = new Sudoku();
    }

    public Parent createContent(){
        HBox pane = new HBox();
        sudokuGrid = createSudokuGrid();
        pane.getChildren().add(sudokuGrid);
        pane.getChildren().add(generateMenuPanel());

        generateSudokuAction();

        return pane;
    }

    private VBox generateMenuPanel(){
        levelBox = new ComboBox<>(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        levelBox.setPrefWidth(180);
        levelBox.setValue("Medium");

        MenuButton generateSudoku = new MenuButton("Start new sudoku");
        generateSudoku.setOnMouseClicked(e -> generateSudokuAction());

        MenuButton solveSudoku = new MenuButton("Solve sudoku");
        solveSudoku.setOnMouseClicked(e -> solveSudokuAction());

        ToggleButton validSudoku = new ToggleButton("Valid sudoku");
        validSudoku.setOnMouseClicked(e -> validSudokuAction());

        messageBox = new TextArea();
        messageBox.setPrefWidth(180);
        messageBox.setPrefHeight(20);
        messageBox.setEditable(false);
        messageBox.setMouseTransparent(true);
        messageBox.setFocusTraversable(false);

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

    private void validSudokuAction(){
        boolean isValid = true;
        loadBoardToSudoku();
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            int val = 0;
            if(field.getText().equals("")){
                val = Integer.parseInt(field.getText());
            }
            if(val != 0){
                int col = GridPane.getColumnIndex(node);
                int row = GridPane.getRowIndex(node);
                sudoku.setField(0, row, col);

                if(!sudoku.isValid(val, row, col)){
                    writeMessage("Sudoku not valid! You made a mistake.", "red");
                    System.out.println(col + " " + row + " " + val);
                    isValid = false;
                    break;
                }
                sudoku.setField(val, row, col);
            }
        }

        if(isValid){
            writeMessage("Sudoku is valid!", "green");
        }
    }

    private void solveSudokuAction(){
        loadBoardToSudoku();
        int[][] board = sudoku.solve();

        if(board == null){
            writeMessage("No solution for your input!!!", "red");
        }
        else{
            sudoku.setBoard(board);
            loadSudokuToBoard(false);
            writeMessage("Solved.", "black");
        }
    }

    private void loadBoardToSudoku(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            int val = 0;
            if(!field.getText().equals("")){
                val = Integer.parseInt(field.getText());
            }
            int col = GridPane.getColumnIndex(node);
            int row = GridPane.getRowIndex(node);


            sudoku.setField(val, row, col);
        }
    }

    private void writeMessage(String msg, String red){
        messageBox.setStyle(null);
        messageBox.setStyle("-fx-text-fill: " + red);
        messageBox.setText(msg);
    }

    private void generateSudokuAction(){
        sudoku.generate(getLevelAsInteger());
        loadSudokuToBoard(true);
        messageBox.setText("Generated new Sudoku!");
    }

    private void loadSudokuToBoard(boolean setNotEditable){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            String val = String.valueOf(sudoku.getBoard()[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]);

            if(val.equals("0")){
                field.setText("");
            }
            else{
                field.setText(val);
            }

            if(setNotEditable){
                resetField(field);
            }

            if(setNotEditable && !val.equals("0")){
                setFieldAsNotEditable(field);
            }
        }
    }

    private void setFieldAsNotEditable(SudokuTextField field){
        field.setEditable(false);
        field.setMouseTransparent(true);
        field.setFocusTraversable(false);
        field.setStyle("-fx-border-color: #9c9c9c; -fx-border-width: 2; -fx-border-radius: 4");
    }

    private void resetField(SudokuTextField field){
        field.setStyle(null);
        field.setEditable(true);
        field.setMouseTransparent(false);
        field.setFocusTraversable(true);
        field.setStyle("-fx-display-caret: false;");
    }

    private int getLevelAsInteger(){
        return switch(levelBox.getSelectionModel().getSelectedItem()){
            case "Easy" -> 4;
            case "Hard" -> 8;
            default -> 6;
        };
    }
}

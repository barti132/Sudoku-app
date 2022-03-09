package pl.bartoszsredzinski.sudokuapp;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
// dodać wskazówkę(sudoku zapisać rozwiązane sudoku),
// timer,
// automatyczne sprawdzanie(opcjonalnie)
// refactor,
// twitter
public class SudokuGame{
    private static final int GRID_WIDTH = 9;
    private static final int GRID_HEIGHT = 9;

    private GridPane sudokuGrid;
    private TextArea messageBox;
    private ComboBox<String> levelCombo;
    private final Sudoku sudoku;
    private final Coordinates lastSelectedTextField;

    private boolean checkValid;

    public SudokuGame(){
        sudoku = new Sudoku();
        lastSelectedTextField = new Coordinates(0, 0);
        checkValid = true;
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
        VBox levelVBox = new VBox();
        levelCombo = new ComboBox<>(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        levelCombo.setPrefWidth(180);
        levelCombo.setValue("Medium");
        levelVBox.getChildren().addAll(new Label("Choose difficulty: "), levelCombo);

        MenuButton generateSudoku = new MenuButton("Start new sudoku");
        generateSudoku.setOnMouseClicked(e -> generateSudokuAction());

        MenuButton solveSudoku = new MenuButton("Solve sudoku");
        solveSudoku.setOnMouseClicked(e -> solveSudokuAction());

        MenuButton adviceSudoku = new MenuButton("Give advice");
        adviceSudoku.setOnMouseClicked(e -> adviceSudokuAction());

        HBox hbox = new HBox();
        hbox.setSpacing(5);

        ToggleGroup toggleGroup = new ToggleGroup();

        ToggleButton checkMistakeY = new ToggleButton("Yes");
        checkMistakeY.getStyleClass().add("btn-primary");
        ToggleButton checkMistakeN = new ToggleButton("No");
        checkMistakeN.getStyleClass().add("btn-primary");
        checkMistakeY.setSelected(true);
        toggleGroup.getToggles().addAll(checkMistakeY, checkMistakeN);
        hbox.getChildren().addAll(new Label("Check mistake: "), checkMistakeY, checkMistakeN);

        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) ->
                checkValid = ((ToggleButton) t1).getText().equals("Yes"));

        messageBox = new TextArea();
        messageBox.setPrefSize(180, 20);
        messageBox.setPrefHeight(20);
        messageBox.setMouseTransparent(true);
        messageBox.setFocusTraversable(false);

        VBox vBox = new VBox();
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(generateSudoku, levelVBox, solveSudoku, adviceSudoku, hbox, messageBox);
        VBox.setMargin(vBox, new Insets(0, 20, 0, 20));
        VBox.setMargin(levelVBox, new Insets(0, 20, 0, 20));
        VBox.setMargin(hbox, new Insets(0, 20, 0, 20));
        return vBox;
    }

    private void adviceSudokuAction(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            if(field.getX() == lastSelectedTextField.getX() && field.getY() == lastSelectedTextField.getY()){
                field.setText(String.valueOf(sudoku.getAdvice(field.getX(), field.getY())));
            }
        }
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

                SudokuTextField field = new SudokuTextField(row, col);

                field.textProperty().addListener((observableValue, s, t1) -> {
                    field.setDefaultStyle();
                    if(checkValid && !t1.equals("")){
                        if(sudoku.isCorrect(Integer.parseInt(t1), field.getX(), field.getY())){
                            sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());

                        }
                        else {
                            field.setStyle("-fx-text-fill: red");
                        }
                    }
                    else if(!t1.equals("")){
                        sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());
                    }
                });

                field.setOnMouseClicked(mouseEvent -> {
                    lastSelectedTextField.setX(field.getX());
                    lastSelectedTextField.setY(field.getY());
                });

                cell.getChildren().add(field);

                sudokuPane.add(cell, col, row);
            }
        }
        return sudokuPane;
    }

    private void validSudokuAction(){
        boolean isValid = true;
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
        int[][] board = sudoku.solve();

        if(board == null){
            writeMessage("No solution for your input!!!", "red");
        }
        else{
            sudoku.setBoard(board);
            loadSudokuToBoard(false);
            writeMessage("Solved.", "green");
        }
    }

    private void writeMessage(String msg, String color){
        messageBox.setStyle(null);
        messageBox.setStyle("-fx-text-fill: " + color);
        messageBox.setText(msg);
    }

    private void generateSudokuAction(){
        sudoku.generate(getLevelAsInteger());
        loadSudokuToBoard(true);
        writeMessage("Generated new Sudoku!", "green");
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
        return switch(levelCombo.getSelectionModel().getSelectedItem()){
            case "Easy" -> 4;
            case "Hard" -> 8;
            default -> 6;
        };
    }
}

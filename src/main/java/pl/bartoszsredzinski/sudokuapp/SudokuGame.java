package pl.bartoszsredzinski.sudokuapp;

import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pl.bartoszsredzinski.sudokuapp.sudokualg.Sudoku;
import pl.bartoszsredzinski.sudokuapp.uicomponents.*;

/**
 * Main sudoku application class
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */

public class SudokuGame{
    private static final int BOARD_SIZE = 9;

    private GridPane sudokuGrid;
    private MessageBox messageBox;
    private LevelComboBox levelComboBox;
    private final Sudoku sudoku;
    private final Coordinates lastSelectedTextField;

    private boolean checkValid;
    private boolean loadingSudoku;

    public SudokuGame(){
        sudoku = new Sudoku();
        lastSelectedTextField = new Coordinates(0, 0);
        checkValid = true;
        loadingSudoku = false;
    }

    public Parent createContent(){
        HBox pane = new HBox();
        sudokuGrid = createSudokuGrid();
        pane.getChildren().add(sudokuGrid);
        pane.getChildren().add(generateMenuPanel());

        onClickStartSudokuButton();

        return pane;
    }

    private VBox generateMenuPanel(){
        //level selection
        levelComboBox = new LevelComboBox();

        //buttons
        MenuButton generateSudoku = new MenuButton("Start new sudoku");
        generateSudoku.setOnMouseClicked(e -> onClickStartSudokuButton());
        MenuButton solveSudoku = new MenuButton("Solve sudoku");
        solveSudoku.setOnMouseClicked(e -> onClickSolveButton());
        MenuButton adviceSudoku = new MenuButton("Give advice");
        adviceSudoku.setOnMouseClicked(e -> onClickAdviceButton());

        //toggle button valid input
        RadioButton checkYes = new RadioButton("Yes");
        checkYes.setSelected(true);
        RadioButton checkNo = new RadioButton("No");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().addAll(checkYes, checkNo);
        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            checkValid = ((RadioButton) t1).getText().equals("Yes");
            if(checkValid){
                validBoard();
            }
        });

        HBox checkValidationBox = new HBox();
        checkValidationBox.setSpacing(5);
        checkValidationBox.getChildren().addAll(new Label("Check mistake: "), checkYes, checkNo);

        messageBox = new MessageBox();

        VBox menuBox = new VBox();
        menuBox.setSpacing(30);
        menuBox.setAlignment(Pos.BASELINE_CENTER);
        menuBox.getChildren().addAll(generateSudoku, levelComboBox, solveSudoku, adviceSudoku, checkValidationBox, messageBox);
        VBox.setMargin(menuBox, new Insets(0, 20, 0, 20));
        VBox.setMargin(levelComboBox, new Insets(0, 20, 0, 25));
        VBox.setMargin(checkValidationBox, new Insets(0, 20, 0, 20));
        return menuBox;
    }

    private GridPane createSudokuGrid(){
        GridPane sudokuPane = new GridPane();
        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

        for(int col = 0; col < BOARD_SIZE; col++){
            for(int row = 0; row < BOARD_SIZE; row++){
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");
                cell.pseudoClassStateChanged(right, col == 2 || col == 5);
                cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);

                SudokuTextField field = new SudokuTextField(row, col);
                field.textProperty().addListener((observableValue, s, t1) -> listenerTextFieldChangeTextAction(field, t1));
                field.setOnMouseClicked(mouseEvent -> lastSelectedTextField.setCoordinates(field.getX(), field.getY()));

                cell.getChildren().add(field);

                sudokuPane.add(cell, col, row);
            }
        }
        return sudokuPane;
    }

    private void listenerTextFieldChangeTextAction(SudokuTextField field, String t1){
        field.setDefaultStyle();
        if(checkValid && !t1.equals("")){ // with checking
            if(sudoku.isCorrect(Integer.parseInt(t1), field.getX(), field.getY())){
                sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());
                messageBox.writeMessage("", "black");
            }
            else{
                sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());
                messageBox.writeMessage("Wrong value!", "red");
                field.setStyle("-fx-text-fill: red");
            }
        }
        else if(!t1.equals("")){ //without checking
            sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());
        }
        else{ //delete value
            sudoku.setField(0, field.getX(), field.getY());
        }

        if(!loadingSudoku && sudoku.isWin()){
            WinAlert alert = new WinAlert();
            alert.showAndWait();
        }
    }

    private void onClickAdviceButton(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            if(field.getX() == lastSelectedTextField.getX() && field.getY() == lastSelectedTextField.getY()){
                field.setText(String.valueOf(sudoku.getAdvice(field.getX(), field.getY())));
                messageBox.writeMessage("Give value.", "green");
            }
        }
    }

    private void onClickSolveButton(){
        int[][] board = sudoku.solve();

        if(board == null){
            messageBox.writeMessage("No solution for your input!!!", "red");
        }
        else{
            sudoku.setBoard(board);
            loadSudokuToBoard(false);
            messageBox.writeMessage("Solved.", "green");
        }
    }

    private void onClickStartSudokuButton(){
        sudoku.generate(levelComboBox.getLevelAsInteger());
        loadSudokuToBoard(true);
        messageBox.writeMessage("Generated new Sudoku!", "green");
    }

    private void validBoard(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            if(field.getText().equals("")){
                continue;
            }

            if(!sudoku.isCorrect(Integer.parseInt(field.getText()), field.getX(), field.getY())){
                field.setStyle("-fx-text-fill: red");
                messageBox.writeMessage("Sudoku not valid!", "red");
            }
        }
    }

    private void loadSudokuToBoard(boolean setNotEditable){
        loadingSudoku = true;
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            String val = String.valueOf(sudoku.getBoard()[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]);

            if(val.equals("0")){
                val = "";
            }

            field.setText(val);

            if(setNotEditable){
                field.resetToEditable();
            }

            if(setNotEditable && !val.equals("")){
                field.setNotEditable();
            }
        }
        loadingSudoku = false;
    }
}
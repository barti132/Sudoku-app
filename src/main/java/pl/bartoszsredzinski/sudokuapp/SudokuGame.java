package pl.bartoszsredzinski.sudokuapp;

import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        VBox levelBox = new VBox();
        levelCombo = new ComboBox<>(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        levelCombo.setPrefWidth(180);
        levelCombo.setValue("Medium");
        levelBox.getChildren().addAll(new Label("Choose difficulty: "), levelCombo);

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

        //message box
        messageBox = new TextArea();
        messageBox.setPrefSize(180, 40);
        messageBox.setMouseTransparent(true);
        messageBox.setFocusTraversable(false);

        VBox menuBox = new VBox();
        menuBox.setSpacing(30);
        menuBox.setAlignment(Pos.BASELINE_CENTER);
        menuBox.getChildren().addAll(generateSudoku, levelBox, solveSudoku, adviceSudoku, checkValidationBox, messageBox);
        VBox.setMargin(menuBox, new Insets(0, 20, 0, 20));
        VBox.setMargin(levelBox, new Insets(0, 20, 0, 25));
        VBox.setMargin(checkValidationBox, new Insets(0, 20, 0, 20));
        return menuBox;
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
                writeMessage("", "black");
            }
            else{
                sudoku.setField(Integer.parseInt(t1), field.getX(), field.getY());
                writeMessage("Wrong value!", "red");
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Win");
            alert.setHeaderText("You solved sudoku puzzle!");
            alert.setContentText("Congratulation!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("logo.png"));
            alert.showAndWait();
        }
    }

    private void onClickAdviceButton(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            if(field.getX() == lastSelectedTextField.getX() && field.getY() == lastSelectedTextField.getY()){
                field.setText(String.valueOf(sudoku.getAdvice(field.getX(), field.getY())));
                writeMessage("Give value.", "green");
            }
        }
    }

    private void onClickSolveButton(){
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

    private void onClickStartSudokuButton(){
        sudoku.generate(getLevelAsInteger());
        loadSudokuToBoard(true);
        writeMessage("Generated new Sudoku!", "green");
    }

    private void writeMessage(String msg, String color){
        messageBox.setStyle(null);
        messageBox.setStyle("-fx-text-fill: " + color);
        messageBox.setText(msg);
    }

    private void validBoard(){
        for(Node node : sudokuGrid.getChildren()){
            SudokuTextField field = (SudokuTextField) ((StackPane) node).getChildren().get(0);
            if(field.getText().equals("")){
                continue;
            }

            if(!sudoku.isCorrect(Integer.parseInt(field.getText()), field.getX(), field.getY())){
                field.setStyle("-fx-text-fill: red");
                writeMessage("Sudoku not valid!", "red");
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
                resetField(field);
            }

            if(setNotEditable && !val.equals("")){
                setFieldAsNotEditable(field);
            }
        }
        loadingSudoku = false;
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
            default -> 1;//6
        };
    }
}
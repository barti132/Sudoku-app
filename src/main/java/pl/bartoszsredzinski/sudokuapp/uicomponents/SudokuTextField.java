package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Sudoku text field
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */
public class SudokuTextField extends TextField{
    private final int x;
    private final int y;

    public SudokuTextField(int x, int y){
        this.x = x;
        this.y = y;

        setDefaultStyle();
        setTextFormatter(new TextFormatter<Integer>(c -> {
            if(c.getControlNewText().matches("[0-9]?")){
                return c;
            }
            else{
                return null;
            }
        }));
    }

    public void setDefaultStyle(){
        setStyle(null);
        setStyle("-fx-display-caret: false;");
        setAlignment(Pos.CENTER);
        setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
    }

    public void resetToEditable(){
        setStyle(null);
        setEditable(true);
        setMouseTransparent(false);
        setFocusTraversable(true);
        setDefaultStyle();
    }

    public void setNotEditable(){
        setEditable(false);
        setMouseTransparent(true);
        setFocusTraversable(false);
        setStyle("-fx-border-color: #9c9c9c; -fx-border-width: 2; -fx-border-radius: 4;");
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

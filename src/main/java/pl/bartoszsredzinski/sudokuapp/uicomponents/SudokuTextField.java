package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */
public class SudokuTextField extends TextField{
    public SudokuTextField(){
        setStyle("-fx-display-caret: false;");
        setAlignment(Pos.CENTER);
        setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        setTextFormatter(new TextFormatter<Integer>(c -> {
            if(c.getControlNewText().matches("[0-9]?")){
                return c;
            }
            else{
                return null;
            }
        }));
    }
}

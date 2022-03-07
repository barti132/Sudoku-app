package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 07.03.2022
 */
public class MenuButton extends javafx.scene.control.Button{
    public MenuButton(String text){
        super(text);
        this.setPrefWidth(180);
        this.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        this.getStyleClass().add("btn");
        this.getStyleClass().add("btn-primary");
    }
}

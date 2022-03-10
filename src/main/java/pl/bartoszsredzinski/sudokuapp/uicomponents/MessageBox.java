package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.control.TextArea;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 10.03.2022
 */
public class MessageBox extends TextArea{
    public MessageBox(){
        setPrefSize(180, 40);
        setMouseTransparent(true);
        setFocusTraversable(false);
    }

    public void writeMessage(String msg, String color){
        setStyle(null);
        setStyle("-fx-text-fill: " + color);
        setText(msg);
    }
}

package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Win Alert
 *
 * @author Bartosz Średziński
 * created on 10.03.2022
 */
public class WinAlert extends Alert{
    public WinAlert(){
        super(AlertType.INFORMATION);
        setTitle("Win");
        setHeaderText("You solved sudoku puzzle!");
        setContentText("Congratulation!");
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("logo.png"));
    }
}

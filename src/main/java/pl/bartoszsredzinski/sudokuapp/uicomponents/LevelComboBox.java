package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 10.03.2022
 */
public class LevelComboBox extends VBox{

    private ComboBox<String> levelCombo;

    public LevelComboBox(){
        levelCombo = new ComboBox<>(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        levelCombo.setPrefWidth(180);
        levelCombo.setValue("Medium");
        getChildren().addAll(new Label("Choose difficulty: "), levelCombo);
    }

    public int getLevelAsInteger(){
        return switch(levelCombo.getSelectionModel().getSelectedItem()){
            case "Easy" -> 4;
            case "Hard" -> 8;
            default -> 1;//6
        };
    }
}

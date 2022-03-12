package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 12.03.2022
 */
@ExtendWith(ApplicationExtension.class)
class LevelComboBoxTest{

    private LevelComboBox comboBox;

    @Start
    public void start(Stage stage) {
        comboBox = new LevelComboBox();
        stage.setScene(new Scene(new StackPane(comboBox), 100, 100));
        stage.show();
    }

    @Test
    public void getLevelAsInteger_should_return_correct_level(FxRobot robot){
        assertEquals(4, comboBox.getLevelAsInteger());
    }

}
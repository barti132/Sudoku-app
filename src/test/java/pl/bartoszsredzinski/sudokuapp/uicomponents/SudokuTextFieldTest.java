package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * @author Bartosz Średziński
 * created on 12.03.2022
 */
@ExtendWith(ApplicationExtension.class)
class SudokuTextFieldTest{

    private SudokuTextField sudokuTextField;

    @Start
    public void start(Stage stage){
        sudokuTextField = new SudokuTextField(0, 0);
        sudokuTextField.setId("textField");
        stage.setScene(new Scene(new StackPane(sudokuTextField), 100, 100));
        stage.show();
    }

    @Test
    public void setDefaultStyle_should_set_default_style(FxRobot robot){
        sudokuTextField.setDefaultStyle();
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).hasStyle("-fx-display-caret: false;");
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).isVisible();
    }

    @Test
    public void resetToEditable_should_set_reset_field_to_editable(FxRobot robot){
        sudokuTextField.resetToEditable();
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).hasStyle("-fx-display-caret: false;");
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).isEnabled();
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).isVisible();
        robot.clickOn("#textField").write("1");
        assertEquals("1", sudokuTextField.getText());
    }

    @Test
    public void setNotEditable_should_set_TextField_not_editable(FxRobot robot){
        sudokuTextField.setNotEditable();
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).hasStyle("-fx-border-color: #9c9c9c; -fx-border-width: 2; -fx-border-radius: 4;");
        assertThat(robot.lookup("#textField").queryAs(SudokuTextField.class)).isVisible();
        robot.clickOn("#textField").write("1");
        assertEquals("", sudokuTextField.getText());
    }
}
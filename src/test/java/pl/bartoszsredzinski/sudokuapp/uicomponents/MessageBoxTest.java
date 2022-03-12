package pl.bartoszsredzinski.sudokuapp.uicomponents;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * @author Bartosz Średziński
 * created on 12.03.2022
 */

@ExtendWith(ApplicationExtension.class)
class MessageBoxTest{

    private MessageBox messageBox;

    @Start
    public void start(Stage stage){
        messageBox = new MessageBox();
        messageBox.setId("messageBox");
        stage.setScene(new Scene(new StackPane(messageBox), 100, 100));
        stage.show();
    }

    @Test
    public void writeMessage_should_change_Textarea_text_and_color(FxRobot robot){
        messageBox.writeMessage("test", "red");
        assertThat(robot.lookup("#messageBox").queryAs(MessageBox.class)).hasText("test");
        assertThat(robot.lookup("#messageBox").queryAs(MessageBox.class)).hasStyle("-fx-text-fill: red");
    }

}
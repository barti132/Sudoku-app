package pl.bartoszsredzinski.sudokuapp.uicomponents;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pl.bartoszsredzinski.sudokuapp.LeaderboardEntity;

import java.io.*;
import java.text.DecimalFormat;


/**
 * Win Alert
 *
 * @author Bartosz Średziński
 * created on 10.03.2022
 */
public class WinAlert extends Stage{

    public WinAlert(long milliseconds, int mistakes, int level){
        initModality(Modality.WINDOW_MODAL);
        setTitle("Win!");
        VBox box = new VBox();
        Label title = new Label("You solved sudoku puzzle!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        long score = (level * 100L) - (milliseconds / 60000) - (mistakes * 10L);

        Label content = new Label("Congratulation! Your time: " + new DecimalFormat("#0.00").format(milliseconds / 60000.d) + " min, score: " + score);
        content.setFont(Font.font("Verdana", 16));


        box.getChildren().addAll(title, content);


        box.getChildren().addAll(new Label("Leaderboard: "), createListView());


        TextField nameField = new TextField();

        HBox buttons = new HBox();

        Button close = new Button("Close");
        close.getStyleClass().addAll("btn", "btn-primary");
        close.setOnAction(e -> close());

        Button save = new Button("Save result");
        save.getStyleClass().addAll("btn", "btn-primary");
        save.setOnAction(e -> saveResult(nameField.getText()));

        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(save, close);
        box.getChildren().addAll(new Label("Your name: "), nameField, buttons);

        box.setPrefSize(600, 400);
        box.setSpacing(10);
        box.setPadding(new Insets(10));

        setResizable(false);


        getIcons().add(new Image("logo.png"));
        Scene scene = new Scene(box);
        save.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        setScene(scene);
    }

    private void saveResult(String text){
        if(!text.equals("")){

        }
    }

    private TableView<LeaderboardEntity> createListView(){
        TableView<LeaderboardEntity> table = new TableView<>();


        TableColumn<LeaderboardEntity, Integer> idCol = new TableColumn<>("Id");
        idCol.setPrefWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<LeaderboardEntity, String> nameCol = new TableColumn<>("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<LeaderboardEntity, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setPrefWidth(200);
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));


        table.setItems(loadLeaderBoard());
        table.getColumns().addAll(idCol, nameCol, scoreCol);

        table.setEditable(false);

        return table;
    }

    private ObservableList<LeaderboardEntity> loadLeaderBoard(){
        LeaderboardEntity[] leaderboard = null;
        File file = new File("leaderboard.txt");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
                return FXCollections.observableArrayList(leaderboard);
            }
        }
        else{

            BufferedReader reader = null;
            try{
                reader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
                Gson gson = new Gson();
                leaderboard = gson.fromJson(builder.toString(), LeaderboardEntity[].class);

                reader.close();


            }catch(IOException e){
                e.printStackTrace();
            }finally{
                if(reader != null){
                    try{
                        reader.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }


        return FXCollections.observableArrayList(leaderboard);
    }

}

package pl.bartoszsredzinski.sudokuapp.uicomponents;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import pl.bartoszsredzinski.sudokuapp.LeaderboardEntity;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Win Alert
 *
 * @author Bartosz Średziński
 * created on 10.03.2022
 */
public class WinAlert extends Stage{

    private List<LeaderboardEntity> leaderboard;
    private final TableView<LeaderboardEntity> table;

    public WinAlert(long milliseconds, int mistakes, int level){
        leaderboard = null;

        initModality(Modality.WINDOW_MODAL);
        setTitle("Win!");
        VBox box = new VBox();
        Label title = new Label("You solved sudoku puzzle!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        long score = (level * 100L) - (milliseconds / 60000) - (mistakes * 10L);

        Label content = new Label("Congratulation! Your time: " + new DecimalFormat("#0.00").format(milliseconds / 60000.d) + " min, score: " + score);
        content.setFont(Font.font("Verdana", 16));


        box.getChildren().addAll(title, content);

        table = createListView();
        table.setItems(loadLeaderboard(score));
        box.getChildren().addAll(new Label("Leaderboard: "), table);


        Button close = new Button("Close");
        close.getStyleClass().addAll("btn", "btn-primary");
        close.setOnAction(e -> close());
        close.setAlignment(Pos.CENTER);

        box.getChildren().addAll(close);

        box.setPrefSize(600, 400);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);

        setResizable(false);


        getIcons().add(new Image("logo.png"));
        Scene scene = new Scene(box);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        setScene(scene);
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

        table.getColumns().addAll(idCol, nameCol, scoreCol);

        table.setEditable(false);

        return table;
    }

    private ObservableList<LeaderboardEntity> loadLeaderboard(long score){
        File file = new File("leaderboard.txt");

        if(!file.exists()){
            createFile(file);
            leaderboard = new ArrayList<>();
        }
        else{
            readLeaderboardFile(file);
        }

        saveNewScore(score);

        return FXCollections.observableArrayList(leaderboard);
    }

    private void saveNewScore(long score){

        int id = leaderboard.size() + 1;

        LeaderboardEntity entity = new LeaderboardEntity(id, System.getProperty("user.name"), (int) score);
        leaderboard.add(entity);
        leaderboard.sort(Collections.reverseOrder());

        //set sorted id
        for(int i = 0; i < leaderboard.size(); i++){
            leaderboard.get(i).setId(i + 1);
        }

        writeToLeaderboardFile();
    }

    private void writeToLeaderboardFile(){
        BufferedWriter writer = null;
        try{
            Gson gson = new Gson();
            writer = new BufferedWriter(new FileWriter("leaderboard.txt"));
            writer.write(gson.toJson(leaderboard));
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void readLeaderboardFile(File file){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine()) != null){
                builder.append(line);
            }

            Type collectionType = new TypeToken<ArrayList<LeaderboardEntity>>(){
            }.getType();
            Gson gson = new Gson();
            leaderboard = gson.fromJson(builder.toString(), collectionType);


        }catch(IOException | JsonSyntaxException e){
            e.printStackTrace();
            leaderboard = new ArrayList<>();
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

    private void createFile(File file){
        try{
            file.createNewFile();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}

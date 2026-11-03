import gui.MainPane;
import io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;

//to do: cut down main class to the bare minimum: psvm -> launch(args) & pv start(Stage stage)

public class ClassRoomGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        MajorMapReader mapReader = new MajorMapReader("major-map.txt");
        Map<String, String> majorMap = mapReader.readMap();
        MainPane mainPane = new MainPane(majorMap);
        Scene scene = new Scene(mainPane, 600, 450);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Classroom App");
        primaryStage.show();
    }
}



package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ChampionshipSelecting.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 300, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Select championship");
        stage.show();
    }
}

package app;

import app.ChampionshipClasses.Championship;
import app.ChampionshipClasses.Comparators;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    int imageButtonHeight = 600;
    int imageButtonWidth = 300;
    public ImageView singleRaceButton;
    public ImageView seasonButton;
    public ImageView settingsButton;
    public Championship currentChampionship;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleRaceButton.setImage(new Image("Images/singlerace.png"));
        singleRaceButton.setPreserveRatio(true);
        seasonButton.setImage(new Image("Images/season.png"));
        seasonButton.setPreserveRatio(true);
        settingsButton.setImage(new Image("Images/settings.png"));
        settingsButton.setPreserveRatio(true);
        var championship = Championship.getInstance();
        Arrays.sort(championship.drivers, new Comparators.PointsComparator());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < championship.drivers.length; i++) {
            //builder.append(i + 1).append(".\t").append(championship.drivers[i].name).append("\t\t\t").append(championship.drivers[i].points).append(" pts\n");
            System.out.println((i+1) + ". " + championship.drivers[i]);
        }
        System.out.println(builder.toString());
    }

    private void zoom(ImageView imageView, double scale){
        imageView.scaleXProperty().set(scale);
        imageView.scaleYProperty().set(scale);
        Rectangle2D viewpoint = new Rectangle2D(0, 0, imageButtonWidth, imageButtonHeight);
        imageView.setViewport(viewpoint);
    }

    private static double zoom = 1.02;
    public void singleZoomIn(MouseEvent mouseEvent) {
        zoom(singleRaceButton, zoom);
    }
    public void singleZoomOut(MouseEvent mouseEvent) {
        zoom(singleRaceButton, 1);
    }
    public void seasonZoomIn(MouseEvent mouseEvent) {
        zoom(seasonButton, zoom);
    }
    public void seasonZoomOut(MouseEvent mouseEvent) {
        zoom(seasonButton, 1);
    }
    public void settingsZoomIn(MouseEvent mouseEvent) {
        zoom(settingsButton, zoom);
    }
    public void settingsZoomOut(MouseEvent mouseEvent) {
        zoom(settingsButton, 1);
    }

    public void singleClick(MouseEvent mouseEvent) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SelectingRace.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent, 610, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Select");
            stage.show();
            Stage current = (Stage) singleRaceButton.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void seasonClick(MouseEvent mouseEvent) {
        System.out.println("Season");
    }

    public void settingsClick(MouseEvent mouseEvent) {
        System.out.println("Settings");
    }
}

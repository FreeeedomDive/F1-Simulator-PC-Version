package app;

import app.GlobalClasses.Championship;
import app.GlobalClasses.Comparators;
import app.GlobalClasses.Settings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
        currentChampionship = Championship.getInstance();
        Arrays.sort(currentChampionship.drivers, new Comparators.PointsComparator());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < currentChampionship.drivers.length; i++) {
            System.out.println((i + 1) + ". " + currentChampionship.drivers[i]);
        }
        System.out.println(builder.toString());
        createOrLoadSettings();
    }

    private void createOrLoadSettings() {
        currentChampionship.settings.createDirectotyIfNotExists();
        if (currentChampionship.settings.isSettingsExist())
            currentChampionship.settings.getFromFile();
        else {
            var settings = new Settings();
            settings.realisticLaps = false;
            settings.raceLength = 30;
            settings.qualificationType = Settings.QualificationType.OneRound;
            settings.q1Length = 10;
            settings.q2Length = 7;
            settings.q3Length = 3;
            settings.qualificationPriority = 750;
            settings.saveToFile();
            currentChampionship.settings = settings;
        }
    }

    private void zoom(ImageView imageView, double scale) {
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/SelectingRace.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent, 610, 400);
            Stage stage = new Stage();
            stage.setResizable(false);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Settings.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 300, 550));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < currentChampionship.drivers.length; i++) {
            System.out.println((i + 1) + ". " + currentChampionship.drivers[i]);
        }
        System.out.println(builder.toString());
    }
}

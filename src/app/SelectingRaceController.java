package app;

import app.GlobalClasses.Championship;
import app.GlobalClasses.Driver;
import app.Tracks.AllTracksInfo;
import app.Tracks.Track;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectingRaceController implements Initializable {

    public ListView<Track> tracksListView;
    public RadioButton gridStandings;
    public RadioButton gridReversed;
    public RadioButton gridRandom;
    public RadioButton gridQual;
    public RadioButton gridCustom;
    public Button customButton;
    public Label selectedRaceLabel;
    public Label gridLabel;
    public Label qualificationLabel;
    public Label raceLabel;
    public Button startButton;
    public Button menuButton;
    public Button settingsButton;

    private Track selectedTrack = null;

    private Driver[] drivers;
    private Championship championship;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championship = Championship.getInstance();
        drivers = new Driver[championship.drivers.length];
        System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
        ToggleGroup radioButtonsGroup = new ToggleGroup();
        gridStandings.setToggleGroup(radioButtonsGroup);
        gridReversed.setToggleGroup(radioButtonsGroup);
        gridRandom.setToggleGroup(radioButtonsGroup);
        gridQual.setToggleGroup(radioButtonsGroup);
        gridCustom.setToggleGroup(radioButtonsGroup);
        tracksListView.setItems(FXCollections.observableList(AllTracksInfo.getTracks()));
        tracksListView.setOnMouseClicked(event -> {
            selectedTrack = tracksListView.getSelectionModel().getSelectedItem();
            selectedRaceLabel.setText(selectedTrack.name);
            var laps = 0;
            if (championship.settings.realisticLaps)
                laps = selectedTrack.laps;
            else
                laps = championship.settings.raceLength * 60 * 1000 / selectedTrack.raceTime;
            raceLabel.setText(laps + " laps");
        });
    }

    public void selectStandings(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridStandings.getText());
        System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
    }

    public void selectReversed(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridReversed.getText());
        for (int i = 0; i < drivers.length; i++) {
            drivers[i] = championship.drivers[drivers.length - 1 - i];
        }
    }

    public void selectRandom(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridRandom.getText());
        System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
        for (int i = 0; i < drivers.length; i++) {
            int new_index = (int) (Math.random() * 20);
            var temp = drivers[new_index];
            drivers[new_index] = drivers[i];
            drivers[i] = temp;
        }
    }

    public void selectQualification(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(qualificationLabel.getText());
        System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
    }

    public void selectCustom(ActionEvent actionEvent) {
        customButton.setVisible(true);
        gridLabel.setText(gridCustom.getText());
        //TODO
    }

    public void customGrid(ActionEvent actionEvent) {
        //TODO
    }

    public void startWeekend(ActionEvent actionEvent) {
        if (selectedTrack == null)
            return;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RaceActivity.fxml"));
            Parent parent = fxmlLoader.load();
            RaceActivityController controller = fxmlLoader.getController();
            controller.setTrack(selectedTrack);
            controller.setDrivers(drivers);
            Scene scene = new Scene(parent, 802, 752);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Race");
            stage.show();
            Stage current = (Stage) customButton.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMenu(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/MainMenu.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent, 900, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Main menu");
            stage.show();
            Stage current = (Stage) menuButton.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSettings(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Settings.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root, 300, 550));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            var laps = 0;
            if (championship.settings.realisticLaps)
                laps = selectedTrack.laps;
            else
                laps = championship.settings.raceLength * 60 * 1000 / selectedTrack.raceTime;
            raceLabel.setText(laps + " laps");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

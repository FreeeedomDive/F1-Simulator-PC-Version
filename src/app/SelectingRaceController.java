package app;

import app.ChampionshipClasses.Championship;
import app.ChampionshipClasses.Comparators;
import app.ChampionshipClasses.Driver;
import app.Tracks.AllTracksInfo;
import app.Tracks.Track;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
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

    private ToggleGroup radioButtonsGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioButtonsGroup = new ToggleGroup();
        gridStandings.setToggleGroup(radioButtonsGroup);
        gridReversed.setToggleGroup(radioButtonsGroup);
        gridRandom.setToggleGroup(radioButtonsGroup);
        gridQual.setToggleGroup(radioButtonsGroup);
        gridCustom.setToggleGroup(radioButtonsGroup);
        tracksListView.setItems(FXCollections.observableList(AllTracksInfo.getTracks()));
        tracksListView.setOnMouseClicked(event -> selectedRaceLabel.setText(tracksListView.getSelectionModel().getSelectedItem().name));
    }

    public void selectStandings(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridStandings.getText());
    }

    public void selectReversed(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridReversed.getText());
    }

    public void selectRandom(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(gridRandom.getText());
    }

    public void selectQualification(ActionEvent actionEvent) {
        customButton.setVisible(false);
        gridLabel.setText(qualificationLabel.getText());
    }

    public void selectCustom(ActionEvent actionEvent) {
        customButton.setVisible(true);
        gridLabel.setText(gridCustom.getText());
    }

    public void customGrid(ActionEvent actionEvent) {
        //TODO
    }

    public void startWeekend(ActionEvent actionEvent) {
        var championship = Championship.getInstance();
        var drivers = new Driver[championship.drivers.length];
        if (gridStandings.selectedProperty().getValue()) {
            System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
        } else if (gridReversed.selectedProperty().getValue()) {
            for (int i = 0; i < drivers.length; i++) {
                drivers[i] = championship.drivers[drivers.length - 1 - i];
            }
        } else if (gridRandom.selectedProperty().getValue()) {
            System.arraycopy(championship.drivers, 0, drivers, 0, drivers.length);
            Arrays.sort(drivers, new Comparators.RandomComparator());
        }
        for (var driver : drivers ) {
            System.out.println(driver);
        }
    }
}

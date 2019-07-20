package app;

import app.GlobalClasses.Championship;
import app.GlobalClasses.Settings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    public ToggleGroup rounds;
    public RadioButton oneShotRadio;
    public RadioButton oneRoundRadio;
    public RadioButton threeRoundsRadio;
    public TextField q1Field;
    public TextField q2Field;
    public TextField q3Field;

    public CheckBox realisticCheckBox;
    public TextField raceLengthField;

    public Slider influenceSlider;

    public Button bigRedButton;
    public Button saveButton;
    public Button cancelButton;

    public Championship championship;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        championship = Championship.getInstance();
        var settings = championship.settings;

        System.out.println(settings);

        switch (settings.qualificationType) {
            case OneShot:
                oneShotRadio.selectedProperty().setValue(true);
                q1Field.setVisible(false);
                q2Field.setVisible(false);
                q3Field.setVisible(false);
                break;
            case OneRound:
                oneRoundRadio.selectedProperty().setValue(true);
                q1Field.setPromptText("Length of round");
                q1Field.setText(String.valueOf(settings.q1Length));
                q2Field.setVisible(false);
                q3Field.setVisible(false);
                break;
            case ThreeRounds:
            default:
                q1Field.setVisible(true);
                q2Field.setVisible(true);
                q3Field.setVisible(true);
                q1Field.setPromptText("Q1 (minutes)");
                q2Field.setPromptText("Q2 (minutes)");
                q3Field.setPromptText("Q3 (minutes)");
                q1Field.setText(String.valueOf(settings.q1Length));
                q2Field.setText(String.valueOf(settings.q2Length));
                q3Field.setText(String.valueOf(settings.q3Length));
                threeRoundsRadio.selectedProperty().setValue(true);
                break;
        }

        if (settings.realisticLaps) {
            realisticCheckBox.selectedProperty().setValue(true);
            raceLengthField.setVisible(false);
        } else {
            realisticCheckBox.selectedProperty().setValue(false);
            raceLengthField.setVisible(true);
            raceLengthField.setText(String.valueOf(settings.raceLength));
        }

        influenceSlider.setValue(settings.qualificationPriority);

        q1Field.textProperty().addListener(this::badCharsListener);
        q1Field.textProperty().addListener((observable, oldValue, newValue) -> emptyListener(newValue, q1Field));
        q2Field.textProperty().addListener(this::badCharsListener);
        q2Field.textProperty().addListener((observable, oldValue, newValue) -> emptyListener(newValue, q2Field));
        q3Field.textProperty().addListener(this::badCharsListener);
        q3Field.textProperty().addListener((observable, oldValue, newValue) -> emptyListener(newValue, q3Field));

        raceLengthField.textProperty().addListener(this::badCharsListener);
        raceLengthField.textProperty().addListener((observable, oldValue, newValue) -> emptyListener(newValue, raceLengthField));
    }

    private void badCharsListener(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        for (int i = 0; i < newValue.length(); i++) {
            if (!Character.isDigit(newValue.charAt(i))) {
                raceLengthField.setText(oldValue);
                return;
            }
        }
    }

    private void emptyListener(String value, TextField field) {
        if (value.isEmpty())
            field.setStyle("-fx-background-color: #ff0000; ");
        else
            field.setStyle("-fx-background-color: #ffffff; ");
    }

    public void saveSettings(ActionEvent actionEvent) {
        if (!realisticCheckBox.selectedProperty().getValue() && raceLengthField.getLength() == 0)
            return;
        if (oneRoundRadio.selectedProperty().getValue() && q1Field.getLength() == 0)
            return;
        if (threeRoundsRadio.selectedProperty().getValue() && q1Field.getLength() == 0 && q2Field.getLength() == 0 && q3Field.getLength() == 0)
            return;

        var settings = new Settings();
        if (oneShotRadio.selectedProperty().getValue()) {
            settings.qualificationType = Settings.QualificationType.OneShot;
            settings.q1Length = 1;
        } else if (oneRoundRadio.selectedProperty().getValue()) {
            settings.qualificationType = Settings.QualificationType.OneRound;
            settings.q1Length = Integer.parseInt(q1Field.textProperty().getValue());
        } else if (threeRoundsRadio.selectedProperty().getValue()) {
            settings.qualificationType = Settings.QualificationType.ThreeRounds;
            settings.q1Length = Integer.parseInt(q1Field.textProperty().getValue());
            settings.q2Length = Integer.parseInt(q2Field.textProperty().getValue());
            settings.q3Length = Integer.parseInt(q3Field.textProperty().getValue());
        }

        if (realisticCheckBox.selectedProperty().getValue()) {
            settings.realisticLaps = true;
            settings.raceLength = -1;
        } else {
            settings.realisticLaps = false;
            settings.raceLength = Integer.parseInt(raceLengthField.textProperty().getValue());
        }
        settings.qualificationPriority = (int) influenceSlider.getValue();
        settings.saveToFile();
        championship.settings = settings;
        Stage current = (Stage) saveButton.getScene().getWindow();
        current.close();
    }

    public void cancelSettings(ActionEvent actionEvent) {
        Stage current = (Stage) cancelButton.getScene().getWindow();
        current.close();
    }

    public void resetChampionship(ActionEvent actionEvent) {
        for (var driver : championship.drivers){
            driver.reset();
        }
        championship.saveChampionship();
    }

    public void clickOneShot(ActionEvent actionEvent) {
        q1Field.setVisible(false);
        q2Field.setVisible(false);
        q3Field.setVisible(false);
    }

    public void clickOneRound(ActionEvent actionEvent) {
        q1Field.setVisible(true);
        q2Field.setVisible(false);
        q3Field.setVisible(false);
        q1Field.setText("");
        q1Field.setPromptText("Length of round");
    }

    public void clickThreeRounds(ActionEvent actionEvent) {
        q1Field.setVisible(true);
        q2Field.setVisible(true);
        q3Field.setVisible(true);
        q1Field.setPromptText("Q1 (minutes)");
        q2Field.setPromptText("Q2 (minutes)");
        q3Field.setPromptText("Q3 (minutes)");
        q1Field.setText("");
        q2Field.setText("");
        q3Field.setText("");
    }

    public void clickRealistic(ActionEvent actionEvent) {
        if (realisticCheckBox.selectedProperty().getValue()) {
            raceLengthField.setVisible(false);
        } else {
            realisticCheckBox.selectedProperty().setValue(false);
            raceLengthField.setVisible(true);
            raceLengthField.setText("");
        }
    }
}

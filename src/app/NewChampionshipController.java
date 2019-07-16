package app;

import app.ChampionshipClasses.Championship;
import app.ChampionshipClasses.Driver;
import app.ChampionshipClasses.SavedChampionship;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class NewChampionshipController implements Initializable {

    //<editor-fold desc="JavaFX Variables">
    public TextField champName;
    public TextArea champDescription;
    public Button champCreate;
    public TextField team1name;
    public TextField team2name;
    public TextField team3name;
    public TextField team4name;
    public TextField team5name;
    public TextField team6name;
    public TextField team7name;
    public TextField team8name;
    public TextField team9name;
    public TextField team10name;
    private TextField[] teamNames;
    public ColorPicker team1color;
    public ColorPicker team2color;
    public ColorPicker team3color;
    public ColorPicker team4color;
    public ColorPicker team5color;
    public ColorPicker team6color;
    public ColorPicker team7color;
    public ColorPicker team8color;
    public ColorPicker team9color;
    public ColorPicker team10color;
    private ColorPicker[] teamColors;
    public TextField team1driver1name;
    public TextField team2driver1name;
    public TextField team3driver1name;
    public TextField team4driver1name;
    public TextField team5driver1name;
    public TextField team6driver1name;
    public TextField team7driver1name;
    public TextField team8driver1name;
    public TextField team9driver1name;
    public TextField team10driver1name;
    private TextField[] teamDriver1Names;
    public TextField team1driver2name;
    public TextField team2driver2name;
    public TextField team3driver2name;
    public TextField team4driver2name;
    public TextField team5driver2name;
    public TextField team6driver2name;
    public TextField team7driver2name;
    public TextField team8driver2name;
    public TextField team9driver2name;
    public TextField team10driver2name;
    private TextField[] teamDriver2Names;
    public Slider team1driver1slider1;
    public Slider team2driver1slider1;
    public Slider team3driver1slider1;
    public Slider team4driver1slider1;
    public Slider team5driver1slider1;
    public Slider team6driver1slider1;
    public Slider team7driver1slider1;
    public Slider team8driver1slider1;
    public Slider team9driver1slider1;
    public Slider team10driver1slider1;
    private Slider[] teamDriver1Sliders1;
    public Slider team1driver1slider2;
    public Slider team2driver1slider2;
    public Slider team3driver1slider2;
    public Slider team4driver1slider2;
    public Slider team5driver1slider2;
    public Slider team6driver1slider2;
    public Slider team7driver1slider2;
    public Slider team8driver1slider2;
    public Slider team9driver1slider2;
    public Slider team10driver1slider2;
    private Slider[] teamDriver1Sliders2;
    public Slider team1driver2slider1;
    public Slider team2driver2slider1;
    public Slider team3driver2slider1;
    public Slider team4driver2slider1;
    public Slider team5driver2slider1;
    public Slider team6driver2slider1;
    public Slider team7driver2slider1;
    public Slider team8driver2slider1;
    public Slider team9driver2slider1;
    public Slider team10driver2slider1;
    private Slider[] teamDriver2Sliders1;
    public Slider team1driver2slider2;
    public Slider team2driver2slider2;
    public Slider team3driver2slider2;
    public Slider team4driver2slider2;
    public Slider team5driver2slider2;
    public Slider team6driver2slider2;
    public Slider team7driver2slider2;
    public Slider team8driver2slider2;
    public Slider team9driver2slider2;
    public Slider team10driver2slider2;
    private Slider[] teamDriver2Sliders2;
    public TitledPane titled1;
    public TitledPane titled2;
    public TitledPane titled3;
    public TitledPane titled4;
    public TitledPane titled5;
    public TitledPane titled6;
    public TitledPane titled7;
    public TitledPane titled8;
    public TitledPane titled9;
    public TitledPane titled10;
    private TitledPane[] titledPanes;
    //</editor-fold>
    private SavedChampionship champ;
    private double minLeft = 1.0;
    private double maxLeft = 1.015;
    private double minRight = 1.025;
    private double maxRight = 1.050;

    Driver[] drivers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addExpandListeners();
        drivers = new Driver[20];
        this.teamNames = new TextField[]{
                team1name,
                team2name,
                team3name,
                team4name,
                team5name,
                team6name,
                team7name,
                team8name,
                team9name,
                team10name
        };
        this.teamColors = new ColorPicker[]{
                team1color,
                team2color,
                team3color,
                team4color,
                team5color,
                team6color,
                team7color,
                team8color,
                team9color,
                team10color
        };
        this.teamDriver1Names = new TextField[]{
                team1driver1name,
                team2driver1name,
                team3driver1name,
                team4driver1name,
                team5driver1name,
                team6driver1name,
                team7driver1name,
                team8driver1name,
                team9driver1name,
                team10driver1name
        };
        this.teamDriver2Names = new TextField[]{
                team1driver2name,
                team2driver2name,
                team3driver2name,
                team4driver2name,
                team5driver2name,
                team6driver2name,
                team7driver2name,
                team8driver2name,
                team9driver2name,
                team10driver2name
        };
        this.teamDriver1Sliders1 = new Slider[]{
                team1driver1slider1,
                team2driver1slider1,
                team3driver1slider1,
                team4driver1slider1,
                team5driver1slider1,
                team6driver1slider1,
                team7driver1slider1,
                team8driver1slider1,
                team9driver1slider1,
                team10driver1slider1
        };
        this.teamDriver1Sliders2 = new Slider[]{
                team1driver1slider2,
                team2driver1slider2,
                team3driver1slider2,
                team4driver1slider2,
                team5driver1slider2,
                team6driver1slider2,
                team7driver1slider2,
                team8driver1slider2,
                team9driver1slider2,
                team10driver1slider2
        };
        this.teamDriver2Sliders1 = new Slider[]{
                team1driver2slider1,
                team2driver2slider1,
                team3driver2slider1,
                team4driver2slider1,
                team5driver2slider1,
                team6driver2slider1,
                team7driver2slider1,
                team8driver2slider1,
                team9driver2slider1,
                team10driver2slider1
        };
        this.teamDriver2Sliders2 = new Slider[]{
                team1driver2slider2,
                team2driver2slider2,
                team3driver2slider2,
                team4driver2slider2,
                team5driver2slider2,
                team6driver2slider2,
                team7driver2slider2,
                team8driver2slider2,
                team9driver2slider2,
                team10driver2slider2
        };
    }

    private void addExpandListeners() {
        titledPanes = new TitledPane[]{
                titled1, titled2, titled3, titled4, titled5, titled6, titled7, titled8, titled9, titled10
        };
        for (var i = 0; i < titledPanes.length; i++) {
            final var index = i;
            titledPanes[i].expandedProperty().addListener((observable, wasOpened, nowOpened) -> {
                if (nowOpened) {
                    for (int j = 0; j < titledPanes.length; j++) {
                        if (j != index)
                            titledPanes[j].expandedProperty().setValue(false);
                    }
                }

            });
        }
    }

    public void create2019season(ActionEvent actionEvent) {
        createSeasonFromFile(
                "ChampionshipConfigs/season2019.txt",
                "Season 2019",
                "Original F1 2019 season");
    }

    public void create2018season(ActionEvent actionEvent) {
        createSeasonFromFile(
                "ChampionshipConfigs/season2018.txt",
                "Season 2018",
                "Original F1 2018 season");
    }

    public void create2017season(ActionEvent actionEvent) {
        createSeasonFromFile(
                "ChampionshipConfigs/season2017.txt",
                "Season 2017",
                "Original F1 2017 season");
    }

    public void create2016season(ActionEvent actionEvent) {
        createSeasonFromFile(
                "ChampionshipConfigs/season2016.txt",
                "Season 2016",
                "F1 2016 season (10 teams instead of 11)");
    }

    private void createSeasonFromFile(String filename, String name, String description) {
        try {
            try (FileReader reader = new FileReader(filename); Scanner scanner = new Scanner(reader)) {
                var i = 0;
                while (scanner.hasNextLine()) {
                    drivers[i] = Driver.createDriverFromString(scanner.nextLine());
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        champName.textProperty().setValue(name);
        champDescription.textProperty().setValue(description);
        for (int i = 0; i < drivers.length; i += 2) {
            teamNames[i / 2].textProperty().setValue(drivers[i].team);
            teamColors[i / 2].setValue(Color.web(drivers[i].teamColor));
            teamDriver1Names[i / 2].textProperty().setValue(drivers[i].name);
            teamDriver2Names[i / 2].textProperty().setValue(drivers[i + 1].name);
            teamDriver1Sliders1[i / 2].setValue((drivers[i].leftRatio - minLeft) / (maxLeft - minLeft));
            teamDriver1Sliders2[i / 2].setValue((drivers[i].rightRatio - minRight) / (maxRight - minRight));
            teamDriver2Sliders1[i / 2].setValue((drivers[i + 1].leftRatio - minLeft) / (maxLeft - minLeft));
            teamDriver2Sliders2[i / 2].setValue((drivers[i + 1].rightRatio - minRight) / (maxRight - minRight));
        }
    }

    public void create(ActionEvent actionEvent) {
        var id = (int) (Math.random() * 89999 + 10000);
        var name = champName.textProperty().getValue();
        var descr = champDescription.textProperty().getValue();
        for (int i = 0; i < drivers.length; i += 2) {
            String team = teamNames[i / 2].textProperty().getValue();
            var teamColor = "#" + teamColors[i / 2].getValue().toString().substring(2, 8).toUpperCase();
            String name1 = teamDriver1Names[i / 2].textProperty().getValue();
            double left1 = minLeft + teamDriver1Sliders1[i / 2].getValue() * (maxLeft - minLeft);
            double right1 = minRight + teamDriver1Sliders2[i / 2].getValue() * (maxRight - minRight);
            String name2 = teamDriver2Names[i / 2].textProperty().getValue();
            double left2 = minLeft + teamDriver2Sliders1[i / 2].getValue() * (maxLeft - minLeft);
            double right2 = minRight + teamDriver2Sliders2[i / 2].getValue() * (maxRight - minRight);
            if (name.isEmpty() || team.isEmpty() || name1.isEmpty() || name2.isEmpty()) {
                showError(champName.getScene().getWindow(), "Something is missing!");
                return;
            }
            drivers[i] = new Driver(name1, team, teamColor, true, left1, right1);
            drivers[i + 1] = new Driver(name2, team, teamColor, false, left2, right2);
        }
        champ = new SavedChampionship(id, name, descr);
        Championship.createDataForDrivers(id, drivers);
        Stage stage = (Stage) champCreate.getScene().getWindow();
        stage.close();
    }

    private void showError(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public SavedChampionship getCreatedChampionship() {
        return champ;
    }
}

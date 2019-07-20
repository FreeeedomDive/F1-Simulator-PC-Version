package app;

import app.ChampionshipClasses.Championship;
import app.ChampionshipClasses.Driver;
import app.Tracks.AllTracksInfo;
import app.Tracks.Track;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RaceActivityController implements Initializable {

    public VBox mainVBox;
    public Label lapsLabel;
    public Label separatorLabel;
    public Label lapsCounterLabel;
    public Label lights;
    public ListView<RaceDriver> listView;
    public Canvas canvas;
    GraphicsContext graphicsContext;
    private ObservableList<RaceDriver> driversList;
    Track track;
    int timeToNextStart = 1000;
    int totalLaps;
    int currentLap = 0;
    int racersFinished = 0;
    boolean started = false, finished = false, ended = false;
    int bestLapTime = 1000000;
    int crashValue = 2000000;
    int crashID;
    int poorTyresBorder = 35;

    private Championship championship;

    public void setTrack(Track tr){
        track = tr;
        totalLaps = 25 * 60 * 1000 / track.raceTime;
    }

    public void setDrivers(Driver[] drivers){
        for (int i = 0; i < drivers.length; i++) {
            var driver = drivers[i];
            driversList.add(new RaceDriver(driver.name, driver.teamColor, track.raceTime * driver.leftRatio, track.raceTime * driver.rightRatio));
            driversList.get(i).position = i + 1;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        championship = Championship.getInstance();
        driversList = FXCollections.observableArrayList();
        listView.setItems(driversList);
        listView.setCellFactory(e -> new RaceListElement());

        crashID = (int) (Math.random() * crashValue + 1);
        startRace();

        Timer updater = new Timer(true);
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (started) {
                        listView.refresh();
                        lapsCounterLabel.setText(String.format("%s / %s", currentLap, totalLaps));
                        graphicsContext.setFill(Paint.valueOf("#333333"));
                        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for (int i = driversList.size() - 1; i >= 0; i--) {
                            var driver = driversList.get(i);
                            if (!driver.crashed && driver.finished)
                                continue;
                            if (driver.crashed && driver.currentLap != currentLap)
                                continue;
                            graphicsContext.setFill(Paint.valueOf(driver.color));
                            var size = 30;
                            double x, y;
                            y = (double) (i) / (driversList.size()) * (canvas.getHeight() - size / 2);
                            if (driver.timeOnPit != 0 && driver.lapTime > (driver.futureLap - driver.timeOnPit) && driver.lapTime < driver.futureLap) {
                                x = canvas.getWidth() - size;
                                graphicsContext.fillText("PIT", x, y, 7);
                            } else {
                                x = ((double) (driver.lapTime) / (driver.futureLap - driver.timeOnPit)) * (canvas.getWidth() - size);
                            }
                            graphicsContext.strokeOval(x, y, size, size / 2);
                            graphicsContext.fillOval(x, y, size, size / 2);
                        }
                    }
                    if (finished) {
                        lapsLabel.setText("FINISH");
                        lapsCounterLabel.setText("");
                        lights.setFont(new Font("Formula1 Display-Regular", 30));
                        lights.setTextFill(Paint.valueOf("#ffffff"));
                        lights.setAlignment(Pos.CENTER);
                        lights.setText("Chequered flag!\n" +
                                "Winner: " + driversList.get(0).name);
                    }
                    if (ended) {
                        updater.cancel();
                    }
                });

            }
        };
        updater.schedule(updateTask, 0, 20);
    }

    private void startRace() {
        long countDownStart = System.currentTimeMillis();
        int delay = (int) (6000 + Math.random() * 3000);
        Timer timer = new Timer(true);
        TimerTask countDownTask = new TimerTask() {
            @Override
            public void run() {
                var now = System.currentTimeMillis();
                if (now < countDownStart + delay) {
                    var numberOfLights = Math.min((now - countDownStart) / 1000, 5);
                    var builder = new StringBuilder();
                    for (var i = 0; i < numberOfLights; i++) {
                        builder.append("●");
                    }
                    Platform.runLater(() -> lights.setText(builder.toString()));
                    return;
                }
                currentLap = 1;
                Platform.runLater(() -> {
                    lights.setText("");
                    lapsLabel.setText("Lap");
                    separatorLabel.setVisible(true);
                    lapsCounterLabel.setText(String.format("%s / %s", currentLap, totalLaps));
                });
                for (var driver : driversList) {
                    driver.currentLap++;
                    startLap(driver);
                }
                started = true;
                timer.cancel();
            }
        };
        timer.schedule(countDownTask, 0, 50);
    }

    private void startLap(RaceDriver driver) {
        int position = driversList.indexOf(driver);
        if (currentLap == 1) {
            driver.futureLap = (int) (Math.random() * (driver.rightTime - driver.leftTime) +
                    driver.leftTime + 3000 + position * timeToNextStart);
        } else {
            driver.timeOnPit = 0;

            int pitChance;
            if (driver.getTyresState() > poorTyresBorder)
                pitChance = 1;
            else
                pitChance = 100 - driver.getTyresState();
            int pitDecider = (int) (Math.random() * 135);
            if (pitDecider < pitChance && driver.currentLap < totalLaps)
                driver.timeOnPit = (int) (Math.random() * 5000 + 12000);
            int drs = 0;
            if (driversList.get(0).currentLap >= 5) {
                for (int i = 0; i < 20; i++) {
                    if (driversList.get(i).equals(driver)) {
                        if (i == 0)
                            break;
                        if (driver.totalTime - driversList.get(i - 1).totalTime <= 1000)
                            drs = (int) (Math.random() * 800 + 200);
                    }
                }
            }
            int wearLose = (100 - driver.getTyresState()) * 30;
            driver.futureLap = (int) (Math.random() * (driver.rightTime - driver.leftTime) +
                    driver.leftTime + driver.timeOnPit - drs + wearLose);
        }
        Timer timer = new Timer();
        var lapStart = System.currentTimeMillis();
        TimerTask lapTimer = new TimerTask() {
            @Override
            public void run() {
                if (isCrashed(driver) && !driver.crashed) {
                    driver.crashed = true;
                    driver.finished = true;
                    racersFinished++;
                    Platform.runLater(() -> {
                        driversList.sort(RaceDriver::compareTo);
                        for (var d : driversList)
                            d.position = driversList.indexOf(d) + 1;
                        listView.refresh();
                    });
                    if (racersFinished == 20)
                        finishRace();
                    cancel();
                } else {
                    driver.lapTime = (int) (System.currentTimeMillis() - lapStart);
                    driver.passed = (driver.currentLap - 1) +
                            Math.min(
                                    Math.min(
                                            (double) (driver.lapTime) / (driver.futureLap - driver.timeOnPit),
                                            driver.futureLap),
                                    0.9999);

                    Platform.runLater(() -> driversList.sort(RaceDriver::compareTo));

                    int problemIndex = (int) (Math.random() * 300000);
                    if (problemIndex == 0) {
                        int loseTime = (int) (Math.random() * 700);
                        driver.futureLap += loseTime;
                        driver.tyresState -= (loseTime / 3);
                    }

                    for (var d : driversList) {
                        d.position = driversList.indexOf(d) + 1;
                    }

                    if (driver.lapTime > driver.futureLap) {
                        finishLap(driver);
                        timer.cancel();
                    }
                }
            }
        };
        timer.schedule(lapTimer, 0, 5);
    }

    private void finishRace() {

        if (racersFinished == driversList.size()) {
            ended = true;
            System.out.println("End");
            System.out.println(RaceDriver.generateTime(driversList.get(0).totalTime));
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        setStatisticsToChampionship();
                        openMenu();
                    });
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task, 3000);
        }
    }


    private void setStatisticsToChampionship() {

        for (var rDriver : driversList) {
            for (var driver : championship.drivers) {
                if (driver.name.equals(rDriver.name)) {
                    driver.totalRaces++;
                    driver.summaryPositions += rDriver.position;
                }
            }
        }

        addPointsToDriver(driversList.get(0), 25);
        addPointsToDriver(driversList.get(1), 18);
        addPointsToDriver(driversList.get(2), 15);
        addPointsToDriver(driversList.get(3), 12);
        addPointsToDriver(driversList.get(4), 10);
        addPointsToDriver(driversList.get(5), 8);
        addPointsToDriver(driversList.get(6), 6);
        addPointsToDriver(driversList.get(7), 4);
        addPointsToDriver(driversList.get(8), 2);
        addPointsToDriver(driversList.get(9), 1);

        for (var i = 0; i < 10; i++) {
            if (driversList.get(i).hasBestLapTime) {
                for (var driver : championship.drivers) {
                    if (driver.name.equals(driversList.get(i).name)) {
                        driver.bestLaps++;
                    }
                }
                addPointsToDriver(driversList.get(i), 1);
                break;
            }
        }

        for (var driver : championship.drivers) {
            if (driver.name.equals(driversList.get(0).name)) {
                driver.wins++;
                break;
            }
        }

        for (var driver : championship.drivers) {
            if (driver.name.equals(driversList.get(0).name) ||
                    driver.name.equals(driversList.get(1).name) ||
                    driver.name.equals(driversList.get(2).name))
                driver.podiums++;
        }

        for (int i = 0; i < 20; i++) {
            if (driversList.get(i).crashed) {
                for (var driver : championship.drivers) {
                    if (driver.name.equals(driversList.get(i).name)) {
                        driver.retires++;
                    }
                }
            }
        }

        for (var rDriver : driversList) {
            for (var driver : championship.drivers) {
                if (rDriver.name.equals(driver.name)) {
                    if (rDriver.position < driver.highestRacePosition) {
                        driver.highestRacePosition = rDriver.position;
                        driver.highestRacePositionReached = 1;
                    }
                    else if (rDriver.position == driver.highestRacePosition) {
                        driver.highestRacePositionReached++;
                    }
                }
            }
        }

        championship.saveChampionship();
    }

    private void addPointsToDriver(RaceDriver rDriver, int points) {
        for (var driver : championship.drivers) {
            if (driver.name.equals(rDriver.name))
                driver.points += points;
        }
    }

    private void openMenu(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/mainmenu.fxml"));
            Parent parent = fxmlLoader.load();

            Scene scene = new Scene(parent, 900, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Main menu");
            stage.show();
            Stage current = (Stage) listView.getScene().getWindow();
            current.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishLap(final RaceDriver driver) {
        if (!driver.crashed) {
            if (driver.timeOnPit != 0) {
                driver.numberOfPits += 1;
                driver.lastLapPit = true;
                driver.setFreshTyres();
            } else {
                double wear = (driver.futureLap / 750) * (3 + Math.random() * 3);
                driver.tyresState -= wear;
                driver.tyresState = Math.max(0, driver.tyresState);
                driver.lastLapPit = false;
            }
            driver.totalTime += driver.futureLap;
            driver.lastTime = driver.futureLap;
            if (driver.lastTime < driver.bestTime) {
                driver.bestTime = driver.lastTime;
                if (driver.bestTime < bestLapTime) {
                    bestLapTime = driver.bestTime;
                    for (RaceDriver dr : driversList) {
                        dr.hasBestLapTime = false;
                    }
                    driver.hasBestLapTime = true;
                }
            }
            driver.lapTime = 0;
            driver.currentLap++;
            Platform.runLater(() -> driversList.sort(RaceDriver::compareTo));

            int position = -1;
            for (var d : driversList) {
                d.position = driversList.indexOf(d) + 1;
                if (d.equals(driver))
                    position = driversList.indexOf(driver) + 1;
            }

            if (position == 1) {
                currentLap = driver.currentLap;
            }
        }
        if (driver.currentLap <= totalLaps && !finished)
            startLap(driver);
        else {
            finished = true;
            driver.finished = true;
            racersFinished++;
            finishRace();
        }
    }

    private boolean isCrashed(RaceDriver driver) {
        int crash = (int) (Math.random() * crashValue + 1);
        if (crash == crashID)
            return true;
        int index = driversList.indexOf(driver);
        if (index == 0)
            return false;
        if (driver.totalTime - driversList.get(index - 1).totalTime < 500) {
            crash = (int) (Math.random() * crashValue + 1);
            return crash == crashID;
        }
        return false;
    }

    public class RaceListElement extends ListCell<RaceDriver> {

        private boolean tipsSetted = false;

        FXMLLoader loader;

        public Label positionLabel;
        public Label colorLabel;
        public Label nameLabel;
        public Label lapLabel;
        public Label currentLapTimeLabel;
        public Label totalTimeLabel;
        public Label gapLabel;
        public Label lastLapLabel;
        public Label bestLapLabel;
        public Label pitsLabel;
        public Label tyresLabel;
        public HBox mainBox;

        private void setupTips() {
            currentLapTimeLabel.setTooltip(new Tooltip("Current lap time"));
            totalTimeLabel.setTooltip(new Tooltip("Gap to leader"));
            gapLabel.setTooltip(new Tooltip("Gap to car ahead"));
            lastLapLabel.setTooltip(new Tooltip("Time of last lap"));
            bestLapLabel.setTooltip(new Tooltip("Time of best lap\nPurple - best lap of the race"));
            pitsLabel.setTooltip(new Tooltip("Number of pit stops"));
            tyresLabel.setTooltip(new Tooltip("Tyres state"));
        }

        @Override
        protected void updateItem(RaceDriver driver, boolean empty) {
            super.updateItem(driver, empty);
            if (empty || driver == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("fxml/RaceListElement.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!tipsSetted) {
                setupTips();
                tipsSetted = true;
            }
            setStyle("-fx-padding: 0px");
            positionLabel.setTextFill(Paint.valueOf("#000000"));
            if (!driver.crashed)
                positionLabel.setText(String.valueOf(driver.position));
            else
                positionLabel.setText("");
            colorLabel.setStyle(String.format("-fx-background-color: %s; ", driver.color));
            nameLabel.setText(driver.shortName);
            lapLabel.setText(String.valueOf(driver.currentLap));
            pitsLabel.setText(String.valueOf(driver.numberOfPits));
            if (driver.crashed) {
                currentLapTimeLabel.setText("OUT");
                totalTimeLabel.setText("");
                gapLabel.setText("");
                tyresLabel.setText("");
            } else if (driver.finished) {
                lapLabel.setText("");
                currentLapTimeLabel.setText("");
                var index = driversList.indexOf(driver);
                if (index == 0) {
                    totalTimeLabel.setText("WINNER");
                    gapLabel.setText("INTERVAL");
                } else {
                    totalTimeLabel.setText("+" + RaceDriver.generateTime(Math.abs(driver.totalTime - driversList.get(0).totalTime)));
                    gapLabel.setText("+" + RaceDriver.generateTime(Math.abs(driver.totalTime - driversList.get(index - 1).totalTime)));
                }
                if (driver.getTyresState() < poorTyresBorder)
                    tyresLabel.setTextFill(Paint.valueOf("aa0000"));
                else
                    tyresLabel.setTextFill(Paint.valueOf("ffffff"));
                tyresLabel.setText(driver.getTyresState() + "%");
                gapLabel.setTextFill(Paint.valueOf("#FFFFFF"));
            } else {
                var index = driversList.indexOf(driver);
                if (driver.timeOnPit != 0 && driver.lapTime > (driver.futureLap - driver.timeOnPit) && driver.lapTime < driver.futureLap) {
                    currentLapTimeLabel.setText("PIT");
                } else
                    currentLapTimeLabel.setText(RaceDriver.generateTime(driver.lapTime));
                if (index == 0) {
                    totalTimeLabel.setText("LEADER");
                    gapLabel.setTextFill(Paint.valueOf("#FFFFFF"));
                    gapLabel.setText("INTERVAL");
                } else {
                    var leader = driversList.get(0);
                    if (leader.passed - driver.passed >= 1) {
                        var laps = Math.floor(leader.passed - driver.passed);
                        totalTimeLabel.setText("+" + laps + (laps == 1 ? "LAP" : "LAPS"));
                    } else {
                        var leaderGap = (leader.passed - driver.passed) * (((double) (driver.futureLap - driver.timeOnPit) + (leader.futureLap - leader.timeOnPit)) / 2);
                        totalTimeLabel.setText("+" + RaceDriver.generateTime(Math.abs((int) leaderGap)));
                    }

                    var previous = driversList.get(index - 1);
                    if (previous.passed - driver.passed >= 1) {
                        var laps = Math.floor(previous.passed - driver.passed);
                        gapLabel.setText("+" + laps + (laps == 1 ? "LAP" : "LAPS"));
                    } else {
                        var gap = (previous.passed - driver.passed) * (((double) (driver.futureLap - driver.timeOnPit) + (previous.futureLap - previous.timeOnPit)) / 2);
                        gapLabel.setText("+" + RaceDriver.generateTime(Math.abs((int) gap)));
                        if (gap <= 1000 && driver.currentLap >= 5)
                            gapLabel.setTextFill(Paint.valueOf("#008800"));
                        else
                            gapLabel.setTextFill(Paint.valueOf("#FFFFFF"));
                    }
                }
                if (driver.getTyresState() <= poorTyresBorder)
                    tyresLabel.setTextFill(Paint.valueOf("aa0000"));
                else
                    tyresLabel.setTextFill(Paint.valueOf("ffffff"));
                tyresLabel.setText(driver.getTyresState() + "%");
            }
            if (driver.currentLap <= 1) {
                lastLapLabel.setText("NO TIME");
                bestLapLabel.setText("NO TIME");
                positionLabel.setStyle("-fx-background-color: #ffffff; ");
                lastLapLabel.setTextFill(Paint.valueOf("#ffffff"));
                bestLapLabel.setTextFill(Paint.valueOf("#ffffff"));
            } else {
                lastLapLabel.setText(RaceDriver.generateTime(driver.lastTime));
                bestLapLabel.setText(RaceDriver.generateTime(driver.bestTime));
                if (driver.hasBestLapTime) {
                    positionLabel.setStyle("-fx-background-color: #ac00f0; ");
                    bestLapLabel.setTextFill(Paint.valueOf("#ac00f0"));
                    if (driver.lastTime == driver.bestTime)
                        lastLapLabel.setTextFill(Paint.valueOf("#ac00f0"));
                    else
                        lastLapLabel.setTextFill(Paint.valueOf("#ffffff"));
                } else {
                    positionLabel.setStyle("-fx-background-color: #ffffff; ");
                    bestLapLabel.setTextFill(Paint.valueOf("#ffffff"));
                    if (driver.lastLapPit)
                        lastLapLabel.setTextFill(Paint.valueOf("#ff9933"));
                    else if (driver.lastTime == driver.bestTime)
                        lastLapLabel.setTextFill(Paint.valueOf("#00aa00"));
                    else
                        lastLapLabel.setTextFill(Paint.valueOf("#ffffff"));
                }
            }

            setText(null);

            setGraphic(mainBox);
        }
    }
}

package app;

import app.GlobalClasses.Championship;
import app.GlobalClasses.Driver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;

public class StandingsController extends Application {

    @Override
    public void start(Stage stage) {
        TableView table = new TableView<Driver>();

        TableColumn<Driver, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Driver, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        TableColumn<Driver, Integer> winsColumn = new TableColumn<>("Wins");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<Driver, Integer> podiumsColumn = new TableColumn<>("Podiums");
        podiumsColumn.setCellValueFactory(new PropertyValueFactory<>("podiums"));
        TableColumn<Driver, Integer> bestLapsColumn = new TableColumn<>("Best laps");
        bestLapsColumn.setCellValueFactory(new PropertyValueFactory<>("bestLaps"));
        TableColumn<Driver, Integer> retiresColumn = new TableColumn<>("Retires");
        retiresColumn.setCellValueFactory(new PropertyValueFactory<>("retires"));

        TableColumn<Driver, String> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        TableColumn<Driver, String> bestColumn = new TableColumn<>("Best position in race");
        bestColumn.setCellValueFactory(new PropertyValueFactory<>("bestPlace"));

        table.getColumns().addAll(nameColumn, pointsColumn, winsColumn, podiumsColumn, bestLapsColumn, retiresColumn, balanceColumn, bestColumn);

        var drivers = FXCollections.observableList(Arrays.asList(Championship.getInstance().drivers));
        table.setItems(drivers);

        table.setPrefSize(750, 600);

        Pane pane = new Pane();
        pane.getChildren().add(table);

        stage.setTitle("Standings");
        Scene scene = new Scene(pane, 750, 600);
        stage.setScene(scene);
        stage.show();
    }

}

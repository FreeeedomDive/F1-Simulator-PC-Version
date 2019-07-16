package app;

import app.ChampionshipClasses.ActiveChampionships;
import app.ChampionshipClasses.Championship;
import app.ChampionshipClasses.SavedChampionship;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ChampionshipSelectingController implements Initializable {

    public ActiveChampionships champs;
    public Label active;
    public ListView listView;
    public Button create;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChampFromFile();
        listView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                if (champs.championships.size() == 0)
                    return;
                SavedChampionship toLoad = (SavedChampionship) listView.getSelectionModel().getSelectedItem();
                if (toLoad == null)
                    return;
                load(toLoad);
            }
        });
    }

    private void loadChampFromFile() {
        try {
            FileInputStream fis = new FileInputStream("SavedChampionships/champs.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            champs = (ActiveChampionships) oin.readObject();
            fis.close();
            oin.close();
        } catch (FileNotFoundException ex) {
            champs = new ActiveChampionships();
            saveChamps();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        active.setText("Active championships: " + champs.championships.size());
        listView.setItems(FXCollections.observableList(champs.championships));
    }

    private void saveChamps() {
        try {
            FileOutputStream fos = new FileOutputStream("SavedChampionships/champs.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(champs);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewChampionship(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/NewChampionship.fxml"));
            Parent root = loader.load();
            NewChampionshipController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("New Championship");
            stage.setScene(new Scene(root, 600, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            var champ = controller.getCreatedChampionship();
            if (champ != null) {
                champs.championships.add(champ);
                listView.refresh();
                active.setText(String.format("Active championships: %s", champs.championships.size()));
                saveChamps();
                load(champ);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadChampionship(ActionEvent actionEvent) {
        if (champs.championships.size() == 0)
            return;
        SavedChampionship toLoad = (SavedChampionship) listView.getSelectionModel().getSelectedItem();
        if (toLoad == null)
            return;
        load(toLoad);
    }

    private void load(SavedChampionship championship){
        try {
            Championship.createExistedInstance(championship.id, championship.name, championship.description);

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

    public void removeChampionship(ActionEvent actionEvent) {
        SavedChampionship toRemove = (SavedChampionship) listView.getSelectionModel().getSelectedItem();
        if (toRemove == null)
            return;
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Removing championship");
        alert.setContentText(String.format("Do you really want to delete \"%s\" championship?", toRemove.name));
        var result = alert.showAndWait();
        if (!result.isPresent() || result.get() == ButtonType.NO)
            return;
        if (result.get() == ButtonType.YES) {
            toRemove.removeDriversData();
            champs.championships.remove(toRemove);
            active.setText(String.format("Active championships: %s", champs.championships.size()));
            listView.refresh();
            saveChamps();
        }
    }

}

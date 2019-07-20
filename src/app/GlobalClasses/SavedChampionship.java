package app.GlobalClasses;

import java.io.File;
import java.io.Serializable;

public class SavedChampionship implements Serializable {

    public int id;
    public String name;
    public String description;

    public SavedChampionship(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = !description.isEmpty() ? description : "None";
    }

    @Override
    public String toString() {
        return this.name + "\nDescription: " + this.description;
    }

    public void removeDriversData(){
        var file = new File(String.format("SavedChampionships/champ%s", this.id));
        file.delete();
    }
}

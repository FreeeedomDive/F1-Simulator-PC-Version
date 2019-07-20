package app.GlobalClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class ActiveChampionships implements Serializable {

    public ArrayList<SavedChampionship> championships;

    public ActiveChampionships(){
        championships = new ArrayList<>();
    }

}

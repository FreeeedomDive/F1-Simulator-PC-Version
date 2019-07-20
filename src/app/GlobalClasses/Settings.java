package app.GlobalClasses;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Settings {

    public enum QualificationType {
        OneShot,
        OneRound,
        ThreeRounds
    }

    public int raceLength;
    public boolean realisticLaps;

    public QualificationType qualificationType;
    public int q1Length;
    public int q2Length;
    public int q3Length;

    public int qualificationPriority;

    public void getFromFile() {
        try {
            try (FileReader reader = new FileReader("Settings/settings"); Scanner scanner = new Scanner(reader)) {
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine())
                    builder.append(scanner.nextLine());
                String json = builder.toString();
                var settings = new Gson().fromJson(json, Settings.class);
                this.realisticLaps = settings.realisticLaps;
                this.raceLength = settings.raceLength;
                this.qualificationType = settings.qualificationType;
                this.q1Length = settings.q1Length;
                this.q2Length = settings.q2Length;
                this.q3Length = settings.q3Length;
                this.qualificationPriority = settings.qualificationPriority;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSettingsExist(){
        File temp = new File("Settings/settings");
        return temp.exists();
    }

    public void saveToFile() {
        try {
            try (FileWriter writer = new FileWriter("Settings/settings")) {
                writer.write(new Gson().toJson(this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Settings:" +
                "\nQualificationType: " + this.qualificationType +
                "\nRealistic race: " + this.realisticLaps +
                "\nTime of race: " + this.raceLength +
                "\nQualification influence: " + this.qualificationPriority;
    }
}

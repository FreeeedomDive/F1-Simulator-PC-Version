package app.GlobalClasses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Scanner;

public class Championship implements Serializable {

    public int id;
    public String name;
    public String description;
    public Driver[] drivers;
    private static Championship INSTANCE = new Championship();
    public Settings settings;

    private Championship() {

    }

    public void saveChampionship() {
        String jsonString = new Gson().toJson(this.drivers);
        FileWriter writer = null;
        try {
            try {
                writer = new FileWriter(String.format("SavedChampionships/champ%s", this.id));
                writer.write(jsonString);
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Championship getInstance() {
        return INSTANCE;
    }

    public static void createDataForDrivers(int id, Driver[] drivers) {
        INSTANCE.settings = new Settings();
        INSTANCE.id = id;
        INSTANCE.drivers = drivers;
        INSTANCE.saveChampionship();
    }

    public static Championship createExistedInstance(int id, String name, String description) {
        INSTANCE.settings = new Settings();
        INSTANCE.id = id;
        INSTANCE.name = name;
        INSTANCE.description = description;
        Type itemsArrType = new TypeToken<Driver[]>() {
        }.getType();
        FileReader reader = null;
        Scanner scanner = null;
        try {
            try {
                reader = new FileReader(String.format("SavedChampionships/champ%s", id));
                scanner = new Scanner(reader);
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine())
                    builder.append(scanner.nextLine());
                String json = builder.toString();
                INSTANCE.drivers = new Gson().fromJson(json, itemsArrType);
            } finally {
                reader.close();
                scanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("CHAMPIONSHIP\n" +
                "ID: " + this.id +
                "\nName: " + this.name +
                "\nDescription: " + this.description + "\nDrivers:\n");
        for (var driver : this.drivers) {
            str.append(driver.toString()).append("\n");
        }
        return str.toString();
    }
}

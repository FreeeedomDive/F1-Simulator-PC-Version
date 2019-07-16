package app.Tracks;

public class Track {

    public String name;
    public String place;
    public int laps;
    public int raceTime;
    public int[] sectors;

    public Track(String name, String place, int laps, int raceTime, int[] sectors){
        this.name = name;
        this.place = place;
        this.laps = laps;
        this.raceTime = raceTime;
        this.sectors = sectors;
    }

}

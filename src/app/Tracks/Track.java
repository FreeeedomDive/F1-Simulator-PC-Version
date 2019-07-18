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

    private static String generateTime(int time) {
        int millis = time % 1000;
        String correctMillis;
        if (millis >= 100)
            correctMillis = Integer.toString(millis);
        else if (millis >= 10)
            correctMillis = "0" + millis;
        else correctMillis = "00" + millis;
        int sec = time / 1000;
        int min = sec / 60;
        sec = sec % 60;
        String correctSec;
        if (sec >= 10)
            correctSec = Integer.toString(sec);
        else correctSec = "0" + sec;
        if (min == 0)
            return correctSec + "." + correctMillis;
        return min + ":" + correctSec + "." + correctMillis;
    }

    @Override
    public String toString() {
        return this.name + "\n" + this.place + "\nLaps: " + this.laps + "\nLap time: " + generateTime(this.raceTime)
                + "\nSectors: " + generateTime(this.sectors[0]) + " / " + generateTime(this.sectors[1]) + " / " + generateTime(this.sectors[2]);
    }
}

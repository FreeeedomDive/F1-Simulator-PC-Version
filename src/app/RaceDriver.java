package app;

public class RaceDriver implements Comparable {

    public String name;
    public String shortName;
    public String color;

    public int position;
    public int[] allPositions;

    public int leftTime;
    public int rightTime;

    public int totalTime;
    public int lapTime;
    public int futureLap;
    public int lastTime;
    public int bestTime;

    public boolean finished;
    public boolean crashed;

    public int currentLap;

    public int pitLap;
    public int timeOnPit;
    public boolean lastLapPit = false;
    public int numberOfPits;
    public int tyresState;

    public boolean hasBestLapTime;

    public double passed;

    private final static int maxTyresState = 10000;

    public RaceDriver(String name, String color, double leftTime, double rightTime) {
        this.name = name;
        this.shortName = name.substring(0, 3).toUpperCase();
        this.color = color;
        this.position = 0;
        this.leftTime = (int) leftTime;
        this.rightTime = (int) rightTime;
        this.totalTime = 0;
        this.lapTime = 0;
        this.lastTime = 0;
        this.bestTime = Integer.MAX_VALUE;
        this.finished = false;
        this.crashed = false;
        this.currentLap = 0;
        this.pitLap = -1;
        this.setFreshTyres();
        this.numberOfPits = 0;
    }

    public void setFreshTyres(){
        this.tyresState = maxTyresState;
    }

    public int getTyresState(){
        return this.tyresState * 100 / maxTyresState;
    }

    static String generateTime(int time) {
        int hours = time / 3600000;
        time %= 3600000;
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
        if (hours == 0)
            return min + ":" + correctSec + "." + correctMillis;
        String correctMin;
        if (min >= 10)
            correctMin = Integer.toString(min);
        else correctMin = "0" + min;
        return hours + ":" + correctMin + ":" + correctSec + "." + correctMillis;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            return -1;
        if (!(o instanceof RaceDriver))
            return -1;
        RaceDriver driver = (RaceDriver) o;
        if (this.finished && !this.crashed && driver.finished && !driver.crashed)
            return Integer.compare(this.totalTime, driver.totalTime);
        if (this.passed > 0.03 || driver.passed > 0.03)
            return -Double.compare(this.passed, driver.passed);
        else return 0;
    }
}

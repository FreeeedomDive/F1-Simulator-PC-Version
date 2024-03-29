package app.GlobalClasses;

import app.Tracks.AllTracksInfo;

import java.text.DecimalFormat;

public class Driver implements Comparable {

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getRetires() {
        return retires;
    }

    public int getPodiums() {
        return podiums;
    }

    public int getBestLaps() {
        return bestLaps;
    }

    public String getBalance() {
        String pattern = "#.###";
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(leftRatio) + " ... " + format.format(rightRatio) + " (" + format.format((leftRatio + rightRatio) / 2) + ")";
    }

    public String getBestPlace() {
        if (totalRaces == 0)
            return "---";
        return highestRacePosition + " (" + highestRacePositionReached + "x)";
    }

    final public String name;
    final public String shortName;
    final public String team;
    final public String teamColor;
    final public double leftRatio;
    final public double rightRatio;
    final public boolean isFirstDriver;

    public int points;

    public int oldPos, newPos;

    public int wins, retires, poles;

    public int totalRaces;
    public int summaryPositions;

    public int q2;
    public int q3;

    public int qualTeammateWins;
    public int raceTeammateWins;

    public int podiums;
    public int bestLaps;

    public int highestQualPosition;
    public int highestRacePosition;

    public int highestQualPositionReached;
    public int highestRacePositionReached;

    public int[] pointsPerRace;

    public Driver(String name, String team, String teamColor, boolean isFirstDriver, double leftRatio, double rightRatio) {
        this.name = name;
        this.shortName = name.substring(0, 3).toUpperCase();
        this.team = team;
        this.teamColor = teamColor;
        this.isFirstDriver = isFirstDriver;
        this.leftRatio = leftRatio;
        this.rightRatio = rightRatio;
        this.highestQualPosition = 21;
        this.highestRacePosition = 21;
        this.pointsPerRace = new int[AllTracksInfo.getNumberOfTracks() + 1];
    }

    public static Driver createDriverFromString(String string) {
        var data = string.split(";");
        var name = data[0];
        var team = data[1];
        var color = data[2];
        var first = Integer.parseInt(data[3]) == 1;
        var left = Double.parseDouble(data[4]);
        var right = Double.parseDouble(data[5]);
        return new Driver(name, team, color, first, left, right);
    }

    public void reset() {
        this.points = 0;
        this.oldPos = 0;
        this.newPos = 0;
        this.wins = 0;
        this.poles = 0;
        this.retires = 0;
        this.totalRaces = 0;
        this.summaryPositions = 0;
        this.q2 = 0;
        this.q3 = 0;
        this.qualTeammateWins = 0;
        this.raceTeammateWins = 0;
        this.podiums = 0;
        this.bestLaps = 0;
        this.highestQualPosition = 21;
        this.highestRacePosition = 21;
        this.highestQualPositionReached = 0;
        this.highestRacePositionReached = 0;
        this.pointsPerRace = new int[AllTracksInfo.getNumberOfTracks() + 1];
    }

    @Override
    public String toString() {
        return this.name + " Points: " + this.points + " Highest position: " + this.highestRacePosition + " reached " + this.highestRacePositionReached + " times";
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

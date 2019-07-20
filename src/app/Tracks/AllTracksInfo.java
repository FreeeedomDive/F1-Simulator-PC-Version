package app.Tracks;

import java.util.ArrayList;

public class AllTracksInfo {

    private static ArrayList<Track> tracks;
    private static int numberOfTracks = 0;

    public static ArrayList<Track> getTracks() {
        if (numberOfTracks == 0){
            tracks = new ArrayList<>();
            tracks.add(new Track("Australia", "Melbourne", 58, 85945, new int[]{27041, 22300, 31400}));
            tracks.add(new Track("Bahrain", "Sahir", 56, 93769, new int[]{22124, 25100, 40500}));
            tracks.add(new Track("China", "Shanghai", 57, 95678, new int[]{29330, 39300, 23200}));
            tracks.add(new Track("Azerbaijan", "Baku", 51, 105593, new int[]{29330, 39300, 23200}));
            tracks.add(new Track("Spain", "Catalonia", 66, 78441, new int[]{20475, 29800, 27800}));
            tracks.add(new Track("Monaco", "Monte-Carlo", 78, 74178, new int[]{19453, 33700, 18500}));
            tracks.add(new Track("Canada", "Monreal", 70, 73864, new int[]{22605, 23400, 24400}));
            tracks.add(new Track("France", "Paul Ricard", 53, 94225, new int[]{30489, 30400, 29400}));
            tracks.add(new Track("Austria", "Spielberg", 71, 66251, new int[]{16377, 26400, 20300}));
            tracks.add(new Track("Britain", "Silverstone", 52, 90696, new int[]{28170, 31700, 25500}));
            tracks.add(new Track("Germany", "Hockenheimring", 67, 75545, new int[]{16325, 33300, 22200}));
            tracks.add(new Track("Hungary", "Hungaroring", 70, 80276, new int[]{27320, 29000, 22600}));
            tracks.add(new Track("Belgium", "Spa Francorchamps", 44, 106286, new int[]{28332, 47600, 28700}));
            tracks.add(new Track("Italy", "Monza", 53, 82497, new int[]{26079, 28100, 26000}));
            tracks.add(new Track("Singapore", "Marina Bay", 61, 101945, new int[]{24084, 39300, 34400}));
            tracks.add(new Track("Russia", "Sochi", 53, 95860, new int[]{33110, 31900, 27400}));
            tracks.add(new Track("Japan", "Suzuka", 53, 92319, new int[]{30784, 39400, 18200}));
            tracks.add(new Track("USA", "Ostin", 56, 97108, new int[]{25178, 37100, 30600}));
            tracks.add(new Track("Mexico", "Mexico City", 71, 78747, new int[]{26230, 29800, 19700}));
            tracks.add(new Track("Brazil", "Interlagos", 71, 70540, new int[]{17732, 34400, 16600}));
            tracks.add(new Track("Abu Dhabi", "Yas Marina", 55, 100755, new int[]{17288, 39060, 38000}));
            numberOfTracks = tracks.size();
        }
        return tracks;
    }

    public static int getNumberOfTracks() {
        if (numberOfTracks == 0)
            getTracks();
        return numberOfTracks;
    }
}

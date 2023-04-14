package model.buildings;

public class Accommodation extends Building {
    private AccommodationType accommodationType;
    private int numberOfSettler;

    public Accommodation(AccommodationType accommodationType, int numberOfSettler) {
        this.accommodationType = accommodationType;
        this.numberOfSettler = numberOfSettler;
    }
}

package model.buildings;

public class Accommodation extends Building {
    private AccommodationType accommodationType;
    private int numberOfSettler;

    public Accommodation(AccommodationType accommodationType) {
        super(accommodationType.getBuildingType());

        this.accommodationType = accommodationType;
        this.numberOfSettler =accommodationType.getNumberOfSettler();
    }
}

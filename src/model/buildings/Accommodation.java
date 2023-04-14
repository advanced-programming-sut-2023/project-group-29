package model.buildings;

public class Accommodation extends Building {
    private final AccommodationType accommodationType;
    private final int numberOfSettler;

    public Accommodation(AccommodationType accommodationType) {
        super(accommodationType.getBuildingType());

        this.accommodationType = accommodationType;
        this.numberOfSettler = accommodationType.getNumberOfSettler();
    }
}

package model.buildings;

public class ResourceExtracter extends Building {
    private final ResourceExtracterType resourceExtracterType;
    private final int rate;

    public ResourceExtracter(ResourceExtracterType resourceExtracterType) {
        super(resourceExtracterType.getBuildingType());
        this.resourceExtracterType = resourceExtracterType;
        this.rate = resourceExtracterType.getRate();
    }
}

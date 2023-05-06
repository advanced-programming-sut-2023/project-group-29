package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductExtractorType;
import model.dealing.Product;

public class ProductExtractor extends Building {
    private ProductExtractorType productExtractorType;
    private int rate;
    private Product producingProduct;

    public ProductExtractor(ProductExtractorType productExtractorType,
                            PlayerNumber playerNumber, int positionX, int positionY) {
        super(productExtractorType.getBuildingType(), playerNumber, positionX, positionY);
        this.productExtractorType = productExtractorType;
        this.rate = productExtractorType.getRate();
        this.producingProduct = productExtractorType.getProducingProduct();
    }

    @Override
    public String getName() {
        return productExtractorType.getName();
    }
    @Override
    public void setShowingSignInMap(String showingSignInMap) {
        showingSignInMap = productExtractorType.getBuildingType().abbreviation() + getOwnerNumber().getNumber();
    }

    public void update() {
        int change = Math.min(ownerEmpire.getEmptySpace(1),rate);
        ownerEmpire.changeProduct(this.producingProduct, change);
        ownerEmpire.fillStorage(1,change);
    }
}

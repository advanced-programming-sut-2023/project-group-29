package model.buildings.buildingClasses;

import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductExtractorType;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public class ProductExtractor extends Building {
    private ProductExtractorType productExtractorType;
    private int rate;
    private Tradable producingTradable;

    public ProductExtractor(ProductExtractorType productExtractorType,
                            PlayerNumber playerNumber, int positionX, int positionY) {
        super(productExtractorType.getBuildingType(), playerNumber, positionX, positionY);
        this.productExtractorType = productExtractorType;
        this.rate = productExtractorType.getRate();
        this.producingTradable = productExtractorType.getProducingTradable();
        setShowingSignInMap();
    }

    @Override
    public String getName() {
        return productExtractorType.getName();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = productExtractorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        int switcher;
        if (producingTradable instanceof Product) {
            switcher = 1;
        } else if (producingTradable instanceof Food) {
            switcher = 0;
        }
        int change = Math.min(ownerEmpire.getEmptySpace(0), rate);
        ownerEmpire.changeTradableAmount(producingTradable, change);
        ownerEmpire.fillStorage(0, change);

    }
}

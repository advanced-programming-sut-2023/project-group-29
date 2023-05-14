package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductExtractorType;
import model.dealing.Product;
import model.dealing.Tradable;

public class ProductExtractor extends Building {
    private final ProductExtractorType productExtractorType;
    private final int rate;
    private final Tradable producingTradable;

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
        int switcher = 0;
        if (producingTradable instanceof Product) {
            switcher = 1;
        }
        Empire ownerEmpire=this.getOwnerEmpire();
        int change = Math.min(ownerEmpire.getEmptySpace(switcher), rate + ownerEmpire.getFearRate());
        ownerEmpire.changeTradableAmount(producingTradable, change);
    }
}

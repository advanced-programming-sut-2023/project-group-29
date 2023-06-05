package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductProcessorType;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public class ProductProcessor extends Building {
    private final ProductProcessorType productProcessorType;
    private final int rate;
    private final Product consumingProduct;
    private final Tradable producingTradable;

    public ProductProcessor(ProductProcessorType productProcessorType,
                            PlayerNumber playerNumber, int positionX, int positionY) {
        super(productProcessorType.getBuildingType(), playerNumber, positionX, positionY);
        this.productProcessorType = productProcessorType;
        this.consumingProduct = productProcessorType.getConsumingProduct();
        this.producingTradable = productProcessorType.getProducingTradable();
        this.rate = productProcessorType.getRate();
        setShowingSignInMap();
    }

    @Override
    public void setShowingSignInMap() {
        showingSignInMap = productProcessorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }


    public ProductProcessorType getProductProcessorType() {
        return productProcessorType;
    }

    public void update() {
        Empire ownerEmpire = this.getOwnerEmpire();
        int availableConsumingProduct = ownerEmpire.getTradableAmount(consumingProduct);
        int changeAmount = Math.min(availableConsumingProduct, rate + ownerEmpire.getFearRate());
        if (producingTradable != null) {
            if (producingTradable instanceof Food) {
                changeAmount = Math.min(changeAmount, ownerEmpire.getEmptySpace(0));
            }
            ownerEmpire.changeTradableAmount(consumingProduct, -changeAmount);
            ownerEmpire.changeTradableAmount(producingTradable, changeAmount);
        }
        else {
            //just for inn:
            ownerEmpire.changeTradableAmount(consumingProduct, -changeAmount);
            int BEER_PRICE = 10;
            ownerEmpire.changeWealth(changeAmount * BEER_PRICE);
        }
    }
}

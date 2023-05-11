package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductProcessorType;
import model.dealing.Food;
import model.dealing.Product;
import model.dealing.Tradable;

public class ProductProcessor extends Building {
    private ProductProcessorType productProcessorType;
    private int rate;
    private Product consumingProduct;
    private Tradable producingTradable;

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
    public String getName() {
        return productProcessorType.getName();
    }


    @Override
    public void setShowingSignInMap() {
        showingSignInMap = productProcessorType.getBuildingType().abbreviation() + (getOwnerNumber().getNumber() + 1);
    }

    public void update() {
        Empire ownerEmpire=this.getOwnerEmpire();

        int availableConsumingProduct = ownerEmpire.getTradableAmount(consumingProduct);
        int changeAmount = Math.min(availableConsumingProduct, rate + ownerEmpire.getFearRate());
        if (producingTradable instanceof Food) {
            changeAmount = Math.min(changeAmount, ownerEmpire.getEmptySpace(0));
        }
        ownerEmpire.changeTradableAmount(consumingProduct, -changeAmount);
        ownerEmpire.changeTradableAmount(producingTradable, changeAmount);
    }
}

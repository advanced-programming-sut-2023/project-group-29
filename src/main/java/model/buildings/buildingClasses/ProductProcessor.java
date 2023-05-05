package model.buildings.buildingClasses;

import model.Empire;
import model.PlayerNumber;
import model.buildings.Building;
import model.buildings.buildingTypes.ProductProcessorType;
import model.dealing.Product;

public class ProductProcessor extends Building {
    private ProductProcessorType productProcessorType;
    private int rate;
    private Product consumingProduct;
    private Product producingProduct;

    public ProductProcessor(ProductProcessorType productProcessorType,
                            PlayerNumber playerNumber, int positionX, int positionY) {
        super(productProcessorType.getBuildingType(), playerNumber, positionX, positionY);
        this.productProcessorType = productProcessorType;
        this.consumingProduct = productProcessorType.getConsumingProduct();
        this.producingProduct = productProcessorType.getProducingProduct();
        this.rate = productProcessorType.getRate();
    }

    public void update(Empire empire) {
        int availableConsumingProduct = empire.getProductAmount(this.consumingProduct);
        int changeAmount = Math.min(availableConsumingProduct, rate);
        empire.changeProduct(this.consumingProduct, -changeAmount);
        empire.changeProduct(this.producingProduct, changeAmount);
    }

    @Override
    public String getName() {
        return productProcessorType.getName();
    }
}

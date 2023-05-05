package controller.menucontrollers;

import model.AppData;
import model.Empire;
import model.dealing.Resource;
import view.messages.ShopMenuMessages;

public class ShopMenuController {

    public static String showPriceList() {
        Empire empire = AppData.getCurrentUser().getEmpire();
        String output = "Resources:";
        for (Resource resource : Resource.values()) {
            output += "\n";
            output += resource.getName() + ":";
            output += " buying price: " + resource.getBuyingPrice();
            output += " selling price: " + resource.getBuyingPrice();
            output += " owned amount: " + empire.getResourceAmount(resource);
        }
        return output;
    }

    public static ShopMenuMessages buy(String resourceName, int amount) {
        Empire empire = AppData.getCurrentUser().getEmpire();
        Resource resource = Resource.getResourceByName(resourceName);
        if (resource == null || resourceName.equals("coins")) {
            return ShopMenuMessages.INVALID_NAME;
        } else if (empire.getWealth() < resource.getBuyingPrice() * amount) {
            return ShopMenuMessages.FEW_CASH;
        } else if (empire.getEmptySpace(1) < amount) {
            return ShopMenuMessages.LACK_OF_SPACE;
        }
        empire.changeWealth(-resource.getBuyingPrice() * amount);
        empire.changeResourceAmount(resource, amount);
        empire.fillStorage(1, amount);
        return ShopMenuMessages.SUCCESS;
    }

    public static ShopMenuMessages sell(String resourceName, int amount) {
        Empire empire = AppData.getCurrentUser().getEmpire();
        Resource resource = Resource.getResourceByName(resourceName);
        if (resource == null || resourceName.equals("coins")) {
            return ShopMenuMessages.INVALID_NAME;
        } else if (empire.getResourceAmount(resource) < amount) {
            return ShopMenuMessages.FEW_AMOUNT;
        }
        empire.changeWealth(resource.getSellingPrice() * amount);
        empire.changeResourceAmount(resource, -amount);
        empire.fillStorage(1, -amount);
        return ShopMenuMessages.SUCCESS;
    }

}

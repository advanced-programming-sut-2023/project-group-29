package controller.menucontrollers;

import model.Empire;
import model.dealing.Resource;
import view.messages.ShopMenuMessages;

public class ShopMenuController {

    public static String showPriceList(Empire empire) {
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

    public static ShopMenuMessages buy(Empire empire, String resourceName, int amount) {
        Resource resource = Resource.getResourceByName(resourceName);
        if (empire.getWealth() < resource.getBuyingPrice() * amount) {
            return ShopMenuMessages.FEW_CASH;
        }
        else if (true/*TODO: check if the empire has enough space*/) {
            return ShopMenuMessages.LACK_OF_SPACE;
        }
        empire.changeWealth(-resource.getBuyingPrice() * amount);
        empire.changeResourceAmount(resource, amount);
        return ShopMenuMessages.SUCCESS;
    }

    public static ShopMenuMessages sell(Empire empire, String resourceName, int amount) {
        Resource resource = Resource.getResourceByName(resourceName);
        if (empire.getResourceAmount(resource) < amount) {
            return ShopMenuMessages.FEW_AMOUNT;
        }
        empire.changeWealth(resource.getSellingPrice() * amount);
        empire.changeResourceAmount(resource, -amount);
        return ShopMenuMessages.SUCCESS;
    }

}

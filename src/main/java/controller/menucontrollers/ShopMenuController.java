package controller.menucontrollers;

import model.Empire;
import model.dealing.Resource;
import view.messages.ShopMenuMessages;

public class ShopMenuController {
    public static ShopMenuMessages buy(String resourceName, int amount) {
        Empire empire = GameController.getGameData().getPlayingEmpire();

        Resource resource = Resource.getResourceByName(resourceName);
        if (empire.getWealth() < resource.getBuyingPrice() * amount) {
            return ShopMenuMessages.FEW_CASH;
        }
        else if (empire.getEmptySpace(1) < amount) {
            return ShopMenuMessages.LACK_OF_SPACE;
        }
        empire.changeWealth(-resource.getBuyingPrice() * amount);
        empire.changeTradableAmount(resource, amount);
        return ShopMenuMessages.SUCCESS;
    }

    public static ShopMenuMessages sell(String resourceName, int amount) {
        Empire empire = GameController.getGameData().getPlayingEmpire();
        Resource resource = Resource.getResourceByName(resourceName);
        if (empire.getTradableAmount(resource) < amount) {
            return ShopMenuMessages.FEW_AMOUNT;
        }
        empire.changeWealth(resource.getSellingPrice() * amount);
        empire.changeTradableAmount(resource, -amount);
        return ShopMenuMessages.SUCCESS;
    }

}

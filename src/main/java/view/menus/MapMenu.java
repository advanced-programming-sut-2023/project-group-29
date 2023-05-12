package view.menus;

import controller.MenuNames;
import controller.menucontrollers.MapMenuController;
import model.map.CellType;
import model.map.TreeType;
import model.people.humanTypes.SoldierType;
import view.Command;
import view.messages.MapMenuMessages;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapMenu {
    public static MenuNames run(Scanner scanner) {
        showMap();
        while(true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher=Command.getMatcher(input, Command.SHOW_MAP)) != null) {
                showMap(matcher);
            }
            else if ((matcher=Command.getMatcher(input, Command.SHOW_DETAILS)) != null) {
                showDetails(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.MOVE_MAP)) != null) {
                moveMap(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.SET_BLOCK_TEXTURE)) != null) {
                setBlockTexture(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.SET_PART_OF_BLOCK_TEXTURE)) != null) {
                setPartOfBlockTexture(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.CLEAR)) != null) {
                clear(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_ROCK)) != null) {
                dropRock(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_TREE)) != null) {
                dropTree(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_BUILDING)) != null) {
                dropBuilding(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.DROP_UNIT)) != null) {
                dropUnit(matcher);
            }
            else if ((matcher = Command.getMatcher(input, Command.BACK_GAME_MENU)) != null) {
                return MenuNames.GAME_MENU;
            }
            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void moveMap(Matcher matcher) {
        String input = matcher.group(0);
        Matcher matcherUp = Pattern.compile("up").matcher(input);
        Matcher matcherDown = Pattern.compile("down").matcher(input);
        Matcher matcherLeft = Pattern.compile("left").matcher(input);
        Matcher matcherRight = Pattern.compile("right").matcher(input);
        int up = 0;
        int left = 0;
        int down = 0;
        int right = 0;
        while (matcherUp.find()) {
            up++;
        }
        while (matcherDown.find()) {
            down++;
        }
        while (matcherLeft.find()) {
            left++;
        }
        while (matcherRight.find()) {
            right++;
        }
        System.out.println(up + " " + left + " " + right + " " + down);
        System.out.println(MapMenuController.moveMap(up, right, down, left));
    }

    private static void setBlockTexture(Matcher matcher) {
        String input = matcher.group(0);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if (!(typeMatcher.find() && xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        String type = typeMatcher.group(1);
        int trueType = 0;
        CellType cellType = null;
        for(CellType myCellType: CellType.values()) {
            if(myCellType.getName().equals(type)) {
                trueType = 1;
                cellType = myCellType;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        System.out.println(MapMenuController.setBlockTexture(cellType, x, y));
    }

    private static void setPartOfBlockTexture(Matcher matcher) {
        String input = matcher.group(0);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher x1Matcher = Pattern.compile("-x1\\s+(\\d+)").matcher(input);
        Matcher y1Matcher = Pattern.compile("-y1\\s+(\\d+)").matcher(input);
        Matcher x2Matcher = Pattern.compile("-x2\\s+(\\d+)").matcher(input);
        Matcher y2Matcher = Pattern.compile("-y2\\s+(\\d+)").matcher(input);
        if (!(typeMatcher.find() && x1Matcher.find() && y1Matcher.find() && x2Matcher.find() && y2Matcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        String type = typeMatcher.group(1);
        int trueType = 0;
        CellType cellType = null;
        for(CellType myCellType: CellType.values()) {
            if(myCellType.getName().equals(type)) {
                trueType = 1;
                cellType = myCellType;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type!");
            return;
        }
        int x1 = Integer.parseInt(x1Matcher.group(1));
        int y1 = Integer.parseInt(y1Matcher.group(1));
        int x2 = Integer.parseInt(x2Matcher.group(1));
        int y2 = Integer.parseInt(y2Matcher.group(1));
        if (x2 <= x1 || y2 <= y1) {
            System.out.println("Please enter your coordinates correctly!");
        }
        System.out.println(MapMenuController.setPartOfBlockTexture(cellType, x1, y1, x2, y2));
    }

    private static void clear(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        MapMenuController.clear(x, y);
    }

    private static void dropRock(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher dMatcher = Pattern.compile("-d\\s+(\\S+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find() && dMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String direction = dMatcher.group(1);
        if(!(direction.equals("w") || direction.equals("e") || direction.equals("n") || direction.equals("s") || direction.equals("random"))){
            System.out.println("Invalid direction!");
            return;
        }
        if(direction.equals("random")) {
            Random random = new Random();
            int myRandom = random.nextInt() % 4;
            if(myRandom == 0){
                direction = "w";
            } else if (myRandom == 1) {
                direction = "e";
            } else if (myRandom == 2) {
                direction = "n";
            } else {
                direction = "s";
            }
        }
        MapMenuController.dropRock(x, y, direction);
    }

    private static void dropTree(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher typeMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find() && typeMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String type = typeMatcher.group(1);
        int trueType = 0;
        TreeType myTreeType = null;
        for (TreeType treeType: TreeType.values()) {
            if (treeType.name().equals(type)) {
                trueType = 1;
                myTreeType = treeType;
                break;
            }
        }
        if(trueType == 0) {
            System.out.println("Invalid type of tree!");
            return;
        }
        System.out.println(MapMenuController.dropTree(x, y, myTreeType));
    }

    private static void dropUnit(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher cMatcher = Pattern.compile("-c\\s+(\\d+)").matcher(input);
        Matcher tMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        Matcher nMatcher = Pattern.compile("-n\\s+(\\d+)").matcher(input);

        if (!(xMatcher.find() && yMatcher.find() && cMatcher.find() && tMatcher.find() && nMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        int count = Integer.parseInt(cMatcher.group(1));
        String type = tMatcher.group(1);
        int playerNumberInt=Integer.parseInt(nMatcher.group(1));
        if (count < 1) {
            System.out.println("Invalid count!");
            return;
        }
        int trueType = 0;
        for(SoldierType soldierType: SoldierType.values()) {
            if(soldierType.getName().equals(type)) {
                trueType = 1;
                break;
            }
        }
        if (trueType == 0) {
            System.out.println("Invalid type of soldier!");
            return;
        }
        System.out.println(MapMenuController.dropUnit(x, y, type, count, playerNumberInt));
    }

    private static void showMap(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xAmount = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yAmount = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xAmount.find() && yAmount.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int positionX=Integer.parseInt(xAmount.group(1));
        int positionY=Integer.parseInt(yAmount.group(1));

        System.out.println(MapMenuController.showMap(positionX,positionY));
    }
    private static void showDetails(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xAmount = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yAmount = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        if(!(xAmount.find() && yAmount.find())) {
            System.out.println("Invalid command!");
            return;
        }
        int positionX=Integer.parseInt(xAmount.group(1));
        int positionY=Integer.parseInt(yAmount.group(1));

        System.out.print(MapMenuController.showDetails(positionX,positionY));
    }

    private static void showMap(){
        System.out.println(MapMenuController.showMap());
    }

    private static void dropBuilding(Matcher matcher) {
        String input = matcher.group(0);
        Matcher xMatcher = Pattern.compile("-x\\s+(\\d+)").matcher(input);
        Matcher yMatcher = Pattern.compile("-y\\s+(\\d+)").matcher(input);
        Matcher tMatcher = Pattern.compile("-t\\s+(\\S+)").matcher(input);
        if(!(xMatcher.find() && yMatcher.find() && tMatcher.find())) {
            System.out.println("Invalid command!");
            return;
        }

        int x = Integer.parseInt(xMatcher.group(1));
        int y = Integer.parseInt(yMatcher.group(1));
        String buildingName = tMatcher.group(1);

        MapMenuMessages result = MapMenuController.dropBuildingAsAdmin(x, y, buildingName,1);   //todo abbasfar like unit handle owner regex
        switch (result) {
            case TWO_MAIN_KEEP -> System.out.println("You aren't allowed to have two main keeps!");
            case INVALID_INDEX -> System.out.println("You have chosen an Invalid amount of x or y!");
            case INVALID_TYPE -> System.out.println("This type of building doesn't exist!");
            case UNCONNECTED_STOREROOMS -> System.out.println("Your storerooms must be connected to each other!");
            case IMPROPER_CELL_TYPE -> System.out.println("This cell is improper for dropping this type of building!");
            case FULL_CELL -> System.out.println("Another building has been already dropped here!");
            case LACK_OF_RESOURCES -> System.out.println("You don't have enough resources to build this building!");
            case SUCCESSFUL -> System.out.println("The building was dropped successfully!");
        }
    }
}

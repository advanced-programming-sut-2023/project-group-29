package model.map;

import model.Pair;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class Map {
    private final Cell[][] cells;
    private final int width;
    private final int usersCount;
    private final int infinity=10000000;

    public Map(int width,int usersCount) {
        this.width = width;
        this.cells = new Cell[width+1][width+1];
        this.usersCount=usersCount;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int distanceOfTwoCellsForMoving(int firstX, int firstY, int secondX, int secondY, boolean ableToClimbLadder,boolean ableToClimbWallAndTower) {
        return BFS(firstX,firstY,secondX,secondY,true,!ableToClimbWallAndTower,!ableToClimbLadder);
    }

    public int distanceOfTwoCellsForAttacking(int firstX, int firstY, int secondX, int secondY, boolean overTheWallAllowed) {
        return BFS(firstX,firstY,secondX,secondY,false,!overTheWallAllowed,!overTheWallAllowed);
    }

    private int BFS(int firstX, int firstY, int secondX, int secondY,boolean blockForbiddenCellTypes,boolean blockWallsAndTowers,boolean blockWallsNearLadder) {

        Queue<Pair> queue=new LinkedList<>();
        int distance=infinity;

        int[][] distances=new int[width+1][width+1];
        for(int[] array:distances)
            Arrays.fill(array,-1);

        queue.add(new Pair(firstX,firstY));

        while (!queue.isEmpty())
        {
            Pair thisCellCoordination=queue.poll();
            if(thisCellCoordination.first==secondX && thisCellCoordination.second==secondY)
                break;

            Cell thisCell=cells[thisCellCoordination.first][thisCellCoordination.second];

            for(Direction direction:Direction.values())
            {
                Pair addingCellCoordination=null;

                if(direction.equals(Direction.RIGHT) && thisCellCoordination.first<width)
                    addingCellCoordination=new Pair(thisCellCoordination.first+1,thisCellCoordination.second);

                if(direction.equals(Direction.DOWN) && thisCellCoordination.second<width)
                    addingCellCoordination=new Pair(thisCellCoordination.first,thisCellCoordination.second+1);

                if(direction.equals(Direction.LEFT) && thisCellCoordination.first>1)
                    addingCellCoordination=new Pair(thisCellCoordination.first-1,thisCellCoordination.second);

                if(direction.equals(Direction.UP) && thisCellCoordination.second>1)
                    addingCellCoordination=new Pair(thisCellCoordination.first,thisCellCoordination.second-1);


                //todo abbasfar complete below ifs
                //if is null
                //if distance== -1

                Cell addingCell=cells[addingCellCoordination.first][addingCellCoordination.second];

//                if(!skipForbiddenCellTypes && (addingCell.getCellType().equals()))
//                    continue;

                queue.add(addingCellCoordination);
                distances[addingCellCoordination.first][addingCellCoordination.second]=distances[thisCellCoordination.first][thisCellCoordination.second]+1;
            }
        }

        return distances[secondX][secondY];
    }
    private enum Direction
    {
        RIGHT,
        DOWN,
        LEFT,
        UP
    }

    public int getWidth() {
        return width;
    }

    public boolean isIndexValid(int x, int y) {
        return x >= 1 && x <= width && y >= 1 && y <= width;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void dropBuilding(int positionX,int positionY,String type,int ownerPlayerNumber)
    {
        //todo
    }
}

package model;

import model.map.Map;

public class Patrol {
    private final Pair<Integer,Integer>[] patrolEndCells = new Pair[2];
    private boolean patrolling = false;
    private CellEnds currentCell = CellEnds.SECOND_CELL;

    public boolean isPatrolling() {
        return patrolling;
    }

    public void startNewPatrol(int firstX, int firstY, int secondX, int secondY) {
        patrolEndCells[0] = new Pair(firstX, firstY);
        patrolEndCells[1] = new Pair(secondX, secondY);

        currentCell = CellEnds.SECOND_CELL;
        patrolling = true;
    }

    public void cancelPatrolling() {
        this.patrolling = false;
    }

    public Pair[] getPatrolEndCells() {
        return patrolEndCells;
    }

    public void patrol(Movable movable, Map map) {
        Movable.MovingResult movingResult;

        if (currentCell.equals(CellEnds.FIRST_CELL))
            movingResult = movable.move(map, patrolEndCells[1].first, patrolEndCells[1].second);
        else
            movingResult = movable.move(map, patrolEndCells[0].first, patrolEndCells[0].second);

        if (movingResult.equals(Movable.MovingResult.SUCCESSFUL)) {
            currentCell = currentCell.equals(CellEnds.FIRST_CELL) ? CellEnds.SECOND_CELL : CellEnds.FIRST_CELL;
        }
        else {
            patrolling = false;
        }
    }

    public enum CellEnds {
        FIRST_CELL,
        SECOND_CELL
    }
}

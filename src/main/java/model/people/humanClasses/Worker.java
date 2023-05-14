package model.people.humanClasses;

import model.PlayerNumber;
import model.people.Human;
import model.people.humanTypes.WorkerType;

public class Worker extends Human {
    private final WorkerType workerType;

    public Worker(WorkerType workerType, PlayerNumber playerNumber, int positionX, int positionY) {
        super(workerType.getHumanType(), playerNumber, positionX, positionY);

        this.workerType = workerType;
    }

}

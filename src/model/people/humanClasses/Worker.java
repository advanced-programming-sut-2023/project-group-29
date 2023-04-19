package model.people.humanClasses;

import model.PlayerNumber;
import model.people.Human;
import model.people.humanTypes.WorkerType;

public class Worker extends Human {
    private final WorkerType workerType;

    public Worker(WorkerType workerType, PlayerNumber playerNumber) {
        //TODO: enter proper position
        super(workerType.getHumanType(), playerNumber,0,0);

        this.workerType = workerType;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}

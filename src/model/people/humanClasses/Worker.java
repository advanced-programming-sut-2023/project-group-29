package model.people.humanClasses;

import model.PlayerNumber;
import model.people.Human;
import model.people.humanTypes.WorkerType;

public class Worker extends Human {
    private final WorkerType workerType;

    public Worker(WorkerType workerType, PlayerNumber playerNumber) {
        super(workerType.getHumanType(), playerNumber);

        this.workerType = workerType;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}

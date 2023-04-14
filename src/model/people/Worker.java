package model.people;

public class Worker extends Human {
    private final WorkerType workerType;

    public Worker(WorkerType workerType) {
        super(workerType.getHumanType());

        this.workerType = workerType;
    }

    public WorkerType getWorkerType() {
        return workerType;
    }
}

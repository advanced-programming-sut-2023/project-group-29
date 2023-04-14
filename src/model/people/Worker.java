package model.people;

public class Worker extends Human
{
    private WorkerType workerType;
    public Worker(WorkerType workerType)
    {
        super(workerType.getHumanType());

        this.workerType=workerType;
    }

    public WorkerType getWorkerType()
    {
        return workerType;
    }
}

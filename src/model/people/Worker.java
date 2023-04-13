package model.people;

public class Worker
{
    enum WorkerType
    {
        ENGINEER,
        LADDER_MAN
    }
    private WorkerType workerType;
    private int hp;
    public Worker(WorkerType workerType)
    {
        this.workerType = workerType;
        this.hp=workerType.equals(WorkerType.ENGINEER) ? 10 : 11;
        //TODO set hp numbers up
    }

    public WorkerType getWorkerType()
    {
        return workerType;
    }

    public int getHp()
    {
        return hp;
    }
}

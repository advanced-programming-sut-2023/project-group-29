package model.people;

public enum WorkerType
{
    ;
    private HumanType humanType;
    private WorkerType(HumanType humanType)
    {
        this.humanType=humanType;
    }

    public HumanType getHumanType()
    {
        return humanType;
    }
}

package model.people;

public enum WorkerType {
    ;
    private final HumanType humanType;

    WorkerType(HumanType humanType) {
        this.humanType = humanType;
    }

    public HumanType getHumanType() {
        return humanType;
    }
}

package model.people.humanTypes;

import model.people.HumanType;

public enum WorkerType {
    ;

    private final HumanType humanType;
    private final String name;

    WorkerType(HumanType humanType) {
        this.humanType = humanType;
        this.name = humanType.name();
    }

    public HumanType getHumanType() {
        return humanType;
    }

    public String getName() {
        return name;
    }
}
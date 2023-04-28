package model.people.humanTypes;

import model.people.HumanType;

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

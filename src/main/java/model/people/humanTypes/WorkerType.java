package model.people.humanTypes;

import model.people.HumanType;
import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.DamageEnum;
import model.speedanddamageenums.SpeedEnum;

public enum WorkerType {
    ;

    private final HumanType humanType;
    private final String name;

    WorkerType(HumanType humanType) {
        this.humanType = humanType;
        this.name= humanType.name();
    }

    public HumanType getHumanType() {
        return humanType;
    }

    public String getName() {
        return name;
    }
}
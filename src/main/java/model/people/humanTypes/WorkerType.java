package model.people.humanTypes;

import model.people.HumanType;
import model.speedanddamageenums.AimRangeEnum;
import model.speedanddamageenums.DamageEnum;
import model.speedanddamageenums.SpeedEnum;

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
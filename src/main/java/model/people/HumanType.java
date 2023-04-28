package model.people;

public class HumanType {
    private final int hp;
    private final boolean ableToClimbLadder;
    private final int speed;

    public HumanType(int hp, boolean ableToClimbLadder, int speed) {
        this.hp = hp;
        this.ableToClimbLadder = ableToClimbLadder;
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public boolean isAbleToClimbLadder() {
        return ableToClimbLadder;
    }

    public int getSpeed() {
        return speed;
    }
}

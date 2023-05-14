package model.people;

import model.speedanddamageenums.SpeedEnum;

public record HumanType(
        String name,
        int hp,
        boolean ableToClimbLadder,
        boolean ableToClimbStairs,
        SpeedEnum speed,
        String showingSignInMap) {
}

package model.people;

import model.speedanddamageenums.SpeedEnum;

public record HumanType(
        int hp,
        boolean ableToClimbLadder,
        SpeedEnum speed,
        String showingSignInMap)
{}

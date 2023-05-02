package model.people;

import model.speedanddamageenums.SpeedEnum;

public record HumanType(
        String name,
        int hp,
        boolean ableToClimbLadder,
        SpeedEnum speed,
        String showingSignInMap)
{}

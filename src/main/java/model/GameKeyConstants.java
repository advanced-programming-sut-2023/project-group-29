package model;

import javafx.scene.input.KeyCode;

import java.security.Key;

public class GameKeyConstants {
    //unit commands
    public static final KeyCode attackKey=KeyCode.A;
    public static final KeyCode attackFinalize=KeyCode.ENTER;
    public static final KeyCode moveKey=KeyCode.M;
    public static final KeyCode moveFinalize=KeyCode.ENTER;
    public static final KeyCode pourOilKey=KeyCode.P;
    public static final KeyCode pourOilFinalize=KeyCode.ENTER;
    public static final KeyCode dropLadderKey=KeyCode.L;
    public static final KeyCode disbandUnitKey=KeyCode.D;
    public static final KeyCode buildEquipmentKey=KeyCode.B;
    public static final KeyCode setStateOfUnit=KeyCode.S;

    //map move
    public static final KeyCode mapMoveLeft=KeyCode.LEFT;
    public static final KeyCode mapMoveDown=KeyCode.DOWN;
    public static final KeyCode mapMoveUp=KeyCode.UP;
    public static final KeyCode mapMoveRight=KeyCode.RIGHT;
    public static final KeyCode mapZoomIn=KeyCode.Z;
    public static final KeyCode mapZoomOut=KeyCode.X;

    //map commands
    public static final KeyCode dropUnit=KeyCode.U;
    public static final KeyCode dropBuilding=KeyCode.G;
    public static final KeyCode setTexture=KeyCode.T;
    //others
    public static final KeyCode cancelKey=KeyCode.ESCAPE;
    public static final KeyCode addRemoveUnit=KeyCode.R;
    public static final KeyCode selectBuilding = KeyCode.C;
    public static final KeyCode pasteBuilding = KeyCode.E;
}

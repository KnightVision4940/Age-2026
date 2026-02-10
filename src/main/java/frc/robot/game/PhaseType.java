package frc.robot.game;

public enum PhaseType {
    AUTO(20),
    TRANSITION_SHIFT(10),
    ALLIANCE_SHIFT_1(25),
    ALLIANCE_SHIFT_2(25),
    ALLIANCE_SHIFT_3(25),
    ALLIANCE_SHIFT_4(25),
    END_GAME(30);

    private final int length;
    /**
     * @return the length in seconds
     */
    public int getLength() {return length;}
    /**
     * @param length how long the phase lasts in seconds
     */
    PhaseType(int length) {
        this.length = length;
    }
}

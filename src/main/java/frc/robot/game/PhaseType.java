package frc.robot.game;

public enum PhaseType {
    AUTO(1, 20, false),
    TRANSITION_SHIFT(2, 10, true),
    ALLIANCE_SHIFT_1(3, 25, true),
    ALLIANCE_SHIFT_2(4, 25, true),
    ALLIANCE_SHIFT_3(5, 25, true),
    ALLIANCE_SHIFT_4(6, 25, true),
    END_GAME(7, 30, true);


    final int numberRepresentation;
    public int getNumberRepresentation() {return numberRepresentation;}

    
    final int length;
    /**
     * @return the length in seconds
     */
    public int getLength() {return length;}

    final boolean isTeleop;
    public boolean isTeleop() {return isTeleop;}


    PhaseType(int numberRepresentation, int length, boolean isTeleop) {
        this.numberRepresentation = numberRepresentation;
        this.length = length;
        this.isTeleop = isTeleop;
    }
}

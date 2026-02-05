package frc.robot.game;

public enum PhaseType {
    AUTO(1, 20, false,  "Auto"),
    TRANSITION_SHIFT(2, 10, true, "Transition Shift"),
    ALLIANCE_SHIFT_1(3, 25, true, "Alliance Shift 1"),
    ALLIANCE_SHIFT_2(4, 25, true, "Alliance Shift 2"),
    ALLIANCE_SHIFT_3(5, 25, true, "Alliance Shift 3"),
    ALLIANCE_SHIFT_4(6, 25, true, "Alliance Shift 4"),
    END_GAME(7, 30, true, "End Game");


    private final int numberRepresentation;
    public int getNumberRepresentation() {return numberRepresentation;}

    
    private final int length;
    /**
     * @return the length in seconds
     */
    public int getLength() {return length;}

    final boolean isTeleop;
    public boolean isTeleop() {return isTeleop;}

    private final String notificationName;
    public String getNotificationName() {return notificationName;}


    PhaseType(int numberRepresentation, int length, boolean isTeleop, String notificationName) {
        this.numberRepresentation = numberRepresentation;
        this.length = length;
        this.isTeleop = isTeleop;
        this.notificationName = notificationName;
    }
}

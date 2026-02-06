package frc.robot.game;

public enum PhaseType {
    AUTO(1, 20, false, false,  "Autonomous"),
    TRANSITION_SHIFT(2, 10, true, false, "Transition Shift"),
    ALLIANCE_SHIFT_1(3, 25, true, true, "Alliance Shift 1"),
    ALLIANCE_SHIFT_2(4, 25, true, true, "Alliance Shift 2"),
    ALLIANCE_SHIFT_3(5, 25, true, true, "Alliance Shift 3"),
    ALLIANCE_SHIFT_4(6, 25, true, true, "Alliance Shift 4"),
    END_GAME(7, 30, true, false, "End Game");


    private final int numberRepresentation;
    public int getNumberRepresentation() {return numberRepresentation;}

    
    private final int length;
    /**
     * @return the length in seconds
     */
    public int getLength() {return length;}

    private final boolean isTeleop;
    public boolean isTeleop() {return isTeleop;}

    private final boolean isAllianceShift;
    public boolean isAllianceShift() {return isAllianceShift;}

    private final String notificationName;
    public String getNotificationName() {return notificationName;}

    /**
     * @param numberRepresentation the order in which the phases happen
     * @param length how long the phase lasts in seconds
     * @param isTeleop whether the phase is teleoperated
     * @param notificationName how the name of the phase displays in an Elastic dashboard
     */
    PhaseType(int numberRepresentation, int length, boolean isTeleop, boolean isAllianceShift, String notificationName) {
        this.numberRepresentation = numberRepresentation;
        this.length = length;
        this.isTeleop = isTeleop;
        this.isAllianceShift = isAllianceShift;
        this.notificationName = notificationName;
    }
}

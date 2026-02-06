package frc.robot.game;

public enum HubStatus {
    RED_ACTIVE("Red Active"),
    BLUE_ACTIVE("Blue Active"),
    FFA("Free-For-All");

    private final String notificationName;
    public String getNotificationName() {return notificationName;}

    HubStatus(String notificationName) {
        this.notificationName = notificationName;
    }
}
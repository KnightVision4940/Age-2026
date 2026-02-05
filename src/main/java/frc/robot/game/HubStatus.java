package frc.robot.game;

public enum HubStatus {
    RED_OPEN("Red Open"),
    BLUE_OPEN("Blue Open"),
    FFA("Free-For-All");

    String notificationName;
    public String getNotificationName() {return notificationName;}

    HubStatus(String notificationName) {
        this.notificationName = notificationName;
    }
}
package frc.robot.game.dashboard;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.game.GameManager;
import frc.robot.game.HubStatus;
import frc.robot.game.PhaseType;
import frc.robot.game.dashboard.elastic.Elastic;

public class ElasticManager {
    GameManager gameManager;

    public ElasticManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void update() {
        //Display tuff stuff
    }

    public void onPhaseChange(PhaseType newPhase, HubStatus newHubStatus) {
        Elastic.sendNotification(new Elastic.Notification(
            Elastic.NotificationLevel.INFO,
            "[INFO] Phase Change!",
            "Phase has changed to " + newPhase.getNotificationName() + ".\n" +
            "Hub Status: " + newHubStatus.getNotificationName() + ".\n" +
            (newPhase.isTeleop() ? "Phase is teleoperated." : "Phase is autonomous."),
            4000,
            450,
            -1
        ));
    }
}
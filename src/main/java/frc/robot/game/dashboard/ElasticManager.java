package frc.robot.game.dashboard;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.game.GameTracker;
import frc.robot.game.HubStatus;
import frc.robot.game.PhaseType;

public class ElasticManager {
    GameTracker gameTracker;

    public ElasticManager(GameTracker gameTracker) {
        this.gameTracker = gameTracker;

        // Default values
        SmartDashboard.putNumber("Time Left in Phase", 10);
        SmartDashboard.putString("Hub Status", Constants.ElasticBoard.kDefaultHubStatusColor.toHexString());
        SmartDashboard.putString("Current Phase", PhaseType.TRANSITION_SHIFT.name());
    }

    public void updateCountdown(double timeLeft) {
        SmartDashboard.putNumber("Time Left in Phase", timeLeft);
    }

    public void updateDashboard(PhaseType newPhase, HubStatus newHubStatus) {
        Color color = switch (newHubStatus) {
            case RED_ACTIVE -> new Color(255, 20, 20);
            case BLUE_ACTIVE -> new Color(20, 20, 255);
            default -> Constants.ElasticBoard.kDefaultHubStatusColor;
        };

        SmartDashboard.putString("Hub Status", color.toHexString());
        SmartDashboard.putString("Current Phase", newPhase.name());
    }
}
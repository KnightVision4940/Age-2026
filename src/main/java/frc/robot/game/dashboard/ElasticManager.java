package frc.robot.game.dashboard;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.game.GameManager;
import frc.robot.game.HubStatus;
import frc.robot.game.PhaseType;

public class ElasticManager {

    GameManager gameManager;

    public ElasticManager(GameManager gameManager) {
        this.gameManager = gameManager;

        // Default values
        SmartDashboard.putNumber("Time Left in Phase", 20);
        SmartDashboard.putString("Hub Status", Constants.ElasticBoard.kDefaultHubStatusColor.toHexString());
    }

    public void updateCountdown(PhaseType currentPhase) {
        // Create a sum of all the phases' length that happen before the current. 
        double elapsedBeforePhase = 0;
        for (PhaseType phase : PhaseType.values()) {
            if (phase.ordinal() < currentPhase.ordinal()) {
                elapsedBeforePhase += phase.getLength();
            }
        }

        double phaseStart = gameManager.getTimeStarted() + elapsedBeforePhase;
        double elapsedInPhase = Timer.getFPGATimestamp() - phaseStart;

        double remaining = currentPhase.getLength() - elapsedInPhase;
        if (remaining < 0) remaining = 0;

        double seconds = Math.round(remaining);
        SmartDashboard.putNumber("Time Left in Phase", seconds);
    }

    public void updateDashboard(PhaseType newPhase, HubStatus newHubStatus) {
        Color color;

        switch (gameManager.getHubStatus()) {
            default:
            case FFA:
                color = Constants.ElasticBoard.kDefaultHubStatusColor;
                break;
            case RED_ACTIVE:
                color = new Color(255, 20, 20);
                break;
            case BLUE_ACTIVE:
                color = new Color(20, 20, 255);
                break;
        }

        SmartDashboard.putString("Hub Status", color.toHexString());
    }
}
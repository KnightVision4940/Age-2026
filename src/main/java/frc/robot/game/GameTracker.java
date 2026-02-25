package frc.robot.game;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.RobotContainer;
import frc.robot.game.dashboard.ElasticManager;
import edu.wpi.first.wpilibj.DriverStation;

//TODO: integrate this tuff code into the actual robot code
public class GameTracker {
    ElasticManager elasticManager = new ElasticManager(this);

    private HubStatus hubStatus = HubStatus.FFA;

    private PhaseType currentPhase = PhaseType.TRANSITION_SHIFT;

    String gameMessage = RobotContainer.getGameSpecificMessage();

    public void updateTracking() {
        double matchTime = Timer.getFPGATimestamp();


        if (matchTime > 20 && matchTime < 30) {
            elasticManager.updateCountdown(30 - matchTime);

            currentPhase = PhaseType.TRANSITION_SHIFT;
            hubStatus = HubStatus.FFA;
        }
        else if (matchTime > 30 && matchTime < 55) {
            elasticManager.updateCountdown(55 - matchTime);

            currentPhase = PhaseType.ALLIANCE_SHIFT;
            hubStatus = (gameMessage.equals("R")) ? HubStatus.BLUE_ACTIVE : HubStatus.RED_ACTIVE;
        } else if (matchTime > 55 && matchTime < 80) {
            elasticManager.updateCountdown(80 - matchTime);

            currentPhase = PhaseType.ALLIANCE_SHIFT;
            hubStatus = (gameMessage.equals("B")) ? HubStatus.BLUE_ACTIVE : HubStatus.RED_ACTIVE;
        } else if (matchTime > 80 && matchTime < 105) {
            elasticManager.updateCountdown(105 - matchTime);

            currentPhase = PhaseType.ALLIANCE_SHIFT;
            hubStatus = (gameMessage.equals("R")) ? HubStatus.BLUE_ACTIVE : HubStatus.RED_ACTIVE;
        } else if (matchTime > 105 && matchTime < 130) {
            elasticManager.updateCountdown(130 - matchTime);

            currentPhase = PhaseType.ALLIANCE_SHIFT;
            hubStatus = (gameMessage.equals("B")) ? HubStatus.BLUE_ACTIVE : HubStatus.RED_ACTIVE;
        } else if (matchTime > 130) {
            elasticManager.updateCountdown(160 - matchTime);

            currentPhase = PhaseType.END_GAME;
            hubStatus = HubStatus.FFA;
        }

        elasticManager.updateDashboard(currentPhase, hubStatus);
    }
}

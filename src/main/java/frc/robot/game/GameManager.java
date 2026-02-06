package frc.robot.game;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.game.dashboard.ElasticManager;

//TODO: integrate this tuff code into the actual robot code
public class GameManager {
    ElasticManager elasticManager = new ElasticManager(this);

    private HubStatus hubStatus = HubStatus.FFA;
    public HubStatus getHubStatus() {return hubStatus;}

    private PhaseType phase = PhaseType.AUTO;
    public PhaseType getPhase() {return phase;}

    Alliance robotTeam;
    
    double timeStarted;
    
    boolean waitingForGameData = true;

    

    public GameManager(Alliance robotTeam) {
        this.robotTeam = robotTeam;

        timeStarted = Timer.getFPGATimestamp();
    }

    public void periodic() {
        PhaseType calculatedPhase = calculatePhase();

        if (phase != calculatedPhase) {
            elasticManager.updateDashboard(calculatedPhase, hubStatus);
            phase = calculatedPhase;
        }

        String data = DriverStation.getGameSpecificMessage();
        if (
            phase != PhaseType.TRANSITION_SHIFT &&
            waitingForGameData &&
            data.length() != 0
            ) {
            char inactiveHub = data.charAt(0);

            switch (inactiveHub) {
                case 'R':
                    hubStatus = HubStatus.BLUE_ACTIVE;
                    break;
                case 'B':
                    hubStatus = HubStatus.RED_ACTIVE;
                    break;
            }

            waitingForGameData = false;
        }

        elasticManager.updateCountdown(phase);
    }

    public void updateHubStatus(PhaseType phaseType) {

    }

    /**
     * Uses time to check the current game phase. Defaults to END_GAME if unable to calculate the phase for some reason.
     * @return the current game phase
     */
    public PhaseType calculatePhase() {
        double timeDifference = Timer.getFPGATimestamp() - timeStarted;

        double combinedLength = 0;
        for (int i = 0; i < PhaseType.values().length - 1; i++) {
            PhaseType phase = PhaseType.values()[i];
            combinedLength += phase.getLength();

            if (timeDifference < combinedLength)
                return phase;
        }

        return PhaseType.END_GAME;
    }
}

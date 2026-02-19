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

    private PhaseType currentPhase = PhaseType.AUTO;
    public PhaseType getCurrentPhase() {return currentPhase;}

    Alliance robotTeam;
    
    private double phaseTimeStarted;
    public double getPhaseTimeStarted() {return phaseTimeStarted;}

    boolean waitingForGameData = true;
    

    public GameManager(Alliance robotTeam) {
        this.robotTeam = robotTeam;

        phaseTimeStarted = Timer.getFPGATimestamp();
    }

    public void periodic() {
        PhaseType calculatedPhase = calculatePhase();

        if (currentPhase != calculatedPhase) {
            if (!waitingForGameData) updateHubStatus(calculatedPhase);

            elasticManager.updateDashboard(calculatedPhase, hubStatus);
            
            currentPhase = calculatedPhase;
        }
        
        String data = "R"; //DriverStation.getGameSpecificMessage();
        if (
            currentPhase != PhaseType.TRANSITION_SHIFT &&
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

        elasticManager.updateCountdown(currentPhase);
    }

    public void updateHubStatus(PhaseType currentPhaseType) {
        // Checks if the currentPhase is an alliance shift
        if (!currentPhaseType.name().contains("ALLIANCE")) {
            hubStatus = HubStatus.FFA;
            return;
        }
        hubStatus = (hubStatus == HubStatus.BLUE_ACTIVE)
            ? HubStatus.RED_ACTIVE
            : HubStatus.BLUE_ACTIVE;
    }

    /**
     * Uses time to check the current game phase. Defaults to AUTO if unable to calculate the currentPhase for some reason.
     * @return the current game phase
     */
    public PhaseType calculatePhase() {
        // How long the match has been going on for.
        double phaseTime = Timer.getFPGATimestamp() - phaseTimeStarted;

        if (currentPhase.getLength() < phaseTime) {
            if (currentPhase.ordinal() + 1 < PhaseType.values().length) {
                phaseTimeStarted = Timer.getFPGATimestamp() + ;
                return PhaseType.values()[currentPhase.ordinal() + 1];
            }
            else
                return PhaseType.END_GAME;
        }

        return PhaseType.AUTO;    
    }
}

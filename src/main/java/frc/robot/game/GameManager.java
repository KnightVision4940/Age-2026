package frc.robot.game;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.game.shuffleboard.ShuffleboardManager;

//TODO: integrate this tuff code into the actual robot code
public class GameManager {
    int redPoints = 0;
    int bluePoints = 0;
    ShuffleboardManager shuffleboardManager = new ShuffleboardManager(this);

    private HubStatus hubStatus = HubStatus.FFA;
    public HubStatus geHubStatus() {return hubStatus;}

    private PhaseType phase = PhaseType.AUTO;
    public PhaseType getPhase() {return phase;}

    Alliance robotTeam;
    

    public long timeStarted;


    public GameManager(Alliance robotTeam) {
        this.robotTeam = robotTeam;

        timeStarted = System.currentTimeMillis();
    }

    public void periodic() {
        PhaseType calculatedPhase = calculatePhase();

        if (phase != calculatedPhase) {
            onPhaseChange(calculatedPhase);
            phase = calculatedPhase;
        }

        shuffleboardManager.update();
    }

    public void onPhaseChange(PhaseType newPhase) {
        //End game and autonomous is FFA
        if (newPhase != PhaseType.AUTO && newPhase != PhaseType.END_GAME) {
            if (redPoints > bluePoints)
                hubStatus = HubStatus.BLUE_OPEN;
            else if (redPoints < bluePoints)
                hubStatus = HubStatus.RED_OPEN;
            else //lets assume the rule is that if both teams have an equal amount of points, it becomes FFA
                hubStatus = HubStatus.FFA;
        }

    }

    /**
     * Uses time to check the current game phase. Defaults to AUTO if unable to calculate the phase for some reason.
     * @return the current game phase
     */
    public PhaseType calculatePhase() {
        long timeDifference = System.currentTimeMillis() - timeStarted;
        
        int combinedLength = 0;
        for (int i = 0; PhaseType.values().length > i; i++) {
            PhaseType phase = PhaseType.values()[i];
            combinedLength += phase.length;

            if (timeDifference < combinedLength)
                return phase;
        }

        return PhaseType.AUTO;
    }
}

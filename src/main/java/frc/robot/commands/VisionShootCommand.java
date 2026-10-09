package frc.robot.commands;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;
import frc.robot.game_util.FieldConstants.Hub;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Hood.Hood;

public class VisionShootCommand extends SequentialCommandGroup {

    public VisionShootCommand(){
        Translation2d distToHubTranslation = BobotState.getDistanceToHub();
        double distToHub = distToHubTranslation.getNorm();
        Drumm drumm = BobotState.getDrumm();
        Hood hood = BobotState.getHood();
        drumm.DRUMMVariable(DrumEquation(distToHub));
        hood.HOODVariable(HoodEquation(distToHub));
    }

    private double DrumEquation(double distance){
        //TODO: Implement Equation for Drum Distance
        throw new java.lang.Error("DrummEquation not implemented!");
    }

    private double HoodEquation(double distance){
        //TODO: Implement equation for Hood Distance
        throw new java.lang.Error("HoodEquation not implemented!");
    }
}

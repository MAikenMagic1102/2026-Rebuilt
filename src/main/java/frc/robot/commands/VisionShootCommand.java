package frc.robot.commands;
import java.util.Optional;

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
import frc.robot.subsystems.util.ShotSetpoint;
import frc.robot.subsystems.util.ShotTable;

public class VisionShootCommand extends Command {
        ShotTable table = BobotState.getShotTable();
        Drumm drumm = BobotState.getDrumm();
        Hood hood = BobotState.getHood();
        // a distToHubTranslation;
        double distToHub;
        Optional<ShotSetpoint> shot;


    public VisionShootCommand(){
    }

    @Override public void initialize(){
    distToHub = BobotState.getDistanceToHubActual();
    
        Optional<ShotSetpoint> shot = Optional.empty();

    }

     @Override public void execute() {
       
        
        boolean poseOk = isFinitePose(BobotState.getGlobalPose());
        boolean goalOk = isFiniteTranslation(BobotState.getDistanceToHub());
        System.out.println(distToHub);

        if (poseOk && goalOk) {
            
            shot = table.lookup(distToHub);
            System.out.println(shot);
        }

        if (shot.isPresent()) {
            ShotSetpoint sp = shot.get();
            double drummRps = sp.flywheelRps();
            double hoodAngle = sp.hoodDeg();
            
            drumm.DRUMMVariable(drummRps);
            hood.HOODVariable(hoodAngle);
            log(hoodAngle, drummRps);

        }
        
     }

    private static boolean isFinitePose(Pose2d pose) {
        return pose != null
                && isFiniteTranslation(pose.getTranslation())
                && Double.isFinite(pose.getRotation().getRadians());
    }

    private static boolean isFiniteTranslation(Translation2d t) {
        return t != null && Double.isFinite(t.getX()) && Double.isFinite(t.getY());
    }
    
  @Override
    public void end(boolean interrupted) {
        drumm.DRUMMStop();
    }

    private void log(double hoodAngle, double drummSpeed){
        SmartDashboard.putNumber("HoodAngle", hoodAngle);
        SmartDashboard.putNumber("Drumm Speed", drummSpeed);
    }

}

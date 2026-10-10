package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BobotState;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.util.ShotSetpoint;
import frc.robot.subsystems.util.ShotTable;

public class VisionShootCommand extends Command {
    ShotTable table = BobotState.getShotTable();
    Drumm drumm = BobotState.getDrumm();
    Hood hood = BobotState.getHood();
    Optional<ShotSetpoint> shot = Optional.empty();

    public VisionShootCommand() {
        addRequirements(drumm, hood);
    }

    @Override
    public void execute() {
        // AutoAlign refreshes this distance every cycle while A is held.
        double distToHub = BobotState.getDistanceToHubActual();
        boolean poseOk = isFinitePose(BobotState.getGlobalPose());
        boolean goalOk = isFiniteTranslation(BobotState.getDistanceToHub());

        shot = Optional.empty();
        if (poseOk && goalOk) {
            shot = table.lookup(distToHub);
        }

        SmartDashboard.putNumber("VisionShot Distance", distToHub);
        SmartDashboard.putBoolean("VisionShot In Range", shot.isPresent());

        if (shot.isPresent()) {
            ShotSetpoint sp = shot.get();
            // Table numbers are RPM, same as DrummClose and DrummTower.
            double drummRpm = sp.flywheelRpm();
            double hoodDegrees = sp.hoodDeg();

            drumm.DrummVariable(drummRpm);
            // The hood Talon setpoint is rotations, not degrees.
            hood.hoodVariable(hoodDegrees); 
            log(hoodDegrees, drummRpm);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drumm.DrummStop();
        hood.HoodHomePos();
    }

    private static boolean isFinitePose(Pose2d pose) {
        return pose != null
                && isFiniteTranslation(pose.getTranslation())
                && Double.isFinite(pose.getRotation().getRadians());
    }

    private static boolean isFiniteTranslation(Translation2d translation) {
        return translation != null
                && Double.isFinite(translation.getX())
                && Double.isFinite(translation.getY());
    }

    private void log(double hoodAngle, double drummSpeed) {
        SmartDashboard.putNumber("HoodAngle", hoodAngle);
        SmartDashboard.putNumber("Drumm Speed", drummSpeed);
    }
}

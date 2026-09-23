package frc.robot.autos;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Util;
import frc.robot.lib.BLine.FlippingUtil;
import frc.robot.lib.BLine.Path;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drum.Drum;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;

public class BackupShoot {

    private final CommandSwerveDrivetrain drive;

    Intake intake = new Intake();
    Pivot pivot = new Pivot();
    Drum drumm = new Drum();
    Feeder feeder = new Feeder();

    public BackupShoot(CommandSwerveDrivetrain drive) {
        this.drive = drive;
    }

    Path BackUpShoot = new Path("BackUpShoot");
    // Path path_2 = new Path("auto_1_path_2");

    public Command getAutoCommand() {
        return Commands.parallel(new InstantCommand(() -> {
            Pose2d startPose = BackUpShoot.getStartPose();
            if (Util.isRedSide()) {
                startPose = FlippingUtil.flipFieldPose(startPose);
            }
            drive.resetPose(startPose);
        }),
                drive.getPathBuilder().build(BackUpShoot).withName("BackUpShoot"),

                new WaitUntilCommand(() -> {
                    return drive.getPose().getX() > (2.092);
                }).andThen(

                        new ParallelDeadlineGroup(
                                new WaitCommand(10),
                                new ParallelCommandGroup(
                                        drumm.DRUMNear(),
                                        new SequentialCommandGroup(
                                                new WaitCommand(2),
                                                feeder.FeederFeed()))

                        ),

                        drumm.DRUMStop(),
                        feeder.FeederStop()

                // new ParallelDeadlineGroup(new WaitCommand(10), new ParallelCommandGroup(
                // drumm.DRUM4(),
                // new SequentialCommandGroup( new WaitCommand(2)),
                // feeder.FeederFeed()))),
                // drumm.DRUMNO(),
                // feeder.FeederStop()
                )

        )

                .withName("Entire sequence");

    }

}

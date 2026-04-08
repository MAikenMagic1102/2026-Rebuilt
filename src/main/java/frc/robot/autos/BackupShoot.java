// package frc.robot.autos;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.InstantCommand;


// import frc.robot.RobotContainer;
// import frc.robot.Util;
// import frc.robot.lib.BLine.FlippingUtil;
// import frc.robot.lib.BLine.Path;
// import frc.robot.subsystems.CommandSwerveDrivetrain;


// public class BackupShoot {

//     private final CommandSwerveDrivetrain drive;

//     public BackupShoot(CommandSwerveDrivetrain drive) {
//         this.drive = drive;
//     }

//     Path path_1 = new Path("BackUpShoot");
//     // Path path_2 = new Path("auto_1_path_2");


//     public Command getAutoCommand() {
//         return Commands.sequence(
//                         new InstantCommand(() -> {
//                             Pose2d startPose = path_1.getStartPose();
//                             if (Util.isRedSide()) {
//                                 startPose = FlippingUtil.flipFieldPose(startPose);
//                             }
//                             drive.resetPose(startPose);
//                         }),
//                         drive.getPathBuilder().build(path_1).withName("Path 1")
//                     )

//                 .withName("Entire sequence");
//     }
// }

// package frc.robot.autos;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
// import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import edu.wpi.first.wpilibj2.command.WaitCommand;
// import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
// import frc.robot.RobotContainer;
// import frc.robot.Util;
// import frc.robot.lib.BLine.FlippingUtil;
// import frc.robot.lib.BLine.Path;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
// import frc.robot.subsystems.Drumm.Drumm;
// import frc.robot.subsystems.Feeder.Feeder;
// import frc.robot.subsystems.Intake.Intake;
// import frc.robot.subsystems.Pivot.Pivot;
// import frc.robot.autos.Shoot10sec;


// public class BackupShoot {

//     private final CommandSwerveDrivetrain drive;

//        Intake intake = new Intake();
//         Feeder tower = new Feeder();
//          Pivot pivot = new Pivot();
//           Drumm drumm = new Drumm();
//             Feeder feeder = new Feeder();

//     public BackupShoot(CommandSwerveDrivetrain drive) {
//         this.drive = drive;
//     }
    

//     Path BackupShoot = new Path("BackupShoot");
//     // Path path_2 = new Path("auto_1_path_2");


//     public Command getAutoCommand() {
//         return Commands.parallel( new InstantCommand(() -> {
//                             Pose2d startPose = BackupShoot.getStartPose();
//                             if (Util.isRedSide()) {
//                                 startPose = FlippingUtil.flipFieldPose(startPose);
//                             }
//                             drive.resetPose(startPose);
//                         }),
//                         drive.getPathBuilder().build(BackupShoot).withName("BackupShoot"),

//                         new WaitUntilCommand(() -> {
//                             return
//                             drive.getPose().getX() > (13.469);
//                         }).andThen(
                                
//                         new ParallelDeadlineGroup( 
//                             new WaitCommand(10),
//                             new ParallelCommandGroup(
//                                 drumm.DRUMM4(),
//                                 new SequentialCommandGroup(
//                                     new WaitCommand(2),
//                                     feeder.FeederOut()
//                                                           )
//                                                     )


//                                                  ),

//                                                  drumm.DRUMMNO(),
//                                                  feeder.FeederStop()
                                                 
//                             //  new ParallelDeadlineGroup(new WaitCommand(10), new ParallelCommandGroup( drumm.DRUMM4(),
//                             //      new SequentialCommandGroup( new WaitCommand(2)),
//                             //      feeder.FeederOut()))),
//                             //     drumm.DRUMMNO(),
//                             //     feeder.FeederStop()
//                      )
                    
//                     )

                  

//                 .withName("Entire sequence");

                
                
                


//     }}
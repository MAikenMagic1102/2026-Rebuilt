// package frc.robot.subsystems;

// import com.ctre.phoenix6.swerve.SwerveRequest;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj.DriverStation;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.BobotState;
// import frc.robot.game_util.FieldConstants.Hub;

// public class AutoAlignComand extends SubsystemBase {

//         private static double metersToInches(double meters){
//     double inches = meters / 0.0254;
//     return inches;
//   }
//     public Command AutoAlignCommand(){
//         CommandSwerveDrivetrain drivetrain = BobotState.getM_Drivetrain();
//     Pose2d pose = BobotState.getGlobalPose();
//     Translation2d robotPos = pose.getTranslation();
//     double distBlue = robotPos.getDistance(Hub.blueHubCenter2d);
//     double distRed = robotPos.getDistance(Hub.redHubCenter2d);
//     Translation2d target =
//         distBlue < distRed ? Hub.blueHubCenter2d : Hub.redHubCenter2d;

//     BobotState.setGlobalPose(pose);
//     BobotState.setDistanceToHub(target);

//     double targetAngle =
//         Math.atan2(target.getY() - robotPos.getY(), target.getX() - robotPos.getX());

    
//     System.out.println(DriverStation.getAlliance());
//     if (DriverStation.getAlliance().toString().contains("Red")){
//         targetAngle += Math.toRadians(90);
//     } else {
//         targetAngle -= Math.toRadians(90);
//     }
//     double distToTgt = robotPos.getDistance(target);


//     double shooterSpeed = (0.0729 * metersToInches(distToTgt)) + 22.5;
//     System.out.println(shooterSpeed);
//     double hoodAngle = 0.2083 * metersToInches(distToTgt) - 8.5208;

//     SmartDashboard.putNumber("HOOD ANGLE!", hoodAngle);
    

//     double hoodRaw = 0.175 - (1.475 * BobotState.getHoodAngle());
//     SmartDashboard.putNumber("Hood Raw", hoodRaw);


//     SmartDashboard.putNumber("SHOOTER SPEED!", shooterSpeed);
    
//     BobotState.setHoodAngle(hoodAngle);
//     BobotState.setShooterSpeed(shooterSpeed);

//     Rotation2d angley = new Rotation2d(targetAngle);

//     final SwerveRequest.FieldCentricFacingAngle driveAtAngle =
//             new SwerveRequest.FieldCentricFacingAngle()
//                 .withHeadingPID(5, 0, 0); // tune kP

                

    
//   // In command:
//         return runOnce(
//             () -> {

//             drivetrain.applyRequest(() ->
//             driveAtAngle
//                 .withVelocityY(0)
//                 .withVelocityX(0)
//                 .withTargetDirection(drivetrain.getAngley())
//                 .withMaxAbsRotationalRate(0.5));
//             }
//         );
//     }

// }

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import static frc.robot.subsystems.vision.VisionConstants.*;

import java.awt.Robot;

import javax.xml.crypto.dsig.Transform;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.autos.*;
import frc.robot.game_util.FieldConstants.Hub;
import frc.robot.generated.TunerConstants;
import frc.robot.lib.BLine.FollowPath;
import frc.robot.lib.BLine.FollowPath;
import frc.robot.subsystems.AutoAlignComand;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;
import frc.robot.subsystems.util.CommandCustomXboxController;
import frc.robot.subsystems.vision.Vision;

import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import static edu.wpi.first.units.Units.*;
import frc.robot.subsystems.AutoAlignComand;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import static frc.robot.subsystems.vision.VisionConstants.*;

import javax.xml.crypto.dsig.Transform;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.game_util.FieldConstants.Hub;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.util.CommandCustomXboxController;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;

public class RobotContainer {

    
    
    private final Vision vision;
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandCustomXboxController joystick = new CommandCustomXboxController(0);
    private final CommandCustomXboxController joystick2 = new CommandCustomXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = BobotState.getM_Drivetrain();

    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    AutoAlignComand autoAlignComand = new AutoAlignComand();
    Intake intake = new Intake();
    Feeder tower = new Feeder();
    Pivot pivot = new Pivot();
    Drumm drumm = new Drumm();
    Feeder feeder = new Feeder();


    private static double metersToInches(double meters){
        double inches = meters / 0.0254;
        return inches;
    }

    public RobotContainer() {
        // BLINE EVENT TRIGGERS HERE
        FollowPath.registerEventTrigger("ShooterOn", drumm.DRUMM4());
        FollowPath.registerEventTrigger("FeederOn", feeder.FeederOut());
        FollowPath.registerEventTrigger("Shoot10sec", new Shoot10sec( drumm, feeder ));

        

        switch (Constants.currentMode) {
            case REAL:
                // Real robot, instantiate hardware IO implementations
                vision =
                    new Vision(
                        drivetrain::addVisionMeasurement,
                        new VisionIOPhotonVision(camera0Name, robotToCameraLeft),
                        new VisionIOPhotonVision(camera1Name, robotToCameraCenter),
                        new VisionIOPhotonVision(camera2Name, robotToCameraRight));
                break;

            case SIM:
                // Sim robot, instantiate physics sim IO implementations
                vision =
                    new Vision(
                        drivetrain::addVisionMeasurement,
                        new VisionIOPhotonVisionSim(camera0Name, robotToCameraLeft, drivetrain::getPose),
                        new VisionIOPhotonVisionSim(camera1Name, robotToCameraCenter, drivetrain::getPose),
                        new VisionIOPhotonVisionSim(camera2Name, robotToCameraRight, drivetrain::getPose));
                break;

            default:
                // Replayed robot, disable IO implementations
                // (Use same number of dummy implementations as the real robot)
                vision = new Vision(drivetrain::addVisionMeasurement, new VisionIO() {}, new VisionIO() {}, new VisionIO() {});
                break;

        }
  
        
        configureBindings();
        configureAutoChooser();
    }

    private void configureAutoChooser() {
        autoChooser.setDefaultOption("Do Nothing", Commands.none());
          autoChooser.addOption("BackupShoot", new BackUpShoot(drivetrain).getAutoCommand());
            autoChooser.addOption("RedRight", new BlueRight(drivetrain).getAutoCommand());
             autoChooser.addOption("RedLeft", new BlueLeft(drivetrain).getAutoCommand());
                autoChooser.addOption("BlueLeft", new RedLeft(drivetrain).getAutoCommand());
                 autoChooser.addOption("BlueRight", new RedRight(drivetrain).getAutoCommand());
        // autoChooser.addOption("TEST", new TEST(drivetrain).getAutoCommand());
        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

//          @SuppressWarnings("resource")
//     PIDController hubAimController = new PIDController(1.0, 0.0, 0.0);
//     hubAimController.enableContinuousInput(-Math.PI, Math.PI);
//     keyboard
//         .button(2)
//         .whileTrue(
//             Commands.startRun(
//                 () -> {
//                   hubAimController.reset();
//                 },
//                 () -> {
//                   Pose2d pose = drive.getPose();
//                   Translation2d robotPos = pose.getTranslation();
//                   double distBlue = robotPos.getDistance(Hub.blueHubCenter2d);
//                   double distRed = robotPos.getDistance(Hub.redHubCenter2d);
//                   Translation2d target =
//                       distBlue < distRed ? Hub.blueHubCenter2d : Hub.redHubCenter2d;

//                   double targetAngle =
//                       Math.atan2(target.getY() - robotPos.getY(), target.getX() - robotPos.getX());
//                   targetAngle += Math.toRadians(-90);

//                   double distToTgt = robotPos.getDistance(target);
//                   double shooterAngle = 0.0729 * metersToInches(distToTgt) + 23.018;
//                   double shooterSpeed = 0.2083 * metersToInches(distToTgt) - 8.5208;

//                   hubAimController.setSetpoint(targetAngle);
//                   drive.run(
//                       0.0, hubAimController.calculate(pose.getRotation().getRadians()));
//                 },
//                 drive));
//   }

        // Pose2d pose = drivetrain.getPose();
        // Translation2d robotPos = pose.getTranslation();
        // double distBlue = robotPos.getDistance(Hub.blueHubCenter2d);
        // double distRed = robotPos.getDistance(Hub.redHubCenter2d);
        // Translation2d target =
        //     distBlue < distRed ? Hub.blueHubCenter2d : Hub.redHubCenter2d;

        // double targetAngle =
        //     Math.atan2(target.getY() - robotPos.getY(), target.getX() - robotPos.getX());
        // targetAngle += Math.toRadians(-90);

     
        // SmartDashboard.putNumber("angley", targetAngle);

        // double distToTgt = robotPos.getDistance(target);
        // double shooterAngle = 0.0729 * metersToInches(distToTgt) + 23.018;
        // double shooterSpeed = 0.2083 * metersToInches(distToTgt) - 8.5208;


        final SwerveRequest.FieldCentricFacingAngle driveAtAngle =
            new SwerveRequest.FieldCentricFacingAngle()
                .withHeadingPID(5, 0, 0); // tune kP

                

    


        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        // joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        // joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        // joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        // joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.start().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

       
        joystick2.rightBumper().onTrue(drumm.DRUMMCLEAN()).onFalse(drumm.DRUMMNO());
        joystick2.leftTrigger().onTrue(intake.OUT()).onFalse(intake.STOP());
        joystick2.rightTrigger().onTrue(feeder.FeederClean()).onFalse(feeder.FeederStop());

        joystick.leftBumper().onTrue(pivot.PUP()).onFalse(pivot.PSTOP());
        joystick.rightBumper().onTrue(pivot.PDOWN()).onFalse(pivot.PSTOP());

        joystick.x().onTrue(drumm.DRUMM4()).onFalse(drumm.DRUMMNO());
        joystick.y().onTrue(drumm.DRUMM7()).onFalse(drumm.DRUMMNO());
        joystick.b().onTrue(drumm.DRUMM9()).onFalse(drumm.DRUMMNO());
        joystick.rightTrigger().onTrue(feeder.FeederOut()).onFalse(feeder.FeederStop());
        joystick.leftTrigger().onTrue(intake.IN()).onFalse(intake.STOP());

        // Drum Vision + Autoalign
        joystick.a().whileTrue(
            drivetrain.applyRequest(() ->
            driveAtAngle

                .withVelocityY(-joystick.getLeftX() * MaxSpeed * 0.5)
                .withVelocityX(-joystick.getLeftY() * MaxSpeed * 0.5)
                .withTargetDirection(drivetrain.getAngley())
                .withMaxAbsRotationalRate(MaxAngularRate)).alongWith(
        Commands.run(() -> {
            drumm.DrummAutoRange();
        }, drumm)
        )).onFalse(
            Commands.runOnce(() -> {
                drumm.DrummStop();
            }, drumm)
        );
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}

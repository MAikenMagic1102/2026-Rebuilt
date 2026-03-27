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
import frc.robot.game_util.FieldConstants.Hub;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Tower.Tower;
import frc.robot.subsystems.util.CommandCustomXboxController;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;
import frc.robot.subsystems.vision.VisionIOPhotonVisionSim;
import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import static frc.robot.subsystems.vision.VisionConstants.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.units.measure.Angle;
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

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    // Initialize Subsystems
    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Tower tower = new Tower();
    Pivot pivot = new Pivot();
    Hood hood = new Hood();

    public RobotContainer() {
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

        // Auto Aim PID
        final SwerveRequest.FieldCentricFacingAngle driveAtAngle =
            new SwerveRequest.FieldCentricFacingAngle().withHeadingPID(5, 0, 0); // tune kP

        // Auto Aim
        joystick.a().whileTrue(
            drivetrain.applyRequest(() ->
            driveAtAngle
                .withVelocityY(0)
                .withVelocityX(-joystick.getLeftY() * MaxSpeed)
                .withTargetDirection(drivetrain.getAngleToHub())
                .withMaxAbsRotationalRate(MaxAngularRate))
        );

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        // Move Around
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Intake
        joystick.rightTrigger().onTrue(intake.IntakeRunCommand()).onFalse(intake.IntakeStopCommand());
        joystick.leftTrigger().onTrue(intake.IntakeOutCommand()).onFalse(intake.IntakeStopCommand());

        // Pivot
        joystick.povUp().onTrue(pivot.PivotUpCommand()).onFalse(pivot.PivotStopCommand());
        joystick.povDown().onTrue(pivot.PivotDownCommand()).onFalse(pivot.PivotStopCommand());

        // Shooting
        joystick2.a().onTrue(shooter.ShooterRunCommand().alongWith(hood.HoodToAngleCommand())).onFalse(shooter.ShooterStopCommand().alongWith(hood.HoodBrakeCommand()));

        // Tower
        joystick2.rightTrigger().onTrue(tower.TowerEmptyCommand()).onFalse(tower.TowerOffCommand());
        joystick2.leftTrigger().onTrue(tower.TowerFeedCommand()).onFalse(tower.TowerOffCommand());
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // Then slowly drive forward (away from us) for 2.8 seconds.
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-0.5)
                    .withVelocityY(0)
                    .withRotationalRate(0)
            )
            .withTimeout(2.8),
            // Finally idle for the rest of auton
            drivetrain.applyRequest(() -> idle)
        );
    }
}

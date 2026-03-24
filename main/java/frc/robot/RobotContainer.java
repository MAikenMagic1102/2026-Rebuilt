// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Spindex.Spindex;
import frc.robot.subsystems.Tower.Tower;
import frc.robot.subsystems.util.CommandCustomXboxController;

public class RobotContainer {
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

    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Tower tower = new Tower();
    Pivot pivot = new Pivot();
    Spindex spindex = new Spindex();
    Hood hood = new Hood();

    public RobotContainer() {
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

        // joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        // joystick.b().whileTrue(drivetrain.applyRequest(() ->
        //     point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        // ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        joystick2.rightBumper().onTrue(intake.IN()).onFalse(intake.STOP());
        joystick2.leftBumper().onTrue(intake.OUT()).onFalse(intake.STOP());

        joystick2.rightTrigger().onTrue(tower.UP()).onFalse(tower.TOWERSTOP());
        joystick2.leftTrigger().onTrue(shooter.ShooterGOSHOOT()).onTrue(shooter.ShooterNoSHOOT());

        joystick.x().onTrue(pivot.PDOWN()).onFalse(pivot.PSTOP());
        joystick.y().onTrue(pivot.PUP()).onFalse(pivot.PSTOP());

        joystick2.x().onTrue(tower.CLEAN());

        


        // Reset the field-centric heading on left bumper press.
        // joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        // drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // Then slowly drive forward (away from us) for 5 seconds.
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


















//^^^ line 123, delete past after pasteing.
    //         intake.IN()
            
    //         .withTimeout(1.5),

    //         intake.STOP().andThen(pivot.PUP()).andThen( 
    //             drivetrain.applyRequest(() ->
    //             drive.withVelocityX(0.5)
    //                 .withVelocityY(0)
    //                 .withRotationalRate(0.5)
    //         )),

    //         shooter.ShooterGOSHOOT()
            
    //         .withTimeout(2),

    //         shooter.ShooterNoSHOOT(),
            
    //         drivetrain.applyRequest(() -> idle)
    //     );
    // }


    //Actually tweaking how the frickity frick frack too many knick knacks do i get this freaking auto to work bro what is this its so late bro
    //im only gonna get like 2hrs of sleep bro its 2 something in the morning
    //We keep messing with this until robot moves foreward, ill figure it out eventually-- 
    //OR i break the robot bc it goes foreward too much and slams into the wall, like a tragic homadge to the 2025 season :.)
    //i miss my choreo :( come back to me my beloved please *sob* im so lonely, im nothing w/out you
}

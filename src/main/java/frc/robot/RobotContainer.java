// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import frc.robot.subsystems.util.CommandCustomXboxController;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.autos.*;
import frc.robot.commands.CloseShot;
import frc.robot.commands.MidRangeShot;
import frc.robot.commands.OutTake;
import frc.robot.commands.Shuttle;
import frc.robot.commands.SystemOff;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotContainer {    
    
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond) * 0.75; // 3/4 of a rotation per second max angular velocity
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandCustomXboxController joystick = new CommandCustomXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = BobotState.getM_Drivetrain();

    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    Intake intake = new Intake();
    Pivot pivot = new Pivot();
    Drumm drumm = new Drumm();
    Feeder feeder = new Feeder();
    Floor floor = new Floor();
    Hood hood =  new Hood();


    public RobotContainer() {
        configureBindings();
        configureAutoChooser();
    }

    private void configureAutoChooser() {
        autoChooser.setDefaultOption("Do Nothing", Commands.none());
            autoChooser.addOption("BackupShoot", new BackupShoot(drivetrain).getAutoCommand());
            autoChooser.addOption("RedRight", new RedRight(drivetrain).getAutoCommand());
            autoChooser.addOption("RedLeft", new RedLeft(drivetrain).getAutoCommand());
            autoChooser.addOption("BlueLeft", new BlueLeft(drivetrain).getAutoCommand());
            autoChooser.addOption("BlueRight", new BlueRight(drivetrain).getAutoCommand());
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
        
        // Reset the field-centric heading on left bumper press.
        joystick.start().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);

        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
       
        //joystick2.rightBumper().onTrue(drumm.DRUMMCLEAN()).onFalse(drumm.DRUMMNO());

        joystick.leftTrigger().onTrue(intake.IN()).onFalse(intake.STOP());
        joystick.rightBumper().onTrue(new OutTake(floor, intake)).onFalse(intake.OUT().alongWith(floor.FloorStop()));

        joystick.leftBumper().onTrue(pivot.PivotUp()).onFalse(pivot.PivotStop());
        joystick.rightBumper().onTrue(pivot.PivotDown()).onFalse(pivot.PivotStop());

        joystick.a().toggleOnTrue(new CloseShot(drumm, hood, feeder, floor));
        joystick.b().toggleOnTrue(new MidRangeShot(drumm, hood, feeder, floor));
        joystick.y().toggleOnTrue(new Shuttle(drumm, hood, feeder, floor));
        joystick.x().toggleOnTrue(new SystemOff(drumm, feeder, floor));

    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}

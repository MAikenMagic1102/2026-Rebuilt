// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Inches;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.PerUnit;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.subsystems.Tower;
import frc.robot.subsystems.pivot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  
  public static int climberMotorRightID = 0;
  public static int climberMotorLeftID = 0;
  public static int hopperMotorID = 0;
  public static int intakeRollerMotorID = 0;
  public static int pivotLeftID = 0;
  public static int pivotRightID = 0; 
  public static int rollerID = 0;
  public static int shooterID = 0;
  public static int towerID = 0;
  public static int turretRotateID = 0;
  public static int turretUDID = 0;
//climber jason
  public static double PosVoltageUpClimb = 0;
  public static double PosVoltageDownClimb = 0;
  public static double ClimberGearRatio = 90.75;
  public static Distance ClimberSprocketRadius = Inches.of(1.128);
  public static double climberDistance = 90;
//Hopper
  public static double PosVoltageInHopper = 0;
  public static double PosVoltageOutHopper = 0;
  public static double HopperGearRatio = 0;
  public static Distance HopperSprocketRadius = Inches.of(1);
//Intake Roller
  public static double IntakeRollerOnSpeed = 0;
  public static double IntakeRollerOffSpeed = 0;
  public static double IntakeRollerOutakeSpeed = 0;
//Roller
  public static double rollerOnSpeed = 0;
  public static double rollerOffSpeed = 0;
  public static double rollerOutakeSpeed = 0;
//Shooter
  public static double shooterOnSpeed = 0;
  public static double shooterOffSpeed = 0;
  public static double shooterSlowSpeed = 0;
//Tower
  public static double towerOnSpeed = 0;
  public static double towerOffSpeed = 0;
  public static double towerOutakeSpeed = 0;
//Pivot
  public static double pivotUp = 0;
  public static double pivotLength = 0;
  public static double pivotMinAngle = 0;
  public static double pivotMaxAngle = 0;
  public static double pivotDown = 1.9531;
  public static double pivotGearRatio = 7.8125 ;
  public static double pivotStartingAngle = 0;
  public static double pivotMass = 0;

  public static TalonFXConfiguration pivotConfig = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(70)
        )
        .withMotorOutput(
            new MotorOutputConfigs()
            .withNeutralMode(NeutralModeValue.Brake)
            .withInverted(InvertedValue.Clockwise_Positive)
        )
        .withFeedback(
            new FeedbackConfigs()
            .withSensorToMechanismRatio(pivotGearRatio)
        )
        
        .withSlot0(
            new Slot0Configs()
            .withKG(.82)
            .withKV(0.0)
            .withKA(0.0)
            .withKP(40.0)
            .withKI(0.0)
            .withKD(4.0)
            .withGravityType(GravityTypeValue.Arm_Cosine)
        );


  public static String busname = "bob";
//Turrett
  public static double TurrettRotate = 360;
  public static double TurrettGearRatio = 72;
  public static double TurrettUD = 180;
  public static double TurrettRotateSpeed = 90;
 
    public static enum Mode {
        /** Running on a real robot. */
        REAL,
    
        /** Running a physics simulator. */
        SIM,
    
        /** Replaying from a log file. */
        REPLAY
      }

      public static final Mode simMode = Mode.SIM;

      public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

      public static final double triggerPressedThreshold = 0.1;

      public static final double controllerDeadband = 0.15;

      public static final String canivore = "can2";
      public static double robotArmCenterOffset = Units.inchesToMeters(1.75);
      public static final double robotToReefOffset = 0.56;
}



package frc.robot.subsystems.Drum;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drum extends SubsystemBase {

  // TODO: Move Configurations, velocities, and CAN IDs to DrumConstants

  public static TalonFX DrumR = new TalonFX(27, "rio");
  public static TalonFX DrumR2 = new TalonFX(26, "rio");
  public static TalonFX DrumL = new TalonFX(24, "rio");
  public static TalonFX DrumL2 = new TalonFX(25, "rio");
  private final Follower m_follower = new Follower(DrumL.getDeviceID(), MotorAlignmentValue.Opposed);
  private final Follower m_followerL = new Follower(DrumL.getDeviceID(), MotorAlignmentValue.Aligned);
  private final Follower m_followerR = new Follower(DrumR.getDeviceID(), MotorAlignmentValue.Aligned);
  public double VoltageClosedLoopRampPeriod = 1;

  public Drum() {

    // var Slot0Configs = new Slot0Configs();

    // Slot0Configs.kP = 5;
    // Slot0Configs.kI = 0;
    // Slot0Configs.kD = 0;

    TalonFXConfiguration drumConfig = new TalonFXConfiguration();
    // drumConfig.Slot0.kS = 0.1;
    // drumConfig.Slot0.kV = 0.12; // * 2,3,4,5,6,7,8,9;
    // drumConfig.Slot0.kP = 0.11;
    // drumConfig.Slot0.kI = 0;
    // drumConfig.Slot0.kD = 0;
    drumConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    drumConfig.CurrentLimits.SupplyCurrentLimit = 80;

    DrumL.getConfigurator().apply(drumConfig);
    DrumR.getConfigurator().apply(drumConfig);
    DrumR2.getConfigurator().apply(drumConfig);
    DrumL2.getConfigurator().apply(drumConfig);
  }

  @Override
  public void periodic() {
    DrumR.setControl(m_follower);
    DrumR2.setControl(m_followerR);
    DrumL2.setControl(m_followerL);

    SmartDashboard.putNumber("Shooter L Speed RPM", DrumL.getVelocity().getValueAsDouble() * 60);
    SmartDashboard.putNumber("Shooter L Voltage", DrumL.getMotorVoltage().getValueAsDouble());
    SmartDashboard.putNumber("Shooter L Current (A)", DrumL.getStatorCurrent().getValueAsDouble());

    SmartDashboard.putNumber("Shooter L2 Speed RPM", DrumL2.getVelocity().getValueAsDouble() * 60);
    SmartDashboard.putNumber("Shooter L2 Voltage", DrumL2.getMotorVoltage().getValueAsDouble());
    SmartDashboard.putNumber("Shooter L2 Current (A)", DrumL2.getStatorCurrent().getValueAsDouble());

    SmartDashboard.putNumber("Shooter R Speed RPM", DrumR.getVelocity().getValueAsDouble() * 60);
    SmartDashboard.putNumber("Shooter R Voltage", DrumR.getMotorVoltage().getValueAsDouble());
    SmartDashboard.putNumber("Shooter R Current (A)", DrumR.getStatorCurrent().getValueAsDouble());

    SmartDashboard.putNumber("Shooter R2 Speed RPM", DrumR2.getVelocity().getValueAsDouble() * 60);
    SmartDashboard.putNumber("Shooter R2 Voltage", DrumR2.getMotorVoltage().getValueAsDouble());
    SmartDashboard.putNumber("Shooter R2 Current (A)", DrumR2.getStatorCurrent().getValueAsDouble());
  }

  public void DrumStop() {
    DrumL.setVoltage(0);
  }

  public void DrumClean() {
    DrumL.setVoltage(6);
  }

  public void DrumNear() {
    DrumL.setVoltage(-4.0);
  }

  public void DrumFar() {
    DrumL.setVoltage(-5.0);
  }

  public void DrumShuttle() {
    DrumL.setVoltage(-9.0);
  }

  public Command DRUMNear() {
    return runOnce(
        () -> {
          DrumNear();
        });
  }

  public Command DRUMFar() {
    return runOnce(
        () -> {
          DrumFar();
        });
  }

  public Command DRUMShuttle() {
    return runOnce(
        () -> {
          DrumShuttle();
        });
  }

  public Command DRUMStop() {
    return runOnce(
        () -> {
          DrumStop();
        });
  }

  public Command DRUMClean() {
    return runOnce(
        () -> {
          DrumClean();
        });
  }
}

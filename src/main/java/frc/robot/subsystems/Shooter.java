 package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
 private TalonFX shooterMotorFx;
 private final VoltageOut voltageRequest = new VoltageOut(0);

 public Shooter () {
    shooterMotorFx = new TalonFX(Constants.shooterID, Constants.busname);
    var shooterConfig = new TalonFXConfiguration();

    var slot0Configs = shooterConfig.Slot0;
   slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
   slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
   slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
   slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
   slot0Configs.kI = 0; // no output for integrated error
   slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output

 }
    
 public void shooterPower () {
    shooterMotorFx.setControl(voltageRequest.withOutput(Constants.shooterOnSpeed));
 }
 public void shooterOff () {
    shooterMotorFx.setControl(voltageRequest.withOutput(Constants.shooterOffSpeed));
 }
 public void shooterLimit() {
    shooterMotorFx.setControl(voltageRequest.withOutput(Constants.shooterSlowSpeed));
 }
 public Command turnShooterOn () {
    return runOnce(
      () ->   {
        shooterPower();
      }
    );
 }
 public Command turnShooterOff () {
    return runOnce(
      () -> {
        shooterOff();
      }  
    );
 }
 public Command slowShooter () {
    return runOnce(
        () -> {
            shooterLimit();
        }
    );
 }
}

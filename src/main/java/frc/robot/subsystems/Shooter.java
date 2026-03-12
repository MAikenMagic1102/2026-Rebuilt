 package frc.robot.subsystems;

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

 package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// This isn't piracy!
public class Shooter extends SubsystemBase {
 private TalonFX shooterMotorFx;

 public Shooter () {
    shooterMotorFx = new TalonFX(Constants.shooterID, Constants.busname);
 }
    
 public void shooterPower () {
    shooterMotorFx.set(Constants.shooterOnSpeed);
 }
 public void shooterOff () {
    shooterMotorFx.set(Constants.shooterOffSpeed);
 }
 public void shooterLimit() {
    shooterMotorFx.set(Constants.shooterSlowSpeed);
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

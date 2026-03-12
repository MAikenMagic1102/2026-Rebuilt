 package frc.robot.subsystems;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@Logged
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
 @Logged(name = "VelocityRPM")
 public double getShooterRPM() {
    return RotationsPerSecond.of(shooterMotorFx.getVelocity().getValueAsDouble()).in(RPM);
 }
 public Command slowShooter () {
    return runOnce(
        () -> {
            shooterLimit();
        }
    );
 }
}

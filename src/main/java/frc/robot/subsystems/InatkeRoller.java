package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class InatkeRoller extends SubsystemBase {
 private TalonFX intakeRollerMotorFx;

 public InatkeRoller () {
    intakeRollerMotorFx = new TalonFX(Constants.intakeRollerMotorID, Constants.busname);
 }


 public void rollerOn () {
    intakeRollerMotorFx.set(Constants.IntakeRollerOnSpeed);
 }
 public void rollerOff () {
    intakeRollerMotorFx.set(Constants.IntakeRollerOffSpeed);
 }
 public void rollerOut () {
    intakeRollerMotorFx.set(Constants.IntakeRollerOutakeSpeed);
 }
 public Command turnRollerOn () {
    return runOnce(
        () -> {
            rollerOn();
        }
    );
 }
public Command turnRollerOff () {
    return runOnce(
        () -> {
            rollerOff();
        }
    );
}
public Command intakeRollerOutake () {
    return runOnce(
        () -> {
            rollerOut();
        }
    );
}



}

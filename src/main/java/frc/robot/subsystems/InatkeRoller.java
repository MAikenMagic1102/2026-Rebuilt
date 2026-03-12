package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class InatkeRoller extends SubsystemBase {
 private TalonFX intakeRollerMotorFx;
 private final VoltageOut voltageRequest = new VoltageOut(0);

 public InatkeRoller () {
    intakeRollerMotorFx = new TalonFX(Constants.intakeRollerMotorID, Constants.busname);
 }


 public void rollerOn () {
    intakeRollerMotorFx.setControl(voltageRequest.withOutput(Constants.IntakeRollerOnSpeed));
 }
 public void rollerOff () {
    intakeRollerMotorFx.setControl(voltageRequest.withOutput(Constants.IntakeRollerOnSpeed));
 }
 public void rollerOut () {
    intakeRollerMotorFx.setControl(voltageRequest.withOutput(Constants.IntakeRollerOnSpeed));
 }
 public Command turnIntakeRollerOn () {
    return runOnce(
        () -> {
            rollerOn();
        }
    );
 }
public Command turnIntakeRollerOff () {
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

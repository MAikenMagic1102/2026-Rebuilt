package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Roller extends SubsystemBase {
    private TalonFX rollerTalonFX;

private final VoltageOut voltageRequest = new VoltageOut(0);

    public Roller () {
        rollerTalonFX = new TalonFX(Constants.rollerID, Constants.busname);
    }

    public void rollerOn () {
        rollerTalonFX.setControl(voltageRequest.withOutput(Constants.rollerOnSpeed));
    }
    public void rollerOff () {
        rollerTalonFX.setControl(voltageRequest.withOutput(Constants.rollerOffSpeed));
    }
    public void rollerOutake () {
        rollerTalonFX.setControl(voltageRequest.withOutput(Constants.rollerOutakeSpeed));
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
    public Command outakeRoller () {
        return runOnce(
            () -> {
                rollerOutake();
            }
        );
    }
}

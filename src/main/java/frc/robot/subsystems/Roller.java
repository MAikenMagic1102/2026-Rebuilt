package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@Logged
public class Roller extends SubsystemBase {
    private TalonFX rollerTalonFX;

    public Roller () {
        rollerTalonFX = new TalonFX(Constants.rollerID, Constants.busname);
    }

    public void rollerOn () {
        rollerTalonFX.set(Constants.rollerOnSpeed);
    }
    public void rollerOff () {
        rollerTalonFX.set(Constants.rollerOffSpeed);
    }
    public void rollerOutake () {
        rollerTalonFX.set(Constants.rollerOutakeSpeed);
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

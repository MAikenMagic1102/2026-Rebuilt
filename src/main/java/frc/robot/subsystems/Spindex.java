package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@Logged
public class Spindex extends SubsystemBase {
    private TalonFX rollerTalonFX;

    public Spindex () {
        rollerTalonFX = new TalonFX(Constants.SpindexID, Constants.busname);
    }

    public void SpindexOn () {
        rollerTalonFX.set(Constants.SpindexOnSpeed);
    }
    public void SpindexOff () {
        rollerTalonFX.set(Constants.SpindexOffSpeed);
    }
    public void SpindexIdle () {
        rollerTalonFX.set(Constants.SpindexIdleSpeed);
    }
    public Command turnSpindexOn () {
        return runOnce(
          () -> {
            SpindexOn();
          }  
        );
    }
    public Command turnSpindexOff () {
        return runOnce(
            () -> {
                SpindexOff();
            }
        );
    }
    public Command SpindexIdleMode () {
        return runOnce(
            () -> {
                SpindexIdle();
            }
        );
    }
}

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@Logged
public class Spindex extends SubsystemBase {
    private TalonFX spindexTalonFX;
     private final VoltageOut voltageRequest = new VoltageOut(0);

    public Spindex () {
        spindexTalonFX = new TalonFX(Constants.SpindexID, Constants.busname);
    }

    public void SpindexOn () {
       spindexTalonFX.setControl(voltageRequest.withOutput(Constants.SpindexOnSpeed));
    }
    public void SpindexOff () {
       spindexTalonFX.setControl(voltageRequest.withOutput(Constants.shooterOffSpeed));
    }
    public void SpindexIdle () {
       spindexTalonFX.setControl(voltageRequest.withOutput(Constants.SpindexIdleSpeed));
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

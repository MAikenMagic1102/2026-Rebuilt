package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Tower extends SubsystemBase{
    private TalonFX towerMotorFx;
    private final VoltageOut voltageRequest = new VoltageOut(0);

    public Tower () {
        towerMotorFx = new TalonFX(Constants.towerID, Constants.busname);
    }

    public void towerOn () {
       towerMotorFx.setControl(voltageRequest.withOutput(Constants.towerOnSpeed));
    }
    public void towerOff () {
       towerMotorFx.setControl(voltageRequest.withOutput(Constants.towerOffSpeed));
    }
    public void towerOutake () {
       towerMotorFx.setControl(voltageRequest.withOutput(Constants.towerOutakeSpeed));
    }
    public Command towerUp () {
        return runOnce(
          () -> {
            towerOn();
          }  
        );
    }
 public Command turnTowerOn () {
        return runOnce(
            () -> {
                towerOn();
            }
        );
    }

    public Command turnTowerOff () {
        return runOnce(
            () -> {
                towerOff();
            }
        );
    }
    public Command outakeTower (){
        return runOnce(
            () -> {
                towerOutake();
            }
        );
    }
}

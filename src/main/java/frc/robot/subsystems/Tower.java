package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Tower extends SubsystemBase{
    private TalonFX towerMotorFx;

    public Tower () {
        towerMotorFx = new TalonFX(Constants.towerID, Constants.busname);
    }

    public void towerOn () {
        towerMotorFx.set(Constants.towerOnSpeed);
    }
    public void towerOff () {
        towerMotorFx.set(Constants.towerOffSpeed);
    }
    public void towerOutake () {
        towerMotorFx.set(Constants.towerOutakeSpeed);
    }
    public Command towerUp () {
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

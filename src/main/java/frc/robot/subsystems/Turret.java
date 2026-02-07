package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Turret {
    private TalonFX turretUDMotorFx;
    private TalonFX turretRotateFx;

    public Turret () {
        turretUDMotorFx = new TalonFX(Constants.turretUDID, Constants.busname);
        turretRotateFx = new TalonFX(Constants.turretRotateID, Constants.busname);
    }

}

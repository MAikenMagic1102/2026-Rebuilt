package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.PerUnit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class pivot extends SubsystemBase{

private TalonFX pivotLeftMotorFx;
private TalonFX pivotRightMotorFx;

private PositionVoltage posVoltageDown = new PositionVoltage(Constants.pivotDown);
private PositionVoltage posVoltageUp = new PositionVoltage(Constants.pivotUp);

public pivot () {
    pivotLeftMotorFx = new TalonFX(Constants.pivotLeftID, Constants.busname);
    pivotRightMotorFx = new TalonFX(Constants.pivotRightID, Constants.busname);
}

public void startPosition () {
  pivotLeftMotorFx.setControl(posVoltageUp);
  pivotRightMotorFx.setControl(posVoltageUp);
}
public void downPosition () {
    pivotLeftMotorFx.setControl(posVoltageDown);
    pivotRightMotorFx.setControl(posVoltageDown);
}
public Command upPositionCommand () {
    return runOnce(
        () -> {
            startPosition();
        }
    );
    
}
public Command downPositionCommand () {
    return runOnce(
        () -> {
            downPosition();
        }
    );
}

}

package frc.robot.subsystems.Pivot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {

    // Initialize Pivot motors  
    public static TalonFX PivotR = new TalonFX(60, "rio");
    public static TalonFX PivotL = new TalonFX(6, "rio");

    final PositionVoltage m_intakVoltage = new PositionVoltage(0).withSlot(0);

    public Pivot(){
        var Slot0Configs = new Slot0Configs();

        // PID values
        Slot0Configs.kP = 5;
        Slot0Configs.kI = 0;
        Slot0Configs.kD = 0;

        PivotL.getConfigurator().apply(Slot0Configs);
        PivotR.getConfigurator().apply(Slot0Configs);
    }

    public void pivotControl(double pivotSpeed){
      PivotL.set(pivotSpeed);
      PivotR.set(pivotSpeed);
    }

    public void pivotUp(){
      pivotControl(0.2);
    }

    public void pivotDown(){
      pivotControl(-0.2);
    }

    public void pivotStop(){
      pivotControl(0);
    }

    public Command PivotDownCommand(){
      return runOnce(
        () -> {
            pivotDown();
        }
      );
    }

     public Command PivotUpCommand(){
     return runOnce(
        () -> {
            pivotUp();
        }
      );
    }

    public Command PivotStopCommand(){
        return runOnce(
        () -> {
            pivotStop();
        }
      );
    }
}

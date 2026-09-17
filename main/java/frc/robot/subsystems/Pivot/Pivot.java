package frc.robot.subsystems.Pivot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
    
    public static TalonFX PivotR = new TalonFX(60, "rio");
    public static TalonFX PivotL = new TalonFX(6, "rio");

    final PositionVoltage m_intakVoltage = new PositionVoltage(0).withSlot(0);

    public Pivot(){

        var Slot0Configs = new Slot0Configs();

        Slot0Configs.kP = 5;
        Slot0Configs.kI = 0;
        Slot0Configs.kD = 0;

        PivotL.getConfigurator().apply(Slot0Configs);
        PivotR.getConfigurator().apply(Slot0Configs);
        
    }

    public void pivUP(){
        PivotL.setControl(m_intakVoltage.withPosition(0));
        PivotR.setControl(m_intakVoltage.withPosition(0));
    }

    public void pivDOWN(){
        PivotL.setControl(m_intakVoltage.withPosition(0));
        PivotR.setControl(m_intakVoltage.withPosition(0));
    }

    public void pivSTOP(){
    PivotL.setControl(m_intakVoltage.withPosition(0));
    PivotR.setControl(m_intakVoltage.withPosition(0));
    }

    public Command PDOWN(){
      return runOnce(
        () -> {
            pivDOWN();
        }

      );
    }
     public Command PUP(){
     return runOnce(
        () -> {
            pivUP();
        }

      );
    }
     public Command PSTOP(){
        return runOnce(
        () -> {
            pivSTOP();
        }

      );
    }

}

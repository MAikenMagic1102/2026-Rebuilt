package frc.robot.subsystems.Pivot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

public class Pivot extends SubsystemBase{

    public static TalonFX PivotL = new TalonFX(41, "rio");
    public static TalonFX PivotR = new TalonFX(44, "rio");                  
    final PositionVoltage m_pivot = new PositionVoltage(0).withSlot(0);
    private final Follower m_follower = new Follower(PivotL.getDeviceID(), MotorAlignmentValue.Opposed);

    
 public Pivot(){

    var Slot0Configs = new Slot0Configs();

    Slot0Configs.kP = 10;
    Slot0Configs.kI = 0;
    Slot0Configs.kD = 0;

    PivotR.getConfigurator().apply(Slot0Configs);
    PivotL.getConfigurator().apply(Slot0Configs);
    
    }

  
    @Override
    public void periodic() {
    //PivotR.setControl(m_follower);
      SmartDashboard.putNumber("Pivot L Pos", PivotL.getPosition().getValueAsDouble());
      SmartDashboard.putNumber("Pivot L Volt", PivotL.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Pivot L Amp", PivotL.getStatorCurrent().getValueAsDouble());
    

      SmartDashboard.putNumber("Pivot R Pos", PivotR.getPosition().getValueAsDouble());
      SmartDashboard.putNumber("Pivot R Volt", PivotR.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Pivot R Amp", PivotR.getStatorCurrent().getValueAsDouble());
    
    }

    public void Pdown(){
        PivotL.setControl(m_pivot.withPosition(2));
        PivotR.setControl(m_pivot.withPosition(-2));
    }

    public void Pup(){
        PivotL.setControl(m_pivot.withPosition(0));
        PivotR.setControl(m_pivot.withPosition(0));
    }

        public void Pstop(){
        PivotL.set(0);
    }



    public Command PDOWN(){

        return run(
            () -> {
                Pdown();
            }
        );

    }

        public Command PUP(){

        return run(
            () -> {
                Pup();
            }
        );

    }

        public Command PSTOP(){

        return run(
            () -> {
                Pstop();
            }
        );

    }


}
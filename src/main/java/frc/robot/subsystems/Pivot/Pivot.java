package frc.robot.subsystems.Pivot;

import static edu.wpi.first.units.Units.Volts;

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

    public static TalonFX Pivot = new TalonFX(1, "can2");
    // public static TalonFX PivotR = new TalonFX(44, "rio");                  
    // final PositionVoltage m_pivot = new PositionVoltage(0).withSlot(0);
    // private final Follower m_follower = new Follower(PivotL.getDeviceID(), MotorAlignmentValue.Opposed);

    
    public Pivot(){

        var Slot0Configs = new Slot0Configs();

        Slot0Configs.kP = 10;
        Slot0Configs.kI = 0;
        Slot0Configs.kD = 0;

        // PivotR.getConfigurator().apply(Slot0Configs);
        Pivot.getConfigurator().apply(Slot0Configs);

        Pivot.setPosition(0);
        // PivotR.setPosition(0);
    
    }

  
    @Override
    public void periodic() {
    //PivotR.setControl(m_follower);
      SmartDashboard.putNumber("Pivot Pos", Pivot.getPosition().getValueAsDouble());
      SmartDashboard.putNumber("Pivot Volt", Pivot.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Pivot Amp", Pivot.getStatorCurrent().getValueAsDouble());
    

    //   SmartDashboard.putNumber("Pivot R Pos", PivotR.getPosition().getValueAsDouble());
    //   SmartDashboard.putNumber("Pivot R Volt", PivotR.getMotorVoltage().getValueAsDouble());
    //   SmartDashboard.putNumber("Pivot R Amp", PivotR.getStatorCurrent().getValueAsDouble());
    }

    public void PIVOTDown(){
        Pivot.setVoltage(12);
        // PivotR.set(0.25);
    }

    public void PIVOTUp(){
        Pivot.setVoltage(-12);
        // PivotR.set(-0.3);
    }

    public void PIVOTStop(){
        Pivot.setVoltage(0);
        // PivotR.set(0);
    }



    public Command PivotDown(){
        return runOnce(
            () -> {
                PIVOTDown();
            }
        );
    }

    public Command PivotUp(){
        return runOnce(
            () -> {
                PIVOTUp();
            }
        );
    }

    public Command PivotStop(){
        return runOnce(
            () -> {
                PIVOTStop();
            }
        );
    }

}
package frc.robot.subsystems.Pivot;


import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase{

    //TODO: Move configuration and CAN IDs to a PivotConstants
    //TODO: Use angle controls instead of voltage controls?

    public static TalonFX Pivot = new TalonFX(1, "can2");
    
    public Pivot(){
        var Slot0Configs = new Slot0Configs();

        Slot0Configs.kP = 10;
        Slot0Configs.kI = 0;
        Slot0Configs.kD = 0;

        Pivot.getConfigurator().apply(Slot0Configs);
        Pivot.setPosition(0);
    }

  
    @Override
    public void periodic() {
      SmartDashboard.putNumber("Pivot Pos", Pivot.getPosition().getValueAsDouble());
      SmartDashboard.putNumber("Pivot Volt", Pivot.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Pivot Amp", Pivot.getStatorCurrent().getValueAsDouble());
    }

    public void PIVOTDown(){
        Pivot.setVoltage(12);
    }

    public void PIVOTUp(){
        Pivot.setVoltage(-12);
    }

    public void PIVOTStop(){
        Pivot.setVoltage(0);
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
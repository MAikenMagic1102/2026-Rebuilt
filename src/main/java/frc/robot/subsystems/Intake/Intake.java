package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    public static TalonFX  IntakeMotorFX = new TalonFX(2, "can2");


    public void periodic() {
        SmartDashboard.putNumber("Intake Speed RPM", IntakeMotorFX.getVelocity().getValueAsDouble() * 60);
        SmartDashboard.putNumber("Intake Voltage", IntakeMotorFX.getMotorVoltage().getValueAsDouble());
        SmartDashboard.putNumber("Intake Current (A)", IntakeMotorFX.getStatorCurrent().getValueAsDouble());
    }

    public void IntakeIN(){
        IntakeMotorFX.setVoltage(12);
    }

    public void IntakeOUT(){
        IntakeMotorFX.setVoltage(-8);
    }

    public void IntakeSTOP(){
        IntakeMotorFX.setVoltage(0);
    }

    public Command IN(){
       return Commands.runOnce(() -> IntakeIN());
    }
     public Command OUT(){
       return Commands.runOnce(() -> IntakeOUT());
    }
     public Command STOP(){
       return Commands.runOnce(() -> IntakeSTOP());
    }

}

package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake {

    public static TalonFX IntakeMotorFX = new TalonFX(39, "rio");

    public Intake(){
        
    }

    public void IntakeIN(){
        IntakeMotorFX.setVoltage(4);
    }

    public void IntakeOUT(){
        IntakeMotorFX.setVoltage(-4);
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

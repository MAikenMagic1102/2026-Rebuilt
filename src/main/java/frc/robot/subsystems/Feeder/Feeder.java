package frc.robot.subsystems.Feeder;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Feeder {
    public static TalonFX IntakeMotorFX = new TalonFX(53, "rio");

    public Feeder(){
        
    }

    public void FeederON(){
        IntakeMotorFX.setVoltage(11);
    }

    public void FeederCLEAR(){
        IntakeMotorFX.setVoltage(-11);
    }

    public void FeederSTOP(){
        IntakeMotorFX.set(0);
    }

    public Command FeederOut(){
       return Commands.runOnce(() -> FeederON());
    }
     public Command FeederClean(){
       return Commands.runOnce(() -> FeederCLEAR());
    }
     public Command FeederStop(){
       return Commands.runOnce(() -> FeederSTOP());
    }
}

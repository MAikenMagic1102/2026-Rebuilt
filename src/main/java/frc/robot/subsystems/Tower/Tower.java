package frc.robot.subsystems.Tower;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Tower {
    public static TalonFX IntakeMotorFX = new TalonFX(50, "rio");

    public Tower(){
        
    }

    public void TowerON(){
        IntakeMotorFX.set(1);
    }

    public void TowerCLEAR(){
        IntakeMotorFX.set(-1);
    }

    public void TowerSTOP(){
        IntakeMotorFX.set(0);
    }

    public Command UP(){
       return Commands.runOnce(() -> TowerON());
    }
     public Command CLEAN(){
       return Commands.runOnce(() -> TowerCLEAR());
    }
     public Command TOWERSTOP(){
       return Commands.runOnce(() -> TowerSTOP());
    }
}

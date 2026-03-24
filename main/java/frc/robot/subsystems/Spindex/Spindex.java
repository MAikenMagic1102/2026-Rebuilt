package frc.robot.subsystems.Spindex;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Spindex {
 public static TalonFX IntakeMotorFX = new TalonFX(7, "rio");

    public Spindex(){
        
    }

    public void SpindexON(){
        IntakeMotorFX.set(1);
    }
    
    public void SpindexCLEAN(){
        IntakeMotorFX.set(-1);
    }

    public void SpindexSTOP(){
        IntakeMotorFX.set(0);
    }

    public Command SPIN(){
       return Commands.runOnce(() -> SpindexON());
    }

        public Command SPINCLEAN(){
       return Commands.runOnce(() -> SpindexCLEAN());
    }

     public Command STOPSPINNING(){
       return Commands.runOnce(() -> SpindexSTOP());
    }
}

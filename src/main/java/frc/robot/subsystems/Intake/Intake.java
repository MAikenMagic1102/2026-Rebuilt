package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Intake {

    public static TalonFX IntakeMotorFX = new TalonFX(54, "rio");

    public Intake(){}

    public void IntakeIn(){
        IntakeMotorFX.set(0.7);
    }

    public void IntakeOut(){
        IntakeMotorFX.set(-0.7);
    }

    public void IntakeStop(){
        IntakeMotorFX.set(0);
    }

    public Command IntakeRunCommand(){
       return Commands.runOnce(() -> IntakeIn());
    }
     public Command IntakeOutCommand(){
       return Commands.runOnce(() -> IntakeOut());
    }
     public Command IntakeStopCommand(){
       return Commands.runOnce(() -> IntakeStop());
    }

}

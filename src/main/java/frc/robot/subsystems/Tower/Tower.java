package frc.robot.subsystems.Tower;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class Tower {
    public static TalonFX IntakeMotorFX = new TalonFX(50, "rio");

    public Tower(){}

    public void towerFeed(){
        IntakeMotorFX.set(1);
    }

    public void towerEmpty(){
        IntakeMotorFX.set(-1);
    }

    public void towerOff(){
        IntakeMotorFX.set(0);
    }

    public Command TowerFeedCommand(){
       return Commands.runOnce(() -> towerOff());
    }

    public Command TowerEmptyCommand(){
       return Commands.runOnce(() -> towerEmpty());
    }

    public Command TowerOffCommand(){
       return Commands.runOnce(() -> towerOff());
    }
}

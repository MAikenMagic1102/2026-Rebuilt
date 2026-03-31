package frc.robot.subsystems.Drumm;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Drumm extends SubsystemBase {
    
    public static TalonFX DrummR = new TalonFX(0, "rio");
    public static TalonFX DrummL = new TalonFX(0, "rio");


    public Drumm(){

        // var Slot0Configs = new Slot0Configs();

        // Slot0Configs.kP = 5;
        // Slot0Configs.kI = 0;
        // Slot0Configs.kD = 0;

        // DrummL.getConfigurator().apply(Slot0Configs);
        // DrummR.getConfigurator().apply(Slot0Configs);
        
        DrummR.setControl(new Follower(0, MotorAlignmentValue.Aligned));
    }

    public void DrummOut(){
      DrummL.set(0.2);
    }

    public void DrummStop(){
      DrummL.set(-0.2);
    }

    public void DrummClean(){
      DrummL.set(0);
    }

    public Command DRUMMGO(){
      return runOnce(
        () -> {
            DrummOut();
        }

      );
    }
     public Command DRUMMNO(){
     return runOnce(
        () -> {
            DrummStop();
        }

      );
    }
     public Command DRUMMCLEAN(){
        return runOnce(
        () -> {
            DrummClean();
        }

      );
    }

}

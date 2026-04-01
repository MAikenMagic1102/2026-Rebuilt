package frc.robot.subsystems.Drumm;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Drumm extends SubsystemBase {
    
    public static TalonFX DrummR = new TalonFX(21, "rio");
    public static TalonFX DrummL = new TalonFX(20, "rio");

    public Drumm(){

        // var Slot0Configs = new Slot0Configs();

        // Slot0Configs.kP = 5;
        // Slot0Configs.kI = 0;
        // Slot0Configs.kD = 0;

        TalonFXConfiguration drumConfig = new TalonFXConfiguration();
        drumConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        drumConfig.CurrentLimits.SupplyCurrentLimit = 80;

        DrummL.getConfigurator().apply(drumConfig);
        DrummR.getConfigurator().apply(drumConfig);
        
        DrummR.setControl(new Follower(DrummL.getDeviceID(), MotorAlignmentValue.Opposed));
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Shooter Speed RPM", DrummL.getVelocity().getValueAsDouble() * 60);
    }

    public void DrummOut(){
      DrummL.setVoltage(12);
    }

    public void DrummStop(){
      DrummL.setVoltage(0);
    }

    public void DrummClean(){
      DrummL.setVoltage(-12);
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

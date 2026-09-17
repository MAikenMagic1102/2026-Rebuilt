package frc.robot.subsystems.Floor;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Floor extends SubsystemBase {
    public static TalonFX floorMotor = new TalonFX(21, "rio");

    public Floor(){
        TalonFXConfiguration feederConfig = new TalonFXConfiguration();
        feederConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        feederConfig.CurrentLimits.SupplyCurrentLimit = 80;
        feederConfig.CurrentLimits.StatorCurrentLimit = 100;

        floorMotor.getConfigurator().apply(feederConfig);
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("Floor Speed RPM", floorMotor.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Floor Voltage", floorMotor.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Floor Current (A)", floorMotor.getStatorCurrent().getValueAsDouble());

      
    }
    

    public void FloorON(){
        floorMotor.setVoltage(1);
    }

    public void FloorREVERSE(){
        floorMotor.setVoltage(-12);
    }

    public void FloorSTOP(){
        floorMotor.setVoltage(0);
    }
    public void FloorIDLE(){
        floorMotor.set(4);
    }

    public Command FloorOut(){
       return Commands.runOnce(() -> FloorREVERSE());
    }
     public Command FloorOn(){
       return Commands.runOnce(() -> FloorON());
    }
     public Command FloorStop(){
       return Commands.runOnce(() -> FloorSTOP());
    }
    public Command FloorIdle() {
        return Commands.runOnce(() -> FloorIDLE());
    }
}

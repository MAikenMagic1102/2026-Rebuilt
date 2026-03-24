package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{

public static TalonFX shooterMotorFx = new TalonFX(10, "rio");
final VelocityVoltage m_shooter = new VelocityVoltage(0).withSlot(0);

    public Shooter(){

     var Slot0Configs = new Slot0Configs();

    Slot0Configs.kP = 5;
    Slot0Configs.kI = 10;
    Slot0Configs.kD = 0;
        
    shooterMotorFx.getConfigurator().apply(Slot0Configs);

    }

        public void ShooterSHOOT(){
       shooterMotorFx.setControl(m_shooter.withVelocity(39));
    }

    public void ShooterSTOP(){
        shooterMotorFx.set(0);
    }

    public Command ShooterGOSHOOT(){
        return runOnce(
            () -> {
                ShooterSHOOT();
            }
        );
    }

        public Command ShooterNoSHOOT(){
        return runOnce(
            () -> {
                ShooterSTOP();
            }
        );
    }


}

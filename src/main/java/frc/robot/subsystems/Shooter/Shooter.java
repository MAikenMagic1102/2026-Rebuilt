package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.BobotState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{

    public static TalonFX shooterMotorFx = new TalonFX(10, "rio");
    final VelocityVoltage m_shooter = new VelocityVoltage(0).withSlot(0);

    public Shooter(){

        var Slot0Configs = new Slot0Configs();

        // PID values
        Slot0Configs.kP = 5;
        Slot0Configs.kI = 10;
        Slot0Configs.kD = 0;

        shooterMotorFx.getConfigurator().apply(Slot0Configs);
    }


    public void shooterRun(){
        // tgtSpeed is a Speed value expressed in rotations per second.
        double tgtSpeed = BobotState.getShooterSpeed();
        shooterMotorFx.setControl(m_shooter.withVelocity(tgtSpeed));
    }

  
    public void shooterStop(){
        shooterMotorFx.set(0);
    }


    public Command ShooterRunCommand(){
        return runOnce(
            () -> {
                shooterRun();
            }
        );
    }

    public Command ShooterStopCommand(){
    return runOnce(
            () -> {
                shooterStop();
            }
        );
    }
}

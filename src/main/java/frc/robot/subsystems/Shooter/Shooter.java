package frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.BobotState;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase{
    final MotionMagicVelocityVoltage motionMagicRequest;
    private final NeutralOut neutralRequest = new NeutralOut();

public static TalonFX shooterMotorFx = new TalonFX(10, "rio");
final VelocityVoltage m_shooter = new VelocityVoltage(0).withSlot(0);



    public Shooter(){

     var Slot0Configs = new Slot0Configs();



    shooterMotorFx = new TalonFX(ShooterConstants.IntakeMotorFX, ShooterConstants.bus);
   

  //From https://v6.docs.ctr-electronics.com/en/stable/docs/api-reference/device-specific/talonfx/motion-magic.html
    var shooterConfig = new TalonFXConfiguration();

   // set slot 0 gains
   var slot0Configs = shooterConfig.Slot0;
   slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
   slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
   slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
   slot0Configs.kP = 5; // An error of 1 rps results in 0.11 V output
   slot0Configs.kI = 10; // no output for integrated error
   slot0Configs.kD = 0; // no output for error derivative
   
   shooterConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;


   // set Motion Magic Velocity settings
   var motionMagicConfigs = shooterConfig.MotionMagic;
   motionMagicConfigs.MotionMagicAcceleration = 400; // Target acceleration of 400 rps/s (0.25 seconds to max)
   motionMagicConfigs.MotionMagicJerk = 4000; // Target jerk of 4000 rps/s/s (0.1 seconds)

   shooterMotorFx.getConfigurator().apply(shooterConfig);
   motionMagicRequest = new MotionMagicVelocityVoltage(0);
        
    shooterMotorFx.getConfigurator().apply(Slot0Configs);

    }

        public void ShooterSHOOTCLIMB(){
       shooterMotorFx.setControl(m_shooter.withVelocity(32.5));
    }
    
    public void ShooterSHOOTSEE(){
        double tgtSpeed = BobotState.getShooterSpeed();
    //    shooterMotorFx.setControl(m_shooter.withVelocity(tgtSpeed));
       shooterMotorFx.setControl(motionMagicRequest.withVelocity(tgtSpeed));
        SmartDashboard.putNumber("Shooter Raw", tgtSpeed);
    }

    
        public void ShooterSHOOTTRENCH(){
    //    shooterMotorFx.setControl(m_shooter.withVelocity(33));
        shooterMotorFx.setControl(motionMagicRequest.withVelocity(33));
    }

    
        public void ShooterSHOOTHP(){
    //    shooterMotorFx.setControl(m_shooter.withVelocity(36.5));
              shooterMotorFx.setControl(motionMagicRequest.withVelocity(36.5));
    }

        public void ShooterSTOP(){
        // shooterMotorFx.set(0);
        shooterMotorFx.setControl(neutralRequest);
    }

    public Command ShooterClimb(){
        return runOnce(
            () -> {
                ShooterSHOOTCLIMB();
            }
        );
    }

        public Command ShooterTrench(){
        return runOnce(
            () -> {
                ShooterSHOOTTRENCH();
            }
        );
    }

        public Command ShooterHP(){
        return runOnce(
            () -> {
                ShooterSHOOTHP();
            }
        );
    }

            public Command ShooterSEE(){
        return runOnce(
            () -> {
                ShooterSHOOTSEE();
            }
        );
    }
        public Command ShooterStop(){
        return runOnce(
            () -> {
                ShooterSTOP();
            }
        );
    }


}

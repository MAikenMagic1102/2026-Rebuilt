 package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
 private TalonFX shooterMotorFx;
 private final VoltageOut voltageRequest = new VoltageOut(0);
 final MotionMagicVelocityVoltage m_request;
 private final NeutralOut neutralRequest = new NeutralOut();

 public Shooter () {
    shooterMotorFx = new TalonFX(Constants.shooterID, Constants.busname);
   

  //From https://v6.docs.ctr-electronics.com/en/stable/docs/api-reference/device-specific/talonfx/motion-magic.html
    var shooterConfig = new TalonFXConfiguration();

   // set slot 0 gains
   var slot0Configs = shooterConfig.Slot0;
   slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
   slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
   slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
   slot0Configs.kP = 0.11; // An error of 1 rps results in 0.11 V output
   slot0Configs.kI = 0; // no output for integrated error
   slot0Configs.kD = 0; // no output for error derivative
   
   shooterConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

   // set Motion Magic Velocity settings
   var motionMagicConfigs = shooterConfig.MotionMagic;
   motionMagicConfigs.MotionMagicAcceleration = 400; // Target acceleration of 400 rps/s (0.25 seconds to max)
   motionMagicConfigs.MotionMagicJerk = 4000; // Target jerk of 4000 rps/s/s (0.1 seconds)

   shooterMotorFx.getConfigurator().apply(shooterConfig);
   m_request = new MotionMagicVelocityVoltage(0);
 }
    
 public void shooterPower(double tgtVelocity) {
      shooterMotorFx.setControl(m_request.withVelocity(tgtVelocity));
 }

public void shooterOff() {
    shooterMotorFx.setControl(neutralRequest);
}

public Command shootCommand(double tgtVelocity) {
    return startEnd(
        () -> shooterPower(tgtVelocity),
        () -> shooterOff()
    );
}

}

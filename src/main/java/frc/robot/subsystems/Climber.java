package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.InchesPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Second;

import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase{
    
    private TalonFX climberMotorLeftFx;
    private TalonFX climberMotorRightFx;

    private PositionVoltage posVoltageUp = new PositionVoltage(Constants.PosVoltageUpClimb);
    private PositionVoltage posVoltageDown = new PositionVoltage(Constants.PosVoltageDownClimb);
    
    public Climber () {
        climberMotorLeftFx = new TalonFX (Constants.climberMotorLeftID, Constants.busname);
        climberMotorRightFx = new TalonFX (Constants.climberMotorRightID, Constants.busname);
    }


    public void climberUp () {
        climberMotorLeftFx.setControl(posVoltageUp);
        climberMotorRightFx.setControl(posVoltageUp);
    }
    public void climberDown () {
        climberMotorLeftFx.setControl(posVoltageDown);
        climberMotorRightFx.setControl(posVoltageDown);
    }
    public Command turnClimberOn () {
        return runOnce(
            () -> {
                climberUp();
            }
        );
    }
    public Command climberHomPos () {
        return runOnce(
            () -> {
                climberDown();
            }
        );
    }
    public static Distance rotationsToInches (Angle rotations) {
        var gearedRadians = rotations.in(Radians) / Constants.ClimberGearRatio;
        return Constants.ClimberSprocketRadius.times(gearedRadians);
    }


}

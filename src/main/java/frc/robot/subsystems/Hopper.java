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

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase{
    private TalonFX hopperMotorFx;

    private PositionVoltage PosVoltageOut = new PositionVoltage(Constants.PosVoltageOutHopper);
    private PositionVoltage PosVolatgeIn = new PositionVoltage(Constants.PosVoltageInHopper);

    public Hopper () {
        hopperMotorFx = new TalonFX (Constants.hopperMotorID, Constants.busname);
    }

    public void hopperIn () {
        hopperMotorFx.setControl(PosVolatgeIn);
    }
    public void hopperOut () {
        hopperMotorFx.setControl(PosVoltageOut);
    }
    public Command runHopperIn () {
        return runOnce(
            () -> {
                hopperIn();
            }
        );
    }
    public Command runHopperOut () {
        return runOnce(
          () -> {
            hopperOut();
          }  
        );
    }
    public static Distance rotationsToInches (Angle rotations) {
        var gearedRadians = rotations.in(Radians) / Constants.HopperGearRatio;
        return Constants.HopperSprocketRadius.times(gearedRadians);
    }
}

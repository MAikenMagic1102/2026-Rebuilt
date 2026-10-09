package frc.robot.subsystems.util;

public record ShotSetpoint(double flywheelRps, double hoodDeg) {
    double getHoodDeg(){
        return hoodDeg;
    }

    double getFlywheelRps(){
        return flywheelRps;
    }
}
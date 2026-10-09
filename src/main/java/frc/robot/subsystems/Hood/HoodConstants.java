package frc.robot.subsystems.Hood;

import edu.wpi.first.math.util.Units;

public class HoodConstants {

    public static double targetPosition = 2.25;


    public static double HoodMaxAngle =  Units.degreesToRadians(55.5);
    public static double HoodMinAngle = Units.degreesToRadians(10.5);
    public static double hoodGearing = 25.4;                   // empirically calibrated from two data points
    public static double hoodCANcoderGearing = 32.0 / 19.0;   // motor rotations per CANcoder rotation: first stage only (32t/19t pulley)
    public static double HoodStartingAngle = 11.0; // physical home angle measured with angle finder

    public static double HoodTotalGearRatio = 30.65;

    public static double positionTolerence = 5;

    public static double HoodHome = 10.5;
    public static double HoodPos1 = 20.5;
    public static double HoodPos2 = 30.5;
    public static double HoodPos3 = 40.5;
    public static double HoodPos4 = 50.5;



}

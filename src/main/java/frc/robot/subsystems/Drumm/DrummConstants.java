package frc.robot.subsystems.Drumm;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class DrummConstants {
    // Distance (meters) → Shooter Voltage
    public static final InterpolatingDoubleTreeMap kVoltageMap = new InterpolatingDoubleTreeMap();

    static {
        // PLACEHOLDER VALUES — must be tuned on the real robot!
        // Format: kVoltageMap.put(distanceMeters, voltage);
        // kVoltageMap.put(1.889125, -0.0); //3ft
        kVoltageMap.put(2.193925, -6.0); //4ft
        // kVoltageMap.put(2.498725, -0.0); //5ft
        kVoltageMap.put(2.803525, -6.5); //6ft
        // kVoltageMap.put(3.108325, -0.0); //7ft
        kVoltageMap.put(3.413125, -7.25); //8ft
        kVoltageMap.put(3.717925, -7.75); //9ft
        // kVoltageMap.put(4.022725, -0.0); //10ft
        // kVoltageMap.put(4.327525, -0.0); //11ft
        // kVoltageMap.put(4.632325, -0.0); //12ft
        // kVoltageMap.put(4.937125, -0.0); //13ft
        // kVoltageMap.put(5.241925, -0.0); //14ft
        // kVoltageMap.put(5.546725, -0.0); //15ft



    }
    
   
}
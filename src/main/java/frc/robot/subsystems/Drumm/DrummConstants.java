package frc.robot.subsystems.Drumm;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class DrummConstants {
    // Distance (meters) → Shooter Voltage
    public static final InterpolatingDoubleTreeMap kVoltageMap = new InterpolatingDoubleTreeMap();

    static {
        // PLACEHOLDER VALUES — must be tuned on the real robot!
        // Format: kVoltageMap.put(distanceMeters, voltage);
        kVoltageMap.put(2.0, 4.0);
        kVoltageMap.put(3.0, 5.0);
        kVoltageMap.put(4.0, 6.0);
        kVoltageMap.put(5.0, 7.0);
        kVoltageMap.put(6.0, 8.0);
    }
    
   
}
package frc.robot.subsystems.Drumm;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class DrummConstants {
    // Distance (meters) → Shooter Voltage
    public static final InterpolatingDoubleTreeMap kVoltageMap = new InterpolatingDoubleTreeMap();

    static {
        // PLACEHOLDER VALUES — must be tuned on the real robot!
        // Format: kVoltageMap.put(distanceMeters, voltage);
        kVoltageMap.put(1.889125, -6.0);
        kVoltageMap.put(2.193925, -7.0);
        kVoltageMap.put(2.498725, -8.0);
    }
    
   
}
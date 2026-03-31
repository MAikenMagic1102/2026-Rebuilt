// // Copyright (c) 2021-2026 Littleton Robotics
// // http://github.com/Mechanical-Advantage
// //
// // Use of this source code is governed by a BSD
// // license that can be found in the LICENSE file
// // at the root directory of this project.

// package frc.robot.subsystems.vision;

// import static edu.wpi.first.units.Units.Inches;

// import edu.wpi.first.apriltag.AprilTagFieldLayout;
// import edu.wpi.first.apriltag.AprilTagFields;
// import edu.wpi.first.math.geometry.Rotation3d;
// import edu.wpi.first.math.geometry.Transform3d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.units.measure.Distance;

// public class VisionConstants {
//   // AprilTag layout
//   public static AprilTagFieldLayout aprilTagLayout =
//       AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

//   // Camera names, must match names configured on coprocessor
//   public static String camera0Name = "cameraLeftWide";
//   public static String camera1Name = "cameraCenterNarrow";
//   public static String camera2Name = "cameraRightWide";

//   private static double inchesToMeters(double inches){
//     double meters = inches * 0.0254;
//     return meters;
//   }

//   // Robot to camera transforms
//   // (Not used by Limelight, configure in web UI instead)
//   // TODO: CONVERT MEASURES (XYZ) TO ROBOT CENTER TO CAMERA CENTER y(Rotations are a-ok)
// //     public static Transform3d robotToCamera0 =
// //       new Transform3d(0.0, 0.0, 0.0, new Rotation3d(0.0, 0, 0.0));
// //   public static Transform3d robotToCamera1 =
// //       new Transform3d(0.0, 0.0, 0.0, new Rotation3d(0.0, 0, Math.PI));
// //   public static Transform3d robotToCamera3 = 
// //       new Transform3d(0.0, 0.0, 0.0, new Rotation3d(0.0, 0.0, -Math.PI));
 
//   public static Transform3d robotToCameraLeft =
//       new Transform3d(inchesToMeters(1.5 + 3.25), inchesToMeters(-1.5), inchesToMeters(16 + 5.75), 
//       new Rotation3d(0.0,Units.degreesToRadians(-35), Units.degreesToRadians(90 + 60)));
//   public static Transform3d robotToCameraCenter =
//       new Transform3d(inchesToMeters(1.5), inchesToMeters(-1.5 + 2.75), inchesToMeters(16 + 6.5),
//       (new Rotation3d(0.0, Units.degreesToRadians(-20), Units.degreesToRadians(90))));
//   public static Transform3d robotToCameraRight = 
//       new Transform3d(inchesToMeters(-1.5 - 3.25), inchesToMeters(-1.5), inchesToMeters(16 + 5.75),
//       new Rotation3d(0.0, Units.degreesToRadians(-35), Units.degreesToRadians(90 - 60)));

//   // Basic filtering thresholds
//   public static double maxAmbiguity = 0.3;
//   public static double maxZError = 0.75;

//   // Standard deviation baselines, for 1 meter distance and 1 tag
//   // (Adjusted automatically based on distance and # of tags)
//   public static double linearStdDevBaseline = 0.02; // Meters
//   public static double angularStdDevBaseline = 0.06; // Radians

//   // Standard deviation multipliers for each camera
//   // (Adjust to trust some cameras more than others)
//   public static double[] cameraStdDevFactors =
//       new double[] {
//         1.0, // Camera 0
//         1.0, // Camera 1
//         1.0 // Camera 3
//       };

//   // Multipliers to apply for MegaTag 2 observations
//   public static double linearStdDevMegatag2Factor = 0.5; // More stable than full 3D solve
//   public static double angularStdDevMegatag2Factor =
//       Double.POSITIVE_INFINITY; // No rotation data available
// }

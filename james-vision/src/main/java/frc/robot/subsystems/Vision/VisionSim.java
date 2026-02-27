package frc.robot.subsystems.Vision;

import org.photonvision.PhotonCamera;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class VisionSim {

    // The PhotonCamera used in the real robot code.
    PhotonCamera camera = new PhotonCamera("cameraName");

    // The simulated camera properties
    SimCameraProperties cameraProp = new SimCameraProperties();

    // The simulation of this camera. Its values used in real robot code will be updated.
    PhotonCameraSim cameraSim = new PhotonCameraSim(camera, cameraProp);


    // Translation Stuff

    // Our camera is mounted 0.1 meters forward and 0.5 meters up from the robot pose,
    // (Robot pose is considered the center of rotation at the floor level, or Z = 0)
    Translation3d robotToCameraTrl = new Translation3d(0.1, 0, 0.5);
    // and pitched 15 degrees up.
    Rotation3d robotToCameraRot = new Rotation3d(0, Math.toRadians(-15), 0);
    Transform3d robotToCamera = new Transform3d(robotToCameraTrl, robotToCameraRot);

    // Here's some code copied from the PhotonLib reference
    // A vision system sim labelled as "main" in NetworkTables
    public VisionSystemSim visionSim = new VisionSystemSim("main");

    // The layout of AprilTags which we want to add to the vision system
    AprilTagFieldLayout tagLayout =  AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField); 

    public void visionSimulate(){

        // Enable the raw and processed streams. These are enabled by default.
        cameraSim.enableRawStream(true);
        cameraSim.enableProcessedStream(true);

        // Enable drawing a wireframe visualization of the field to the camera streams.
        // This is extremely resource-intensive and is disabled by default.
        cameraSim.enableDrawWireframe(true);
        
        visionSim.addAprilTags(tagLayout);

        // Add this camera to the vision system simulation with the given robot-to-camera transform.
        visionSim.addCamera(cameraSim, robotToCamera);
    }

    public Pose2d update(){
        return null;
    }


}

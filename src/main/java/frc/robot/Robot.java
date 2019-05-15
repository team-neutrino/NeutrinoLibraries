/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot 
{
    /**
     * Remember to put helpful javadoc comments in your code - a good rule
     * of thumb is to have all instance variables, methods, classes, and complex
     * loops with a comment.
     * 
     * Below is an example of a good description
     * 
     * The orange LEDs on the robot used for signaling when battery is low
     */
    private LEDController orangeLED;

    /**
     * Having short descriptive names helps make the code more readable and easier 
     * to change in the future.
     * 
     * The Pixy cam that is used to track the cargo balls
     */
    private PixyCam cargoPixy;

    /**
     * Keeping coding styles consistent throught the whole workspace makes for a 
     * prettier, and more professional look. Consistent indents, other spacing, variable names,
     * upper case letter placement, and bracket placement all maintains good style. 
     * 
     * The Pixy cam that will be used to line up with the retroreflective tape
     * on the cargo bays
     */
    private PixyController lineUpPixy;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() 
    {
        //Create LEDController as an instance variable for use throughout the whole class....
        orangeLED = new LEDController(0);
        orangeLED.setMode(LEDController.Mode.OFF);

        //Or create it then forget about it and have it continuously flash
        new LEDController(1).setMode(LEDController.Mode.FLASH);

        //Create a pixycam with manual control
        cargoPixy = new PixyCam(SPI.Port.kOnboardCS0);

        //Start the pixy cam and let it stay on if we always want to track cargo
        new Thread(cargoPixy).start();
        //The cargoPixy data is now being processed for us to use at any time

        //Create a pixy that we can start and stop that takes care of the light source
        //and thread for us
        lineUpPixy = new PixyController(SPI.Port.kOnboardCS1, 2);
        //We can now start and stop the linUpPixy data processing and turn the LEDs on and off as we need
        lineUpPixy.startTracking();

        //Creates new value printer designed for putting values to the 
        //Smartdashboard at regular intervales determined by param timeBetween
        new ValuePrinter(()-> //Syntax for creating an anonymous class - create print() method 
        {                     //from Printer interface without creating new class
            //Put code for printing here...
            //This code will be continuously called by ValuePrinter with given wait time inbetween
            SmartDashboard.putBoolean("Data name: ", true);
        }, 
        ValuePrinter.NORMAL_PRIORITY); //Use one of the default values for printing priority or 
    }                                  //use custom wait time between prints using miliseconds

    @Override
    public void teleopPeriodic() 
    {
        //Use utility class to sleep the thread without needing a try catch block
        //Always put a short sleep here
        Util.threadSleep(1);
    }
}
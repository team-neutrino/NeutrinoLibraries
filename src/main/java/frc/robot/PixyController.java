/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * Class to control pixy in one place with all the useful methods
 * and other needed objects including the LEDs and thread.
 * 
 * @author JoelNeppel
 * 
 */
public class PixyController extends PixyCam
{
    /**
     * The controller for the LEDs
     */
    private LEDController leds;

    /**
     * The thread for the pixy cam
     */
    private Thread pixyThread;

    /**
     * Makes pixy cam controller with LEDs.
     * @param port
	 * 	The SPI Port the pixy is connected to
     * @param ledChannel
     *  The PCM channel the LEDs are plugged into
     */
    public PixyController(SPI.Port port, int ledChannel)
    {
        super(port);
        leds = new LEDController(ledChannel, LEDController.Mode.OFF);
        pixyThread = new Thread(this);
    }

    /**
     * Starts tracking objects using the pixy cam by starting
     * the pixy thread and turning on the LEDs.
     */
    public void startTracking()
    {
        if(!pixyThread.isAlive())
        {
            pixyThread = new Thread(this);
            pixyThread.start();
            leds.setMode(LEDController.Mode.ON);
        }
    }

    /**
     * Stops tracking objects to save system resources and energy
     * by stopping the pixy thread and turning off the LEDs.
     */
    public void stopTracking()
    {
        pixyThread.interrupt();
        leds.setMode(LEDController.Mode.OFF);
    }
}
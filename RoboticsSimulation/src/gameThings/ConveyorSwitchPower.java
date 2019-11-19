/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameThings;

import de.lessvoid.nifty.controls.Button;
import guiControllers.ConveyorScreen;
import testerPackage.Main;

/**
 *
 * @author adrientremblay
 */
public class ConveyorSwitchPower extends Powerup{

    public ConveyorSwitchPower(int duration, int cooldown, String cooldownString, Button button, int coolClass) {
        super(duration, cooldown, cooldownString, button, coolClass);
    }

    @Override
    public void enable() {
        Main.conveyorDirection *= -1;
        Main.app.timeSinceDirChange = 0;
        ConveyorScreen.console.output("Switching Conveyor Direction...");
    }

    @Override
    public void disable() {
        
    }
    
}

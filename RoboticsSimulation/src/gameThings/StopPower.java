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
public class StopPower extends Powerup{

    public StopPower(int duration, int cooldown, String cooldownString, Button button, int coolClass){
        super(duration, cooldown, cooldownString, button, coolClass);
    }

    @Override
    public void enable() {
        Main.conveyorEnable = 0.0f;
        ConveyorScreen.console.output("Conveyor Stopped momentarily!");
    }

    @Override
    public void disable() {
        Main.conveyorEnable = 1.0f;
    }
    
}

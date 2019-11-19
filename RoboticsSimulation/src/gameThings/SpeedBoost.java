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
public class SpeedBoost extends Powerup{
    
    private float multiplier;
    
    public SpeedBoost(int duration, int cooldown, String cooldownString, Button button, int coolClass, float multiplier){
        super(duration, cooldown, cooldownString, button, coolClass);
        this.multiplier = multiplier;
    }

    @Override
    public void enable() {
        Main.conveyorMultiplier *= multiplier;
        ConveyorScreen.console.output("Coveyor Multiplier set to "+multiplier + "!");
    }

    @Override
    public void disable() {
        Main.conveyorMultiplier /= multiplier;
    }
    
    
    
}

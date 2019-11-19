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
public class DropBoost extends Powerup{
    
    private float multiplier;

    public DropBoost(int duration, int cooldown, String cooldownString, Button button , int coolClass ,float multiplier){
        super(duration, cooldown, cooldownString, button, coolClass);
        this.multiplier = multiplier;
    }

    @Override
    public void enable() {
        Main.dropMultiplier *= multiplier;
        ConveyorScreen.console.output("Drop Multiplier set to " + multiplier + "!");
    }

    @Override
    public void disable() {
        Main.dropMultiplier /= multiplier;
    }
    
}

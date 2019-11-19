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
public class BlackPower extends Powerup {

     public BlackPower(int duration, int cooldown, String cooldownString, Button button, int coolClass){
        super(duration, cooldown, cooldownString, button, coolClass);
    }

    @Override
    public void enable() {
        Main.app.spawnBall(true);
        ConveyorScreen.console.output("Spawing BLACK GP!");
    }

    @Override
    public void disable() {
        
    }
    
}

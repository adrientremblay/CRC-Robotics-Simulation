/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameThings;

import com.jme3.bullet.objects.PhysicsRigidBody;
import de.lessvoid.nifty.controls.Button;
import guiControllers.ConveyorScreen;
import testerPackage.Main;

/**
 *
 * @author adrientremblay
 */
public class WipePower extends Powerup {

    public WipePower(int duration, int cooldown, String cooldownString, Button button, int coolClass){
        super(duration, cooldown, cooldownString, button, coolClass);
    }

    @Override
    public void enable() {
        Main.removeAll();
        ConveyorScreen.console.output("Conveyor wiped!");
    }

    @Override
    public void disable() {
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import gameThings.Ball;
import testerPackage.Main;

/**
 *  DEPREACTED
 * 
 * @author adrientremblay
 */
public class ConveyorControl extends RigidBodyControl implements PhysicsCollisionListener, PhysicsTickListener {
    public ConveyorControl() {
        super();
        Main.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        Main.bulletAppState.getPhysicsSpace().addTickListener(this);
        
    }

    public ConveyorControl(CollisionShape conveyor_phy, int i) {
        super(conveyor_phy, i);
        Main.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        Main.bulletAppState.getPhysicsSpace().addTickListener(this);
    }
    
    public void collision(PhysicsCollisionEvent event) {
        final Spatial[] spatials = {event.getNodeA(), event.getNodeB()};
        
        Ball b = null;
        // There is a ball
        if (spatials[0].getName() == "ball") {
            b = (Ball) spatials[0];
        } else if (spatials[1].getName() == "ball") {
            b = (Ball) spatials[1];
        }
        
        // There is a conveyor colliding with said ball
        if ((spatials[0].getName() == "conveyor" || spatials[1].getName() == "conveyor") && b != null) {
//            b.setOnConveyor(true);
        } else if (b != null) {
//            b.setOnConveyor(false);
        }
        
    }

    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        // Ball movement
        for (Spatial s : Main.app.getRootNode().getChildren()) {
            if (s.getName() == "ball" ) {
                Ball b = (Ball) s;
//                b.update();
            }
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

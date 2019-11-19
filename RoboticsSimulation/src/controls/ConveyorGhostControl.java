/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.GhostControl;
import gameThings.Ball;
import java.util.List;
import testerPackage.Main;

/**
 *
 * @author adrientremblay
 */
public class ConveyorGhostControl extends GhostControl implements PhysicsTickListener{
    
    public ConveyorGhostControl() {
        super();
        Main.bulletAppState.getPhysicsSpace().addTickListener(this);
        
    }

    public ConveyorGhostControl(CollisionShape conveyor_phy) {
        super(conveyor_phy);
        Main.bulletAppState.getPhysicsSpace().addTickListener(this);
    }
    
    @Override
    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        if (this.getOverlappingCount() > 0) {
           List<PhysicsCollisionObject> overlappingObjects = this.getOverlappingObjects();
           
           for (PhysicsCollisionObject o : overlappingObjects) {
               if (o.getUserObject().getClass() == Ball.class) {
                   Ball b = (Ball) o.getUserObject();
                   b.move(); // Moving Ball
                 
               }
           }
        }
    }

    @Override
    public void physicsTick(PhysicsSpace space, float tpf) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

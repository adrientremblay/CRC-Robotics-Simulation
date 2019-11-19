/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameThings;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import testerPackage.Main;
import static testerPackage.Main.bulletAppState;

/**
 *
 * @author adrientremblay
 */
public class Ball extends Geometry{
    
    private float timeAtCreation;
    private boolean toDestroy;
    private int value;
    
    // Constructors
    
    public Ball(){
        super();
        this.toDestroy = false;
        this.value = Main.BALL_VALUE_STD;
//        timeAtCreation = Main.app.timer.getTimeInSeconds();
    }
    
    public Ball(String name, Mesh mesh, boolean black) {
        super(name, mesh);
        this.toDestroy = false;
        if (! black) this.value = Main.BALL_VALUE_STD;
        else this.value = Main.BALL_VALUE_BLK;
//        timeAtCreation = Main.app.timer.getTimeInSeconds();
    }
    
    // Class Methods
    
    /**
     * method to destory this ball -> removing it from the scene and physics space
     */
    public void destroy(){
        Main.app.getRootNode().detachChild(this);   
        bulletAppState.getPhysicsSpace().remove(this.getControl(0)); //Destorying Physics
    }
    
    /**
     * Moves the ball according to the conveyotspeed
     */
    public void move(){
        RigidBodyControl c = this.getControl(RigidBodyControl.class);
        c.setLinearVelocity(new Vector3f(Main.conveyorSpeed, c.getLinearVelocity().y, c.getLinearVelocity().z));
    }
    
    public void givePoints(boolean blueTeam){
        if (blueTeam) {
            Main.app.blueScore += this.value;
        } else {
            Main.app.yellowScore += this.value;
        }
    }
    
    // Mutators
    
    public float getTimeAtCreation(){
        return this.timeAtCreation;
    }
    
    public void setToDestory(boolean toDestroy){
        this.toDestroy = toDestroy;
    }
    
    public boolean getToDestroy() {
        return this.toDestroy;
    }

    
}

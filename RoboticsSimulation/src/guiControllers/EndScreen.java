/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiControllers;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import testerPackage.Main;
import static testerPackage.Main.nifty;

/**
 *
 * @author adrientremblay
 */
public class EndScreen extends BaseAppState implements de.lessvoid.nifty.screen.ScreenController{
    
    private Screen screen;

    @Override
    protected void initialize(Application app) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void cleanup(Application app) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onEnable() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onDisable() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
        Element txt = screen.findElementById("endmsg");
        Main.started = false;
        txt.getRenderer(TextRenderer.class).setText("Blue Team Score: " + String.valueOf(Main.app.blueScore) + " Yellow Team Score: " + String.valueOf(Main.app.yellowScore));
    }

    @Override
    public void onEndScreen() {
    }
    
    public void resetButton2(){
        Main.app.clearScore();
        nifty.gotoScreen("start");
    }
    
}

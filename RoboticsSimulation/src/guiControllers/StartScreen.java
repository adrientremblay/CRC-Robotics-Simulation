/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiControllers;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.screen.Screen;
import java.util.ArrayList;
import testerPackage.Main;
import static testerPackage.Main.nifty;

/**
 *
 * @author adrientremblay
 */
public class StartScreen extends BaseAppState implements de.lessvoid.nifty.screen.ScreenController{
    
    private Screen screen;
    private DropDown<String> dropDownB;
    private DropDown<String> dropDownY;
    
    @Override
    protected void initialize(Application app) {
//        DropDown<String> dropDown = this.findNiftyControl("dropDown2",DropDown.class);
//        dropDown.addAllItems(someListOfOptions);
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
        ArrayList<String> items = new ArrayList<String>();
        items.add("0.5x Conveyor Speed");
        items.add("3.0x Drop Rate");
        items.add("Drop Black GP");
        items.add("Wipe Conveyor");
        dropDownB = screen.findNiftyControl("blueDropdown",DropDown.class);
        dropDownY = screen.findNiftyControl("yellowDropdown",DropDown.class);
        dropDownB.addAllItems(items);
        dropDownY.addAllItems(items);
    }

    @Override
    public void onEndScreen() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void startCrap(){
        ConveyorScreen.blueSpecial = dropDownB.getSelection();
        ConveyorScreen.yellowSpecial = dropDownY.getSelection();
        nifty.gotoScreen("hud");
        Main.started = true;
    }
    
    //Drag race start events
    public void blueStart(){
        startCrap();
        Main.conveyorDirection = 1;
//        getElementByID("directionLabel").getRenderer(TextRenderer.class).setText("BLUE");
    }
    
    public void yellowStart(){
        startCrap();
        Main.conveyorDirection = -1;
//        getElementByID("directionLabel").getRenderer(TextRenderer.class).setText("YELLOW");
    }
    
}

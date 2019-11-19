/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiControllers;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import gameThings.BlackPower;
import gameThings.ConveyorSwitchPower;
import gameThings.DropBoost;
import gameThings.Powerup;
import gameThings.SpeedBoost;
import gameThings.StopPower;
import gameThings.WipePower;
import java.util.ArrayList;
import testerPackage.Main;
import static testerPackage.Main.nifty;

/**
 * Screen controller for the conveyor screen.
 *
 * @author adrientremblay
 */
public class ConveyorScreen extends BaseAppState implements de.lessvoid.nifty.screen.ScreenController {
    
    public static Console console;
    private static Screen screen;
    
    public static String blueSpecial;
    public static Powerup blueSpecialPowerup;
    public static String yellowSpecial;
    public static Powerup yellowSpecialPowerup;
    
    private static Button[] specials;
    
    //Dropdowns
    private static DropDown<String> modifyDurationDD;
    private static DropDown<String> modifyCooldownDD;
    
    //Powerups
    public static SpeedBoost speedBlue;
    public static SpeedBoost speedYellow ;
    
    public static SpeedBoost speedBlue2;
    public static SpeedBoost speedYellow2;
    
    public static DropBoost dropBlue;
    public static DropBoost dropYellow;
    
    public static StopPower stopPower;
    
    //Specials
    public static SpeedBoost halfBlue;
    public static SpeedBoost halfYellow;
    
    public static DropBoost halfDropBlue;
    public static DropBoost halfDropYellow;
    
    public static BlackPower GPBlue;
    public static BlackPower GPYellow;
    
    public static WipePower wipeBlue;
    public static WipePower wipeYellow;
    
    //Bonuses
    public static ConveyorSwitchPower bonusBlue;
    public static ConveyorSwitchPower bonusYellow;
    
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
        
        //Creating the powerups:
        
            //Main Powerups
        speedBlue = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpeedCoolBlue", screen.findNiftyControl("powerSpeedBlue", Button.class),0, 2.0f);
        speedYellow = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpeedCoolYellow", screen.findNiftyControl("powerSpeedYellow", Button.class) ,0, 2.0f);

        speedBlue2 = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpeedCoolBlue2", screen.findNiftyControl("powerSpeedBlue2", Button.class) ,0, 1.5f);
        speedYellow2 = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpeedCoolYellow2", screen.findNiftyControl("powerSpeedYellow2", Button.class) ,0, 1.5f);

        dropBlue = new DropBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerDropCoolBlue", screen.findNiftyControl("powerDropBlue", Button.class),1, 2.0f);
        dropYellow = new DropBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerDropCoolYellow", screen.findNiftyControl("powerDropYellow", Button.class),1, 2.0f);

        stopPower = new StopPower(10, Main.BASE_POWERUP_COOLDOWN, "powerStopCool",  screen.findNiftyControl("powerStop", Button.class), 0);

            //Specials
        halfBlue = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial1CoolBlue", screen.findNiftyControl("powerSpecial1Blue", Button.class),0, 0.5f);
        halfYellow = new SpeedBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial1CoolYellow", screen.findNiftyControl("powerSpecial1Yellow", Button.class),0, 0.5f);

        halfDropBlue = new DropBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial2CoolBlue", screen.findNiftyControl("powerSpecial2Blue", Button.class),1, 3.0f);
        halfDropYellow = new DropBoost(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial2CoolYellow", screen.findNiftyControl("powerSpecial2Yellow", Button.class),1, 3.0f);

        GPBlue = new BlackPower(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial3CoolBlue", screen.findNiftyControl("powerSpecial3Blue", Button.class),5 );
        GPYellow = new BlackPower(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial3CoolYellow", screen.findNiftyControl("powerSpecial3Yellow", Button.class), 5);

        wipeBlue = new WipePower(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial4CoolBlue", screen.findNiftyControl("powerSpecial4Blue", Button.class), 6);
        wipeYellow = new WipePower(20, Main.BASE_POWERUP_COOLDOWN, "powerSpecial4CoolYellow", screen.findNiftyControl("powerSpecial4Blue", Button.class), 6);
        
            //Bonus
        bonusBlue = new ConveyorSwitchPower(20, Main.BASE_POWERUP_COOLDOWN, "bonusCoolBlue", screen.findNiftyControl("powerBonusBlue", Button.class), 7);
        bonusYellow = new ConveyorSwitchPower(20, Main.BASE_POWERUP_COOLDOWN, "bonusCoolYellow", screen.findNiftyControl("powerBonusYellow", Button.class) , 8);
        
        //Adding all the powers to an array in Main for logic purposes
        Main.allPowers.add(speedBlue);Main.allPowers.add(speedYellow);Main.allPowers.add(speedBlue2);Main.allPowers.add(speedYellow2);Main.allPowers.add(dropBlue);Main.allPowers.add(dropYellow);Main.allPowers.add(stopPower);Main.allPowers.add(halfBlue);Main.allPowers.add(halfYellow);Main.allPowers.add(halfDropBlue);Main.allPowers.add(halfDropYellow);Main.allPowers.add(GPBlue);Main.allPowers.add(GPYellow);Main.allPowers.add(wipeBlue);Main.allPowers.add(wipeYellow);Main.allPowers.add(bonusYellow);Main.allPowers.add(bonusBlue);
        
        //Creating the console
        console = screen.findNiftyControl("console", Console.class);
        //Possibly hiding the admin menu
        if(!Main.ADMIN_MENU){
            screen.findElementById("admin-panel").hide();
        }
        //Possibly hiding the console
        if(!Main.CONSOLE_MENU){
            screen.findElementById("console").hide();
        }
    }

    @Override
    public void onStartScreen() {
        // Showing only the appropriate special powerups
        removeAllButGiven("Blue", blueSpecial);
        removeAllButGiven("Yellow", yellowSpecial);
        // Setting the special powerup to a static var for use with the key event
        if (blueSpecial!= null) {
            switch(blueSpecial){
                case "0.5x Conveyor Speed":
                    blueSpecialPowerup = halfBlue;
                    break;
                case "3.0x Drop Rate":
                    blueSpecialPowerup = halfDropBlue;
                    break;
                case "Drop Black GP":
                    blueSpecialPowerup = GPBlue;
                    break;
                case "Wipe Conveyor":
                    blueSpecialPowerup = wipeBlue; 
                    break;
            }
        }
        if (yellowSpecial != null) {
            switch(yellowSpecial){
                case "0.5x Conveyor Speed":
                    yellowSpecialPowerup = halfYellow;
                    break;
                case "3.0x Drop Rate":
                    yellowSpecialPowerup = halfDropYellow;
                    break;
                case "Drop Black GP":
                    yellowSpecialPowerup = GPYellow;
                    break;
                case "Wipe Conveyor":
                    yellowSpecialPowerup = wipeYellow;
                    break;

            }
        }
        
        // Hiding the bonus powers
        getElementByID("bonusYellowPanel").hide();
        getElementByID("bonusBluePanel").hide();
        
        //Admin Change power menu
        modifyDurationDD = screen.findNiftyControl("durationDropdown",DropDown.class);
        modifyCooldownDD = screen.findNiftyControl("cooldownDropdown",DropDown.class);
        
        ArrayList<String> items = new ArrayList<String>();
        items.add("2x Conveyor Speed");
        items.add("1.5x Conveyor Speed");
        items.add("2x Dropper Speed");
        if (blueSpecial != null) items.add(blueSpecial);
        if (yellowSpecial != null) items.add(yellowSpecial);
        items.add("Stop Conveyor");
        
        modifyDurationDD.addAllItems(items);
        modifyCooldownDD.addAllItems(items);
        
    }

    @Override
    public void onEndScreen() {
        for (int i = 1 ; i <= specials.length ; i ++) {
            Button s = specials[i - 1];
            ConveyorScreen.getElementByID("Blue" + i + "panel").show();
            ConveyorScreen.getElementByID("Yellow" + i + "panel").show();
        }
    }
    
    //Drop ball button event
    public void dropABall(){
        Main.app.spawnBall(false);
        console.output("Dropping a ball...");
    }
    
    //Exit button event
    public void exitApp(){
        System.exit(0);
    }
    
    //Event for ball drop checkbox
    @NiftyEventSubscriber(id="ballDropCheck")
    public void onListBoxSelectionChanged(final String id, final CheckBoxStateChangedEvent event) {
      if (event.isChecked()) {
          Main.app.dropBalls = true;
          console.output("Now Dropping Balls!");
      } else {
          Main.app.dropBalls = false;
          console.output("Stopped Dropping Balls!");
      }
    } 
    
    //Event for conveyor ttime slider
    @NiftyEventSubscriber(id="ttimeSlider")
    public void ttimeSliderChange(final String id, final SliderChangedEvent event) {
        float value = event.getValue();
        Main.ttime = value;
        console.output("Conveyor speed changed to " + value);
    } 
    
//    Event for drop delay field
    public void dropDelayChange() {
        //checking if value is a number
        try {
            int value = Integer.parseInt(screen.findNiftyControl("dropDelayField", TextField.class).getRealText());
            Main.BASE_DROP_TIME = value;
            
        } catch(Exception e) {
            console.output("Value is not a number!");
        }
    } 
    
    
    // Powerup activated event
    @NiftyEventSubscriber(pattern="power.*")
    public void dropDelayChange(final String id, final ButtonClickedEvent event) {
        if (id.equals("powerSpeedBlue")){
            speedBlue.activate();
        } else if (id.equals("powerSpeedYellow")) {
            speedYellow.activate();
        } else if (id.equals("powerSpeedBlue2")){
            speedBlue2.activate();
        } else if (id.equals("powerSpeedYellow2")) {
            speedYellow2.activate();
        } else if (id.equals("powerDropYellow")) {
            dropYellow.activate();
        } else if (id.equals("powerDropBlue")) {
            dropBlue.activate();
        } else if (id.equals("powerStop")) {
            stopPower.activate();
        } else if (id.equals("powerSpecial1Blue")) {
            halfBlue.activate();
        } else if (id.equals("powerSpecial1Yellow")) {
            halfYellow.activate();
        } else if (id.equals("powerSpecial2Blue")) {
            halfDropBlue.activate();
        } else if (id.equals("powerSpecial2Yellow")) {
            halfDropYellow.activate();
        } else if (id.equals("powerSpecial3Blue")) {
            GPBlue.activate();
        } else if (id.equals("powerSpecial3Yellow")) {
            GPYellow.activate();
        } else if (id.equals("powerSpecial4Blue")) {
            wipeBlue.activate();
        } else if (id.equals("powerSpecial4Yellow")) {
            wipeYellow.activate();
        } else if (id.equals("powerBonusYellow")) {
            ConveyorScreen.getElementByID("bonusYellowPanel").hide();
            bonusYellow.activate();
        }  else if (id.equals("powerBonusBlue")) {
            ConveyorScreen.getElementByID("bonusBluePanel").hide();
            bonusBlue.activate();
        } 
        
    } 
    
    // method for start special logic
    private static void removeAllButGiven(String team, String given) {
        Button s1 = screen.findNiftyControl("powerSpecial1" + team, Button.class);
        Button s2 = screen.findNiftyControl("powerSpecial2" + team, Button.class);
        Button s3 = screen.findNiftyControl("powerSpecial3" + team, Button.class);
        Button s4 = screen.findNiftyControl("powerSpecial4" + team, Button.class);
        
        specials = new Button[] {s1, s2, s3, s4};
        for (int i = 1 ; i <= specials.length ; i ++) {
            Button s = specials[i - 1];
            if (! s.getText().equals(given)){
                ConveyorScreen.getElementByID(team + i + "panel").hide();
            }
        }
    }
    
    // event for changing powerup stuff
    public void changeDuration(){
        String toChange = modifyDurationDD.getSelection();
        try{
            int duration = Integer.parseInt(screen.findNiftyControl("durationField", TextField.class).getRealText());
            
             if (toChange.equals("0.5x Conveyor Speed")){
                halfBlue.setDuration(duration);
                halfYellow.setDuration(duration);
            } else if (toChange.equals("3.0x Drop Rate")) {
                halfDropBlue.setDuration(duration);
                halfDropYellow.setDuration(duration);
            } else if (toChange.equals("Drop Black GP")) {
                GPBlue.setDuration(duration);
                GPYellow.setDuration(duration);
            } else if (toChange.equals("Wipe Conveyor")) {
                wipeBlue.setDuration(duration);
                wipeYellow.setDuration(duration);
            } else if (toChange.equals("2x Conveyor Speed")) {
                speedBlue.setDuration(duration);
                speedYellow.setDuration(duration);
            } else if (toChange.equals("1.5x Conveyor Speed")) {
                speedBlue2.setDuration(duration);
                speedYellow2.setDuration(duration);
            } else if (toChange.equals("2x Dropper Speed")) {
                dropBlue.setDuration(duration);
                dropYellow.setDuration(duration);
            } else if (toChange.equals("Stop Conveyor")) {
                stopPower.setDuration(duration);
            } 
        } catch (Exception e) {
            System.err.println("Error with field!");
            e.printStackTrace();
        }   
        
    }
    
    //Change cooldown admin event
    public void changeCooldown(){
        String toChange = modifyCooldownDD.getSelection();
        try{
            int cooldown = Integer.parseInt(screen.findNiftyControl("cooldownField", TextField.class).getRealText());
            
             if (toChange.equals("0.5x Conveyor Speed")){
                halfBlue.setCooldown(cooldown);
                halfYellow.setCooldown(cooldown);
            } else if (toChange.equals("3.0x Drop Rate")) {
                halfDropBlue.setCooldown(cooldown);
                halfDropYellow.setCooldown(cooldown);
            } else if (toChange.equals("Drop Black GP")) {
                GPBlue.setCooldown(cooldown);
                GPYellow.setCooldown(cooldown);
            } else if (toChange.equals("Wipe Conveyor")) {
                wipeBlue.setCooldown(cooldown);
                wipeYellow.setCooldown(cooldown);
            } else if (toChange.equals("2x Conveyor Speed")) {
                speedBlue.setCooldown(cooldown);
                speedYellow.setCooldown(cooldown);
            } else if (toChange.equals("1.5x Conveyor Speed")) {
                speedBlue2.setCooldown(cooldown);
                speedYellow2.setCooldown(cooldown);
            } else if (toChange.equals("2x Dropper Speed")) {
                dropBlue.setCooldown(cooldown);
                dropYellow.setCooldown(cooldown);
            } else if (toChange.equals("Stop Conveyor")) {
                stopPower.setCooldown(cooldown);
            } 
        } catch (Exception e) {
            System.err.println("Error with field!");
            e.printStackTrace();
        }   
        
    }
    
    //reset button event
    public void resetButton(){
        Main.app.endGame();
    }
    
    //Utility Methods
    
    /**
     * Set text of the element e to the string text
     * @param e the element
     * @param text the text
     */
    public static void setTextToElement(Element e, String text) {
    	e.getRenderer(TextRenderer.class).setText(text);
    }
    
    /**
     * Finds and returns element of the given id.
     * @param elementID the id of the element to find
     * @return the element
     */
    public static Element getElementByID(String elementID) {
        try {
            return (Element) nifty.getScreen("hud").findElementById(elementID);
        } catch (Exception e) {
            System.err.println("Failed to find a nifty element called " + elementID +" !");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Set text of an element indicated by id to the String text.
     * 
     * @param id the id of the element
     * @param text the string to set
     */
    public static void setTextToID(String id, String text) {
        setTextToElement(getElementByID(id), text);
    }
    
}

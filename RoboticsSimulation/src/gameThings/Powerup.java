/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameThings;

import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.tools.Color;
import guiControllers.ConveyorScreen;
import testerPackage.Main;

/**
 * abstact class for every powerup.
 * 
 * @author adrientremblay
 */
public abstract class Powerup {
    private int duration;
    private int cooldown;
    private long lastCall;
    private String cooldownString;
    private boolean active;
    private Button button;
    private int coolClass;
    
    public Powerup(int duration, int cooldown, String cooldownString, Button button, int coolClass){
        this.duration = duration;
        this.cooldown = cooldown;
        this.lastCall = 0;
        this.cooldownString = cooldownString;
        this.active = false;
        this.button = button;
        this.coolClass = coolClass;
    }
    
    public void addToActivePowerups(){
        try {
            ConveyorScreen.getElementByID(cooldownString).getRenderer(TextRenderer.class).setText(Integer.toString(cooldown));
        } catch (Exception e) {
            System.err.println("Could not find label!");
        }
        Main.activePowerups.add(this);
    }
    
    public void activate() {
        
        if (System.currentTimeMillis() - lastCall >= cooldown * 1000){
            this.lastCall = System.currentTimeMillis();
            this.button.setTextColor(new Color(0, 255, 0, 1));
            
            // Searching for powerups of same type
            for (Powerup p : Main.activePowerups) {
                if (p.getCoolClass() == this.getCoolClass() && p.isActive()) { //disactivating same class ones
                    p.disactivate();
                }
            }
            
            addToActivePowerups();
            
            this.active = true;
            enable(); // so we enable
            
        } else {
            ConveyorScreen.console.output("Powerup must cooldown!", Color.WHITE);
        }
        
    }
    
    public void disactivate() {
        this.button.setTextColor(Color.WHITE);
        this.active = false;
        disable();
    }
    
    public boolean done(){
         if (System.currentTimeMillis() - lastCall >= duration * 1000){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean cooled(){
         if (System.currentTimeMillis() - lastCall >= cooldown * 1000){
            return true;
        } else {
            return false;
        }
    }
    
    public void resetCoolDown(){
        ConveyorScreen.setTextToID(cooldownString, String.valueOf(0));
        this.lastCall = 0;
    }
    
    // Abstract action methods
    
    public abstract void enable();
     
    public abstract void disable();
    
    // Mutators
    
    public String getcooldownString(){
        return this.cooldownString;
    }
    
    public void setcooldownString(String s){
        this.cooldownString = s;
    }
    
    public boolean isActive(){
        return this.active;
    }
    
    public void setActive(boolean b) {
        this.active = b;
    }
    
    public int getCoolClass(){
        return this.coolClass;
    }
    
    public void setcooldownString(int i){
        this.coolClass = i;
    }
    
    public void setDuration(int duration) {
        if (!this.active) this.duration = duration;
    }
    
    public void setCooldown(int cooldown) {
        if (!this.active) this.cooldown = cooldown;
    }
    
}

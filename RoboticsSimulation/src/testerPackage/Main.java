package testerPackage;

import guiControllers.ConveyorScreen;
import controls.ConveyorGhostControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.util.TangentBinormalGenerator;
import controls.TeamGhostControl;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import gameThings.Ball;
import gameThings.Powerup;
import java.util.ArrayList;

/**
 * Main class of Simulation
 * 
 * @author adrientremblay
 */
public class Main extends SimpleApplication {
    
    public static BulletAppState bulletAppState;
    public static Main app;
    
    // SIMULATION OPTIONS
    private final static boolean DEBUG = false;
    public static final boolean ADMIN_MENU = true;
    public static final boolean CONSOLE_MENU = false;
    
    // SIMULATION CONSTANTS
    private final static float BALL_MASS = 1f; // mass of each ballm
    public final static int BALL_VALUE_STD = 5; // points of a reglar ball
    public final static int BALL_VALUE_BLK = 50; // points of a black ball
    private final static int BASE_DIR_CHANGE_TIME = 30; // DC THIS!!
    public static int BASE_DROP_TIME = 5;
    private final static Vector3f CAMERA_POS_INITIAL = new Vector3f(6f, 6f, 6f);
    private final static Vector3f BALL_DROP_POS =  new Vector3f(0, 5.0f, 0);
    public final static int BASE_POWERUP_COOLDOWN = 30; //seconds
    public final static int BASE_GAME_TIME = 300; //seconds
    
    // SIMULATION DIMENSIONS
    public static final float BELT_LENGTH = 3.5687f; // the length of belt section in meters -> 1:1 scale with real belt
    private final float BELT_WIDTH = 0.33213f; // SIMULATION width of the conveyor belt
    private final float BELT_HEIGHT = 1.0f; // SIMULATION height of belt structure used for ghost controls
    
    //LOGIC VARIABLES
    private float timeSinceDrop; // time since ball drop
    public float timeSinceDirChange; // time since conveyor direction changes
    public float dropInterval = 0; // time between red bean bag drops
    public boolean dropBalls = true; // if true, drop RED balls onto conveyor
    public static float ttime = 45; // speed to go all the way across the conveyor
    public static int conveyorDirection = 0; // +1 -> right , -1 -> left, 0 -> stopped
    public static float conveyorSpeed = 0; // speed of conveyor
    public static boolean started = false; // false -> not started, true -> game started
    public static float conveyorMultiplier = 1; // flat multiplier for powerups
    public static float conveyorEnable = 1; // 1 -> conveyor enables, 0 -> conveyor disabled
    private float sCounter = 0; // Secondcounter for second delays
    public static float dropMultiplier = 1; // multiplier for ball drop
    private static int timeLeft = BASE_GAME_TIME; // time left in game
        // score of the blue and yellow team respectively
    public int blueScore = 0;
    public int yellowScore = 0;
    
    public static ArrayList<Powerup> allPowers = new ArrayList<Powerup>();
    
    //Declaring Nodes
    private Node conveyorNode;
    
    //Declare materials
    private Material conveyorMat;
    
    
    private RigidBodyControl ball_phy;
    private static final Sphere SPHERE= new Sphere(32, 32, 0.1143f, true, false);;
    
    //GUI declerations
    public static Nifty nifty;
    public static ArrayList<Powerup> activePowerups = new ArrayList<Powerup>();
    
    static {
//        floor = new Box(3.5f, 0.1f, 3.5f);
        SPHERE.setTextureMode(TextureMode.Projected);

    };
    
    //Main Method
    public static void main(String[] args) {
        app = new Main();
        app.start();
    }

    /**
     * Method called when app is initialized.
     */
    @Override
    public void simpleInitApp() {
        //Setup GUI
        initNifty();
        
        //Setting up JBullet physics envirnoment
        bulletAppState = new BulletAppState();
        if (DEBUG) bulletAppState.setDebugEnabled(true); //Enable JBullet debug -> Wireframes
        stateManager.attach(bulletAppState);
        
        //Configering Camera
        cam.setLocation(CAMERA_POS_INITIAL);
        cam.lookAt(new Vector3f(3f, 3f, 3f), Vector3f.UNIT_Y);
        flyCam.setDragToRotate(true); // activate windowed input behaviour
        
        //Adding lightsource
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(light);
        
        //Scene
        createConveyor();

        //Creating Keyboard
        initKeys();
    }
    
    /**
     * Method to create Nifty GUI.
     */
    public void initNifty(){
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/gui.xml", "start", new ConveyorScreen());
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    /**
     * Method to create the conveyor mesh.
     */
    public void createConveyor(){
        //Creating the conveyor node
        conveyorNode = new Node("conveyor");
        
        //Conveyor Material
        conveyorMat =  new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        conveyorMat.setColor("Color", new ColorRGBA(1,0,0,0.1f));  
        conveyorMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        //Creating structure geometry
        Spatial conveyor_geo = assetManager.loadModel("Models/conveyor.j3o");
        conveyor_geo.setName("conveyor");
        conveyor_geo.scale(2.0f);
        conveyorNode.attachChild(conveyor_geo);
        
        //Creating structure physics
        CollisionShape conveyor_phy = CollisionShapeFactory.createMeshShape(conveyor_geo);
        RigidBodyControl conveyorControl = new RigidBodyControl(conveyor_phy, 0);
        conveyor_geo.addControl(conveyorControl);
        bulletAppState.getPhysicsSpace().add(conveyorControl);
        
        
        //Creating Conveyor Glass
        createGlass();
        //Creating Conveyor Ghost
        createConveyorGhost();
        //Create Blue Team Ghost Control
        createTeamGhost("teamBNode", true, new Vector3f(1.0f,0.5f,1.0f), 4.0f, 0.0f, 0.0f);
        //Create Yellow Team Ghost Control
         createTeamGhost("teamANode", false, new Vector3f(1.0f,0.5f,1.0f), -4.0f, 0.0f, 0.0f);
        //Attatching conveyorNode
        rootNode.attachChild(conveyorNode);
    }
    
    /**
     * Create the conveyor ghost.  In layman's terms an invisible box that hovers over the conveyor.  
     * Precisely when a ball is inside this box it will be affected by the conveyors belt.
     */
    public void createConveyorGhost(){
        ConveyorGhostControl conveyorGhost = new ConveyorGhostControl(new BoxCollisionShape(new Vector3f(BELT_LENGTH, 0.2f,BELT_WIDTH)));
        Node ghostNode = new Node("conveyorGhostNode");
        ghostNode.setLocalTranslation(0.0f, 2.0f, 0.0f);
        ghostNode.addControl(conveyorGhost);
        conveyorNode.attachChild(ghostNode);
        bulletAppState.getPhysicsSpace().add(conveyorGhost);
    }
    
    /**
     * Create a ghost control for a team.  
     * 
     * @param ID String to be used for identification of ghost control
     * @param blueTeam is this the blue team haha?
     * @param dimensions dimensions of control
     * @param x x position
     * @param y y position
     * @param z z position
     */
    void createTeamGhost(String ID, boolean blueTeam, Vector3f dimensions, float x, float y, float z){
        TeamGhostControl teamGhost = new TeamGhostControl(new BoxCollisionShape(dimensions), blueTeam);
        Node teamANode = new Node(ID);
        teamANode.setLocalTranslation(x, y, z);
        teamANode.addControl(teamGhost);
        rootNode.attachChild(teamANode);
        bulletAppState.getPhysicsSpace().add(teamGhost);
    }
    
    /**
     * Method to create glass mesh of conveyor.
     */
    public void createGlass(){
        //Creating the glass geometry
        Spatial glass_geo = assetManager.loadModel("Models/glass.j3o");
        glass_geo.scale(2.0f);
        Material glassMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        glassMat.setColor("Color", new ColorRGBA(1, 1, 1, 0.5f));
        glassMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        glass_geo.setMaterial(glassMat);
        glass_geo.setQueueBucket(Bucket.Transparent);
        conveyorNode.attachChild(glass_geo);
        
        //Creating glass physics object
        CollisionShape glass_phy = CollisionShapeFactory.createMeshShape(glass_geo);
        RigidBodyControl glassControl = new RigidBodyControl(glass_phy, 0);
        glass_geo.addControl(glassControl);
        bulletAppState.getPhysicsSpace().add(glassControl);
    }
    
    /**
     * Spawn a ball at the location of the BALL_DROP_POS constant
     * 
     * @param black will drop a black GP if true
     */
    public void spawnBall(boolean black){
        //Creating ball geometry
        SPHERE.setTextureMode(Sphere.TextureMode.Projected);
        TangentBinormalGenerator.generate(SPHERE);
        Ball ball_geo = new Ball("ball", SPHERE, black);
        Material ballMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        if (black) ballMat.setColor("Color", new ColorRGBA(0, 0, 0, 1));
        else ballMat.setColor("Color", new ColorRGBA(1, 0, 0, 1));
        ball_geo.setMaterial((Material) ballMat);
        rootNode.attachChild(ball_geo);
        ball_geo.setLocalTranslation(BALL_DROP_POS);
        //Physics
        ball_phy = new RigidBodyControl(BALL_MASS); //creating ball rigid body with mass 1
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        
    }
    
    /**
     * Method called when a game is complete.
     */
    public void endGame(){
        resetGame();
        nifty.gotoScreen("end");
    }
    
    /**
     * Reset the simulation completely.
     */
    public void resetGame(){
        removeAll(); // removing all balls
 
        // disactivating all powerups
        ArrayList<Powerup> remove = new ArrayList<>();
        for (Powerup p : activePowerups){
            remove.add(p);
        }
        activePowerups.removeAll(remove);
        
        for (Powerup p : allPowers) {
            p.resetCoolDown();
            if (p.isActive()) p.disactivate();
        }
        
        //resetting conveyor change
        timeSinceDirChange = 0;
        //resetting drop time
        timeSinceDrop = 0;
        //resetting game time
        timeLeft = BASE_GAME_TIME;
        
        //Making sure the specials are hidden
        ConveyorScreen.getElementByID("bonusYellowPanel").hide();
        ConveyorScreen.getElementByID("bonusBluePanel").hide();
        
        //setting Main.started to false
        Main.started = false;
    }
    
    /**
     * Reset score of both teams.
     */
    public void clearScore(){
        blueScore = 0;
        yellowScore = 0;
    }
    
    /**
     * Remove all balls from JBullet physics space
     */
    public static void removeAll(){
        for (PhysicsRigidBody r : Main.bulletAppState.getPhysicsSpace().getRigidBodyList()){
            if (r.getUserObject().getClass() == Ball.class) {
               Ball b = (Ball) r.getUserObject();
               b.setToDestory(true); // Set the ball to be deleted
           }
        }
    }
    
    /**
     * The analog listener for keyboard input
     */
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (started) {
                // Matching event to input
                if (name.equals("1")) {
                    ConveyorScreen.speedYellow.activate();
                } else if (name.equals("2")) {
                    ConveyorScreen.speedYellow2.activate();
                } else if (name.equals("3")) {
                    ConveyorScreen.dropYellow.activate();
                } else if (name.equals("4") && ConveyorScreen.yellowSpecialPowerup != null) {
                    ConveyorScreen.yellowSpecialPowerup.activate();
                } else if (name.equals("5") && ConveyorScreen.getElementByID("bonusYellowPanel").isVisible()) {
                    ConveyorScreen.getElementByID("bonusYellowPanel").hide();
                    ConveyorScreen.bonusYellow.activate();
                } else if (name.equals("6") && ConveyorScreen.getElementByID("bonusBluePanel").isVisible()) {
                    ConveyorScreen.getElementByID("bonusBluePanel").hide();
                    ConveyorScreen.bonusBlue.activate();
                } else if (name.equals("7")) {
                    ConveyorScreen.speedBlue.activate();
                } else if (name.equals("8")) {
                    ConveyorScreen.speedBlue2.activate();
                } else if (name.equals("9")) {
                    ConveyorScreen.dropBlue.activate();
                } else if (name.equals("0") && ConveyorScreen.blueSpecialPowerup != null) {
                    ConveyorScreen.blueSpecialPowerup.activate();
                } else if (name.equals("G")) {
                    ConveyorScreen.stopPower.activate();
                } 
            } else {
                System.out.println("Invalid Key!");
            }
        }
    };

    /**
     * Creating keyboard mappings.
     */
    private void initKeys() {
        // Player 1 keys
        inputManager.addMapping("1",  new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("2",  new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("3",  new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("4",  new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("5",  new KeyTrigger(KeyInput.KEY_5));
        
        // Player 2 keys
        inputManager.addMapping("6",  new KeyTrigger(KeyInput.KEY_6));
        inputManager.addMapping("7",  new KeyTrigger(KeyInput.KEY_7));
        inputManager.addMapping("8",  new KeyTrigger(KeyInput.KEY_8));
        inputManager.addMapping("9",  new KeyTrigger(KeyInput.KEY_9));
        inputManager.addMapping("0",  new KeyTrigger(KeyInput.KEY_0));
        
        // Mutual Keys
        inputManager.addMapping("G",  new KeyTrigger(KeyInput.KEY_G));

        // Add the names to the action listener.
        inputManager.addListener(analogListener, "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "G");
    }
    
    /**
     * The main update method.  Runs every frame.  Where the game logic occurs.
     * 
     * @param tpf the time per frame in milliseconds
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (started){
            //Calculating Speed
            conveyorSpeed = conveyorMultiplier * conveyorDirection * conveyorEnable * (BELT_LENGTH / ttime) * 2;
            //Calculating drop delay
            dropInterval = BASE_DROP_TIME / dropMultiplier;
            
            //Ball Dropping Logic
            timeSinceDrop += tpf;
            ConveyorScreen.setTextToID("dropCountdown", String.format("%.0f", dropInterval - timeSinceDrop));
            if(timeSinceDrop >= dropInterval && dropBalls) {
                timeSinceDrop = 0.0f;
                spawnBall(false);
            }
            
            //Conveyor Switching Logic
            timeSinceDirChange += tpf;
            ConveyorScreen.setTextToID("countdown", String.format("%.0f", BASE_DIR_CHANGE_TIME - timeSinceDirChange));
            if (timeSinceDirChange > BASE_DIR_CHANGE_TIME){
                System.out.println("wiw");
                conveyorDirection *= -1;
                timeSinceDirChange = 0;
                ConveyorScreen.console.output("Switching Conveyor Direction...");
            }
            
            //Updating ttime and speed
            ConveyorScreen.setTextToID("ttimeLabel", String.format("%.0f",ttime));
            ConveyorScreen.setTextToID("speedLabel", String.format("%.2f",conveyorSpeed));
            //Updating Direction String
            String dir;
            if (conveyorSpeed > 0){
                dir = "BLUE";
            } else if (conveyorSpeed < 0){
                dir = "YELLOW";
            } else {
                dir = "PAUSED";
            }
            //Updating Multipliers
            ConveyorScreen.setTextToID("multiplierLabel", String.format("%.1f",conveyorMultiplier));
            ConveyorScreen.setTextToID("dropMultiplierLabel", String.format("%.1f",dropMultiplier));
            ConveyorScreen.setTextToID("directionLabel", dir);
            //Updating Points
            ConveyorScreen.setTextToID("teamALabel", String.valueOf(yellowScore));
            ConveyorScreen.setTextToID("teamBLabel", String.valueOf(blueScore));
            //Updating cooldownspeeds for Powerups
            sCounter +=tpf;
            if (sCounter > 1 ) { // Run each second
                //Updating Game Timer
                timeLeft--;
                //Checking if time is out... XXX
                if (timeLeft == 0) {
                    endGame();
                }
                //Checking if one minute left for bonus powerup
                if (timeLeft == BASE_GAME_TIME  - 60) {
                    ConveyorScreen.console.output("ONE MINUTE PASSED!  ONE TIME CONVEYOR SWITCH POWERUP ENABLED!");
                    ConveyorScreen.getElementByID("bonusYellowPanel").show();
                    ConveyorScreen.getElementByID("bonusBluePanel").show();
                }
                
                //Updating time left display
                int minutes = timeLeft / 60;
                int seconds = (timeLeft) % 60;
                String displayTL = String.format("%d:%02d", minutes, seconds);
                ConveyorScreen.setTextToID("gameTimeLabel", displayTL);
                
                //active powerup logic
                ArrayList<Powerup> remove = new ArrayList<>();
                for (Powerup p : activePowerups){
                    String id = p.getcooldownString();
                    Element e = ConveyorScreen.getElementByID(id);
                    //updating powerup cooldown
                    int value = Integer.parseInt(e.getRenderer(TextRenderer.class).getOriginalText()) - 1; 
                    if (value != -1) ConveyorScreen.setTextToID(id, String.valueOf(value));
                    // Logic to remove powerup
                    if (p.done()) {
                        if (p.cooled()) remove.add(p);
                        if (p.isActive()) p.disactivate();
                    }
                }
                activePowerups.removeAll(remove);
                sCounter = 0.0f;
            }

        }
        
        // Ball Deletion
        for (Object o :bulletAppState.getPhysicsSpace().getRigidBodyList().toArray()){
            RigidBodyControl c = (RigidBodyControl)o;
            if (c.getUserObject().getClass() == Ball.class){ //Ball detected in physics space
                Ball b = (Ball) c.getUserObject();
                if(b.getToDestroy()) {
                    b.destroy();
                }
            }
        }

    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
    
    public Nifty getNifty(){
        return Main.nifty;
    }
}

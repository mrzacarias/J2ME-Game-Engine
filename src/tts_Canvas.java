//TTS CANVAS

/**
 * This is the template of a basic Canvas. We don't have here (yet) some of basic
 * porting aspects, like full and gamecanvas for nokia and Black Berry devices,
 * but we are working on it :)
 *
 * However, we have here the most simply and elegant basic implementation of canvas
 * that we can think of. :D
 * What are the most basic and important methods of a Canvas class:
 *
 * run():
 * The main loop. Will be the method where will call the logic and paint methods.
 * In there, we have a "while" loop with the control flux variable "continueGame",
 * changed only by logic method.The game only stops when this variable is setted
 * to false.
 *
 * paint(Graphics g):
 * This is the method that will draw all images, texts and whatever on the device
 * screen.
 */

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
//import javax.microedition.lcdui.Font;
//import javax.microedition.rms.*;// RecordStore

public class tts_Canvas extends Canvas implements Runnable
{
    /**
     * MIDlet instance
     */
    public static tts_MIDlet midlet;

    /**
     * Input class instance
     */
    public static tts_Input input;

    /**
     * Sound handler instance
     */
    public static tts_Sound soundHandler;

    /**
     * Canvas Width and Height
     */
    public static int canvasWidth = 0;
    public static int canvasHeight = 0;

    /**
     * Here we have an example of specific canvas methods and variables, which will
     * be compiled or not with Abilities.
     *
     * In this case, we have the sizeChanged method, which pauses the game if the screen
     * size was changed. This generally occurs when we have some devices, like N95, HTC and Black
     * Berry 9530, where the screen size can be changed during ingame. This probably harm the
     * graphic structures positions, like images and text, so our common process is to pause the
     * game in this situations.
     */

    //#if DEVICE_CHANGES_SIZE
//#     /*
//#      * Variable to control if device changed size or not
//#      */
//#     public static boolean changedSize = false;
//#
//#     /*
//#      * Method to change size of screen when needed.
//#      *
//#      * If the changed size height is greater than width, then the device will
//#      * be paused to avoid graphic problems. A message to user will be displayed
//#      * on paint method
//#      */
//#     protected void sizeChanged(int w, int h)
//#     {
//#         canvasWidth = w;
//#         canvasHeight = h;
//#
//#         if(h < w)
//#         {
//#             changedSize = true;
//#         }else
//#         {
//#             changedSize = false;
//#         }
//#
//#         hideNotify();
//#         showNotify();
//#     }
    //#endif

    /**
     * Game flux controller.
     *
     * The game will run while continueGame is true.
     */
    private boolean continueGame = true;

    private long cycleStartTime;

    /**
     * Frame limiter variables
     */
    //24/25 frames per second. We don't know yet why this need to be 31 =/
    public static final int MAX_FRAMES = 25;
    public static final int MS_PER_FRAME = 1000/(MAX_FRAMES+6);

    private long sleepTime;
    private long cycleTime;

    /**
     * Frame counter variables
     */
    private long frameInitTimer;
    public static int frameCounter;

    //#if DEBUG_FRAMES
//#     private int maxFrameCount;
    //#endif


    /*
     * BASIC FUNCTIONS
     */

    /**
     * Constructor
     *
     * Will be called in MIDlet class, having his own instance passed by argument.
     * Here
     */
    public tts_Canvas(tts_MIDlet mid)
    {
        /**
         * Setting midlet instance, passed by argument
         */
        tts_Canvas.midlet = mid;

        /**
         * Setting input handler instance. Also, clean the keys.
         */
        tts_Canvas.input = new tts_Input(this);
        input.keyPressedPurge();

        /** 
         * Setting sound handler instance
         */
        tts_Canvas.soundHandler = new tts_Sound();
        
        /**
         * Creating the first state: Logo
         *
         * To Logo State, the only argument is the current time in MilliSeconds
         */
        tts_StateMachine.changeState( new tts_StateLogo(System.currentTimeMillis()));

        /**
         * Graphics initialization
         */
        initGraphics();

        //#if DEVICE_CHANGES_SIZE
//#         /**
//#          * Check if device has changed canvas size
//#          */
//#         sizeChanged(canvasWidth, canvasHeight);
        //#endif

        /**
         * Frame limiter and counter initialization
         */
        frameLogicInitialization();
    }

    /**
     * Frame limiter and counter initialization
     */
    private void frameLogicInitialization()
    {
        /*
         * Frame limiter initialization
         */
        cycleTime = System.currentTimeMillis();
        frameCounter = 0;

        /**
         * Frame counter initialization
         */
        frameCounter = 0;
        frameInitTimer = System.currentTimeMillis();

        //#if DEBUG_FRAMES
//#         maxFrameCount = 0;
        //#endif
    }

    /**
     * Main game loop
     *
     * It's here where we call logic and paint methods
     */
    public void run()
    {
        /*
         * Frame Counter logic start
         */
        frameInitTimer = System.currentTimeMillis();

        /**
         * The flux control variable will make the main loop run yet true
         */
        while(continueGame)
        {
            /**
             * Frame Limiter logic start
             */
            cycleStartTime = System.currentTimeMillis();

            /**
             * The game logic method is the only place where the flux control
             * variable can be changed.
             */
            //#if DEVICE_CHANGES_SIZE
//#             /**
//#              * If the device has changed the canvas size, we don't do the logic
//#              */
//#             if(!changedSize)
//#             {
//#                 continueGame = stateMachine.currentState.logic();
//#             }
            //#else
            continueGame = tts_StateMachine.currentState.logic();
            //#endif

            /**
             * This is where the main loop calls the paint method.
             */
            repaint();
            serviceRepaints();

            /*
             * Frame Logic
             */
            frameLogic();
        }
        
        /**
         * Main loop is over, try to close application
         */
        try
        {
            /**
             * This will call destroyApp method from MIDlet
             */
            midlet.destroyApp(false);
        }
        catch (Exception e)
        {
        }
    }
    
    private void frameLogic()
    {
        /**
         * Frame Counter magic
         */
        frameCounter++;

        if(System.currentTimeMillis() - frameInitTimer > 1000)
        {
            //#if DEBUG_FRAMES
//#             maxFrameCount = frameCounter;
            //#endif
            frameCounter = 0;
            frameInitTimer = System.currentTimeMillis();
        }

        /**
         * Frame Limiter magic
         */
        sleepTime = 0;
        cycleTime = System.currentTimeMillis() - cycleStartTime;

        if (cycleTime < MS_PER_FRAME)
        {
            sleepTime = MS_PER_FRAME - cycleTime;
        }

        //#if DEBUG_FRAMES
//#         System.out.println( "Frame: " + frameCounter + "===================" );
//#         System.out.println( "cycleTime: "  + cycleTime);
//#         System.out.println( "sleepTime: "  + sleepTime);
        //#endif

        if (sleepTime > 0)
        {
            try
            {
                Thread.sleep(sleepTime);
            }
            catch (Exception ex)
            {
                System.out.println( "Could not sleep." );
            }
        }

        /*
         * Estimating time elapsed here
         */
        //timeElapsed = (frameCounter+1) * 1000/(MAX_FRAMES);
    }

    /**
     * Hide and Show notify methods
     *
     * Here we need to set all to pause and unpaused state.
     */
    protected void hideNotify()
    {
        //stateMachine.currentState.pause();
    }
    protected void showNotify()
    {
        //stateMachine.currentState.unpause();
    }

    /**
     * Paint method
     *
     * All that will be painted on screen need to be called here.
     * If we are in a load state, the paint method of tts_Load will be
     * called instead of the current state.
     */
    protected void paint(Graphics g)
    {
        if(tts_StateMachine.isLoading())
        {
            tts_StateMachine.loadState.paint(g);
        }
        else
        {
            tts_StateMachine.currentState.paint(g);
        }

        //#if DEBUG_FRAMES
//#         g.setColor(0x000000);
//#         g.fillRect(0,0,canvasWidth, 30);
//#         g.setColor(0x00ff00);
//#         g.drawString("Frames: " + maxFrameCount, 2, 2, 0);
        //#endif
    }

    /*
     * GRAPHICS GENERIC FUNCTIONS
     */

    /**
     * Graphics initialization
     */
    private void initGraphics()
    {
        /**
         * Set canvas to fullScreen mode
         */
        setFullScreenMode(true);

        /**
         * Get the canvas Width and Height
         */
        canvasWidth = this.getWidth();
        canvasHeight = this.getHeight();
    }

    /*
     * KEYS FUNCTIONS
     *
     * This is the overload methods to abstract definitions for keyPressed and keyReleased.
     * J2ME automaticaly call this functions when a key is pressed or released. Then, we call
     * the respectives methods from tts_Input class and let the magic begins :)
     */
    protected void keyPressed(int keyCode)
    {
        input.keyPressed( keyCode );
    }

    protected void keyReleased(int keyCode)
    {
        input.keyReleased( keyCode );
    }
}

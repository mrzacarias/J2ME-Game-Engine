//TTS INPUT

/**
 * Ok, guys, this is the most common Input class we need. We have keyPressed and keyReleased functions,
 * suported by keySet, which is the core method and set the respective booleans for each key and the
 * variables ingameAction and keyPressed.
 * So, in fact, we have 2 ways to check keys here.
 * First, we can check the boolean of each pressed key and do the game logic with this.
 * e.g. "if (key1) { key1 = false; doSomething(); }
 * It's an easy way to check keys and also let us check if more than one key was pressed at same time
 * (depending on device).
 *
 * Another way to check keys is using the ingame frontend. Here, we have 7 ingame commands:
 *   INGAME_UP
 *   INGAME_DOWN
 *   INGAME_LEFT
 *   INGAME_RIGHT
 *   INGAME_SELECT
 *   INGAME_SOFT1
 *   INGAME_KEY_SOFT_2
 *
 * On keySet() method, we set the booleans for each key, but also we set the varable "ingameAction"
 * with the correspondent ingame key, e.g. if we press "up", we set the boolean "keyUp" to "true" and
 * "ingameAction" to "INGAME_UP", and if we press "2", we set the boolean "key2" to "true", but
 * as when we pressed the key "up", we set "ingameAction" to "INGAME_UP". We do this because in
 * several J2ME games, we use the key "2" as "up" key, like "8" to "down", "5" to "select", etc.
 *
 * So, with this "Ingame keys" approach, we can map in the keySet() method which key correspond to each
 * ingame action, and this "mapping" can be changed to each game (or minigame), giving to us more flexibility.
 */

import javax.microedition.lcdui.Canvas;

public class tts_Input
{

    /*
     * INGAME KEYS DEFINES
     *
     * Here we'll set the constants to ingame actions. In this template, we have the canonical
     * action set.
     */
    public static final int INGAME_UNKNOWN    = -1;
    public static final int INGAME_UP     =  0;
    public static final int INGAME_DOWN   =  1;
    public static final int INGAME_LEFT   =  2;
    public static final int INGAME_RIGHT  =  3;
    public static final int INGAME_SELECT =  4;
    public static final int INGAME_SOFT1 =  5;
    public static final int INGAME_SOFT2 =  6;

    /** Here will be saved the last ingame key **/
    private int ingameAction = INGAME_UNKNOWN;

    /** Get the last ingame key pressed **/
    public int ingameAction()
    {
        return ingameAction;
    }

    /*
     * BOOLEAN KEYS
     *
     * This variables keep the state of all keys in the device.
     * If a key was pressed sometime, will be saved on his respective boolean.
     */
    protected boolean key1 = false;
    protected boolean key2 = false;
    protected boolean key3 = false;
    protected boolean key4 = false;
    protected boolean key5 = false;
    protected boolean key6 = false;
    protected boolean key7 = false;
    protected boolean key8 = false;
    protected boolean key9 = false;
    protected boolean keyUp = false;
    protected boolean keyDown = false;
    protected boolean keyLeft = false;
    protected boolean keyRight = false;
    protected boolean keySelect = false;
    protected boolean keySoft1 = false;
    protected boolean keySoft2 = false;
    protected boolean keyZero = false;

    /* We'll save the last key pressed and last game action of this key. This can help us to
     * check some input bugs.
     */
    private int keyPressed = 0;
    private int keyAction = 0;

    /** Get the last key pressed **/
    public int lastKey()
    {
        return keyPressed;
    }

    /** Get the last game action for last key pressed **/
    public int lastAction()
    {
        return keyAction;
    }

//***********************************************************************************************************************
// PORTING DEFINES
//***********************************************************************************************************************

/*
 * Yep, almost each device manufacturer have his own set of keyCodes, what's is awful...
 * Bur, after years and years of porting, we have mapped all of this keyCodes =D
 * Depending on the manufacturer define (DeviceMaker), we set the keyCodes.
 */

//#if DeviceMaker == "sonyericsson" || DeviceMaker == "samsung" || DeviceMaker == "nokia" || TEST_EMULATOR_KEYS
//#     private final byte KEY_UP        = -1;  //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = -2;  //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = -3;  //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = -4;  //Key Code tecla J_Right
//#     private final byte KEY_SELECT    = -5;  //Key Code tecla Select
//#     private final byte KEY_MENU1     = -6;  //Key Code tecla Menu esquerda
//#     private final byte KEY_MENU2     = -7;  //Key Code tecla Menu direita
//#elif DeviceMaker == "motorola"
//#     private final byte KEY_UP        = -1;  //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = -6;  //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = -2;  //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = -5;  //Key Code tecla J_Right
//#     //some motorola devices may be -20, -21, -22
//#     private final byte KEY_SELECT    = 20;  //Key Code tecla Select
//#     private final byte KEY_MENU1     = 21;  //Key Code tecla Menu esquerda
//#     private final byte KEY_MENU2     = 22;  //Key Code tecla Menu direita
//#elif DeviceMaker == "lg"
//#     private final byte KEY_UP        = -1;   //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = -2;   //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = -3;   //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = -4;   //Key Code tecla J_Right
//#     private final byte KEY_SELECT    = -5;   //Key Code tecla Select
//#     private final short KEY_MENU1    = -202; //Key Code tecla Menu esquerda
//#     private final short KEY_MENU2    = -203; //Key Code tecla Menu direita
//#elif DeviceMaker == "nextel"
//#     private final byte KEY_UP        = -10;  //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = -11;  //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = -13;  //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = -12;  //Key Code tecla J_Right
//#     private final byte KEY_SELECT    = -23;  //Key Code tecla Select
//#     private final short KEY_MENU1    = -20;  //Key Code tecla Menu esquerda
//#     private final short KEY_MENU2    = -21;  //Key Code tecla Menu direita
//#elif Device == "8800" || Device == "9000"
//#     private final byte KEY_UP        = 1;    //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = 6;    //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = 2;    //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = 5;    //Key Code tecla J_Right
//#     private final byte KEY_SELECT    = -108; //Key Code tecla Select
//#     private final short KEY_MENU1    = 'q';  //Key Code tecla Menu esquerda
//#     private final short KEY_MENU2    = 'p';  //Key Code tecla Menu direita
//#elif Device == "8100"
//#     private final byte KEY_UP        = 't';  //Key Code tecla J_Up
//#     private final byte KEY_DOWN      = 'b';  //Key Code tecla J_Down
//#     private final byte KEY_LEFT      = 'd';  //Key Code tecla J_Left
//#     private final byte KEY_RIGHT     = 'j';  //Key Code tecla J_Right
//#     private final byte KEY_SELECT    = 'g';  //Key Code tecla Select
//#     private final short KEY_MENU1    = 'q';  //Key Code tecla Menu esquerda
//#     private final short KEY_MENU2    = 'o';  //Key Code tecla Menu direita
//#else
    private final byte KEY_UP        = -1;   //Key Code tecla J_Up
    private final byte KEY_DOWN      = -2;   //Key Code tecla J_Down
    private final byte KEY_LEFT      = -3;   //Key Code tecla J_Left
    private final byte KEY_RIGHT     = -4;   //Key Code tecla J_Right
    private final byte KEY_SELECT    = -5;   //Key Code tecla Select
    private final byte KEY_MENU1     = -6;   //Key Code tecla Menu esquerda
    private final byte KEY_MENU2     = -7;   //Key Code tecla Menu direita
//#endif

//***********************************************************************************************************************
// ENGINE
//***********************************************************************************************************************

    /*
     * We get a instance of the current canvas to get gameAction
     */
    private tts_Canvas canvas;

    /*
     * Constructor
     *
     * Set the current canvas to canvas instance
     */
    public tts_Input(tts_Canvas c)
    {
        canvas = c;
    }

    /*
     * Method which will be called by the Canvas method keyPressed.
     * Here we'll set the keyAction for the correspondent keyCode.
     * Also, will save the key pressed on "keyPressed" and set the
     * ingame key and the correspondent boolean on keySet method.
     */
    public void keyPressed(int keyCode)
    {
        keyAction = canvas.getGameAction(keyCode);
        keyPressed = keySet(keyCode, true);
    }

    /*
     * Analog as keyPressed, but here we'll set to false the correspondent boolean
     * and reset the ingame key variable.
     */
    public void keyReleased(int keyCode)
    {
        keySet(keyCode, false);
        ingameAction = INGAME_UNKNOWN;
    }

    /** Resets every key pressed variable (sets it to released). */
    public void keyPressedPurge()
    {
        keySelect = key1 = key2 = key3 = key4 = key5 = key6 = key7 = key8 = key9 = keyZero = keyLeft = keyRight = keyUp = keyDown = keySoft1 = keySoft2 = false;
        ingameAction = INGAME_UNKNOWN;
        keyPressed   = 0;
    }

    /**
     * Sets a key to pressed or released. Also, sets the current ingame key action
     * @param keyCode the key code.
     * @param isPressed true if the key has just been pressed, false if released.
     * @return the keyCode possibly changed (some devices may have different mappings).
     */
    private int keySet(int keyCode, boolean isPressed)
    {
//#if DeviceMaker == "motorola" || DeviceMaker == "htc"
//#         if (keyCode < Canvas.KEY_NUM0 || keyCode > Canvas.KEY_NUM9)
//#         {
//#             switch (canvas.getGameAction(keyCode))
//#             {
//#                 case Canvas.FIRE:
//#                     ingameAction = INGAME_SELECT;
//#                     keySelect = isPressed;
//#                     return keyCode;
//#                 case Canvas.UP:
//#                     ingameAction = INGAME_UP;
//#                     keyUp = isPressed;
//#                     return keyCode;
//#                 case Canvas.DOWN:
//#                     ingameAction = INGAME_DOWN;
//#                     keyDown = isPressed;
//#                     return keyCode;
//#                 case Canvas.LEFT:
//#                     ingameAction = INGAME_LEFT;
//#                     keyLeft = isPressed;
//#                     return keyCode;
//#                 case Canvas.RIGHT:
//#                     ingameAction = INGAME_RIGHT;
//#                     keyRight = isPressed;
//#                     return keyCode;
//#             }
//#         }
//#endif
        switch (keyCode)
        {
            case KEY_UP: 
                ingameAction = INGAME_UP;
                keyUp = isPressed;
                break;
            case KEY_DOWN:
                ingameAction = INGAME_DOWN;
                keyDown = isPressed;
                break;
            case KEY_LEFT: 
                ingameAction = INGAME_LEFT;
                keyLeft = isPressed;
                break;
            case KEY_RIGHT: 
                ingameAction = INGAME_RIGHT;
                keyRight = isPressed;
                break;
            case KEY_SELECT:
//#if DeviceMaker == "motorola"
//#                 case -20:
//#endif
                ingameAction = INGAME_SELECT;
                keySelect = isPressed;
                break;
            case Canvas.KEY_NUM1:
                key1 = isPressed;
                break;
            case Canvas.KEY_NUM2:
                ingameAction = INGAME_UP;
                key2 = isPressed;
                break;
            case Canvas.KEY_NUM3:
                key3 = isPressed;
                break;
            case Canvas.KEY_NUM4:
                ingameAction = INGAME_LEFT;
                key4 = isPressed;
                break;
            case Canvas.KEY_NUM5:
                ingameAction = INGAME_SELECT;
                key5 = isPressed;
                break;
            case Canvas.KEY_NUM6:
                ingameAction = INGAME_RIGHT;
                key6 = isPressed;
                break;
            case Canvas.KEY_NUM7:
                key7 = isPressed;
                break;
            case Canvas.KEY_NUM8:
                ingameAction = INGAME_DOWN;
                key8 = isPressed;
                break;
            case Canvas.KEY_NUM9:
                key9 = isPressed;
                break;
            case Canvas.KEY_NUM0:
                keyZero = isPressed;
                break;
            case KEY_MENU1:
//#if DeviceMaker == "motorola"
//#                 case -21:
//#elif DeviceMaker == "lg"
//#                 case -6:
//#endif
                ingameAction = INGAME_SOFT1;
                keySoft1 = isPressed;
                break;
            case KEY_MENU2:
//#if DeviceMaker == "motorola"
//#                 case -22:
//#elif DeviceMaker == "lg"
//#                 case -7:
//#endif
                ingameAction = INGAME_SOFT2;
                keySoft2 = isPressed;
                break;
//#if DeviceMaker == "nextel"
//#             //"Back" key will do nothing at all
//#             case -14:
//#                 return -1;
//#endif
            default:
                break;
        }
        return keyCode;
    }
}
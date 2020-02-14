//TTS MIDLET

/**
 * This is the basic MIDlet. The 3 basic functions (start, pause and destroy app)
 * are defined here and only they. Every another method, function or variable
 * will be defined on tts_Canvas or another auxiliar class.
 */

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class tts_MIDlet extends MIDlet
{
    /*
     * This is important. To run a game on J2ME, we need a Thread, which will be
     * defined on startApp()
     */
    private static Thread mainThread = null;

    /*
     * This is the canvas instance, used to call some non-static methods and to
     * set the main thread and Display.
     */
    private static tts_Canvas canvas = null;

    /*
     * Only an auxiliar variable to get the game version
     */
    public  static String myVersion;

    /*
     * The MIDlet Constructor.
     */
    public tts_MIDlet()
    {
        //we get the version here
        myVersion = this.getAppProperty("MIDlet-Version");
    }

    /*
     * START APP:
     * This method is called on the first iteration of game.
     * Here, we'll instance our canvas, setting the thread
     * and display to work with this and start the thread
     * In some devices, startApp() is also called after
     * returning from pause, so we have this "if" to check if
     * the main thread is already running.
     */
    protected void startApp()
    {
        if(mainThread == null)
        {
            canvas = new tts_Canvas(this);
            mainThread = new Thread(canvas);
            Display.getDisplay(this).setCurrent(canvas);
            mainThread.start();
        }
        else
        {
            if(canvas != null)
            {
                Display.getDisplay(this).setCurrent(canvas);
            }
        }
    }

    /*
     * PAUSE APP:
     * This method is called by device's OS in some situations where is needed to
     * pause the game. Can also can be called mechanicaly, e.g. Force pause with
     * "Device unnavailable", but is desirable to call, in this cases, directly
     * hide and showNotify methods.
     */
    protected void pauseApp()
    {
        // In these methods will define all stuff we need to do when pause and
        // unpause the game.
        canvas.hideNotify();
        canvas.showNotify();
    }

    /*
     * DESTROY APP:
     * This method is called when we want to close the application. Will be here
     * where we will stop the main thread, clean the memory with our marvelous
     * Garbage collector.
     * Also, we call the notifyDestroyed method, which sends to the device's OS a
     * message asking to destroy every already running stuff, like sound. However,
     * some Devices don't have a decent and respectable OS, so sometimes we'll need
     * to shut down these functions by hand. =/
     */
    protected void destroyApp(boolean b)
    {
        if (canvas != null)
        {
            mainThread = null;
            System.gc();
        }

        canvas = null;
        notifyDestroyed();
    }
}
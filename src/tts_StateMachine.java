// TTS STATE MACHINE

/**
 * So, finally, we are here, in the state machine controller. This can be very tricky
 * to understand, but is very simple.
 *
 * This is a extremely basic implementation of a state machine. Here we have only
 * one state, the currentState. To go to another state, we acces this class staticaly,
 * probably in the logic() implementation of our currentState.
 *
 * So, how does this works? Very simple:
 * Each state it's an specification of tts_State abstract class. So, each one tts_StateX class
 * will have at last two very important methods: logic() and paint().
 * In the tts_Canvas, we have the very important run() and paint() methods. The run method, after doing
 * his own logic to keep the game running, access the logic() method of our beloved currentState.
 * In an analog manner, we do this to paint() method: We access the paint() method of currentState.
 *
 * In this framework example, we start our game and set the state to an tts_StateLogo instance, created
 * on the tts_Canvas constructor. So, when the ttsCanvas run() and paint() methods were called, we'll be
 * calling, actually, the logic() and paint() methods of tts_StateLogo. In annalog manner, this will occurs
 * to every other tts_StateX.
 *
 * And how we change the state?
 * Well, this we'll do on the logic() method for each tts_StateX. Again, in the case of tts_StateLogo, after
 * the game has reached the logo time limit, the logic() function access staticly this tts_StateMachine class,
 * using the method changeState and passing as argument an tts_StateTest instance. Very easy :)
 *
 * Another important thing to explain here is the loadState. At any point of our code, we can call the loading
 * methods to show/update the loading screen.
 *
 * How does this works. Again, very simple.
 * Probably you will load some data to your state or in the very end of your current state or in the very beggining
 * of the new state (probably in the constructor) and you don't want to show a blank screen or something like that to
 * the user. To avoid that, you just need to use the loading methods like this:
 *
 * 1) Call statically the method setLoading(): This will create an instance to the tts_Loading class and set the
 *    boolean "isLoading" to true. When this boolean is true, instead of using the current State paint method on Canvas,
 *    the game will call the tts_Loading paint method, drawing on screen our loading screen.
 *
 * 2) Update the loading counter  while you're loading your stuff with updateLoading(): The updateLoading method
 *    needs an integer between 0 and 100 which represents the current percentage of your loading step. It's up to
 *    you to set the correct state of loading.
 *
 * 3) After loading's done, return to your normal currentState with unsetLoading(): This method will call the tts_Loading
 *    destroy method, to clean all memory used. Also, sets the loadStat to null and the isLoading boolean to false,
 *    returning the paint to the currentState.
 *
 */

public class tts_StateMachine
{
    public static tts_State currentState = null;
    public static tts_Loading loadState = null;
    private static boolean isLoading = false;

    public static void changeState(tts_State state)
    {
        try
        {
            currentState.destroy();

            currentState = null;
            System.gc();
        }
        catch(Exception ex)
        {

        }
        currentState = state;
    }

    /**
     * LOADING STATE METHODS
     */
    public static boolean isLoading()
    {
        return isLoading;
    }

    public static void setLoading()
    {
        if(!isLoading)
        {
            if(loadState == null)
            {
                loadState = new tts_Loading();
                isLoading = true;
            }
        }
    }

    public static void updateLoading(int percentage)
    {
        if(isLoading)
        {
            loadState.updateLoad(percentage);
        }
    }

    public static void unsetLoading()
    {
        if(isLoading)
        {
            loadState.destroy();
            loadState = null;
            isLoading = false;
        }
    }
}

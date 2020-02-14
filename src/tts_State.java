// TTS STATE

/**
 * This is the abstract class for our state class.
 *
 * This will indicate what methods all of tts_State"X" classes will need to implement.
 */

import javax.microedition.lcdui.Graphics;

public abstract class tts_State
{
    public tts_State(){}
    protected abstract boolean logic();
    protected abstract void paint(Graphics g);
    protected abstract void pause();
    protected abstract void unpause();
    protected abstract void destroy();
}

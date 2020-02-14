//TTS STATE TEST

import javax.microedition.lcdui.Graphics;

public class tts_StateTest extends tts_State
{
    //gamelogic test
    private String gameString = "";
    private int counter = 0;

    //we'll not use that here
    protected void pause(){}
    protected void unpause(){}
    protected void destroy(){}

    public tts_StateTest()
    {
    }

    protected boolean logic()
    {
        switch(tts_Canvas.input.ingameAction())
        {
            case tts_Input.INGAME_UP:
                gameString = "UP: " + counter++;
                return true;
            case tts_Input.INGAME_DOWN:
                gameString = "DOWN: " + counter++;
                return true;
            case tts_Input.INGAME_LEFT:
                gameString = "LEFT: " + counter++;
                return true;
            case tts_Input.INGAME_RIGHT:
                gameString = "RIGHT: " + counter++;
                return true;
            case tts_Input.INGAME_SOFT1:
                gameString = "SOFT 1: " + counter++;
                return true;
            case tts_Input.INGAME_SOFT2:
                gameString = "SOFT 2: " + counter++;
                return true;
            case tts_Input.INGAME_SELECT:
                gameString = "SELECT: " + counter++;
                return false;
            default:
                gameString = "UNKNOWN/NONE ACTION";
                counter = 0;
                return true;
        }
    }

    protected void paint(Graphics g)
    {
        g.setColor( 0x000000 );
        g.fillRect( 0, 0, tts_Canvas.canvasWidth, tts_Canvas.canvasHeight );
        g.setColor( 0x00ff00 );
        g.drawString( "Press keys to change string", tts_Canvas.canvasWidth>>1, 1, Graphics.HCENTER | Graphics.TOP );
        g.drawString( "Press 5/OK to exit", tts_Canvas.canvasWidth>>1, 20, Graphics.HCENTER | Graphics.TOP );

        //Gamelogic string
        g.drawString( gameString, tts_Canvas.canvasWidth>>1, tts_Canvas.canvasHeight>>1, Graphics.HCENTER | Graphics.TOP );

        //last key pressed keyCode
        g.drawString( "Last key pressed: " + tts_Canvas.input.lastKey(), tts_Canvas.canvasWidth>>1, tts_Canvas.canvasHeight - 1, Graphics.HCENTER | Graphics.BOTTOM );
    }
}

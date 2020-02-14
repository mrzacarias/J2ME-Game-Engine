//TTS STATE LOGO

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class tts_StateLogo extends tts_State
{
    private final long LOGO_START_TIME = 300;
    private final long LOGO_FINISH_TIME = 1800;
    private final long CHANGE_STATE_TIME = 2100;

    /**
     * Logo Image
     */
    private Image logoImage;

    /**
     * Time when the logo state was called
     */
    private long initTime = 0;

    /**
     * Variable to control the logo draw
     */
    private boolean drawLogo = false;

    //we'll not use that here
    protected void pause(){}
    protected void unpause(){}

    public tts_StateLogo(long initTime)
    {
        this.initTime = initTime;
        
        try
        {
            logoImage = Image.createImage( "/ttd.png" );
        }
        catch ( IOException ex )
        {
            System.out.println( "TTD image not found." );
        }
    }

    protected boolean logic()
    {
        long diffTime = System.currentTimeMillis() - initTime;

        if(diffTime < LOGO_START_TIME)
        {
            if(drawLogo)
            {
                drawLogo = false;
            }
        }
        else if( diffTime >= LOGO_START_TIME && diffTime < LOGO_FINISH_TIME )
        {
            if(!drawLogo)
            {
                drawLogo = true;
            }
        }
        else if(diffTime >= LOGO_FINISH_TIME && diffTime < CHANGE_STATE_TIME)
        {
            if(drawLogo)
            {
                drawLogo = false;
            }
        }
        else
        {
            //testing here the load state
            //tts_StateMachine.changeState( new tts_StateTest() );
            tts_StateMachine.changeState( new tts_StateGame() );
        }

        return true;
    }

    protected void paint(Graphics g)
    {
        g.setColor( 0xffffff );
        g.fillRect( 0, 0, tts_Canvas.canvasWidth, tts_Canvas.canvasHeight );

        if(drawLogo)
        {
            g.drawImage( logoImage, tts_Canvas.canvasWidth>>1, (tts_Canvas.canvasHeight - logoImage.getHeight())>>1, Graphics.HCENTER | Graphics.TOP );
        }
    }

    protected void destroy()
    {
        logoImage = null;
    }

}

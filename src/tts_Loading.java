//TTS LOADING

/**
 * This is the loading class. Works like an tts_State overloaded class, like the example class tts_StateGame and
 * tts_StateLogo, but have some minor changes.
 *
 * First, we don't have here the pause() and unpause() methods, because we don't pause a game on loading screen.
 * Second, we don't have also a logic() method. Why? Well, works like that.
 *
 * When we call the method setLoading(), we are still runiing the logic() method from the currentState. We do that
 * to still update the loading state directly from the currentState. For example, in the tts_StateGame class, when
 * we start the initializing methods, we call loading like this:
 *
 *      tts_StateMachine.setLoading();
 *      tts_Canvas.soundHandler.loadSound( "main.mid", true );
 *      tts_StateMachine.updateLoading( 33 );
 *      playerInit();
 *      tts_StateMachine.updateLoading( 66 );
 *      fishInit();
 *      tts_StateMachine.updateLoading( 100 );
 *      tts_StateMachine.unsetLoading();
 *
 * As you can see, while we're loading our data and setting the ingame variables, we are updating our loading. While
 * this are happening, the paint() method that's being called, instead of the currentState paint() method, it's the
 * tts_Loading paint().
 *
 * In other words, when you set the loading, all the logic will be made by the currentState, but what the user will
 * see is the tts_Loading paint() method.
 */

import javax.microedition.lcdui.Graphics;

//#if LOAD_SPLASH_IMAGE
//# import java.io.IOException;
//# import javax.microedition.lcdui.Image;
//#endif

public class tts_Loading
{
    private int currentLoad;
    private final int BAR_HEIGHT = 10;

    //#if LOAD_SPLASH_IMAGE
//#     private Image splashLoad = null;
    //#endif

    public tts_Loading()
    {
        currentLoad = 0;

        //#if LOAD_SPLASH_IMAGE
//#         try
//#         {
//#             splashLoad = Image.createImage( "/splashTTS.png" );
//#         }
//#         catch ( IOException ex )
//#         {
//#             System.out.println( "Splash Load image not found" );
//#         }
        //#endif
    }

    protected void updateLoad(int percentage)
    {
        percentage = tts_Math.boundedInt( percentage, 0, 100 );
        
        currentLoad = percentage;
    }

    protected void paint(Graphics g)
    {
        //background
        g.setColor( 0x000000 );
        g.fillRect( 0, 0, tts_Canvas.canvasWidth, tts_Canvas.canvasHeight );

        //#if LOAD_SPLASH_IMAGE
//#         //Splash Image
//#         g.drawImage( splashLoad, tts_Canvas.canvasWidth>>1, (tts_Canvas.canvasHeight - splashLoad.getHeight() - 20)>>1, Graphics.HCENTER | Graphics.TOP );
//# 
//#         //Draw loading message
//#         g.setColor(0xaaaaaa);
//#         g.drawString( "Loading...", tts_Canvas.canvasWidth>>1, (tts_Canvas.canvasHeight + splashLoad.getHeight() + 20)>>1, Graphics.HCENTER | Graphics.TOP );
        //#else
        //Draw loading message
        g.setColor(0xaaaaaa);
        g.drawString( "Loading...", tts_Canvas.canvasWidth>>1, (tts_Canvas.canvasHeight)>>1, Graphics.HCENTER | Graphics.BOTTOM );
        //#endif
        
        //bar background
        g.setColor( 0xaaaaaa );
        g.fillRect( 0, tts_Canvas.canvasHeight - BAR_HEIGHT, tts_Canvas.canvasWidth, BAR_HEIGHT );

        //bar
        g.setColor( 0xffffff );
        g.fillRect( 0, tts_Canvas.canvasHeight - BAR_HEIGHT, (tts_Canvas.canvasWidth * currentLoad)/100, BAR_HEIGHT );
    }

    protected void destroy()
    {
        //#if LOAD_SPLASH_IMAGE
//#         try
//#         {
//#             splashLoad = null;
//#             System.gc();
//#         }
//#         catch (Exception ex)
//#         {
//#             System.out.println( "Error while cleaning load" );
//#         }
        //#endif
    }
}

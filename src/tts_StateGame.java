//TTS STATE GAME

/**
 * This is an ingame State example using our framework. Here is where the ingame logic and paint
 * will be executed.
 */

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * The tts_Game class. This class extends tts_State.
 *
 */
public class tts_StateGame extends tts_State
{
    /**
     * Some constants
     */
    
    //Status bar
    private final int STATUS_BAR_HEIGHT = 21;
    
    //Player speed (pixels/frame)
    private final int PLAYER_SPEED = 4;
    
    //Game variables for time (*1000)
    private final int HUNGER_TIME = 1600;
    private final int FISH_TIME = 1500;

    //Frame Counter
    private int hungerFrames = 0;
    private int fishFrames = 0;
    
    //franchi image - this is the player character image
    private Image playerImg = null;
    private int playerSize;
    
    //hunger control
    private int playerX, playerY;
    private int playerHunger;
    
    //fish image
    private Image fishImg = null;
    private int fishSize;

    //fish control
    private int fishX, fishY;
    private boolean eated;
    private long fishRandomDelta;

    //we'll not use that here (yet)
    protected void pause(){}
    protected void unpause(){}

    public tts_StateGame()
    {
        tts_StateMachine.setLoading();

        tts_Canvas.soundHandler.loadSound( "main.mid", true );
        tts_StateMachine.updateLoading( 20 );

        playerInit();

        fishInit();
        
        //RANDOM SETTINGS
        tts_Utils.setSeed( System.currentTimeMillis() );
        fishRandomDelta = tts_Utils.nextRandomInt( 1000 );
        setFishPos();

        tts_StateMachine.unsetLoading();

        tts_Canvas.soundHandler.play();
    }

    protected boolean logic()
    {
        boolean ret = true;

        if(playerHunger > 0)
        {
            //Input handler
            ret = doInput();

            doPlayerLogic();

            doFishLogic();
        }
        else
        {
            if(tts_Canvas.input.ingameAction() == tts_Input.INGAME_SELECT)
            {
                ret = false;
            }
        }

        return ret;
    }

    private void playerInit()
    {
        //PLAYER INITIALIZATION
        try
        {
            playerImg = Image.createImage( "/franchi_bote.png" );
        }
        catch ( IOException ex )
        {
            System.out.println( "franchi_bote.png image not loaded." );
        }

        tts_StateMachine.updateLoading( 40 );
        playerSize = playerImg.getHeight();
        playerX = (tts_Canvas.canvasWidth  - playerSize)>>1;
        playerY = (tts_Canvas.canvasHeight - playerSize)>>1;

        tts_StateMachine.updateLoading( 60 );
        playerHunger = 100;
    }

    private void fishInit()
    {
        tts_StateMachine.updateLoading( 80 );

        //FISH INITIALIZATION
        try
        {
            fishImg = Image.createImage( "/fish.png" );
        }
        catch ( IOException ex )
        {
            System.out.println( "fish.png image not loaded." );
        }

        tts_StateMachine.updateLoading( 100 );
        fishSize = fishImg.getHeight();
        eated = true;
    }

    private int frames2Mili(int frames)
    {
        return (frames/tts_Canvas.MAX_FRAMES)*1000;
    }

    private void doPlayerLogic()
    {
        hungerFrames++;

        //player logic
        if(frames2Mili(hungerFrames) >= HUNGER_TIME)
        {
            playerHunger -= 10;
            hungerFrames = 0;
        }

        //sound
        if(playerHunger <= 0)
        {
            tts_Canvas.soundHandler.unloadSound();
            tts_Canvas.soundHandler.loadSound( "over.mid", false );
            tts_Canvas.soundHandler.play();
        }
    }

    private void doFishLogic()
    {
        fishFrames++;

        //fishLogic
        if(frames2Mili(hungerFrames) > FISH_TIME + fishRandomDelta)
        {
            setFishPos();
            fishRandomDelta = tts_Utils.nextRandomInt( 1000 );
            eated = false;
            fishFrames = 0;
        }

        if(!eated)
        {
            if(eat())
            {
                eated = true;
                playerHunger += 15;
                if(playerHunger > 100)
                {
                    playerHunger = 100;
                }
            }
        }
    }

    private void setFishPos()
    {
        //generate a fish position inside canvas
        fishX = tts_Utils.nextRandomInt(240 - fishSize);
        fishY = tts_Utils.nextRandomInt(320 - fishSize - STATUS_BAR_HEIGHT) + STATUS_BAR_HEIGHT;

        //check if it is not inside player space
        while(eat())
        {
            fishX = tts_Utils.nextRandomInt(240 - fishSize);
            fishY = tts_Utils.nextRandomInt(320 - fishSize - STATUS_BAR_HEIGHT) + STATUS_BAR_HEIGHT;
        }
    }

    private boolean eat()
    {
        //check if player "eated" the fish
        if(playerX + playerSize >= fishX && playerX <= fishX + fishSize &&
           playerY + playerSize >= fishY && playerY <= fishY + fishSize)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean doInput()
    {
        /**
         * Every frame we'll check this
         */

        switch(tts_Canvas.input.ingameAction())
        {
            case tts_Input.INGAME_UP:
                if(playerY > STATUS_BAR_HEIGHT)
                {
                    playerY-= PLAYER_SPEED;
                }
                return true;
            case tts_Input.INGAME_DOWN:
                if(playerY + playerSize < tts_Canvas.canvasHeight)
                {
                    playerY+= PLAYER_SPEED;
                }
                return true;
            case tts_Input.INGAME_LEFT:
                if(playerX > 0)
                {
                    playerX-= PLAYER_SPEED;
                }
                return true;
            case tts_Input.INGAME_RIGHT:
                if(playerX + playerSize < tts_Canvas.canvasWidth)
                {
                    playerX+= PLAYER_SPEED;
                }
                return true;
            case tts_Input.INGAME_SOFT1:
                return true;
            case tts_Input.INGAME_SOFT2:
                return true;
            case tts_Input.INGAME_SELECT:
                if(tts_Canvas.soundHandler.getState() == tts_Sound.TTS_STARTED)
                {
                    tts_Canvas.soundHandler.stop();
                    try
                    {
                        Thread.sleep( 200 );
                    }
                    catch ( InterruptedException ex )
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    tts_Canvas.input.keyPressedPurge();
                    tts_Canvas.soundHandler.play();
                }
                return true;
            default:
                return true;
        }
    }

    protected void paint(Graphics g)
    {
        //draw background
        g.setColor( 0x0000ff );
        g.fillRect( 0, STATUS_BAR_HEIGHT, tts_Canvas.canvasWidth, tts_Canvas.canvasHeight );

        //draw the game status bar
        drawStatusBar(g);

        //drawPlayer
        g.drawImage( playerImg, playerX, playerY, Graphics.LEFT |  Graphics.TOP);

        //drawFish
        if(!eated)
        {
            g.drawImage( fishImg, fishX, fishY, Graphics.LEFT |  Graphics.TOP);
        }

        //draw dead message
        if(playerHunger <= 0)
        {
            g.setColor( 0x000000 );
            g.fillRect( tts_Canvas.canvasWidth/5, (tts_Canvas.canvasHeight>>1) - 20, tts_Canvas.canvasWidth*3/5, 40 );
            g.setColor( 0x00ff00 );
            g.drawRect( tts_Canvas.canvasWidth/5 + 1, (tts_Canvas.canvasHeight>>1) - 19, tts_Canvas.canvasWidth*3/5 - 2, 40 - 2 );

            g.drawString( "DEAD", tts_Canvas.canvasWidth>>1, (tts_Canvas.canvasHeight>>1) - 10, Graphics.HCENTER | Graphics.TOP );
        }
    }

    private void drawStatusBar(Graphics g)
    {
        g.setColor(0x000000);
        g.fillRect( 0, 0, tts_Canvas.canvasWidth, STATUS_BAR_HEIGHT );

        g.setColor(0x00ff00);
        g.drawString("HUNGER:", 1, 1, Graphics.LEFT | Graphics.TOP );
        
        if(playerHunger <= 20)
        {
            g.setColor(0xff0000);
        }
        else if (playerHunger > 20 && playerHunger <= 60)
        {
            g.setColor(0xffff00);
        }
        else
        {
            g.setColor(0x00ff00);
        }

        g.fillRect(82, 4, ((tts_Canvas.canvasWidth - 84) * playerHunger) / 100, STATUS_BAR_HEIGHT - 6);
    }

    protected void destroy()
    {
        tts_Canvas.soundHandler.unloadSound();
        playerImg = null;
        fishImg = null;
    }
}

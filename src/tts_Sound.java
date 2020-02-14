//TTS SOUND

/**
 * And, finally, the Sound Class. :)
 *
 * This tts_Sound Class it's a basic class function that does exactly what a Sound Class needs
 * to do: Play and stop a F**** Sound =D
 *
 * I'm saying this because after a doing a lot of portings, it's evident that some great studios
 * don't have this in mind as teh highest priority. This class will do this, play and stop a midi
 * sound already loaded.
 *
 * It's quite simple, but here goes the methods explanation:
 *
 * Contructor tts_Sound:
 * Just sets the variables to an initial state.
 *
 * loadSound(String m, boolean loop):
 * Here it's where we create the player and load the "m" music on it. This will be thee music that
 * will be played by play() method.
 * The boolean (loop) will indicate if the music will repeat ad infinitum. (Main menu music, anyone?)
 *
 * unloadSound():
 * This method will reset the player to null, also unload the current music and will reset the main
 * variables.
 *
 * play():
 * Straightfoward method, will just play the current loaded music. Has some protection when called
 * with no music loaded, but it's highly recommended to call this once, not in a logic loop.
 *
 * stop():
 * Stop the music :)
 */

import java.io.*;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

//#if VOLUME_CONTROL
//# import javax.microedition.media.control.VolumeControl;
//#endif

public class tts_Sound implements PlayerListener
{
    /*
     * Constants to return player state
     */
    public static final int TTS_PREFETCHED = 0;
    public static final int TTS_CLOSED = 1;
    public static final int TTS_REALIZED = 2;
    public static final int TTS_STARTED = 3;
    public static final int TTS_TIME_UNKNOWN = 4;
    public static final int TTS_UNREALIZED = 5;

    private InputStream is = null;
    private Player p;

    private String music;
    private boolean bLoop = false;
    
    private String playerEvent;

    //#if VOLUME_CONTROL
//#     private final int MAX_VOLUME = 50;
    //#endif

    /**
     * Constructor
     */
    public tts_Sound()
    {
        p = null;
        bLoop = false;
        music = "";
    }

//*************************************************************************************************
// LOADING AND UNLOADING METHODS
//*************************************************************************************************
    public void loadSound(String m, boolean loop)
    {
        if(music.equals(m))
        {
            return;
        }

        if(p != null)
        {
            unloadSound();
        }

        music = m;
        bLoop = loop;

        try
        {
            is = getClass().getResourceAsStream( "/" + music );
            p = Manager.createPlayer(is, "audio/midi");
            p.prefetch();
            
            System.out.println( "Player Created, music " + music + " loaded" );

            is.close();
            is = null;
        }
        catch (Exception ex)
        {
            System.out.println( "Could not load " + music );
        }

        try
        {
            p.addPlayerListener(this);
        }
        catch (Exception ex)
        {
            System.out.println( "Could not set this as playerListener" );
        }
    }

    public void unloadSound()
    {
        if(p == null)
        {
            return;
        }

        music = "";
        bLoop = false;

        try
        {
            p.stop();
            p.deallocate();
            p.close();
            p = null;

            System.out.println( "Player Deleted, music unloaded" );
        }
        catch(Exception ex)
        {
            System.out.println( "Could not unload sound" );
        }
    }

//*************************************************************************************************
// MUSIC CONTROL
//*************************************************************************************************
    public void play()
    {
        if(p == null)
        {
            System.out.println( "Player not initialized." );
            return;
        }

        try
        {
            p.prefetch();
            p.deallocate();
            p.realize();

            //#if VOLUME_CONTROL
//#             VolumeControl volControl = (VolumeControl)p.getControl( "VolumeControl");
//#             if( volControl != null && volControl.getLevel() > MAX_VOLUME )
//#             {
//#                 volControl.setLevel( MAX_VOLUME );
//#             }
            //#endif

            //#if !FORCE_LOOP
            if(bLoop)
            {
                p.setLoopCount(999);
            }
            //#endif

            p.start();

        }
        catch (Exception ex)
        {
            System.out.println( "Could not play " + music );
        }
    }

    public void stop()
    {
        try
        {
            int state = p.getState();
            
            if( state == p.STARTED )
            {
                p.stop();
                System.out.println( "Music " + music + " Stopped." );
            }
        }
        catch (Exception ex)
        {
            System.out.println( "Could not stop " + music );
        }
    }

//*************************************************************************************************
// ANOTHER METHODS
//*************************************************************************************************
    public void playerUpdate(Player player, String event, Object o )
    {
        //#if FORCE_LOOP
//#         if(event.equals( PlayerListener.END_OF_MEDIA ) && bLoop)
//#         {
//#             try
//#             {
//#                 p.deallocate();
//#                 p.realize();
//#                 play();
//#             }
//#             catch ( Exception ex )
//#             {
//#                 System.out.println( "Could not play " + music + " again." );
//#             }
//#         }
        //#endif
        playerEvent = event;
    }

    public int getState()
    {
        switch(p.getState())
        {
            case Player.PREFETCHED:
                return TTS_PREFETCHED;
            case Player.CLOSED:
                return TTS_CLOSED;
            case Player.REALIZED:
                return TTS_REALIZED;
            case Player.STARTED:
                return TTS_STARTED;
            case (int)Player.TIME_UNKNOWN:
                return TTS_TIME_UNKNOWN;
            case Player.UNREALIZED:
                return TTS_UNREALIZED;
        }

        return p.getState();
    }

    public String getLastEvent()
    {
        return playerEvent;
    }

    public String getMusic()
    {
        return music;
    }
}

package multiplayer;

import controller.Listeners;
import model.Character;
import model.Level;
import model.Textures;

import java.net.InetAddress;

/**
 * Player class that creates character for multiplayer.
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 * @see Character
 */

public class PlayerMP extends Character{
    public InetAddress ipAddress;
    public int port;


    public PlayerMP(Level level, int x, int y, String username,InetAddress ipAddress, int port){
        super(level, x, y, null, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(Level level, int x, int y, Listeners listeners, String username,  InetAddress ipAddress, int port){
        super(level, x, y, listeners, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void updateInfo(){
        super.updateInfo();
    }
}

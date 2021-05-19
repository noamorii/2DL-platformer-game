package model;

import java.util.logging.Logger;

/**
 * Key class lets user unlock ability to finish level
 *
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 * @see Textures
 */
public class Key extends Textures{

    private boolean isTaken;
    private static final Logger logger = Logger.getLogger("model.Key");

    public Key(int x, int y) {
        super("key.png", 48, 48, false);
        xPos = x;
        yPos = y;
        isTaken = false;
        logger.info("Key created");
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean getTakenStatus() {
        return isTaken;
    }
}

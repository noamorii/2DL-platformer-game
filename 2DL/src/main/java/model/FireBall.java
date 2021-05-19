package model;

import controller.Settings;

import java.awt.*;

/**
 * Fireball class lets user attack enemies with fireball
 *
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 * @see Textures
 */
public class FireBall extends Textures{
    private static int count;
    private final boolean throwRight;
    private boolean expired;
    private final double initialX;

    public FireBall(double playerX, double playerY, boolean throwRight) {
        super("fireball.gif", 120, 120, false);
        xPos = playerX + ((throwRight) ? Settings.characterHitBox - 40 : Settings.characterHitBox -40);
        yPos = playerY - 10;
        this.throwRight = throwRight;
        this.expired = false;
        count = 4;
        this.initialX = xPos;

    }

    /**
     * Method that updates the state of fireball.
     * @param g - texture object
     */
    public void update(Graphics g) {
        if (throwRight) {
            xPos += 15;
        } else {
            xPos -= 15;
        }

        super.update(g);

        if (Math.abs(xPos - initialX) >= Settings.fireballExpireDist) {
            removeFireBall();
        }
    }

    public static int getCount() {
        return count;
    }

    public boolean isExpired() {
        return expired;
    }

    public void removeFireBall() {
        count--;
        expired = true;
    }
}

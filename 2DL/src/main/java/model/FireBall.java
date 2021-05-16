package model;

import controller.Settings;

import java.awt.*;

public class FireBall extends Textures{
    private static int count;
    private boolean throwRight;
    private boolean expired;
    private double initialX;

    public FireBall(double playerX, double playerY, boolean throwRight) {
        super("fireball.gif", 120, 120, false);
        xPos = playerX + ((throwRight) ? Settings.characterHitBox - 40 : Settings.characterHitBox -40);
        yPos = playerY - 10; // throw from arm height, not head height
        this.throwRight = throwRight;
        this.expired = false;
        count = 4;
        this.initialX = xPos;


    }

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

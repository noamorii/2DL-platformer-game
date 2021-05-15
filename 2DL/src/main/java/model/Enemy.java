package model;

import javax.swing.*;
import java.awt.*;
import controller.Settings;


public class Enemy extends Textures {
    private static Character player;
    private boolean dead;
    private boolean attackingLeft;
    private boolean attackingRight;
    private double xPosBeforeAttack;
    private double yPosBeforeAttack;

    public static void setPlayer(Character p) {
        player = p;
    }

    public Enemy(int x, int y, int roamDist) {
        super("", 96, 96, false);
        xPos = x;
        yPos = y;

        dead = false;
    }

    public void kill() {
        if (isAttacking()) {
            resetFromAttack();
        }
        dead = true;

    }

    public boolean isDead() {
        return dead;
    }

    public boolean isAttacking() {
        return attackingLeft || attackingRight;
    }

    private boolean playerOnSameLevel() {
        // Player's head is above skeleton's head by less than 60 pixels
        return player.getY() + Settings.characterHitBoxTop - yPos > 0 && player.getY() - yPos < 60;
    }

    private boolean playerInAttackDistance() {
        return player != null && playerOnSameLevel() && (Math.abs(player.getX() + player.getWidth() - Settings.characterHitBox - xPos) <= Settings.enemyAttackDist
                || Math.abs(player.getX() + Settings.characterHitBox - (xPos + imageWidth)) <= Settings.enemyAttackDist);
    }

    private void walkRight() {
        xPos += 2;
    }

    private void walkLeft() {
        xPos -= 2;
    }

    private void attackLeft() {
        xPosBeforeAttack = xPos;
        yPosBeforeAttack = yPos;


        //attack here
        imageWidth = 96;
        imageHeight = 96;

        xPos -= 15;
    }

    private void attackRight() {
        xPosBeforeAttack = xPos;
        yPosBeforeAttack = yPos;

        //attack here
        imageWidth = 96;
        imageHeight = 96;

        xPos += 15;
    }

    private void resetFromAttack() {
        imageWidth = 96;
        imageHeight = 96;
        xPos = xPosBeforeAttack;
        yPos = yPosBeforeAttack;

        attackingRight = false;
        attackingLeft = false;

    }

    public void update(Graphics g) {
        if (!dead) {
            if (isAttacking()) {
                    resetFromAttack();
                    update(g);
                    return;
                } else {
                    if (attackingLeft) {
                        image = new ImageIcon(Settings.assetDirectory + "sattack_left.gif").getImage();
                    } else {
                        image = new ImageIcon(Settings.assetDirectory + "sattack_right.gif").getImage();
                    }
                }
                    player.kill();
                }
        super.update(g);
    }
}
package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.Settings;

public class Enemy extends Textures {
    private static Character player;
    private boolean dead;
    private boolean attackingLeft;
    private boolean attackingRight;
    private int framesSinceDeath;
    private int framesSinceAttack;
    private boolean walkingRight;
    private double maxPos;
    public static int hp;
    private double minPos;
    private double xPosBeforeAttack;
    private double yPosBeforeAttack;

    public static void setPlayer(Character p) {
        player = p;
    }

    public Enemy(int x, int y, int roamDist) {
        super("npc_walking_right.gif", 96, 96, false);
        xPos = x;
        yPos = y;
        hp = 3;
        maxPos = xPos + roamDist;
        minPos = xPos - roamDist;
        dead = false;
        attackingLeft = false;
        attackingRight = false;
        walkingRight = true;
        framesSinceDeath = 3;
    }

    public void kill() {
//        if (hp != 1){
//            hp--;
//        }else {
        if (isAttacking()) {
            resetFromAttack();
        }
        image = walkingRight ? new ImageIcon(Settings.assetDirectory + "dying_left.gif").getImage() : new ImageIcon(Settings.assetDirectory + "dying_right.gif").getImage();
        dead = true;
        if (walkingRight) {
            xPos -= 20;
        }
        hp = 3;
//        }
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isAttacking() {
        return attackingLeft || attackingRight;
    }

    public int getFramesSinceDeath() {
        return framesSinceDeath;
    }

    public void incrementFramesSinceDeath() {
        if (walkingRight) {
            image = new ImageIcon(Settings.assetDirectory + "dying_right.gif").getImage();
        } else {
            image = new ImageIcon(Settings.assetDirectory + "dying_left.gif").getImage();
        } framesSinceDeath += 1;
    }

    private boolean playerOnSameLevel() {
        // Player's head is above skeleton's head by less than 60 pixels
        return player.getY() + Settings.characterHitBoxTop - yPos > 0 && player.getY() - yPos < 60;
    }

    private boolean playerInAttackDistance() {
        return player != null && playerOnSameLevel() && (Math.abs(player.getX() + player.getWidth() - Settings.characterHitBox - xPos) <= Settings.enemyAttackDist
                || Math.abs(player.getX() + Settings.characterHitBox - (xPos + imageWidth)) <= Settings.enemyAttackDist);
    }

    private void turnLeft() {
        walkingRight = false;
        image = new ImageIcon(Settings.assetDirectory + "npc_walking_left.gif").getImage();
    }

    private void turnRight() {
        walkingRight = true;
        image = new ImageIcon(Settings.assetDirectory + "npc_walking_right.gif").getImage();
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
        attackingLeft = true;
        imageWidth = 96;
        imageHeight = 96;

        xPos -= 15;

    }

    private void attackRight() {
        xPosBeforeAttack = xPos;
        yPosBeforeAttack = yPos;


        //attack here
        attackingRight = true;
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
        if (walkingRight) {
            image = new ImageIcon(Settings.assetDirectory + "npc_walking_right.gif").getImage();
        } else {
            image = new ImageIcon(Settings.assetDirectory + "npc_walking_left.gif").getImage();
        }
    }

    public void update(Graphics g) {
        if (!dead) {
            Font skeleton = new Font("Courier New", Font.BOLD, 24);
            g.setFont(skeleton);
            g.setColor(Color.black);

            Image h3 = new ImageIcon(Settings.assetDirectory + "h3.png").getImage();
            Image h2 = new ImageIcon(Settings.assetDirectory + "h2.png").getImage();
            Image h1 = new ImageIcon(Settings.assetDirectory + "h1.png").getImage();
            Image h0 = new ImageIcon(Settings.assetDirectory + "h0.png").getImage();

            if (hp == 3) {
                g.drawImage(h3, (int)xPos + 35, (int)yPos - 10, 25, 22, null);
            } else if (hp == 2) {
                g.drawImage(h2, (int)xPos + 35, (int)yPos - 10, 25, 22, null);
            } else if (hp == 1) {
                g.drawImage(h1, (int)xPos + 35, (int)yPos - 10, 25, 22, null);
            } else {
                g.drawImage(h0, (int)xPos + 35, (int)yPos - 10, 25, 22, null);
            }

//            g.drawString("" + hp, (int)xPos + 20, (int)yPos);
            if (isAttacking()) {
                if (framesSinceAttack == Settings.enemyAttackDist) {
                    resetFromAttack();
                    update(g);
                    return;
                } else {
                    if (attackingLeft) {
                        image = new ImageIcon(Settings.assetDirectory + "sattack_left.gif").getImage();
                    } else {
                        image = new ImageIcon(Settings.assetDirectory + "sattack_right.gif").getImage();
                    }
                    framesSinceAttack++;
                }

                if (framesSinceAttack == 15 && overlaps(player)) { //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    if (Character.hp > 1) {
                        Character.hp--;
                    } else {
                        player.kill();
                    }
                }
            } else if (playerInAttackDistance()) {
                if (player.getX() <= xPos) {
                    attackLeft();
                } else {
                    attackRight();
                }
                framesSinceAttack = 0;
            } else if (walkingRight && xPos < maxPos) {
                walkRight();
            } else if (walkingRight && xPos >= maxPos) {
                turnLeft();
                walkLeft();
            } else if (!walkingRight && xPos > minPos) {
                walkLeft();
            } else if (!walkingRight && xPos <= minPos) {
                turnRight();
                walkRight();
            }
        }
        super.update(g);
    }

    @Override
    public void loadImage() {
        if (dead) {
            if (walkingRight) {
                image = new ImageIcon(Settings.assetDirectory + "dying_right.gif").getImage();
            } else {
                image = new ImageIcon(Settings.assetDirectory + "dying_left.gif").getImage();
            }
        } else if (attackingRight) {
            image = new ImageIcon(Settings.assetDirectory + "sattack_right.gif").getImage();
        } else if (attackingLeft) {
            image = new ImageIcon(Settings.assetDirectory + "sattack_left.gif").getImage();
        } else if (walkingRight) {
            image = new ImageIcon(Settings.assetDirectory + "npc_walking_right.gif").getImage();
        } else {
            image = new ImageIcon(Settings.assetDirectory + "npc_walking_left.gif").getImage();
        }
    }
}
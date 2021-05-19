package model;

import controller.Settings;
import javax.swing.*;
import java.util.logging.Logger;

/**
 *  Character class represents a character from texture.
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 * @see Textures
 */
public class Character extends Textures{

    private double prev_X;
    private double prev_Y;
    private static int hp;
    private static boolean hasKey;
    private static final int accel = 3;
    private int velocity;
    private Settings.VelocityState velocityState;
    private boolean inAir;
    private boolean hasDoubleJump;
    private boolean facingRight;
    private boolean dead;
    Level level1;

    private static final Logger logger = Logger.getLogger("model.Character");

    /**
     * Basic constructor for Character class.
     * @param level - represents Level class
     * @param xPos1 - character position by x
     * @param yPos1 - character position by y
     */

    public Character(Level level, int xPos1, int yPos1) {

        super("player_right.png", Settings.characterSize, Settings.characterSize, false);
        velocity = 0;
        level1 = level;
        hasKey = false;
        hp = 3;
        inAir = true;
        hasDoubleJump = true;
        facingRight = true;
        dead = false;
        xPos = xPos1;
        yPos = yPos1;
        logger.info("Character created");

    }

    public static int getHp() {
        return hp;
    }


    public static boolean getKeyStatus() {
        return hasKey;
    }

    public void setKeyStatus(boolean status) {
        hasKey = status;
    }

    /**
     * Calls when character get damage.
     */
    public void getDamage() {
        if (hp > 1) {
            hp--;
        } else {
            kill();
        }
        logger.info("Player was damaged");
    }

    public int getVelocity() {

        return this.velocity;
    }

    public void setVelocity(int vel) {
        velocity = vel;}

    public boolean isAirborne() {

        return inAir;
    }

    public void setDoubleJump(boolean doubleJump) {
        hasDoubleJump = doubleJump; }

    public boolean isFacingRight() {

        return facingRight;
    }

    /**
     * Method that allows you to put the correct image for a character in air and not.
     * @param inAir - jump status of the Character
     */
    public void setAirborne(boolean inAir) {
        this.inAir = inAir;

        if (inAir) {

            if (velocityState == Settings.VelocityState.LEFT) {
                image = new ImageIcon(Settings.assetDirectory + "player_jump_left.gif").getImage();
                facingRight = false;
            } else if (velocityState == Settings.VelocityState.RIGHT) {
                image = new ImageIcon(Settings.assetDirectory + "player_jump_right.gif").getImage();
                facingRight = true;
            }
        } else {
            if (velocityState == Settings.VelocityState.LEFT) {
                image = new ImageIcon(Settings.assetDirectory + "player_walk_left.gif").getImage();
                facingRight = false;
            } else if (velocityState == Settings.VelocityState.RIGHT) {
                image = new ImageIcon(Settings.assetDirectory + "player_walk_right.gif").getImage();
                facingRight = true;
            }
        }
    }

    /**
     * Check if character has doublejump, configure velocity and set right jump animation.
     */
    public void jump() {

        if (hasDoubleJump) {
            if (inAir) {
                hasDoubleJump = false;
            }
            velocity = -22;
            inAir = true;

            if (facingRight) {
                image = new ImageIcon(Settings.assetDirectory + "player_jump_right.gif").getImage();
            } else{
                image = new ImageIcon(Settings.assetDirectory + "player_jump_left.gif").getImage();
            }
        }
    }

    /**
     * Display animation.
     * The method allows you to display the correct animation depending on the direction of the character and his position.
     * @param state - direction of the character
     */
    public void setDirection(Settings.VelocityState state) {
        if (state == Settings.VelocityState.LEFT) {
            if (inAir) {
                image = new ImageIcon(Settings.assetDirectory + "player_jump_left.gif").getImage();
            } else {
                image = new ImageIcon(Settings.assetDirectory + "player_walk_left.gif").getImage();
            }
            facingRight = false;
        } else if (state == Settings.VelocityState.RIGHT) {
            if (inAir) {
                image = new ImageIcon(Settings.assetDirectory + "player_jump_right.gif").getImage();
            } else {
                image = new ImageIcon(Settings.assetDirectory + "player_walk_right.gif").getImage();
            }
            facingRight = true;
        } else {
            // STILL
            if (velocityState == Settings.VelocityState.LEFT) {
                image = new ImageIcon(Settings.assetDirectory + "player_left.png").getImage();
                facingRight = false;
            } else if (velocityState == Settings.VelocityState.RIGHT) {
                image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
                facingRight = true;
            }
        }
        velocityState = state;
    }

    public void setStandingRightImage() {
        image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
    }

    public void setStandingLeftImage() {
        image = new ImageIcon(Settings.assetDirectory + "player_left.png").getImage();
    }

    public void kill() {
        dead = true;
        logger.info("Player was killed");
    }

    public boolean isDead() {
        return dead;
    }

    public Settings.VelocityState getVelocityState() {
        return velocityState;
    }

    public double getPrev_X() {
        return prev_X; }

    public double getPrev_Y() {
        return prev_Y;
    }

    public double getX() {
        return xPos; }

    public double getY() {
        return yPos;
    }


    /**
     * Updates character coords.
     */
    public void updateInfo() {

        // store previous coordinates for collisions logic
        prev_X = xPos;
        prev_Y = yPos;

        // update player info based on world physics
        if (velocityState == Settings.VelocityState.LEFT) {
            xPos -= Settings.characterSpeed;
        } else if (velocityState == Settings.VelocityState.RIGHT) {
            xPos += Settings.characterSpeed;
        }
        yPos += velocity;
        velocity += accel;

    }

    /**
     * Method to determine if two textures intersect.
     * @param tex - texture object
     * @return boolean value true if texture is overlapping the character
     */
    public boolean overlaps(Textures tex) {

        if (xPos + imageWidth - Settings.characterHitBox < tex.xPos) {
            return false;
        }
        if (xPos + Settings.characterHitBox > tex.xPos + tex.getWidth()) {
            return false;
        }
        if (yPos + imageHeight < tex.getY()) {
            return false;
        }
        return !(yPos + Settings.characterHitBoxTop > tex.getY() + tex.getHeight());
    }

    @Override
    public void saveImage() {
    }

    @Override
    public void loadImage() {
        image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
    }
}

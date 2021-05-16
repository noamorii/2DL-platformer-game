package model;

import controller.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Character extends Textures{

    private Level level;

    private double prev_X;
    private double prev_Y;
    public static int hp;
    private static final int accel = 3;
    private int velocity;
    private Settings.VelocityState velocityState;
    private transient ArrayList<Image> player_sprites;
    private boolean inAir;
    private boolean hasDoubleJump;
    private boolean facingRight;
    private boolean dead;
    private int imageIndex;

    public void loadImages() {

    }

    public Character() {

        super("player0.png", Settings.characterSize, Settings.characterSize, false);
        velocity = 0;
        hp = 3;
        inAir = true;
        hasDoubleJump = true;
        facingRight = true;
        dead = false;
        xPos = 90;
        yPos = 530;
        imageIndex = 0;
        loadImages();
    }

    public int getVelocity() {

        return this.velocity;
    }

    public void setVelocity(int vel) {
        velocity = vel;}

    public boolean isAirborne() {

        return inAir;
    }

    public boolean hasDoubleJump() {

        return hasDoubleJump;
    }

    public void setDoubleJump(boolean doubleJump) {
        hasDoubleJump = doubleJump; }

    public boolean isFacingRight() {

        return facingRight;
    }

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

    public void attack(){
        if (velocityState == Settings.VelocityState.LEFT){
            image = new ImageIcon(Settings.assetDirectory + "attack_left.gif").getImage();
        } else if (velocityState == Settings.VelocityState.RIGHT) {
            image = new ImageIcon(Settings.assetDirectory + "attack_right.gif").getImage();
        }
    }

    public void jump() {
        if (hasDoubleJump) {
            if (inAir) {
                hasDoubleJump = false;
            }
            velocity = -22;
            inAir = true;
            image = facingRight ? new ImageIcon(Settings.assetDirectory + "player_jump_right.gif").getImage() : new ImageIcon(Settings.assetDirectory + "player_jump_left.gif").getImage();

        }
    }

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

    public void updateInfo() {
        // store previous coordinates for collisions logic
        prev_X = xPos;
        prev_Y = yPos;

        // update player info based on world physics
        if (velocityState == Settings.VelocityState.LEFT) {
            xPos -= 8;
        } else if (velocityState == Settings.VelocityState.RIGHT) {
            xPos += 8;
        }
        yPos += velocity;
        velocity += accel;

        // Keep the player from falling out of the world
//        if (yPos + imageHeight >= Definitions.worldHeight) {
//            yPos = Definitions.worldHeight - imageHeight;
//        }
    }

    public boolean overlaps(Textures s) {

        if (xPos + imageWidth - Settings.characterHitBox < s.xPos) {
            return false;
        }
        if (xPos + Settings.characterHitBox > s.xPos + s.getWidth()) {
            return false;
        }
        if (yPos + imageHeight < s.getY()) {
            return false;
        }
        if (yPos + Settings.characterHitBoxTop > s.getY() + s.getHeight()) {
            return false;
        }
        return true;
    }

    @Override
    public void saveImage() {
    }

    @Override
    public void loadImage() {
        loadImages();
        image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
    }
}

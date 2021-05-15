package model;

import controller.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Character extends Textures{

    private double prev_X;
    private double prev_Y;
    private int velocity;
    private Settings.VelocityState velocityState;
    private boolean inAir;
    private boolean hasDoubleJump;

    private boolean dead;


    public Character() {

        super("player_right.png", Settings.characterSize, Settings.characterSize, false);
        velocity = 0;
        inAir = true;
        hasDoubleJump = true;
        dead = false;
        xPos = 90;
        yPos = 530;
    }

    public int getVelocity() {
        return this.velocity;
    }

    public void setVelocity(int vel) {velocity = vel;}

    public boolean isAirborne() {
        return inAir;
    }

    public boolean hasDoubleJump() {
        return hasDoubleJump;
    }

    public void setDoubleJump(boolean doubleJump) {
        hasDoubleJump = doubleJump;
    }

    public void setAirborne(boolean inAir) {
        this.inAir = inAir;

        if (inAir) {

            image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();

        } else {
            image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
        }

    }

    public void jump() {
        if (hasDoubleJump) {
            if (inAir) {
                hasDoubleJump = false;
            }
            velocity = -50;
            inAir = true;

            image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
        }
    }

    public void setDirection(Settings.VelocityState state) {
        if (state == Settings.VelocityState.LEFT) {
            //left
        } else if (state == Settings.VelocityState.RIGHT) {
            //right
        }

        velocityState = state;
    }

    public void setStandingRightImage() {
        image = new ImageIcon(Settings.assetDirectory + "player_right.png").getImage();
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

    public double getPrev_X() { return prev_X; }

    public double getPrev_Y() {
        return prev_Y;
    }

    public void updateInfo() {

        prev_X = xPos;
        prev_Y = yPos;

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
        return !(yPos + Settings.characterHitBoxTop > s.getY() + s.getHeight());
    }

}

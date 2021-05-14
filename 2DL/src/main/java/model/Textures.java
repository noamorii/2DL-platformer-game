package model;

import controller.Settings;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Textures implements Serializable {

    protected double xPos;
    protected double yPos;
    protected int imageWidth;
    protected int imageHeight;
    protected boolean collidable;
    protected transient Image image;
    protected String currentImagePath; // set on a game save

    public Textures(String textureName, int width, int height, boolean collidable) {

        image = new ImageIcon(Settings.assetDirectory + textureName).getImage();
        this.imageWidth = width;
        this.imageHeight = height;
        this.xPos = 0;
        this.yPos = 0;
        this.collidable = collidable;
        currentImagePath = textureName;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public int getHeight() {
        return imageHeight;
    }

    public int getWidth() {
        return imageWidth;
    }

    public Textures setX(double x) {
        xPos = x;
        return this;
    }

    public Textures setY(double y) {
        yPos = y;
        return this;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public boolean overlaps(Textures tex) {

        double object1XLeft = this.xPos;
        double object1YDown = this.yPos;
        double object1XRight = this.xPos + imageWidth;
        double object1YUp = this.yPos + imageWidth;

        double object2XLeft = tex.xPos;
        double object2YDown = tex.yPos;
        double object2XRight = tex.xPos + imageWidth;
        double object2YUp = tex.yPos + imageWidth;

        return (object1XLeft < object2XRight &&
                object1XRight > object2XLeft &&
                object1YDown < object2YUp &&
                object1YUp > object2YDown);

    }

}

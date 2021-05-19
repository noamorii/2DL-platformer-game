package model;

import controller.Settings;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Textures class loads all textures and animations from /assets/ folder
 *
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 */
public class Textures implements Serializable {

    protected double xPos;
    protected double yPos;
    protected int imageWidth;
    protected int imageHeight;
    protected boolean collidable;
    protected transient Image image;
    protected String currentImagePath; // set on a game save

    private static final Logger logger = Logger.getLogger("model.Textures");

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

    public Textures setXsetY(double x, double y) {
        xPos = x;
        yPos = y;
        return this;
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

    public void saveImage() {
        // base sprites don't need to do anything extra
    }

    /**
     * Setting image to texture
     * @param imagePath - path to image
     */
    public void setImage(String imagePath) {
        if (!imagePath.equals(Settings.assetDirectory + "")) {
            try {
                image = ImageIO.read(new File(imagePath));
            } catch (IOException ioe) {
                logger.warning("Unable to load image file.");
            }
        }

    }

    public void setGif(String imagePath) {
        image = new ImageIcon(imagePath).getImage();
    }

    public void loadImage() {
        if (currentImagePath.contains(".gif")) {
            setGif(Settings.assetDirectory + currentImagePath);
        } else {
            setImage(Settings.assetDirectory + currentImagePath);
        }
    }

    /**
     * Draw textures
     * @param g - graphics
     */
    public void update(Graphics g) {
        g.drawImage(image, (int)xPos, (int)yPos, imageWidth, imageHeight, null);
    }

    /**
     * Method to determine if two textures intersect.
     * @param tex - texture object
     * @return boolean value true if texture is overlapping the character
     */
    public boolean overlaps(Textures tex) {

        double object1XLeft = this.xPos;
        double object1YUp = this.yPos;
        double object1XRight = this.xPos + imageWidth;
        double object1YDown = this.yPos + imageWidth;

        double object2XLeft = tex.xPos;
        double object2YDown = tex.yPos;
        double object2XRight = tex.xPos + imageWidth;
        double object2YUp = tex.yPos + imageWidth;

        return (object1XLeft < object2XRight &&
                object1XRight > object2XLeft &&
                object1YUp < object2YUp &&
                object1YDown > object2YDown);
    }
}

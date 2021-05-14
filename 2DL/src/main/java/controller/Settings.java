package controller;

import javax.swing.*;

public abstract class Settings {
    public enum VelocityState {
        LEFT, RIGHT, STILL
    }

    public static String gameName = "Samurai's Quest";

    public static int worldHeight;
    public static int worldWidth;

    public static final String assetDirectory = "";

    public static void setWorldHeight(int height) {
        worldHeight = height;
    }
    public static void setWorldWidth(int width) {
        worldWidth = width;
    }
}

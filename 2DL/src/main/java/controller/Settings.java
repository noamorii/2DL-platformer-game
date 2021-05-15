package controller;

import javax.swing.*;

public abstract class Settings {
    public enum VelocityState {
        LEFT, RIGHT, STILL
    }

    public static String gameName = "2DL Engine Little samurai";

    public static int windowWidth = 1500;
    public static int windowHeight = 800;

    public static final int characterHitBox = 40;
    public static final int characterHitBoxTop = 20;
    public static final int characterSize = 96;

    public static final int enemyAttackDist = 35;
    public static final int enemyDeathLength = 45;
    public static final int enemyAttackLength = 24;

    public static final String assetDirectory = "assets/";


}

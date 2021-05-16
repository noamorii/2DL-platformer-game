package controller;

import javax.swing.*;

public abstract class Settings {
    public enum VelocityState {
        LEFT, RIGHT, STILL
    }

    public static String gameName = "2DL Engine";
    public static String winningText = "\nCongratulations";
    public static String losingText = "Don't feel bad";

    public static int windowWidth = 1500;
    public static int windowHeight = 800;

    public static String instructionText = "";

    public static final int characterHitBox = 40; // pixels between player and edge of player image
    public static final int characterHitBoxTop = 20; // pixels between player and top edge of player image
    public static final int characterSize = 105; // 105x105

    public static final int fireballExpireDist = 200; // fireballs expire after travelling 400px

    public static final int enemyAttackDist = 35;
    public static final int enemyDeathLength = 24; // Model should keep a dead skeleton for this many frames before removing, so death animation can play out.
    public static final int enemyAttackLength = 54; // Skeleton will be in attacking animation for 54 frames.

    public static final String assetDirectory = "assets/";

}
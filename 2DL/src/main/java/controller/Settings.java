package controller;

import model.Textures;

/**
 * Settings where user can control basic game properties
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 * @see Textures
 */
public abstract class Settings {

    public enum VelocityState {
        LEFT, RIGHT, STILL
    }

    public static String gameName = "2DL Engine";
    public static String winningText = "\nGood Job! You finished the Game!\n\nWell Done!";
    public static String losingText = "Don't Worry. Everyone has their ups and downs";

    public static int windowWidth = 1500;
    public static int windowHeight = 800;

    public static String instructionText =
            "How to play:\n" +
                    "A - move left\nD - move right\nSpace - Jump (double tap in air to double jump)\nEnter - cast a fire spell (up to 3 can be thrown at once!)\n" +
                    "p - Pause/Resume the game\ni - Display these instructions\n\n" +
                    "Good luck! Find the key and reach the finish!";

    public static final int characterHitBox = 30; // pixels between player and edge of player image
    public static final int characterHitBoxTop = 10; // pixels between player and top edge of player image
    public static final int characterSize = 96; // 96*96
    public static final int characterSpeed = 8;

    public static final int fireballExpireDist = 200; // fireballs expire after travelling 400px

    public static final int enemyAttackDist = 35;
    public static final int enemyDeathLength = 24; // Model should keep a dead skeleton for this many frames before removing, so death animation can play out.
    public static final int enemyAttackLength = 54; // Skeleton will be in attacking animation for 54 frames.

    public static final String assetDirectory = "assets/";
}
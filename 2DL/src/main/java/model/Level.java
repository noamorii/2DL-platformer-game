package model;

import controller.Settings;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Level implements Serializable {

    private final ArrayList<Textures> texturesArrayList;
    private final Textures goblet;
    private final Character character;
    private boolean gameIsOver;
    private boolean won;

    public Level() {

        gameIsOver = false;
        won = false;

        texturesArrayList = new ArrayList<>();
        //back
        //back
        texturesArrayList.add(new Textures("background.png", 1500, 800, false));
        //platforms

        texturesArrayList.add(new Textures("Plants_03.png", 300, 180, false).setXsetY(-65, 550));
        texturesArrayList.add(new Textures("Plants_07.png", 150, 180, false).setXsetY(360, 550));
        texturesArrayList.add(new Textures("Plants_07.png", 100, 100, false).setXsetY(480, 620));
        texturesArrayList.add(new Textures("Plants_03.png", 150, 90, false).setXsetY(1250, 520));

        texturesArrayList.add(new Textures("Plants_12.png", 150, 150, false).setXsetY(220, 350));
        texturesArrayList.add(new Textures("Plants_12.png", 150, 150, false).setXsetY(520, 80));
        texturesArrayList.add(new Textures("Plants_12.png", 150, 150, false).setXsetY(900, 380));


        goblet = new Textures("goblet.png", 70, 70, false).setX(1400).setY(95);
        texturesArrayList.add(goblet);
        character = new Character();
        texturesArrayList.add(character);

        texturesArrayList.add(new Enemy(875, 380, 180));
        texturesArrayList.add(new Enemy(375, 330, 180));
        texturesArrayList.add(new Enemy(675, 105, 180));

        //map

        texturesArrayList.add(new Textures("", 230, 40, true).setXsetY(0, 700));
        texturesArrayList.add(new Textures("platform3.png", 285, 120, false).setXsetY(-35, 680));

        texturesArrayList.add(new Textures("", 350, 40, true).setXsetY(325, 700));
        texturesArrayList.add(new Textures("platform2.png", 400, 150, false).setXsetY(300, 675));

        texturesArrayList.add(new Textures("", 250, 40, true).setXsetY(850, 700));
        texturesArrayList.add(new Textures("platform3.png", 300, 140, false).setXsetY(825, 680));

        texturesArrayList.add(new Textures("", 200, 40, true).setXsetY(1200, 600));
        texturesArrayList.add(new Textures("platform3.png", 265, 110, false).setXsetY(1170, 580));

        texturesArrayList.add(new Textures("", 400, 40, true).setXsetY(700, 475));
        texturesArrayList.add(new Textures("platform.png", 465, 120, false).setXsetY(675, 460));

        texturesArrayList.add(new Textures("", 400, 40, true).setXsetY(200, 425));
        texturesArrayList.add(new Textures("platform.png", 470, 120, false).setXsetY(175, 415));

        texturesArrayList.add(new Textures("", 200, 40, true).setXsetY(150, 250));
        texturesArrayList.add(new Textures("platform3.png", 250, 110, false).setXsetY(125, 225));

        texturesArrayList.add(new Textures("", 400, 40, true).setXsetY(500, 200));
        texturesArrayList.add(new Textures("platform.png", 460, 120, false).setXsetY(475, 185));

        texturesArrayList.add(new Textures("", 200, 40, true).setXsetY(1000, 200));
        texturesArrayList.add(new Textures("platform3.png", 265, 110, false).setXsetY(975, 180));

        texturesArrayList.add(new Textures("", 200, 40, true).setXsetY(1350, 125));
        texturesArrayList.add(new Textures("platform3.png", 265, 110, false).setXsetY(1325, 125));

        Enemy.setPlayer(character); // give skeletons the player to attack
    }

    public void update(Graphics g) {
        Iterator<Textures> iter = texturesArrayList.iterator();
        Textures texture;
        while (iter.hasNext()) {
            texture = iter.next();

            if (texture instanceof Character) {
                if (texture.overlaps(goblet)) {
                    gameIsOver = true;
                    won = true;
                } else {
                    ((Character) texture).updateInfo();
                    fixCollisions();
                    if (texture.getY() >= Settings.windowHeight || character.isDead()) {
                        gameIsOver = true;
                    }
                }


            } else if (texture instanceof FireBall) {
                if (((FireBall) texture).isExpired()) {
                    ((FireBall) texture).removeFireBall();
                    iter.remove();
                }


            } else if (texture instanceof Enemy) {
                if (((Enemy) texture).isDead()) {
                    if (((Enemy) texture).getFramesSinceDeath() == Settings.enemyDeathLength) {
                        iter.remove();
                    } else {
                        ((Enemy) texture).incrementFramesSinceDeath();
                    }
                } else {
                    for (Textures fireball : texturesArrayList) {
                        if (fireball instanceof FireBall && texture.overlaps(fireball)) {
                            if (Enemy.hp != 1) {
                                Enemy.hp--;
                            } else {
                                ((Enemy) texture).kill();
                                ((Enemy) texture).incrementFramesSinceDeath();
                            }

                            ((FireBall) fireball).removeFireBall();

                        }
                    }
                }
            }
            texture.update(g);
        }
    }

    public void saveImages() {
        character.saveImage();
    }

    public void loadImages() {
        for (Textures s : texturesArrayList) {
            s.loadImage();
        }
    }

    public boolean gameIsOver() {
        return gameIsOver;
    }

    public boolean playerWon() {
        return won;
    }

    public void jumpPlayer() {
        character.jump();
    }

    public void setPlayerDirection(Settings.VelocityState state) {
        character.setDirection(state);
    }

    public void setPlayerDirection(Settings.VelocityState state, char keyReleased) {
        if (state == Settings.VelocityState.STILL && character.getVelocityState() == Settings.VelocityState.LEFT && keyReleased == 'd') {
            return;
        }
        if (state == Settings.VelocityState.STILL && character.getVelocityState() == Settings.VelocityState.RIGHT && keyReleased == 'a') {
            return;
        }
        setPlayerDirection(state);
    }

    private void fixCollisions() {
        for (Textures s : texturesArrayList) {
            if (!(s instanceof Character) && s.isCollidable() && character.overlaps(s)) { // then there is a collision that needs to be resolved
                if (character.getPrev_X() + character.getWidth() - Settings.characterHitBox < s.getX()) {
                    character.setX(s.getX() - character.getWidth() + Settings.characterHitBox - 1);
                } else if (character.getPrev_X() + Settings.characterHitBox > s.getX() + s.getWidth()) {
                    character.setX(s.getX() + s.getWidth() - Settings.characterHitBox + 1);
                } else if (character.getPrev_Y() + character.getHeight() < s.getY()) {
                    if (character.isFacingRight() && character.isAirborne()) {
                        character.setStandingRightImage();
                    } else if (!character.isFacingRight() && !character.isAirborne()) {
                        character.setStandingLeftImage();
                    }
                    character.setY(s.getY() - character.getHeight() - 1);
                    character.setAirborne(false);
                    character.setDoubleJump(true);
                    if (character.getVelocity() > 0) {
                        character.setVelocity(0);
                    }
                } else if (character.getPrev_Y() + Settings.characterHitBoxTop > s.getY() + s.getHeight()) {
                    character.setY(s.getY() + s.getHeight() - Settings.characterHitBoxTop + 1);
                    character.setVelocity((int) (-0.6 * character.getVelocity()));
                }
            }
        }
    }

    public void throwFireball() {
        //character.attack();
        //image = new ImageIcon(Settings.assetDirectory + "player_jump_right.gif").getImage();
        if (FireBall.getCount() < 3) {
            texturesArrayList.add(new FireBall(character.getX(), character.getY(), character.isFacingRight()));
        }
    }
}

package model;

import controller.Settings;
import multiplayer.PlayerMP;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Level class spawns all entities and tiles on level
 *
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 */
public class Level implements Serializable {

    private final ArrayList<Textures> texturesArrayList;
    private final Textures flag;
    private PlayerMP playerMP;
    private Character character;
    //private Character character2;
    private final Key key;
    private boolean gameIsOver;
    private boolean won;
    private static final Logger logger = Logger.getLogger("model.Level");

    public Level() {

        logger.info("New level created");
        key = new Key(800, 300);
        flag = new Textures("flag.gif", 75, 150, false).setX(1380).setY(0);
        gameIsOver = false;
        won = false;
        texturesArrayList = new ArrayList<>(); //List of all textures
    }

    /**
     * Method to set up the level.
     * @param level - texture object
     */
    public void setUpLevel(Level level) {
        level.setDecorations();
        level.setEnemies();
        level.setFlag();
        level.setKey();
        level.setCharacter();
        level.setPlatforms();
    }

    public Character getCharacter() {
        return character;
    }

    public Key getKey() {
        return key;
    }

    /**
     * Method to decorate the level.
     */
    public void setDecorations() {
        addToArrayList(new Textures("background.png", 1500, 800, false));
        // Level decoration
        addToArrayList(new Textures("Plants_03.png", 300, 180, false).setXsetY(-65,550));
        addToArrayList(new Textures("Plants_07.png", 150, 180, false).setXsetY(360,550));
        addToArrayList(new Textures("Plants_07.png", 100, 100, false).setXsetY(480,620));
        addToArrayList(new Textures("Plants_03.png", 150, 90, false).setXsetY(1250,520));

        addToArrayList(new Textures("Plants_12.png", 150, 150, false).setXsetY(220,350));
        addToArrayList(new Textures("Plants_12.png", 150, 150, false).setXsetY(520,80));
        addToArrayList(new Textures("Plants_12.png", 150, 150, false).setXsetY(900,380));
    }

    /**
     * Set character to texture array list
     */
    public void setCharacter() {
        character = new Character(this, 90, 530);
        addToArrayList(character);
        Enemy.setPlayer(character);
    }

    public void setFlag() {
        addToArrayList(flag);
    }

    public void addToArrayList(Textures textures) {
        texturesArrayList.add(textures);
    }

    public void setKey() {
        addToArrayList(key);
    }

    /**
     * Set enemies to texture array list
     */
    public void setEnemies() {
        addToArrayList(new Enemy(875, 380, 180));
        addToArrayList(new Enemy(375, 330, 180));
        addToArrayList(new Enemy(675, 105, 180));
    }

    /**
     * Set character to texture array list and update
     */
    public void addPlayer(Character character2){
        character2 = new Character(this, 100, 200);
        addToArrayList(character2);
        character2.updateInfo();
    }

    /**
     * Method to build the level by platforms
     */
    public void setPlatforms() {


        addToArrayList(new Textures("", 230, 40, true).setXsetY(0,700));
        addToArrayList(new Textures("platform3.png", 285, 120, false).setXsetY(-35,680));

        addToArrayList(new Textures("", 325, 40, true).setXsetY(340,700));
        addToArrayList(new Textures("platform2.png", 400, 150, false).setXsetY(300,675));

        addToArrayList(new Textures("", 250, 40, true).setXsetY(850,700));
        addToArrayList(new Textures("platform3.png", 300, 140, false).setXsetY(825,680));

        addToArrayList(new Textures("", 200, 40, true).setXsetY(1200,600));
        addToArrayList(new Textures("platform3.png", 265, 110, false).setXsetY(1170,580));

        addToArrayList(new Textures("", 400, 40, true).setXsetY(700,475));
        addToArrayList(new Textures("platform.png", 465, 120, false).setXsetY(675,460));

        addToArrayList(new Textures("", 400, 40, true).setXsetY(200,425));
        addToArrayList(new Textures("platform.png", 470, 120, false).setXsetY(175,415));

        addToArrayList(new Textures("", 200, 40, true).setXsetY(150,250));
        addToArrayList(new Textures("platform3.png", 250, 110, false).setXsetY(125,225));

        addToArrayList(new Textures("", 400, 40, true).setXsetY(500,200));
        addToArrayList(new Textures("platform.png", 460, 120, false).setXsetY(475,185));

        addToArrayList(new Textures("", 200, 40, true).setXsetY(1000,200));
        addToArrayList(new Textures("platform3.png", 265, 110, false).setXsetY(975,180));

        addToArrayList(new Textures("", 200, 40, true).setXsetY(1350,150));
        addToArrayList(new Textures("platform3.png", 265, 110, false).setXsetY(1325,125));
    }

    /**
     * Method update the state of the level and its entities.
     * @param g - texture object
     */
    public void update(Graphics g) {
        Iterator<Textures> iter = texturesArrayList.iterator();
        Textures texture;
        while(iter.hasNext()) {
            texture = iter.next();

            if (texture instanceof Character) {
                if (texture.overlaps(key)) {
                    ((Character) texture).setKeyStatus(true);
                    key.setTaken(true);
                }
                if (texture.overlaps(flag) && Character.getKeyStatus()) {
                    logger.info("Player reached the end with the key");
                    gameIsOver = true;
                    won = true;
                } else {
                    ((Character) texture).updateInfo();
                    fixCollisions((Character) texture);
                    if (texture.getY() >= Settings.windowHeight || character.isDead()) {
                        logger.info("Player is dead");
                        gameIsOver = true;
                    }
                }

            } else if (texture instanceof FireBall) {
                if (((FireBall) texture).isExpired()) {
                    ((FireBall) texture).removeFireBall();
                    iter.remove();
                }

            } else if ((texture instanceof Key)) {
                if (((Key) texture).getTakenStatus()) {
                    logger.info("Player picked up the key");
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
                            ((Enemy) texture).damaged();
                            ((FireBall) fireball).removeFireBall();
                        }
                    }
                }
            }
            if (g != null) {
                texture.update(g);
            }
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
        if (state == Settings.VelocityState.STILL && character.getVelocityState() == Settings.VelocityState.LEFT && keyReleased == 'd') {return;}
        if (state == Settings.VelocityState.STILL && character.getVelocityState() == Settings.VelocityState.RIGHT && keyReleased == 'a') {return;}
        setPlayerDirection(state);
    }

    /**
     * Method to fix problematic collisions to prevent bugs.
     * @param character - texture object
     */
    public void fixCollisions(Character character) {
        for (Textures s : texturesArrayList) {

            if (!(s instanceof Character) && s.isCollidable() && character.overlaps(s)) {// then there is a collision that needs to be resolved
                if (character.getPrev_X() + Settings.characterSize - Settings.characterHitBox < s.getX()) {
                    character.setX(s.getX() - Settings.characterSize + Settings.characterHitBox - 1); //when character goes from the left

                } else if (character.getPrev_X() + Settings.characterHitBox > s.getX() + s.getWidth()) {
                    character.setX(s.getX() + s.getWidth() - Settings.characterHitBox + 1); // when when character goes from the right

                } else if (character.getPrev_Y() + Settings.characterSize < s.getY()) {// when the character is on the tex

                    if (character.isFacingRight() && character.isAirborne()) {
                        character.setStandingRightImage(); //stop jump animation

                    } else if (!character.isFacingRight() && character.isAirborne()){
                        character.setStandingLeftImage(); //stop jump animation
                    }

                    character.setY(s.getY() - Settings.characterSize - 1);
                    character.setAirborne(false);
                    character.setDoubleJump(true);

                    if (character.getVelocity() > 0) {
                        character.setVelocity(0);
                    }

                } else if (character.getPrev_Y() + Settings.characterHitBoxTop > s.getY() + s.getHeight()) { // when the character is under
                    character.setY(s.getY() + s.getHeight() - Settings.characterHitBoxTop + 1);
                    character.setVelocity((int)(-0.6 * character.getVelocity())); //the speed of the player's rebound from the block and fall
                }
            }


        }
    }

    public void throwFireball() {

        if (FireBall.getCount() < 3) {
            texturesArrayList.add(new FireBall(character.getX(), character.getY(), character.isFacingRight()));
        }
    }




}

package model;

import controller.Settings;
import multiplayer.PlayerMP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    private Textures flag;
    private PlayerMP playerMP;
    private Character character;
    //private Character character2;
    private Key key;
    private boolean gameIsOver;
    private boolean won;
    private static final Logger logger = Logger.getLogger("model.Level");

    public Level() {

        logger.info("New level created");
        gameIsOver = false;
        won = false;
        texturesArrayList = new ArrayList<>(); //List of all textures
    }

    /**
     * Method to set up the level.
     * @param level - texture object
     */
    public void setUpLevel(Level level) throws IOException, ParseException {

        level.setDecorations();
        level.setEnemies();
        level.setKey();
        level.setFlag();
        level.setCharacter();
        level.setPlatforms();
    }

    public ArrayList<Textures> getArray() {
        return texturesArrayList;
    }

    public Character getCharacter() {
        return character;
    }

    public Key getKey() {
        return key;
    }

    public void parseJson(String word) throws IOException, ParseException {

        Object level = new JSONParser().parse(new FileReader("level.json"));
        JSONObject levelObj= (JSONObject) level;

        JSONArray array = (JSONArray) levelObj.get(word);

        switch (word) {
            case "background":
                JSONObject background = (JSONObject) array.get(0);
                addToArrayList(new Textures((String) background.get("img"),
                        ((Long) background.get("width")).intValue(),
                        ((Long) background.get("height")).intValue(),
                        (boolean) background.get("collidable")));
                break;

            case "decorations":
            case "platforms":
                for (Object obj : array) {
                    JSONObject decOrPlat = (JSONObject) obj;
                    addToArrayList(new Textures((String) decOrPlat.get("img"),
                            ((Long) decOrPlat.get("width")).intValue(),
                            ((Long) decOrPlat.get("height")).intValue(),
                            (boolean) decOrPlat.get("collidable"))
                            .setXsetY(((Long) decOrPlat.get("x")).intValue(),
                                    ((Long) decOrPlat.get("y")).intValue()));
                }
                break;

            case "character":
            case "flag":
            case "key":
                JSONObject object = (JSONObject) array.get(0);

                switch (word) {
                    case "character":
                        character = new Character(this, ((Long) object.get("x")).intValue(),
                                ((Long) object.get("y")).intValue());
                        addToArrayList(character);
                        Enemy.setPlayer(character);
                        break;

                    case "flag":
                        flag = new Flag(((Long) object.get("x")).intValue(),
                                ((Long) object.get("y")).intValue());
                        addToArrayList(flag);
                        break;

                    default:
                        key = new Key(((Long) object.get("x")).intValue(),
                                ((Long) object.get("y")).intValue());
                        addToArrayList(key);
                        break;
                }
                break;

            case "enemies":
                for (Object obj : array) {
                    JSONObject enemy = (JSONObject) obj;
                    addToArrayList(new Enemy(((Long) enemy.get("x")).intValue(),
                            ((Long) enemy.get("y")).intValue(),
                            ((Long) enemy.get("roamDist")).intValue()));
                }
                break;
        }
    }

    /**
     * Method to decorate the level.
     */
    public void setDecorations() throws IOException, ParseException {
        parseJson("background");
        parseJson("decorations");
    }

    /**
     * Set character to texture array list
     */
    public void setCharacter() throws IOException, ParseException {
        parseJson("character");
    }

    public void setFlag() throws IOException, ParseException {
        parseJson("flag");
    }

    public void addToArrayList(Textures textures) {
        texturesArrayList.add(textures);
    }

    public void setKey() throws IOException, ParseException {
        parseJson("key");
    }

    /**
     * Set enemies to texture array list
     */
    public void setEnemies() throws IOException, ParseException {
        parseJson("enemies");
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
    public void setPlatforms() throws IOException, ParseException {
        parseJson("platforms");
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


import controller.Settings;
import model.*;
import model.Character;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for game modules
 */
public class GameTest {

    /**
     * TestOverlaps
     */
    @Test
    public void testOverlaps() {

        Level level = new Level();
        Character character = new Character(level, 30,30);

        Textures texture = new Textures("Plants_12.png", 150, 150, true);
        texture.setXsetY(30,30);

        boolean result = character.overlaps(texture);
        assertTrue(result);
    }
    /**
     * testNotOverlaps
     */
    @Test
    public void testNotOverlaps() {

        Level level = new Level();
        Character character = new Character(level, 210,210);

        Textures texture = new Textures("Plants_12.png", 150, 150, true);
        texture.setXsetY(30,30);

        boolean result = character.overlaps(texture);
        assertFalse(result);
    }
    /**
     * characterGetDamage
     */
    @Test
    public void characterGetDamage() {

        Level level = new Level();
        Character character = new Character(level, 210,210);

        character.getDamage();
        assertEquals(Character.getHp(), 2);

        character.getDamage();
        assertEquals(Character.getHp(), 1);

        character.getDamage();
        assertTrue(character.isDead());

    }
    /**
     * enemyGotDamage
     */
    @Test
    public void enemyGotDamage() {

        Enemy enemy = new Enemy(0,0, 180);

        enemy.damaged();
        assertEquals(enemy.getHp(), 2);

        enemy.damaged();
        assertEquals(enemy.getHp(), 1);

        enemy.damaged();
        assertTrue(enemy.isDead());
    }
    /**
     * playerGotKey
     */
    @Test
    public void playerGotKey() throws IOException, ParseException {

        Level level = new Level();
        level.setCharacter();
        Character character = level.getCharacter();
        Key key = level.getKey();
        level.setKey();

        character.setXsetY(30,30);
        key.setXsetY(30,30);

        Graphics g = null;
        level.update(g);

        assertTrue(Character.getKeyStatus());
    }
    /**
     * playerDoesntGotKey
     */
    @Test
    public void playerDoesntGotKey() throws IOException, ParseException {

        Level level = new Level();
        level.setCharacter();
        Character character = level.getCharacter();
        Key key = level.getKey();
        level.setKey();

        character.setXsetY(30,30);
        key.setXsetY(2000,300);

        Graphics g = null;
        level.update(g);

        assertFalse(Character.getKeyStatus());
    }
    /**
     * fixedCollision
     */
    @Test
    public void fixedCollision() throws IOException, ParseException {

        Level level = new Level();
        level.setCharacter();
        Character character = level.getCharacter();
        character.setXsetY(100, 700);


        Textures texture = new Textures("", 230, 40, true);
        texture.setXsetY(100,700);
        level.addToArrayList(texture);

        level.fixCollisions(character);

        double expectedResult = texture.getX() - Settings.characterSize + Settings.characterHitBox - 1;

        assertEquals(expectedResult, character.getX());

    }

    @Test
    public void testSetDirection() throws IOException, ParseException {

        Level level = new Level();
        level.setCharacter();
        Character character = level.getCharacter();

        character.setDirection(Settings.VelocityState.LEFT);

        assertFalse(character.isFacingRight());

    }

    @Test
    public void testAddToArray() throws IOException, ParseException {

        Level level = new Level();
        level.setPlatforms();

        int result = (level.getArray()).size();

        assertEquals(20, result);
    }

    @Test
    public void testAddAllToArray() throws IOException, ParseException {

        Level level = new Level();
        level.setUpLevel(level);

        int result = (level.getArray()).size();

        assertEquals(34, result);
    }

    @Test
    public void TestLevelKey() {
        Level level = new Level();

        double resultX = level.getKey().getX();
        double resultY = level.getKey().getY();

        int expectedX = 800;
        int expectedY = 300;

        assertEquals(expectedX, resultX);
        assertEquals(expectedY, resultY);
    }



}

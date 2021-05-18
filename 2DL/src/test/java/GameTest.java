
import controller.Settings;
import model.*;
import model.Character;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {


    @Test
    public void testOverlaps() {

        Character character = new Character();
        character.setXsetY(30,30);

        Textures texture = new Textures("Plants_12.png", 150, 150, true);
        texture.setXsetY(30,30);

        boolean result = character.overlaps(texture);
        assertTrue(result);
    }

    @Test
    public void testNotOverlaps() {

        Character character = new Character();
        character.setXsetY(210,210);

        Textures texture = new Textures("Plants_12.png", 150, 150, true);
        texture.setXsetY(30,30);

        boolean result = character.overlaps(texture);
        assertFalse(result);
    }
    @Test
    public void characterGetDamage() {

        Character character = new Character();
        character.getDamage();
        assertEquals(Character.getHp(), 2);

        character.getDamage();
        assertEquals(Character.getHp(), 1);

        character.getDamage();
        assertTrue(character.isDead());

    }

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

    @Test
    public void playerGotKey() {

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

    @Test
    public void playerDoesntGotKey() {

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

    @Test
    public void fixedCollision() {

        Level level = new Level();

        Character character = level.getCharacter();
        character.setXsetY(100,700);
        level.setCharacter();

        Textures texture = new Textures("", 230, 40, true);
        texture.setXsetY(100,700);
        level.addToArrayList(texture);

        level.fixCollisions();

        double expectedResult = texture.getX() - Settings.characterSize + Settings.characterHitBox - 1;

        assertEquals(expectedResult, character.getX());



    }



}

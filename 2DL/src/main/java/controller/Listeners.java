package controller;

import model.Level;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ENTER;

public class Listeners implements KeyListener {

    private boolean paused;
    private Level level;
    Game game;

    public Listeners(Game game) {
        this.game = game;
        paused = game.isPaused();
        level = game.getLevel();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Listener that allows user to move character by a,d, space and shot fireballs by enter.
     */
    public void keyPressed(KeyEvent e) {
        if (!paused) {
            if (e.getKeyChar() == 'a') {
                game.getLevel().setPlayerDirection(Settings.VelocityState.LEFT);
            } else if (e.getKeyChar() == 'd') {
                game.getLevel().setPlayerDirection(Settings.VelocityState.RIGHT);
            } else if (e.getKeyChar() == ' ') {
                game.getLevel().jumpPlayer();
            } else if (e.getKeyChar() == VK_ENTER) {
                game.getLevel().throwFireball();
            }
        }
    }

    /**
     * Listener that allows user to move character by a,d.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            game.getLevel().setPlayerDirection(Settings.VelocityState.STILL, 'a');
        } else if (e.getKeyChar() == 'd') {
            game.getLevel().setPlayerDirection(Settings.VelocityState.STILL, 'd');
        }
    }
}

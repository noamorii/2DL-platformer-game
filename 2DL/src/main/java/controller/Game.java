package controller;

import model.Level;
import view.Window;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.event.KeyEvent.VK_ENTER;

public class Game implements MouseListener, KeyListener, ActionListener {

    private static Timer timer;
    private final Window window;
    private static boolean paused;
    private Level level;

    public Game() {
        paused = false;
        window = new Window(this);
        level = new Level();
        timer = new Timer(20, window);
        timer.start();
    }


    public void update(Graphics g) {
        if (level!= null) {
            if (level.gameIsOver()) {
                timer.stop();
                if (level.playerWon()) {
                    //
                } else {
                    //
                }
                level = new Level();
                timer.start();
            } else {
                level.update(g);
            }
        }
    }

    public void pause() {

        if (!paused) {
            paused = true;
            timer.stop();
        } else {
            paused = false;
            timer.start();
        }
    }

    public void saveGame() {
    }

    public void loadGame() {

    }

    public void keyPressed(KeyEvent e) {
        if (!paused) {
            if (e.getKeyChar() == 'a') {
                level.setPlayerDirection(Settings.VelocityState.LEFT);
            } else if (e.getKeyChar() == 'd') {
                level.setPlayerDirection(Settings.VelocityState.RIGHT);
            } else if (e.getKeyChar() == ' ') {
                level.jumpPlayer();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            level.setPlayerDirection(Settings.VelocityState.STILL, 'a');
        } else if (e.getKeyChar() == 'd') {
            level.setPlayerDirection(Settings.VelocityState.STILL, 'd');
        }
    }

    public void keyTyped(KeyEvent e) {}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Pause/Resume":
                pause();
                break;
            case "Save Game":
                saveGame();
                break;
            case "Load Game":
                loadGame();
                break;
            case "Exit":
                System.exit(0);
        }
    }
}

package controller;

import model.Character;
import model.Level;
import view.Window;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
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
        displayInstructions();
    }


    public void update(Graphics g) {
        if (level!= null) { // on first call, model will be null, necessary to have view run first to populate Definitions class with world info
            if (level.gameIsOver()) {
                timer.stop();
                if (level.playerWon()) {
                    JOptionPane.showMessageDialog(window, Settings.winningText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window, Settings.losingText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
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

        level.saveImages();

        try {
            FileOutputStream fileStream = new FileOutputStream("save.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileStream);
            out.writeObject(level);
            fileStream.close();
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void loadGame() {
        paused = true;
        timer.stop();

        try {
            FileInputStream fileStream = new FileInputStream("save.txt");
            ObjectInputStream in = new ObjectInputStream(fileStream);
            level = (Level) in.readObject();
            fileStream.close();
            in.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            System.exit(1);
        }

        level.loadImages();
        paused = false;
        timer.start();
    }

    public void displayInstructions() {
        JOptionPane.showMessageDialog(window, Settings.instructionText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
    }

    public void keyPressed(KeyEvent e) {
        if (!paused) {
            if (e.getKeyChar() == 'a') {
                level.setPlayerDirection(Settings.VelocityState.LEFT);
            } else if (e.getKeyChar() == 'd') {
                level.setPlayerDirection(Settings.VelocityState.RIGHT);
            } else if (e.getKeyChar() == ' ') {
                level.jumpPlayer();
            } else if (e.getKeyChar() == VK_ENTER) {
                level.throwFireball();
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

    // unused KeyListener methods
    public void keyTyped(KeyEvent e) { }

    // unused MouseListener methods
    public void mousePressed(MouseEvent e) {
//		if (SwingUtilities.isLeftMouseButton(e)) {
//
//		} else if (SwingUtilities.isRightMouseButton(e))  {
//
//		}
//
//		view.repaint();
    }
    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand() == "Pause/Resume") {
            pause();
        } else if (evt.getActionCommand().equals("Save Game")) {
            saveGame();
        } else if (evt.getActionCommand().equals("Load Game")) {
            loadGame();
        } else if (evt.getActionCommand().equals("Instructions")) {
            displayInstructions();
        } else if (evt.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }
}

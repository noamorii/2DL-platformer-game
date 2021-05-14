package controller;

import view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {

    private static Timer timer;
    private static boolean paused;

    public void init() {
        Window window = new Window();
        timer = new Timer(20, window);
        paused = false;
        timer.start();

    }

    public void update(Graphics g) {

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

    private static class GameLoop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}

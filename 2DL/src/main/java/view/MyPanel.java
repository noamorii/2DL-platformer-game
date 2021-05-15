package view;

import controller.Game;

import javax.swing.*;
import java.awt.*;

class MyPanel extends JPanel {

    private final Game game;

    MyPanel(Game gm) {

        game = gm;
        addMouseListener(gm);
    }

    public void paintComponent(Graphics g) {

        game.update(g);
        revalidate();
    }

    public JMenuBar showOptions(Game gm) {

        JMenuBar mainMenu = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem pause = new JMenuItem("Pause/Resume");
        pause.addActionListener(gm);
        pause.setAccelerator(KeyStroke.getKeyStroke('p'));

        JMenuItem save = new JMenuItem("Save Game");

        JMenuItem load = new JMenuItem("Load Game");

        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.addActionListener(gm);
        instructions.setAccelerator(KeyStroke.getKeyStroke('i'));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(gm);

        optionsMenu.add(pause);
        optionsMenu.add(save);
        optionsMenu.add(load);
        optionsMenu.add(instructions);
        optionsMenu.add(exit);
        mainMenu.add(optionsMenu);

        return mainMenu;
    }
}

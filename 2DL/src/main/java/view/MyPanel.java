package view;

import controller.Game;
import controller.Settings;
import model.Character;

import javax.swing.*;
import java.awt.*;

class MyPanel extends JPanel {

    private final Game game;

    MyPanel(Game gm) {
        game = gm;
    }

    public void paintComponent(Graphics g) {

        game.update(g);

        Image heart = new ImageIcon(Settings.assetDirectory + "green_heart.png").getImage();
        Image emptyHeart = new ImageIcon(Settings.assetDirectory + "empty_heart.png").getImage();
        Image keySlot = new ImageIcon(Settings.assetDirectory + "key.png").getImage();
        g.setColor(Color.black);

        g.drawImage(emptyHeart, 30, 30, 70, 65, null);
        g.drawImage(emptyHeart, 120, 30, 70, 65, null);
        g.drawImage(emptyHeart, 210, 30, 70, 65, null);

        if (Character.getHp() == 3) {
            g.drawImage(heart, 30, 30, 70, 65, null);
            g.drawImage(heart, 120, 30, 70, 65, null);
            g.drawImage(heart, 210, 30, 70, 65, null);

        } else if (Character.getHp() == 2) {

            g.drawImage(heart, 30, 30, 70, 65, null);
            g.drawImage(heart, 120, 30, 70, 65, null);
        } else {
            g.drawImage(heart, 30, 30, 70, 65, null);
        }

        if (Character.getKeyStatus()) {
            g.drawImage(keySlot, 40, 120, 50, 50, null);
        }

        revalidate();

    }

    public JMenuBar showOptions(Game gm) {

        JMenuBar mainMenu = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem pause = new JMenuItem("Pause/Resume");
        pause.addActionListener(gm);
        pause.setAccelerator(KeyStroke.getKeyStroke('p'));

        JMenuItem save = new JMenuItem("Save Game");
        save.addActionListener(gm);
        save.setAccelerator(KeyStroke.getKeyStroke('k'));

        JMenuItem load = new JMenuItem("Load Game");
        load.addActionListener(gm);
        load.setAccelerator(KeyStroke.getKeyStroke('l'));

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

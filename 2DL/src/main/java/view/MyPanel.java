package view;

import controller.Game;
import controller.Settings;
import model.Character;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Implements panel with options and draw character hearts.
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 */

class MyPanel extends JPanel {

    private final Game game;

    MyPanel(Game gm) {
        game = gm;

    }

    /**
     * Paint players hp
     * @param g
     */
    public void paintComponent(Graphics g) {

        try {
            game.update(g);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Image heart = new ImageIcon(Settings.assetDirectory + "green_heart.png").getImage();
        Image emptyHeart = new ImageIcon(Settings.assetDirectory + "empty_heart.png").getImage();
        Image keySlot = new ImageIcon(Settings.assetDirectory + "key.png").getImage();

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

    /**
     * Show options on game window.
     * @param gm
     * @return - mainMenu with options
     */
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

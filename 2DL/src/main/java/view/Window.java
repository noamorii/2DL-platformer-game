package view;

import controller.Game;
import controller.Settings;
import model.Textures;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Window class opens a window with certain parameters and calls MyPanel class
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */
public class Window extends JFrame implements ActionListener{

    private static final Logger logger = Logger.getLogger("view.Window");

    public Window(Game gm) {
        // Define the viewing window and viewing properties
        setTitle(Settings.gameName);
        setSize(Settings.windowWidth, Settings.windowHeight);
        requestFocus();
        setLocation(0,0);
        getContentPane().add(new MyPanel(gm));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addKeyListener(gm);

        gm.start();

        MyPanel myPanel = new MyPanel(gm);
        JMenuBar mainMenu = myPanel.showOptions(gm);
        this.setJMenuBar(mainMenu);

        logger.info("Level window was created and opened");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}


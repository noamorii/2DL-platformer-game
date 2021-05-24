package view;

import controller.Game;
import controller.Listeners;
import controller.Settings;
import model.Textures;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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


    public Window(Game gm) throws IOException, ParseException {
        Listeners listeners = new Listeners(gm);
        // Define the viewing window and viewing properties
        setTitle(Settings.gameName);
        setSize(Settings.windowWidth, Settings.windowHeight);
        requestFocus();
        setLocation(0,0);
        getContentPane().add(new MyPanel(gm));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
//        addKeyListener(gm);
        addKeyListener(listeners);

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


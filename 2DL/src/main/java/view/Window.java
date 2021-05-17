package view;

import controller.Game;
import controller.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Window extends JFrame implements ActionListener{

    public Window(Game gm) {
        // Define the viewing window and viewing properties
        setTitle(Settings.gameName);
        setSize(Settings.windowWidth, Settings.windowHeight);
        requestFocus();
        setLocation(210, 125);


        getContentPane().add(new MyPanel(gm));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        addKeyListener(gm);

        MyPanel myPanel = new MyPanel(gm);
        JMenuBar mainMenu = myPanel.showOptions(gm);
        this.setJMenuBar(mainMenu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}

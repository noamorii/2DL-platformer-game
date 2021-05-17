package view;


import controller.Game;
import controller.Settings;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Menu extends JFrame implements ActionListener {

    public static boolean startButtonPressed;
    private static final long serialVersionUID = 1L;

    int width, height;

    JButton play, playOnline, exit;
    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    //JPanel game = new JPanel();
    JPanel menu = new JPanel();
    JLabel lblNewLabel = new JLabel();

    public void paintComponent(Graphics g) {

    }

    public Menu(int width, int height) {

        panel.setLayout(layout);
        addButtons();

        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle(Settings.gameName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();
        startButtonPressed = false;
    }

    private void addButtons() {

        play = new JButton("play");
        playOnline = new JButton("play with friend");
        exit = new JButton("exit");

        play.addActionListener(this);
        playOnline.addActionListener(this);
        exit.addActionListener(this);

        //menu buttons
        menu.add(play);
        menu.add(playOnline);
        menu.add(exit);
        //background colors
        lblNewLabel.setIcon(new ImageIcon("assets/background.png"));
        menu.add(lblNewLabel);

        //adding children to parent Panel
        panel.add(menu,"Menu");
        //panel.add(game,"Game");

        add(panel);
        layout.show(panel,"Menu");

    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == play) {
            setVisible(false); //you can't see me!
            new Game();
            dispose(); //Destroy the JFrame object
        } else if (source == playOnline){
            //TODO: make a multiplayer package for launching a game for 2 people
        }
    }
}
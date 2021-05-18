package view;

import controller.Game;
import controller.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;


public class Menu extends JPanel implements ActionListener {

    JButton newGame, loadGame, exit;
    private static final Logger logger = Logger.getLogger("view.Window");

    public Menu() {

        JFrame jFrame = new JFrame();
        jFrame.getContentPane().add(this);
        jFrame.setSize(Settings.windowWidth, Settings.windowHeight);
        jFrame.requestFocus();
        jFrame.setLocation(0,0);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);

        logger.info("Menu window was created and opened");

        setBounds(0, 0, Settings.windowWidth, Settings.windowHeight);
        setLayout(null);

        requestFocus();

        newGame = new JButton("NewGame");
        loadGame = new JButton("LoadGame");
        exit = new JButton("Exit");

        newGame.setIcon(new ImageIcon(Settings.assetDirectory + "newGameW.png"));
        addButton(newGame, 100,280,430,120);

        loadGame.setIcon(new ImageIcon(Settings.assetDirectory + "loadGameW.png"));
        addButton(loadGame, 100,430,430,120);

        exit.setIcon(new ImageIcon(Settings.assetDirectory + "exitGameW.png"));
        addButton(exit, 100,580,430,120);

        jFrame.setVisible(true);
        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newGame.setIcon(new ImageIcon(Settings.assetDirectory + "newGameP.png"));
            }

            public void mouseExited(MouseEvent evt) {
                newGame.setIcon(new ImageIcon(Settings.assetDirectory + "newGameW.png"));
            }

            public void mouseReleased(MouseEvent evt) {
                new Game();
                jFrame.dispose();

            }
        });

        loadGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadGame.setIcon(new ImageIcon(Settings.assetDirectory + "loadGameP.png"));
            }

            public void mouseExited(MouseEvent evt) {
                loadGame.setIcon(new ImageIcon(Settings.assetDirectory + "loadGameW.png"));
            }

            public void mouseClicked(MouseEvent evt) {
                new Game().loadGame();
                jFrame.dispose();
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(new ImageIcon(Settings.assetDirectory + "exitGameP.png"));
            }

            public void mouseExited(MouseEvent evt) {
                exit.setIcon(new ImageIcon(Settings.assetDirectory + "exitGameW.png"));
            }

            public void mouseClicked(MouseEvent evt) {
                System.exit(0);
            }
        });

        newGame.addActionListener(this);
        loadGame.addActionListener(this);
        exit.addActionListener(this);


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image heart = new ImageIcon(Settings.assetDirectory + "menu.png").getImage();
        g.drawImage(heart, 0, 0, Settings.windowWidth, Settings.windowHeight, this);

    }

    public void addButton(JButton button, int x, int y, int width, int height) {

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBounds(x,y,width,height);
        add(button);

    }

    public void actionPerformed(ActionEvent event) {

    }
}


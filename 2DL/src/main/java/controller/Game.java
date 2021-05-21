package controller;

import model.Character;
import model.Enemy;
import model.Level;
import multiplayer.Client;
import multiplayer.Server;
import multiplayer.packets.Packet00Login;
import org.json.simple.parser.ParseException;
import view.Window;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Main Game class that starts the whole game.
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */
public class Game implements KeyListener, ActionListener {

    private static Timer timer;
    private final Window window;
    /**
     * Character
     */
    public Character player;
    private boolean paused;
    private static boolean wasSaved;
    private Level level;
    /**
     * Client
     */
    public Client socketClient;
    /**
     * Server
     */
    public Server socketServer;

    private static final Logger logger = Logger.getLogger("controller.Game");

    public Game() throws IOException, ParseException {

        wasSaved = false;
        paused = false;
        window = new Window(this);
        level = new Level();
        level.setUpLevel(level);
        timer = new Timer(20, window);
        timer.start();
        displayInstructions();
        logger.info("Game class called");

        Packet00Login loginPacket = new Packet00Login(JOptionPane.showInputDialog(this, "Please enter a username"),
                (int)level.getCharacter().getX(),
                (int)level.getCharacter().getY());
        loginPacket.writeData(socketClient);
    }

    public Level getLevel() {
        return level;
    }

    /**
     * Method that starts the server.
     */
    public synchronized void start(){

        if (JOptionPane.showConfirmDialog(window, "Do you want to run the server") == 0){
            socketServer = new Server(this);
            socketServer.start();
        }

        socketClient = new Client(this, "localhost");
        socketClient.start();
    }

    /**
     * Updating game state.
     * @param g - graphics parameter
     */
    public void update(Graphics g) throws IOException, ParseException {

        if (level!= null) { // on first call, model will be null, necessary to have view run first to populate Definitions class with world info
            if (level.gameIsOver()) {
                timer.stop();
                if (level.playerWon()) {
                    logger.info("Player won");
                    JOptionPane.showMessageDialog(window, Settings.winningText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(window, Settings.losingText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
                }
                level = new Level();
                level.setUpLevel(level);
                timer.start();
            } else {
                level.update(g);
            }
        }
    }

    /**
     * Represents game pause.
     */
    public void pause() {

        if (!paused) {
            paused = true;
            timer.stop();
            logger.info("Game paused");
        } else {
            paused = false;
            timer.start();
            logger.info("Game unpaused");
        }
    }

    /**
     * Save level objects to txt file.
     */
    public void saveGame() {

        wasSaved = true;
        level.saveImages();

        try {
            FileOutputStream fileStream = new FileOutputStream("save.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileStream);
            out.writeObject(level);
            fileStream.close();
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
            logger.warning(i.getMessage());
        }
        logger.info("Level saved");
    }

    /**
     * Allows user to load saved game from txt file.
     */
    public void loadGame() throws IOException, ParseException {
        paused = true;
        timer.stop();

        if (!wasSaved) {
            logger.warning("Game wasn't saved!");
            Level level = new Level();
            level.setUpLevel(level);
        } else {
            try {
                FileInputStream fileStream = new FileInputStream("save.txt");
                ObjectInputStream in = new ObjectInputStream(fileStream);
                level = (Level) in.readObject();
                fileStream.close();
                in.close();
            } catch (IOException | ClassNotFoundException i) {
                i.printStackTrace();
                logger.warning(i.getMessage());
                System.exit(1);
            }
            Enemy.setPlayer(level.getCharacter());
            level.loadImages();
        }

        paused = false;
        timer.start();

        logger.info("Level loaded");
    }

    public void displayInstructions() {
        JOptionPane.showMessageDialog(window, Settings.instructionText, Settings.gameName, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Listener that allows user to move character by a,d, space and shot fireballs by enter.
     */
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

    /**
     * Listener that allows user to move character by a,d.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            level.setPlayerDirection(Settings.VelocityState.STILL, 'a');
        } else if (e.getKeyChar() == 'd') {
            level.setPlayerDirection(Settings.VelocityState.STILL, 'd');
        }
    }

    /**
     * Calls functions depending on the selected value
     * @param evt - action event
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (evt.getActionCommand()) {
            case "Pause/Resume":
                pause();
                break;
            case "Save Game":
                saveGame();
                break;
            case "Load Game":
                try {
                    loadGame();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "Instructions":
                displayInstructions();
                break;
            case "Exit":
                logger.info("Exit the application");
                System.exit(0);
        }
    }
}

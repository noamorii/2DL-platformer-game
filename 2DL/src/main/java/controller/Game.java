package controller;

import model.Character;
import model.Enemy;
import model.Level;
import multiplayer.Client;
import multiplayer.PlayerMP;
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

import static java.awt.event.InputEvent.getMaskForButton;
import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Main Game class that starts the whole game.
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */
public class Game implements ActionListener {

    private static Timer timer;
    private final Window window;
    public Listeners listeners;
    /**
     * Character
     */
    public PlayerMP player;
    private boolean paused;
    public static Game game;
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
        game = this;
        paused = false;
        level = new Level();
        window = new Window(this);
        level.setUpLevel(level);
        timer = new Timer(20, window);
        timer.start();
        displayInstructions();
        listeners = new Listeners(this);
        logger.info("Game class called");
        player = new PlayerMP(this.getLevel(), 90, 530, listeners, JOptionPane.showInputDialog(this, "Please enter a username"), null,-1);
        getLevel().addPlayer(player);
        Packet00Login loginPacket = new Packet00Login(player.getUsername());
        if (socketServer != null) {
            socketServer.addConnection((PlayerMP) player, loginPacket);
        }
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

        socketClient = new Client(this, "127.0.0.1");
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

    public boolean isPaused() {
        return paused;
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

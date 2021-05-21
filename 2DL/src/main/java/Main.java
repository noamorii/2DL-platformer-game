import view.Menu;
import view.Sound;

import java.util.logging.Logger;

/**
 * Main class that starts it all!
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */

public class Main {

    private static final Logger logger = Logger.getLogger("Main");

    /**
     * Main class
     */
    public static void main(String[] args) {

        logger.info("Launching the program");

        String filepath = "assets/music/1st soundtrack.wav";
        Sound sound = new Sound();
        sound.playMusic(filepath);

        new Menu();

    }
}
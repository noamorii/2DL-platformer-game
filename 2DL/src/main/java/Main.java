import view.Menu;
import view.Sound;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {

        logger.info("Launching the program");

        String filepath = "assets/music/1st soundtrack.wav";
        Sound sound = new Sound();
        sound.playMusic(filepath);

        new Menu();

    }
}
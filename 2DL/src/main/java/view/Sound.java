package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

public class Sound {

    private static final Logger logger = Logger.getLogger("view.Sound");

    public void playMusic(String soundLocation){

        try{
            File soundPath = new File(soundLocation);

            if(soundPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                logger.info("Sound loaded");
            } else {
                logger.warning("Cant find song file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.info(ex.getMessage());
        }
    }
}




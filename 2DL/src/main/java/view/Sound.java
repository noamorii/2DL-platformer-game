package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.logging.Logger;

/**
 * Sound class that starts music from /assets/music/ folder
 *
 * @author Olesia Cheremnykh and Dmitrtii Zamedianskii
 * @version 1.0
 */
public class Sound {

    private static final Logger logger = Logger.getLogger("view.Sound");

    public void playMusic(String soundLocation){

        try{
            File soundPath = new File(soundLocation);

            if(soundPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                FloatControl vc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                vc.setValue(-30);
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



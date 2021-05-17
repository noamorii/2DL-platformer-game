
import controller.Game;
import view.Menu;
import view.Sound;



public class Main {
    public static void main(String[] args) {
        String filepath = "assets/music/1st soundtrack.wav";
        Sound sound = new Sound();
        sound.playMusic(filepath);
        new Menu(1500, 800);
    }
}
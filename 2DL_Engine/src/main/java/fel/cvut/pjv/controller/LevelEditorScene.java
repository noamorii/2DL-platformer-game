package fel.cvut.pjv.controller;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene{

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene(){
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {

        System.out.println("" + (1.0f / dt) + "FPS");

        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0){
            timeToChangeScene -= dt;
            Window.getWindow().r -= dt * 5.0f;
            Window.getWindow().g -= dt * 5.0f;
            Window.getWindow().b -= dt * 5.0f;
        } else if (changingScene){
            Window.changeScene(1);
        }
    }

}

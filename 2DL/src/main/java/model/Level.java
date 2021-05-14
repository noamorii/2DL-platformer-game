package model;

import java.util.ArrayList;

public class Level {

    private final ArrayList<Textures> texturesArrayList;

    public Level() {

        texturesArrayList = new ArrayList<>();
        //back
        texturesArrayList.add(new Textures("background.png", 1500, 800, false));
        //platforms
        texturesArrayList.add(new Blocks(325, 700, 350, 30));
        texturesArrayList.add(new Blocks(850, 700, 250, 30));
        texturesArrayList.add(new Blocks(325, 700, 350, 30));
        texturesArrayList.add(new Blocks(700, 475, 400, 30));
        texturesArrayList.add(new Blocks(200, 425, 400, 30));
        texturesArrayList.add(new Blocks(150, 250, 200, 30));
        texturesArrayList.add(new Blocks(500, 200, 400, 30));
        texturesArrayList.add(new Blocks(1000, 200, 200, 30));
        texturesArrayList.add(new Blocks(1350, 125, 200, 30));






    }


}

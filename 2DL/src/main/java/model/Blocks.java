package model;

public class Blocks extends Textures {

    public Blocks(int coordX, int coordY, int width, int height) {

        super("platform.png", width, height, true);
            xPos = coordX;
            yPos = coordY;
    }
}

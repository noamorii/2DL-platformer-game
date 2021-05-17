package model;

public class Key extends Textures{

    private boolean isTaken;

    public Key(int x, int y) {
        super("key.png", 48, 48, false);
        xPos = x;
        yPos = y;
        isTaken = false;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean getTakenStatus() {
        return isTaken;
    }
}

package multiplayer.packets;

import multiplayer.Client;
import multiplayer.Server;

import java.nio.charset.StandardCharsets;

public class Packet02Move extends Packet {

    private String username;
    private double x,y;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Double.parseDouble(dataArray[1]);
        this.y = Double.parseDouble(dataArray[2]);
    }

    public Packet02Move(String username, double x, double y) {
        super(02);
        this.x = x;
        this.y = y;
        this.username = username;
    }

    @Override
    public void writeData(Client client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("02" + this.username + "," + this.x + "," + this.y).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

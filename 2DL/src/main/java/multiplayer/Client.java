package multiplayer;

import controller.Game;
import model.Textures;
import multiplayer.packets.Packet;
import multiplayer.packets.Packet00Login;
import multiplayer.packets.Packet02Move;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

/**
 *  Client class lets client connect to the server
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */
public class Client extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Game game;
    private static final Logger logger = Logger.getLogger("multiplayer.Client");


    public Client(Game game, String ipAddress){
        this.game = game;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());

//            String message = new String(packet.getData());
//            System.out.println("SERVER < " + message.trim());

        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                logger.info("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login)packet).getUsername() + " has joined the game...");
                PlayerMP player = new PlayerMP(game.getLevel(), 90, 530,  ((Packet00Login)packet).getUsername(), address, port);
                game.getLevel().addPlayer(player);
                break;
            case MOVE:
                packet = new Packet02Move(data);
                handleMove((Packet02Move)packet);
        }
    }

    private void handleMove(Packet02Move packet) {
        this.game.getLevel().movePlayer(packet.getUsername(), packet.getX(), packet.getY());
    }


    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

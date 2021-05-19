package multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import controller.Game;
import multiplayer.packets.Packet;
import multiplayer.packets.Packet.PacketTypes;
import multiplayer.packets.Packet00Login;

/**
 *  Server class starts the server so anyone could join it
 *
 * @author Cheremnykh Olesia and Dmitrii Zamedianskii
 * @version 1.0
 */
public class Server extends Thread{

    private DatagramSocket socket;
    private final Game game;
    private final List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
    private static final Logger logger = Logger.getLogger("multiplayer.Server");

    public Server(Game game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to start the server
     */
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
            logger.info("Server is working");
        }
    }

    /**
     * Method to parse every packet
     * @param data - data that will be parsed
     * @param address - server address
     * @param port - server port
     */
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                Packet00Login packet = new Packet00Login(data);
                logger.info("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected...");
                PlayerMP player = new PlayerMP(game.getLevel(), 800, 200, packet.getUsername(), address, port);
                if (player != null) {
                    this.connectedPlayers.add(player);
                    game.getLevel().addPlayer(player);
                    game.player = player;
                }
                break;
        }
    }

    /**
     * Method to send data about game states.
     * @param data - data that will be sent
     * @param ipAddress - address to send data to
     * @param port - port to send data to
     */
    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        }catch (IOException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for(PlayerMP p : connectedPlayers){
            sendData(data, p.ipAddress, p.port);
        }
    }
}

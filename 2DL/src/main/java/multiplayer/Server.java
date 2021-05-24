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
import controller.Listeners;
import multiplayer.packets.Packet;
import multiplayer.packets.Packet.PacketTypes;
import multiplayer.packets.Packet00Login;
import multiplayer.packets.Packet02Move;

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
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
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
        Packet packet = null;
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                packet = new Packet00Login(data);
                logger.info("[" + address.getHostAddress() + ":" + port + "] "
                        + ((Packet00Login)packet).getUsername() + " has connected...");
                PlayerMP player = new PlayerMP(game.getLevel(), 90, 530,  ((Packet00Login)packet).getUsername(), address, port);
                this.addConnection(player,  ((Packet00Login)packet));
                break;
            case MOVE:
                packet = new Packet02Move(data);
                System.out.println(((Packet02Move) packet).getUsername() + "has moved to" + ((Packet02Move) packet).getX() + "," + ((Packet02Move) packet).getY());
                this.handleMove((Packet02Move) packet);
                break;
        }
    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean isConnected = false;
        for (PlayerMP p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                isConnected = true;
            } else {
                sendData(packet.getData(), p.ipAddress, p.port);
                packet = new Packet00Login(p.getUsername());
                sendData(packet.getData(), player.ipAddress, player.port);
            }
            if (!isConnected) {
                this.connectedPlayers.add(player);
                game.getLevel().addPlayer(player);

            }
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

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        } return index;
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public void handleMove(Packet02Move packet02Move) {
        if (getPlayerMP(packet02Move.getUsername()) != null) {
            int index = getPlayerMPIndex(packet02Move.getUsername());
            this.connectedPlayers.get(index).xPos = packet02Move.getX();
            this.connectedPlayers.get(index).yPos = packet02Move.getY();
            packet02Move.writeData(this);
        }
    }
}

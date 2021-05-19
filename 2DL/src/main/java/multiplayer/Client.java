package multiplayer;

import controller.Game;
import model.Textures;

import java.io.IOException;
import java.net.*;

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

            String message = new String(packet.getData());
            System.out.println("SERVER < " + message.trim());

        }
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

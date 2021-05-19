package multiplayer.packets;

import multiplayer.Client;
import multiplayer.Server;

/**
 * Abstract class Packet
 */
public abstract class Packet {
    /**
     * enum that lets you determine the right action from
     *      server-client connection
     */
    public enum PacketTypes{
        INVALID(-1), LOGIN(00), DISCONNECT(01);

        private int packetId;

        PacketTypes(int packetId){
            this.packetId = packetId;
        }

        public int getId(){
            return packetId;
        }
    }

    public byte packetId;

    public Packet(int packetId){ // returning packetId in bytes
        this.packetId = (byte) packetId;
    }

    public abstract void writeData(Client client);
    public abstract void writeData(Server server);

    public String readData(byte[] data){ // reading data from the packet-message
        String message = new String(data).trim();
        return message.substring(2);
    }

    public abstract byte[] getData();

    public static PacketTypes lookupPacket(String packetId){ // looking for right packet-message
        try {
            return lookupPacket(Integer.parseInt(packetId));
        } catch (NumberFormatException e){
            return PacketTypes.INVALID;
        }
    }

    public static PacketTypes lookupPacket(int id){ // looking for right packet with an id
        for (PacketTypes p : PacketTypes.values()){
            if (p.getId() == id){
                return p;
            }
        }
        return PacketTypes.INVALID;
    }
}
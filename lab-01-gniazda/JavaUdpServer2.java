import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class JavaUdpServer2 {

    public static void main(String args[]) {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try {
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg);

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();

                System.out.println("Address: " + address);

                int nb = ByteBuffer.wrap(receiveBuffer).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt()+1;
                byte[] buff = ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(nb).array();

                DatagramPacket sendPacket = new DatagramPacket(buff, buff.length, address, port);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}

package org.example;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    private final DatagramSocket datagramSocket;
    private final InetAddress inetAddress;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenReceive() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String messageToSend = scanner.nextLine();
                byte[] buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, 3000);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("The Server says you said: " + messageFromServer);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Send datagram packets to a server");
        client.sendThenReceive();
    }
}



import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

public class MultithreadedTCPServer {
	
	public static ArrayList offerList = new ArrayList<Offer>();
	public static ArrayList demandList = new ArrayList<Demand>();
	
    public static void main(String[] args) throws Exception{
        int port = 9999;
        ServerSocket listenSocket = new ServerSocket(port);
        System.out.println("Multithreaded Server starts on Port "+port);
        while (true){
            Socket client = listenSocket.accept();
            System.out.println("Connection with: " +     // Output connection
                    client.getRemoteSocketAddress());   // (Client) address
            new TradeService(client).start();
            
        }
    }
}

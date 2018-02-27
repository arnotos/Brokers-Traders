//package examples;

/*
 * 22. 10. 10
 */

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

  static String line;
  static Socket socket;
  static BufferedReader fromServer;
  static DataOutputStream toServer;
  static UserInterface user = new UserInterface();

  public static void main(String[] args) throws Exception {
    //socket = new Socket("localhost", 9999);
    socket = new Socket("172.17.1.118", 9999);
	  toServer = new DataOutputStream(     // Datastream FROM Server
      socket.getOutputStream());
    fromServer = new BufferedReader(     // Datastream TO Server
      new InputStreamReader(socket.getInputStream()));
    while (sendRequest()) {              // Send requests while connected
      receiveResponse();                 // Process server's answer
    }
    socket.close();
    toServer.close();
    fromServer.close();
  }

  private static boolean sendRequest() throws IOException {
    boolean holdTheLine = true;          // Connection exists
    String type;
    String actionName;
    int quantity;
    double price;
    String trame;
    
    while(true)
    {   
		user.output("Do you want to Buy or Sell (b or s) : ");
	    type = user.input();
		if(type.length() == 1 && (type.toUpperCase().equals("B") || type.toUpperCase().equals("S")))
		{
			break;
		}
		else
		{
			System.out.println("Wrong action !");
			continue;
		}
	}
    
    
    while(true)
    {
        user.output("What is the action name : ");
        actionName = user.input();
        if(actionName.length() == 4)
        {
        	break;
        }
        else
        {
        	System.out.println("Wrong action name !");
        }
    }


    while(true)
    {
        user.output("Which quantity : ");
        
        try {
            quantity = Integer.parseInt(user.input());
		} catch (Exception e) {
			System.out.println("Wrong format !");
			continue;
		}
        
        if(quantity > 0)
        {
        	break;
        }
        else
        {
        	System.out.println("Quantity have to be superior to 0 !");
        }
    }

    while(true)
    {
        user.output("Which price : ");
        
        try
        {
        	price = Double.parseDouble(user.input());
        }catch (Exception e) {
			System.out.println("Wrong format !");
			continue;
		}
        if(quantity > 0)
        {
        	break;
        }
        else
        {
        	System.out.println("Price have to be superior to 0 !");
        }
    }
    
    
    //After verification
    trame = type + "//" + actionName + "//" + quantity + "//" + price + "//";
    System.out.println(trame.getBytes("UTF-8").length);
    trame = trame.getBytes("UTF-8").length + "//" + trame;
    
    toServer.writeBytes((line =  trame) + '\n');
    if (line.equals(".")) {              // Does the user want to end the session?
      holdTheLine = false;
    }
    return holdTheLine;
  }

  private static void receiveResponse() throws IOException {
    user.output("Server answers: " +
      new String(fromServer.readLine()) + '\n');
  }
}

package sources;


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

//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class to manage TCP Client
 * @author William
 *
 */
public class TCPClient {

  // Attribut
  static String line;
  static Socket socket;
  static BufferedReader fromServer;
  static DataOutputStream toServer;
  static UserInterface user = new UserInterface();

  public static void main(String[] args) throws Exception {
	//Selection if we want an automatic sell or buy client  
	String clientMode;
	user.output("Choose if you want an :\n -automatic buyer (b)\n -automatic seller (s)\n -automatic buyer and seller (c)\n -manual client (m)\n\n");
	
	while(true)
	{
		user.output("Your choice : ");
		clientMode = user.input();
		if(clientMode.toUpperCase().equals("B") || clientMode.toUpperCase().equals("S") || clientMode.toUpperCase().equals("M") ||clientMode.toUpperCase().equals("C") )
		{
			break;
		}
		else
		{
			user.output("Wrong option !\n");
			continue;
		}
	}
	  
    socket = new Socket("localhost", 9999);

    //socket = new Socket("172.17.1.57", 9999);


	toServer = new DataOutputStream(     // Datastream FROM Server
    socket.getOutputStream());
    fromServer = new BufferedReader(     // Datastream TO Server
      new InputStreamReader(socket.getInputStream()));
    while (sendRequest(clientMode)) {              // Send requests while connected
      receiveResponse();                 // Process server's answer
    }
    socket.close();
    toServer.close();
    fromServer.close();
  }

  /**
   * Send a request to server
   * @param clientMode : to send offer/demand or manual share
   * @return : boolean = false when want to stop the communication with the server.
   * @throws IOException
   */
  private static boolean sendRequest(String clientMode) throws IOException {
    boolean holdTheLine = true;          // Connection exists
    String type;
    String actionName;
    int quantity;
    double price;
    String trame;
    
    if(clientMode.toUpperCase().equals("M"))
    {
	    while(true)
	    {   
			user.output("Do you want to Buy or Sell (b or s) : ");
		    type = user.input();
			if(type.length() == 1 && (type.toUpperCase().equals("B") || type.toUpperCase().equals("S")))
			{
				break;
			}
			else if (line.equals("."))
			{              // Does the user want to end the session?
		        holdTheLine = false;
		        return holdTheLine;
		    }
			else
			{
				user.output("Wrong action !");
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
	        	user.output("Wrong action name !");
	        	continue;
	        }
	    }
	
	
	    while(true)
	    {
	        user.output("Which quantity : ");
	        
	        try {
	            quantity = Integer.parseInt(user.input());
			} catch (Exception e) {
				user.output("Wrong format !");
				continue;
			}
	        
	        if(quantity > 0)
	        {
	        	break;
	        }
	        else
	        {
	        	user.output("Quantity have to be superior to 0 !");
	        	continue;
	        }
	    }
	
	    while(true)
	    {
	        user.output("Which price : ");
	        
	        try
	        {
	        	price = Double.parseDouble(user.input());
	        }catch (Exception e) {
	        	user.output("Wrong format !");
				continue;
			}
	        if(quantity > 0)
	        {
	        	break;
	        }
	        else
	        {
	        	user.output("Price have to be superior to 0 !");
	        	continue;
	        }
	    }
    }
    else
    {
    	//An automatic client
    	if(clientMode.toUpperCase().equals("B"))
    	{
    		user.output("Press enter to create a new buy demand : ");
    		user.input();
    		type = "B";
    	}
    	else if(clientMode.toUpperCase().equals("S"))
    	{
    		user.output("Press enter to create a new sell demand : ");
    		user.input();
    		type = "S";
    	}
    	else if(clientMode.toUpperCase().equals("C"))
    	{
    		user.output("Press enter to create a new demand : ");
    		user.input();
    		if(ThreadLocalRandom.current().nextInt(0, 1 + 1) == 1)
    		{
    			type = "B";
    		}
    		else
    		{
    			type = "S";
    		}
    	}
    	else
    	{
    		//Probleme
    		type ="";
    	}
    	
    	actionName = "AAAA";
    	quantity = ThreadLocalRandom.current().nextInt(1000, 1010 + 1);
    	price = 1000;
    }
    
    //After verification
    trame = type + "//" + actionName + "//" + quantity + "//" + price + "//";
    //System.out.println(trame.getBytes("UTF-8").length);
    user.output("Trame sended : " + trame + "\n");
    trame = trame.getBytes("UTF-8").length + "//" + trame;
    
    toServer.writeBytes((line =  trame) + '\n');
    return holdTheLine;
  }

  /**
   * Display the server's response
   * 
   * @throws IOException
   */
  private static void receiveResponse() throws IOException {
    user.output("Server answers: " +
      new String(fromServer.readLine()) + '\n');
  }
}

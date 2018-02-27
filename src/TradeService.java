/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * @author laurent.freyss
 *
 */	
public class TradeService extends Thread{
    Socket client;
   
    
    TradeService(Socket client) {	  
    	this.client = client;
    } 
    
    @Override
    public void run (){
        String line;
        BufferedReader fromClient;
        DataOutputStream toClient;
        boolean stop = true;
        System.out.println("Thread started: "+this); // Display Thread-ID
        try{
        	
            fromClient = new BufferedReader              // Datastream FROM Client
            (new InputStreamReader(client.getInputStream()));
            toClient = new DataOutputStream (client.getOutputStream()); // TO Client
           
            while(stop){     // repeat as long as connection exists
            	
            	 line = fromClient.readLine();              // Read Request
            	 String response = createOfferOrDemand(line);
                 System.out.println("Received: "+ line);
                 
                
                if (line.equals(".")) stop = false;   // Break Conneciton?
                else toClient.writeBytes(response+"\n"); // Response
            }
            
            fromClient.close(); toClient.close(); client.close(); // End
            System.out.println("Thread ended: "+this);
            
        }catch (Exception e){
        	System.out.println(e);
        }
    }
    
    public String createOfferOrDemand(String line) {
    	String[] data = line.split("//");
    	if(data[1].equals("B") || data[1].equals("b")) {
    		Action of = new Offer(data[2], Integer.parseInt(data[3]));
    		MultithreadedTCPServer.offerList.add(of);
    		return runMechanism(of);
    	} else {
    		Action de = new Demand(data[2], Integer.parseInt(data[3]));
    		MultithreadedTCPServer.demandList.add(de);
    		return runMechanism(de);
    	}
    }
    
    public String runMechanism(Action act) {
    	if(act instanceof Offer) {
    		for (Iterator<Demand> i = MultithreadedTCPServer.demandList.iterator(); i.hasNext();) {
        	    Demand item = i.next();
        	    if(item.getActionLabel().equals(((Offer) act).getActionLabel())) {
    				return "Successfully found a demand.";
    			}
        	}
    	} else {
    		for (Iterator<Offer> i = MultithreadedTCPServer.offerList.iterator(); i.hasNext();) {
    			Offer item = i.next();
        	    if(item.getActionLabel().equals(((Demand) act).getActionLabel())) {
    				return "Successfully found an offer.";
    			}
        	}
    	}
    	
    			
    	return "Matchmaking not found..";
    }
	
}

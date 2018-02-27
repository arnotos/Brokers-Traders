/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
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


	private DataOutputStream toClient;
   
    
    TradeService(Socket client) {	  
    	this.client = client;
    } 
    
    @Override
    public void run (){
        String line;
        BufferedReader fromClient;
        boolean stop = true;
        System.out.println("Thread started: "+this); // Display Thread-ID
        try{
        	
            fromClient = new BufferedReader              // Datastream FROM Client
            (new InputStreamReader(client.getInputStream()));
            setToClient(new DataOutputStream (client.getOutputStream())); // TO Client
           
            while(stop){     // repeat as long as connection exists
            	
            	 line = fromClient.readLine();              // Read Request
            	 String response = createOfferOrDemand(line);
                 System.out.println("Received: "+ line);
                 
                
                if (line.equals(".")) stop = false;   // Break Connection?
                else if(response != null) this.responseToClient(response+"\n"); // Response
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
    		Action of = new Offer(data[2], Integer.parseInt(data[3]), this);
    		MultithreadedTCPServer.offerList.add(of);
    		return runMechanism(of);
    	} else {
    		Action de = new Demand(data[2], Integer.parseInt(data[3]), this);
    		MultithreadedTCPServer.demandList.add(de);
    		return runMechanism(de);
    	}
    }
    
    public String runMechanism(Action act) {
    	
    	if(act instanceof Offer) {
    		Demand item = new Demand("TEST",1,this);
    		for (Iterator<Demand> i = MultithreadedTCPServer.demandList.iterator(); i.hasNext();) {
        	    item = i.next();
        	    if(item.getActionLabel().equals(((Offer) act).getActionLabel())) {
        	    	try {
						((TradeService) item.client).responseToClient("Waiting for : Successfully sell " + item.getActionLabel() + " shares.");
						
						this.responseToClient("Successfully sell " + ((Offer) act).getActionLabel() +" shares.");
						break;
        	    	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
        	    }	
        	}
    		MultithreadedTCPServer.demandList.remove(item);
    	} else {
    		Offer item = new Offer("TEST",1,this);
    		for (Iterator<Offer> i = MultithreadedTCPServer.offerList.iterator(); i.hasNext();) {
    			item = i.next();
        	    if(item.getActionLabel().equals(((Demand) act).getActionLabel())) {
						
        	    	try {
        	    		((TradeService) item.client).responseToClient("Waiting for : Successfully sell " + item.getActionLabel() + " shares.");
    					
            	    	
						this.responseToClient("Successfully buy " + ((Demand) act).getActionLabel() +" shares.");
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
    			}
        	}
    		MultithreadedTCPServer.offerList.remove(item);
    	}
    	
    			
    	return null;
    }
    
    public void responseToClient(String message) throws IOException {
		((DataOutputStream) this.getToClient()).writeBytes(message);
    }
    
    //Getter//Setter
	public DataOutputStream getToClient() {
		return toClient;
	}

	public void setToClient(DataOutputStream toClient) {
		this.toClient = toClient;
	}
	
}

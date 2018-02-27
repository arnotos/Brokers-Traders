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
                 
                
                if (line.equals(".")) stop = false;   // Break Connection?
                else if(response != null) {
                	this.responseToClient(toClient, response);
                }
                
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
    	String currentMessage = "";
    	String secondClientMessage = "";
    	if(act instanceof Offer) {
    		Demand item = null;
    		for (Iterator<Demand> i = MultithreadedTCPServer.demandList.iterator(); i.hasNext();) {
        	    item = i.next();
        	    if(item.getActionLabel().equals(((Offer) act).getActionLabel())) {
        	    	try {
        	    		secondClientMessage = "Waiting for : Successfully buy " + item.getActionLabel() + " shares.";
        	    		currentMessage = "Successfully sell " + ((Offer) act).getActionLabel() +" shares.";
        	    	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
        	    }	
        	}
    		if(item != null) {
        		MultithreadedTCPServer.demandList.remove(item);
        		return currentMessage;
    		}else {
    			waitForAction();
    			return secondClientMessage;
    		}
    	} else {
    		Offer item = null;
    		for (Iterator<Offer> i = MultithreadedTCPServer.offerList.iterator(); i.hasNext();) {
    			item = i.next();
        	    if(item.getActionLabel().equals(((Demand) act).getActionLabel())) {
						
        	    	try {
        	    		secondClientMessage = "Waiting for : Successfully sell " + item.getActionLabel() + " shares.";
        	    		currentMessage = "Successfully buy " + ((Demand) act).getActionLabel() +" shares.";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
    			}
        	}
    		if(item != null) {
        		MultithreadedTCPServer.offerList.remove(item);
        		return currentMessage;
    		} else {
    			waitForAction();
    			return secondClientMessage;
    		}
    		
    	}
    }

    public synchronized void waitForAction() {
      try {
         
    	wait();

      } catch (Exception e) {
         e.printStackTrace();
      }
    }
    
    public synchronized void responseToClient(DataOutputStream toClient, String response) {
    	try {
    		toClient.writeBytes(response+"\n"); // Response
    		notifyAll();

          } catch (Exception e) {
             e.printStackTrace();
          }
    }
}

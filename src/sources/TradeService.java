package sources;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import tools.CSVTools;

/**
 * Service to manage the client's connections/sockets, offer and demand for shares/stosks and their responses
 * @author laurent.freyss
 *
 */	
public class TradeService extends Thread{
	
	// Attribut
    Socket client; // socket for current client
    private DataOutputStream toClient; // stream for current client
   
    Object lock = new Object(); // Like mutex
    
    //	Constructor
	TradeService(Socket client) {	  
    	this.client = client;
    } 
    
	/**
	 * Run the thread
	 */
    @Override
    public void run (){
        String line;
        BufferedReader fromClient;
       
        boolean stop = true;
        System.out.println("Thread started: "+this); // Display Thread-ID
        try{
        	
            fromClient = new BufferedReader              // Datastream FROM Client
            (new InputStreamReader(client.getInputStream()));
            
            this.setToClient(new DataOutputStream (client.getOutputStream())); // TO Client
            CSVTools.createFile();
            while(stop){     // repeat as long as connection exists
            	
            	 line = fromClient.readLine();              // Read Request
            	 if (line.equals(".")) 
                 	break;
            	 Thread.sleep(new Random().nextInt(2000)+1000);
            	 CSVTools.addRows(line);
            	 String response = createOfferOrDemand(line);
                 System.out.println("Received: "+ line);
                 
                
                
                if(response != null) {
                	this.getToClient().writeBytes(response + "\n");
                }
                
            }
            
            fromClient.close(); this.getToClient().close(); client.close(); // End
            System.out.println("Thread ended: "+this);
            
        }catch (Exception e){
        	System.out.println(e);
        }
    }
    
    /**
     * Create an offer or demand due to the client's request
     * @param line : client's request
     * @return the response for the client's socket 
     */
    public String createOfferOrDemand(String line) {
    	String[] data = line.split("//");
    	if(data[1].equals("B") || data[1].equals("b")) {
    		Action of = new Offer(data[2], Integer.parseInt(data[3]),Double.parseDouble(data[4]), this);
    		MultithreadedTCPServer.offerList.add(of);
    		return runMechanism(of);
    	} else {
    		Action de = new Demand(data[2], Integer.parseInt(data[3]),Double.parseDouble(data[4]), this);
    		MultithreadedTCPServer.demandList.add(de);
    		return runMechanism(de);
    	}
    }
    
    /**
     * This mechanism search an offer or demand that correspond with an other demand or offer
     * @param act : is an offer or demand, depend of the client's request 
     * @return the response for the client's socket
     */
    public String runMechanism(Action act) {
    	String currentMessage = "";
    	String secondClientMessage = "";
    	boolean hasFind = false;
    	// It's an offer (selling) so search a demand 
    	if(act instanceof Offer) {
    		Demand item = null;
    		for (Iterator<Demand> i = MultithreadedTCPServer.demandList.iterator(); i.hasNext();) {
        	    item = i.next();
        	    if(item.getActionLabel().equals(((Offer) act).getActionLabel())) {
        	    	try {
        	    		secondClientMessage = "Waiting for : Successfully sell " + item.getActionLabel() + " shares.";
        	    		currentMessage = "Successfully buy " + ((Offer) act).getActionLabel() +" shares.";
        	    		
        	    		// Response to other client
        	    		responseToClient(item.client, secondClientMessage);
        	    		
        	    		hasFind = true;
        	    	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
        	    }	
        	}
    		if(item != null && hasFind) {
        		MultithreadedTCPServer.demandList.remove(item);
        		MultithreadedTCPServer.offerList.remove(act);
        		System.out.println("Size OfferList: "+ MultithreadedTCPServer.offerList.size());
        		System.out.println("Size DemandList: "+ MultithreadedTCPServer.demandList.size());
        		return currentMessage;
    		}
    		// No demand so waiting for action
    		else {
    			waitForAction();
    			return null;
    		}
    	}
    	// It's a demand so search an offer 
    	else {
    		Offer item = null;
    		for (Iterator<Offer> i = MultithreadedTCPServer.offerList.iterator(); i.hasNext();) {
    			item = i.next();
        	    if(item.getActionLabel().equals(((Demand) act).getActionLabel())) {
						
        	    	try {
        	    		secondClientMessage = "Waiting for : Successfully buy " + item.getActionLabel() + " shares.";
        	    		currentMessage = "Successfully sell " + ((Demand) act).getActionLabel() +" shares.";
        	    		
        	    		// Response to other client
        	    		responseToClient(item.client, secondClientMessage);
        	    		
        	    		hasFind=true;
					} catch (Exception e) {
						// Auto-generated catch block
						e.printStackTrace();
						System.out.println(e);
					}
    			}
        	}
    		if(item != null && hasFind) {
        		MultithreadedTCPServer.offerList.remove(item);
        		MultithreadedTCPServer.demandList.remove(act);
        		System.out.println("Size OfferList: "+ MultithreadedTCPServer.offerList.size());
        		System.out.println("Size DemandList: "+ MultithreadedTCPServer.demandList.size());
        		return currentMessage;
    		}
    		// No offer so waiting for action
    		else {
    			waitForAction();
    			return null;
    		}
    		
    	}
    }

    /**
     * Thread is waiting
     */
    public void waitForAction() {
    	synchronized(lock){
	    	try {
	    		lock.wait();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
    	}
    }
    
    /**
     * Thread is notify with a response for the client's socket
     * @param otherTradeService : Thread which must be notify
     * @param response : message for the client's socket
     */
    public void responseToClient(TradeService otherTradeService, String response) {
    	
    	try {
    		otherTradeService.getToClient().writeBytes(response+"\n"); // Response
    		synchronized(otherTradeService.lock){
    			
    			otherTradeService.lock.notify();
    		}
          } catch (Exception e) {
             e.printStackTrace();
          }
    }
    
    //Getter //Setter
    public DataOutputStream getToClient() {
		return toClient;
	}

	public void setToClient(DataOutputStream toClient) {
		this.toClient = toClient;
	}
}

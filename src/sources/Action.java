package sources;

import java.net.Socket;

/**
 * Model Object Action, mother class for offer and demand
 * @author laurent.freyss
 *
 */

public class Action {
	// Attribut
	private int id = 0;
	TradeService client;
	private int price;
	

	// Constructor
	public Action(TradeService client, int price) {
		this.id++;
		this.client = client;
		this.price = price;
		//this.ip = ip;
		//this.port = port;
	}
	
	// Getter//Setter
	public TradeService getClient() {
		return client;
	}

	public void setClient(TradeService client) {
		this.client = client;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

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
	
	// Constructor
	public Action(TradeService client) {
		this.id++;
		this.client = client;
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

}

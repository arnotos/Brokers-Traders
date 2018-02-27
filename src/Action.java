import java.net.Socket;

/**
 * 
 */

/**
 * @author laurent.freyss
 *
 */


public class Action {
	private int id = 0;
	TradeService client;
	
	public Action(TradeService client) {
		this.id++;
		this.client = client;
		//this.ip = ip;
		//this.port = port;
	}
	
	public TradeService getClient() {
		return client;
	}

	public void setClient(TradeService client) {
		this.client = client;
	}

}

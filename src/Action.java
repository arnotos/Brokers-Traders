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
	Socket client;
	
	public Action(Socket client) {
		this.id++;
		this.client = client;
		//this.ip = ip;
		//this.port = port;
	}
	
	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

}

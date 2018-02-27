import java.net.Socket;

/**
 * 
 */

/**
 * @author laurent.freyss
 *
 */
public class Demand extends Action {
	
	//attribut
	private String actionLabel;
	private int quantity;
	
	
	//constructor
	public Demand(String actionLabel,  int quantity, Socket client){
		super(client);
		this.actionLabel = actionLabel;
		this.quantity = quantity;
	}
	
	
	//getter//setter
	public String getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(String actionLabel) {
		this.actionLabel = actionLabel;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

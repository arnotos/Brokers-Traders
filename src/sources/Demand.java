package sources;

import java.net.Socket;

/**
 * Model Object Demand to buy stocks
 * @author laurent.freyss
 *
 */
public class Demand extends Action {
	
	//attribut
	private String actionLabel;
	private int quantity;
	
	
	//constructor
	public Demand(String actionLabel,  int quantity, double d, TradeService client){
		super(client, d);
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

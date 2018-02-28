package sources;

import java.net.Socket;

/**
 * Model Object Offer to sell stocks
 * @author laurent.freyss
 *
 */
public class Offer extends Action {
	
	//attribut
	private String actionLabel;
	private int quantity;
	
	
	//constructor
	public Offer(String actionLabel,  int quantity, double price, TradeService client){
		super(client, price);
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

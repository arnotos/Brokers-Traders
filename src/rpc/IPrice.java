package rpc;

import java.util.ArrayList;
import java.util.Date;

public interface IPrice {
	
	int getPrice(String type);
	
	ArrayList<Integer> getAllPrices(String type);
	
	float getPricesbetween(String type, Date dateBegin, Date dateEnd);
	
	float getAveragePrice(String type);
	
	int getLowestPrice(String type);
	
	int getHighestPrice(String type);
	
}

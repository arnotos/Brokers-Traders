package rpc;

import java.util.ArrayList;
import java.util.Date;

public interface IPrice {

	/**
	 * Simple method to get the price of a product (highest price by default)
	 * 
	 * @param type
	 *            name of the specific product transaction
	 * @return the highest price find
	 */
	double getPrice(String name);

	/**
	 * return all prices found of the type action
	 * 
	 * @param type
	 *            name of the action
	 * @return all the prices of this action
	 */
	ArrayList<Double> getAllPrices(String type);

	float getPricesbetween(String type, Date dateBegin, Date dateEnd);

	float getAveragePrice(String type);

	int getLowestPrice(String type);

	/**
	 * Getting the highest price of the action
	 * 
	 * @param type
	 *            name of the action
	 * @return the price
	 */
	double getHighestPrice(String type);

}

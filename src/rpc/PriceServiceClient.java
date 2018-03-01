package rpc;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import sources.UserInterface;

/**
 * 
 * @author arnaud lapenna
 * 
 *         A client class who call the server with RPC-XML to get the price of a
 *         product and display it.
 *
 */
public class PriceServiceClient {

	public static UserInterface user = new UserInterface();
	public static String type = "";
	public static String methodWanted = "a";

	public static void main(String[] args) throws Exception {

		// instanciations for RPC XML
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		XmlRpcClient client = new XmlRpcClient();

		/**
		 * @TODO change the serveur url
		 **/

		//config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
		config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
		client.setConfig(config);

		// setting the params
		// Table of object needed to match the params of the functions.
		Object[] params = new Object[] { type };
		double result;
		List<Double> results;

		user.output("To end the process type 'exit'\n");
		while (true) {
			user.output("Do you want to get all hystory prices (h) or the actual price (a) : ");
			methodWanted = user.input();

			user.output("Enter the type you want to get the price of : ");
			type = user.input();

			params[0] = type;

			if (type.equals("exit") || methodWanted.equals("exit")) {
				break;
			}

			switch (methodWanted) {
			case "a":
				result = (double) client.execute("Pricing.getPrice", params);
				user.output("Price result = " + result + "\n");
				break;
			case "h":
				results = decodeList(client.execute("Pricing.getAllPrices", params));
				user.output("Price(s) result = " + results.toString() + "\n");
				break;
			default:
				user.output("BAD option re-try.\n");
			}
		}

		user.output("## PRICE SERVICE CLIENT END ##");
		user.finalize();
	}

	/**
	 * Method to decode a List comunicated by RPC-XML protocol
	 * 
	 * Use in this case to decode the list of prices just recieved.
	 * 
	 * @param element
	 *            Object in which contain the List.
	 * @return a decoded List from an Object type
	 */
	public static List<Double> decodeList(Object element) {
		if (element == null) {
			return null;
		}
		if (element instanceof List) {
			return (List) element;
		}
		if (element.getClass().isArray()) {
			int length = Array.getLength(element);
			ArrayList result = new ArrayList();
			for (int i = 0; i < length; i++) {
				result.add(Array.get(element, i));
			}
			return result;
		}
		return null;
	}
}

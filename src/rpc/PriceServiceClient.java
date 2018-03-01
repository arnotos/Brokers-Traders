package rpc;

import java.net.URL;

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

	public static void main(String[] args) throws Exception {

		// instanciations for RPC XML
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		XmlRpcClient client = new XmlRpcClient();

		/**
		 * @TODO change the serveur url
		 **/
		config.setServerURL(new URL("http://172.17.1.123:8080/xmlrpc"));
		client.setConfig(config);

		// setting the params
		// Table of object needed to match the params of the functions.
		Object[] params = new Object[] { type };
		double result;
		
		user.output("To end the process type 'exit'\n");
		while (true) {
			user.output("Enter the type you want to get the price of : ");
			type = user.input();
			params[0]=type;
			if (type.equals("exit")) {
				break;
			}
			result = (double) client.execute("Pricing.getPrice", params);
			user.output("Price result = " + result + "\n");
		}
		user.output("## PRICE SERVICE CLIENT END ##");
		user.finalize();
	}
}

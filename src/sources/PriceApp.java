package sources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class PriceApp {

	public Integer getPrice(String name) {
		ArrayList<String[]> actions = readCSV();
		int maxPrice = 0;
		for (Iterator<String[]> i = actions.iterator(); i.hasNext();) {
			String[] item = i.next();
			if (item[2].equals(name)) {
				if (Integer.parseInt(item[4]) >= maxPrice) {
					maxPrice = Integer.parseInt(item[3]);
				}
			}
		}
		return maxPrice;
	}

	public Integer sub(int x, int y) {
		return new Integer(x - y);
	}

	public Integer mul(int x, int y) {
		return new Integer(x * y);
	}

	private static final int port = 8080;

	public static void main(String[] args) {
		try {
			System.out.println("Attempting to start XML-RPC Server...");
			WebServer webServer = new WebServer(port);

			XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
			PropertyHandlerMapping phm = new PropertyHandlerMapping();

			phm.addHandler("PriceApp", PriceApp.class);
			xmlRpcServer.setHandlerMapping(phm);

			XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
			// serverConfig.setEnabledForExtensions(true);
			// serverConfig.setContentLengthOptional(false);

			webServer.start();

			System.out.println("Started successfully.");
			System.out.println("Accepting requests. (Halt program to stop.)");

		} catch (Exception exception) {
			System.err.println("JavaServer: " + exception);
		}

	}

	private ArrayList<String[]> readCSV() {
		String csvFile = "/Users/laurent.freyss/Documents/CNAM/Distributed System/BrokerTraders.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		ArrayList<String[]> allActionFromCsv = new ArrayList<String[]>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] action = line.split(cvsSplitBy);
				allActionFromCsv.add(action);
				System.out.println("Action [code= " + action[1] + " , name=" + action[2] + " , quantity=" + action[3]
						+ " , price=" + action[4] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return allActionFromCsv;
		}

	}
}

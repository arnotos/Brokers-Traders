package rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import tools.CSVTools;

public class PriceServer implements IPrice {
	
  private static final int port = 8080;

  public static void main (String [] args) {
    try {

      WebServer webServer = new WebServer(port);

      XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
      PropertyHandlerMapping phm = new PropertyHandlerMapping();

      phm.addHandler( "Pricing", PriceServer.class);
      xmlRpcServer.setHandlerMapping(phm);

     XmlRpcServerConfigImpl serverConfig =
              (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
     // serverConfig.setEnabledForExtensions(true);
     // serverConfig.setContentLengthOptional(false);

      webServer.start();

      System.out.println("The Calculator Server has been started..." );

    } catch (Exception exception) {
       System.err.println("JavaServer: " + exception);
    }
  }

@Override
public double getPrice(String type) {
	ArrayList<String[]> actions = CSVTools.readCSV();
	double maxPrice = 0;
	for (Iterator<String[]> i = actions.iterator(); i.hasNext();) {
		String[] item = i.next();
		if (item[2].equals(type)) {
			if (Double.parseDouble(item[4]) >= maxPrice) {
				maxPrice = Double.parseDouble(item[4]);
			}
		}
	}
	return maxPrice;
}

@Override
public ArrayList<Integer> getAllPrices(String type) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public float getPricesbetween(String type, Date dateBegin, Date dateEnd) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public float getAveragePrice(String type) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getLowestPrice(String type) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getHighestPrice(String type) {
	// TODO Auto-generated method stub
	return 0;
}
}

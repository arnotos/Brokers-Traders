

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;


public class PriceAppClient {
      public static void main(String[] args) throws Exception {

    XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

    config.setServerURL(new URL("http://127.0.0.1:8080/xmlrpc"));
    XmlRpcClient client = new XmlRpcClient();
    client.setConfig(config);

    Object[] params = new Object[]{new String("AAPL")};
	System.out.println("About to get results...(params[0] = " + params[0] 
                           + ")." );

    Integer result = (Integer) client.execute("PriceApp.getPrice", params);
    System.out.println("Get CSV = " + result.toString() );

  
	
    }
}

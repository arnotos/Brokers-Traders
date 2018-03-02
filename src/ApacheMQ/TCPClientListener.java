package ApacheMQ;

/*
 * 22. 10. 10
 */

/**
 *
 * @author Peter Altenberd
 * (Translated into English by Ronald Moore)
 * Computer Science Dept.                   Fachbereich Informatik
 * Darmstadt Univ. of Applied Sciences      Hochschule Darmstadt
 */

//import java.util.Scanner;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import sources.UserInterface;

/**
 * Class to manage TCP Client
 * 
 * @author William
 *
 */
public class TCPClientListener {

	// Attribut
	static String line;
	static Socket socket;
	static BufferedReader fromServer;
	static DataOutputStream toServer;
	static UserInterface userInterface = new UserInterface();

	public static void main(String[] args) throws Exception {

		String user = env("ACTIVEMQ_USER", "admin");
		String password = env("ACTIVEMQ_PASSWORD", "password");
		String host = env("ACTIVEMQ_HOST", "localhost");
		int port = Integer.parseInt(env("ACTIVEMQ_PORT", "61616"));
		String destination = arg(args, 0, "event");

		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://" + host + ":" + port);

		Connection connection = factory.createConnection(user, password);
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination dest = new ActiveMQTopic(destination);

		String clientMode;
		userInterface.output("Choose if you want a :\n -cyclic (c)\n -acyclic (a)\n\n");
		while (true) {
			userInterface.output("Your choice : ");
			clientMode = userInterface.input();
			if (clientMode.toUpperCase().equals("C") || clientMode.toUpperCase().equals("A")) {
				break;
			} else {
				userInterface.output("Wrong option !\n");
				continue;
			}
		}

		MessageConsumer consumer = session.createConsumer(dest);
		long start = System.currentTimeMillis();
		long count = 1;
		System.out.println("Trader Waiting for messages...");
		
		socket = new Socket("localhost", 9999);
		// socket = new Socket("172.17.1.57", 9999);
		
		toServer = new DataOutputStream( // Datastream FROM Server
				socket.getOutputStream());
		fromServer = new BufferedReader( // Datastream TO Server
				new InputStreamReader(socket.getInputStream()));
		
		while (true) {
			Message msg = consumer.receive();
			if (msg instanceof TextMessage) {
				String body = ((TextMessage) msg).getText();
				if ("SHUTDOWN".equals(body)) {
					long diff = System.currentTimeMillis() - start;
					System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
					toServer.writeBytes(".");
					break;
				} else {
					//System.out.println(msg.toString());
					if (count != msg.getIntProperty("id")) {
						System.out.println("mismatch: " + count + "!=" + msg.getIntProperty("id"));
					}
					count = msg.getIntProperty("id");

					if (count == 0) {
						start = System.currentTimeMillis();
					}

					// Selection if we want an automatic sell or buy client
					System.out.println(String.format("\n\n%s \n", body));
					
					sendRequest(clientMode, body); // Send requests
					receiveResponse(); // Process server's answer
					//sendRequest(clientMode, ".");
					
					

					
					// }
					count++;
				}

			} else {
				System.out.println("Unexpected message type: " + msg.getClass());
			}
		}
		
		socket.close();
		toServer.close();
		fromServer.close();
		connection.close();
	}

	private static String env(String key, String defaultValue) {
		String rc = System.getenv(key);
		if (rc == null)
			return defaultValue;
		return rc;
	}

	private static String arg(String[] args, int index, String defaultValue) {
		if (index < args.length)
			return args[index];
		else

			return defaultValue;
	}

	/**
	 * Send a request to server
	 * 
	 * @param clientMode
	 *            : to send cyclic/acyclic
	 * @return : boolean = false when want to stop the communication with the
	 *         server.
	 * @throws IOException
	 */
	private static boolean sendRequest(String clientMode, String body) throws IOException {
		boolean holdTheLine = true; // Connection exists
		String type;
		String actionName="";
		int quantity;
		double price;
		String trame;

		String[] bodySplit = body.split(" "); // Split the body
		// An automatic client
		if ((clientMode.toUpperCase().equals("C") || clientMode.toUpperCase().equals("A"))) {
			// user.output("Press enter to create a new buy demand : ");
			// user.input();

			if (bodySplit[0].equals("Bad") && clientMode.toUpperCase().equals("C")
					|| bodySplit[0].equals("Good") && clientMode.toUpperCase().equals("A")) {
				type = "S";
			} else {
				type = "B";
			}
			
			//System.out.println(body);

		} else {
			// Probleme close connexion
			type = " ";
		}

		actionName = bodySplit[3];
		quantity = ThreadLocalRandom.current().nextInt(1000, 1010 + 1);
		price = ThreadLocalRandom.current().nextInt(800, 1010 + 1);

		// After verification
		trame = type + "//" + actionName + "//" + quantity + "//" + price + "//";
		
		// System.out.println(trame.getBytes("UTF-8").length);
		userInterface.output("Trame sended : " + trame + "\n");
		trame = trame.getBytes("UTF-8").length + "//" + trame;

		toServer.writeBytes((line = trame) + '\n');
		return holdTheLine;
	}

	/**
	 * Display the server's response
	 * 
	 * @throws IOException
	 */
	private static void receiveResponse() throws IOException {
		userInterface.output("Server answers: " + new String(fromServer.readLine()) + '\n');
	}
}

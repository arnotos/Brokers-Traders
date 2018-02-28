package sources;

/**
 * 
 * @author arnaud lapenna
 *
 * @info custom exeption classes for error handling
 * 
 * Exeption class for the broker
 * 
 */
public class ServerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1754766737955662949L;
	
	//Custom message to identify the error and have a clear message of the incoming error
	String messageServer="";

	/**
	 * Basic constructor who print just 'ERREUR SERVER' when instantiated 
	 * 
	 */
	public ServerException(){
		System.out.println("ERREUR SERVER");
	}
	
	/**
	 * Advanced constructor of ServerException with a custom message
	 * 
	 * @param messagePerso
	 * 			the custom message describing the new error 
	 * @info set the custom message and print it immediately. 
	 */
	public ServerException(String messagePerso){
		messageServer=messagePerso;
		System.out.println(messageServer);		
	}
	
	/**
	 * Print the register message into the console
	 */
	public void showErrorMessage(){
		System.out.println(messageServer);
	}

	public String getMessageServer() {
		return messageServer;
	}

	public void setMessageServer(String messageServer) {
		this.messageServer = messageServer;
	}

}

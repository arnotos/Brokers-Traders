/**
 * 
 * @author arnaud lapenna
 *
 * @info custom exeption classes for error handling
 * 
 * Exception class for the traders
 * 
 */
public class ClientException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7065268619672827899L;
	
	//Custom message to identify the error and have a clear message of the incoming error
	String messageUser="";

	/**
	 * Basic constructor who print just 'ERREUR CLIENT' when instantiated 
	 * 
	 */
	public ClientException(){
		System.out.println("ERREUR CLIENT");
	}
	
	/**
	 * Advanced constructor of ClientException with a custom message
	 * 
	 * @param messagePerso
	 * 			the custom message describing the new error 
	 * @info set the custom message and print it immediately. 
	 */
	public ClientException(String messagePerso){
		messageUser=messagePerso;
		System.out.println(messageUser);		
	}
	
	/**
	 * Print the register message into the console
	 */
	public void showErrorMessage(){
		System.out.println(messageUser);
	}

	public String getMessageUser() {
		return messageUser;
	}

	public void setMessageUser(String messageUser) {
		this.messageUser = messageUser;
	}
	
}

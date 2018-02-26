

public class ClientException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7065268619672827899L;
	
	String messageUser="";

	public ClientException(){
		System.out.println("ERREUR CLIENT");
	}
	
	public ClientException(String messagePerso){
		messageUser=messagePerso;
		System.out.println(messageUser);		
	}
	
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

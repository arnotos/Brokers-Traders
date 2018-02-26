

public class ServerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1754766737955662949L;
	
	String messageServer="";

	public ServerException(){
		System.out.println("ERREUR SERVER");
	}
	
	public ServerException(String messagePerso){
		messageServer=messagePerso;
		System.out.println(messageServer);		
	}
	
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

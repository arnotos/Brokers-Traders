package sources;




/**
 * 
 * @author lapenna arnaud
 * Classe de test pour le premier tp ( non utile pour l'instant) 
 *
 */
public class Trader {
	
	 
	
	public static void main(String[] args) throws Exception {
		
		
		
		
		 for(int i=0;i<=20;i++){
			 System.out.println(randomAction());
		 }
	 }
	 
	 public void sell(){
		 
	 }
	 
	 public void byu(){
		 
	 }
	 
	 
	 public static int randomAction(){
		 return  (1 + (int)(Math.random() * ((2 - 1) + 1)));
	 }
	

}

import java.util.*;
package source;
public class SourceAleatoire extends Source{
	
	Information mess=new Information();
	
	public static boolean getRandomBoolean() {
	       return Math.random() < 0.5;
	       //I tried another approaches here, still the same result
	   }

	   public static void main(String[] args) {
		   
		   for(int i = 0; i<100; i++) {
			   mess.add(getRandomBoolean());
		   }
		   System.out.println(mess.toString());
	   }
	   
	  
}

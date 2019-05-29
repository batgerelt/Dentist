package payment;

public class userDetials {
	
	private String onoshN;
	private String tolborMedeelel;
	 public userDetials(String onoshN,String tolborMedeelel)
	 {
		 this.onoshN = onoshN;
		 this.tolborMedeelel = tolborMedeelel;
	 }	
	 public String getOnoshN()
	 {
		 return this.onoshN;
	 }
	 public String getTolborMedeelel()
	 {
		 return this.tolborMedeelel;
	 }
	
	 public void setOnoshN(String value)
	 {
		 this.onoshN = value;
	 }
	 public void setTolborMedeelel(String value)
	 {
		 this.tolborMedeelel = value;
	 }
		
}

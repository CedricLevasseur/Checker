public class StringSplitted  {

	private StringBuffer s ;

	public StringSplitted(String _s){
		s=new StringBuffer(_s);
	}

   public ArrayList<String> split(){
	ArrayList<String> toReturn =new  ArrayList<String>();
	int i=0
	int spIndex
	while((spIndex=s.indexOf(' ')) >= 0  && spIndex<=s.size() ){
		System.out.println(spIndex+" "+s );
		toReturn[i++] = s.substring(0,spIndex)
		s=new StringBuffer(s.substring(spIndex+1,s.size()))
	}
	toReturn[i++] = s //le dernier mot
	return toReturn
   }




}



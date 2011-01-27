//package Checker;


public class SizeFormatter{

    public static char[] unites=['B','K','M','G','T']

    public static String format(long bytesNumber){

        def iter=0;
        while (bytesNumber >= 1024){
            bytesNumber= Math.round(bytesNumber/1024)
            iter++;
        }
        return bytesNumber.toString() + unites[iter];
    }

    public static Double convertToBytes(String data){
	
	char last=data.charAt(data.size()-1)
	String number = data.substring(0,data.size()-1);
	Double value=Double.parseDouble(number)
	int i=0
	Double toReturn=null
	unites.each(){
		 unit -> 
		 if(last==unit) {
			toReturn= value*Math.pow(1024,i)
		}
		i++
	 }

	if(toReturn==null)
		toReturn=Double.parseDouble(data)
	return toReturn;
	
	
    }	

}


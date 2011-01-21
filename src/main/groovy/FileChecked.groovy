//package Checker;

public class FileChecked {
    
	public static FILE_NOT_FOUND_OR_EMPTY = -1;
    	public static FILE_SIZE_VALID = -3;
    	public static FILE_SIZE_EXPECTED_INCORRECT = -2;

	public static float ERROR_PERCENT_DEFAULT = 0.05;

	private double errorPercent = 0;
	private double errorDiff = 0;
	private int errorStatus;


	public double getErrorStatus() {
		return errorStatus;
	}

	public double getErrorPercent() {
		return errorPercent;
	}

	public double getErrorDiff(){
		return errorDiff;
	}

	public FileChecked(double fileLength, double fileLengthExpected ){
		this(fileLength, fileLength, ERROR_PERCENT_DEFAULT);

	}
	
	public FileChecked(double fileLength, double fileLengthExpected , float errorPercent){

		errorStatus=check(fileLength,fileLengthExpected);
		errorDiff=Math.abs(fileLength-fileLengthExpected);
		errorPercent=Math.round(errorDiff/fileLengthExpected * 100);

		}

	public int check(double fileLength,double fileLengthExpected){
		if(fileLengthExpected<1){
			return FILE_SIZE_EXPECTED_INCORRECT;
		}
		if (fileLength <= 0){
		    return FILE_NOT_FOUND_OR_EMPTY;
		}
		if (fileLengthExpected==fileLength){
			return FILE_SIZE_VALID;	
			}
		if ( ((fileLengthExpected * (1-errorPercent)) <= fileLength  ) && ( fileLength >= (fileLengthExpected * (1+errorPercent))) ){
		   return FILE_SIZE_VALID;		
				
			
		}

	}	
			
	public String toString(){
		return "errorStatus="+errorStatus;
	}


}
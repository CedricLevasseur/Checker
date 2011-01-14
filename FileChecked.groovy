//package Checker;

public class FileChecked {
    
	public static FILE_NOT_FOUND_OR_EMPTY = -1;
    	public static FILE_SIZE_VALID = -3;
    	public static FILE_SIZE_EXPECTED_INCORRECT = -2;

	public static float ERRORPERCENTDEFAULT = 0.05;

	private double errorPercent = 0;
	private double error = 0;

	private int errorStatus;



	public double getErrorPercent() {
		return errorPercent;
	}

	public double getError(){
		return error;
	}

	//public FileChecked(double fileLength, double fileLengthExpected ){
	//	super(fileLength, fileLength, ERRORPERCENTDEFAULT);
//
//	}
	
	public FileChecked(double fileLength, double fileLengthExpected , float errorPercent){
		if (fileLength <= 0){
		    errorStatus= FILE_NOT_FOUND_OR_EMPTY;
		}
		
		if ( ((fileLengthExpected * (1-errorPercent)) >= fileLength  ) && ( fileLength <= (fileLengthExpected * (1+errorPercent))) ){
		   //return FILE_SIZE_VALID;
		   errorStatus=FILE_SIZE_VALID;		
		}

		error=Math.abs(fileLength-fileLengthExpected);
		errorPercent=Math.round(error/fileLengthExpected * 100);
	}	
			


}

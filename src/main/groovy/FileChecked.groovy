//package Checker;

public class FileChecked {
    
	public static FILE_NOT_FOUND_OR_EMPTY = -1;
    	public static FILE_SIZE_VALID = -4;
    	public static FILE_SIZE_INVALID = -3;
    	public static FILE_SIZE_EXPECTED_INCORRECT = -2;

	private double delta = 0;
	/* return values */
	private double errorDiff = 0;
	private int errorStatus;


	public double getErrorStatus() {
		return errorStatus;
	}

	public double getDelta() {
		return delta;
	}

	public double getErrorDiff(){
		return errorDiff;
	}

/* DEPRECATED
 *	public FileChecked(double fileLength, double fileLengthExpected ){
 *		this(fileLength, fileLength, ERROR_PERCENT_DEFAULT);
 *	}
 */
	
	public FileChecked(double fileLength, double fileLengthExpected , double delta){
		errorStatus=check(fileLength,fileLengthExpected);
		errorDiff=Math.abs(fileLength-fileLengthExpected);
		this.delta=delta
	}

	private int check(double fileLength,double fileLengthExpected){
		if(fileLengthExpected<1){
			return FILE_SIZE_EXPECTED_INCORRECT;
		}
		if (fileLength <= 0){
		    return FILE_NOT_FOUND_OR_EMPTY;
		}
		if (fileLengthExpected==fileLength){
			return FILE_SIZE_VALID;	
			}
		/* When delta was a percent 
		if ( ((fileLengthExpected * (1-errorPercent)) <= fileLength  ) && ( fileLength >= (fileLengthExpected * (1+errorPercent))) ){
		   return FILE_SIZE_VALID;		
		}*/
		//println fileLengthExpected-fileLength+" "+delta		
		if((Math.abs(fileLengthExpected-fileLength))<delta){
			return FILE_SIZE_VALID
		}
		return FILE_SIZE_INVALID 	

	}	
			
	public String toString(){
		//return "errorStatus="+errorStatus+",errorDiff="+errorDiff;
		return "diff="+SizeFormatter.format((long)errorDiff)+ " exceed a delta of "+SizeFormatter.format((long)delta);
	}

}

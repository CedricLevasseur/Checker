import groovy.util.GroovyTestCase;
import SizeFormatter;

public class SizeFormatterTest extends GroovyTestCase{

      public static char[] unites=['B','K','M','G','T']

	public void testSizeFormatter(){
		long dataSize=0;
		String f=SizeFormatter.format(dataSize);
		assert f.charAt(f.size()-1) == 'B';
		
		for(dataSize=1;dataSize<(1024^5);dataSize=dataSize*1024){
			assert dataSize>0;
			f=SizeFormatter.format(dataSize);
			assert f.charAt(f.size()-1) in unites; 
			
		}
	}

	public void testConvertToBytes(){
		long dataSize=0;
		String f="768M";
		float result=((768*1024)*1024)
		assert SizeFormatter.convertToBytes(f)== result
	}
	
}

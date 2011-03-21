import groovy.util.GroovyTestCase;

public class StringSplittedTest extends GroovyTestCase{

	public void testStringSplitted(){
		StringSplitted sentence = new StringSplitted("Les oiseaux se cachent pour mourrir")
		ArrayList<String> splitted = sentence.split()
	
		assert splitted.get(0) == "Les"
		assert splitted.size() == 6		
	}

	
}

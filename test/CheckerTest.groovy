import groovy.util.GroovyTestCase;

class CheckerTest extends GroovyTestCase{

	public void testFileLastMatch(){

	Checker c1=new Checker();
	assert c1.fileLastMatch("") == "";
	assert c1.fileLastMatch("SomethingThatDoesntExist")=="SomethingThatDoesntExist";

	}
}



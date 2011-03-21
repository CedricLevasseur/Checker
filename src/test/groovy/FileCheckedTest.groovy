import groovy.util.GroovyTestCase;

class FileCheckedTest extends GroovyTestCase{

	public void testFileChecked(){

		FileChecked f1 = new FileChecked(0,0, 1);
		assert f1.errorStatus==f1.FILE_SIZE_EXPECTED_INCORRECT;

		FileChecked f2 = new FileChecked(-1,0, 1);
		assert f2.errorStatus==f2.FILE_SIZE_EXPECTED_INCORRECT;

		FileChecked f3 = new FileChecked(1024, 1024, 1);
		assert f3.errorStatus==f3.FILE_SIZE_VALID;

		FileChecked f4 = new FileChecked(1024*1024, 1024*1024, 1);
		assert f4.errorStatus==f4.FILE_SIZE_VALID;
	}
}

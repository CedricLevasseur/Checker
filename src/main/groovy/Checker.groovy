/*package checker*/

import org.htmlparser.*
import org.htmlparser.util.*
import org.htmlparser.filters.*
import org.htmlparser.tags.*
import FileChecked;


public class Checker{


    private String url;
    private double size;
    public  ConfigObject config;

    //private float errorPercent=0.05;

    public setErrorPercent(float percent){
	errorPercent=percent;
    }	
    
    public float getErrorPercent(){
	return errorPercent;
    }	

    public Checker(){
 	load();	
    }
    
    public void setUrl(String _url){
    	url=_url;
    }
    public String getUrl(){
    	return url;
    }
    public void setSize(Integer _size){
    	size=_size;
    }

    public Object getConfig(){
	return config;
    }


    public static void main(String[] args){

        def checker = new Checker();

	for (e in checker.config){
		checker.setUrl(e.getValue().get('url'));
		checker.setSize(e.getValue().get('size'));

		FileChecked fileChecked=checker.fileCheck();
		int status=fileChecked.getErrorStatus();
		switch(status){
			case FileChecked.FILE_NOT_FOUND_OR_EMPTY :
	            		System.out.println("File Not Found or empty : "+checker.getUrl());
				break;
			case FileChecked.FILE_SIZE_EXPECTED_INCORRECT :
	            		System.out.println("Expected File Size is invalid : "+checker.getUrl());
				break;
			case FileChecked.FILE_SIZE_VALID :
	            		System.out.println("CONFORME : "+checker.getUrl());
				break;
			default :
				System.out.println("FILE SIZE NOT CONFORME, error=" + fileChecked.getErrorPercent() +"% : "+checker.getUrl() );
		}
    	}
    }

    private void load(){

	config = new ConfigSlurper().parse(new File('SizeConfig.groovy').toURL())
	//System.out.println("Config :"+config);
    }

    private void save(){

	new File("SizeConfig.groovy").withWriter { writer ->config.writeTo(writer)}
    }


    private FileChecked fileCheck(){

	if(url.contains("*")){
		url=fileLastMatch(url);
	}
	if(url!=null && url.size()>0 ) {		
		if(url.startsWith("http")){
			return fileHttpCheck(url,size);
		}else{
			return fileFsCheck(url,size);
		}
	}
    }

    private String fileLastMatch(String url){

	if(!url.contains("*")){
		return url;
	}		
	
            String prefix=url.substring(url.lastIndexOf('/')+1,url.lastIndexOf('*'));
		System.out.println(prefix)
	    if(url.startsWith("http")){
	    	String directory= url.substring(0,url.lastIndexOf('/'));
		Parser parser = new Parser(directory);
		//NodeFilter filter = new NodeClassFilter (LinkTag.class);
		LinkStringFilter filter = new LinkStringFilter (prefix)
		NodeList list = new NodeList();
		list = parser.extractAllNodesThatMatch (filter);
		//parser.parse (new HasAttributeFilter ("id"));
		//parser.parse();
		//parser.parse(new LinkRegexFilter(prefix+"*",true);
		/*for (NodeIterator e = parser.elements (); e.hasMoreNodes (); )
			e.nextNode ().collectInto (list, filter);*/
		List<String> files = Arrays.asList(list);
		//System.out.println(files);
		//System.out.println("was prefix "+ prefix);

	    }else{
	    	File directory= new File (url.substring(0,url.lastIndexOf('/')));
		FilenameFilter filter = new FilenameFilter() { 
			public boolean accept(File dir, String name) { 
				return name.startsWith(prefix); 
			} 
		}; 
		List<String> files = Arrays.asList(directory.listFiles(filter));
		files.sort();
		Collections.reverse(files);
		if (files != null && files.size() > 0) { 
			//return the last file of the directory
			return files.get(0);//[files.length-1];
		}
		else{
			return url;
		}
	    }
    }

    public FileChecked fileHttpCheck( String httpurl, double fileLengthExpected ){


        URL url                  = new URL(httpurl);
        URLConnection connection = url.openConnection();

        String fileType          = connection.getContentType();
        long fileLength           = connection.getContentLength();
/*	Date lastModified = new Date(connection.getLastModified());
 *	System.out.println(lastModified);
 *	Date date = new Date(connection.getDate());
 *	System.out.println(date);
 */
	
 	//System.out.println("size="+fileLength);
	FileChecked toReturn = new FileChecked((double)fileLength,fileLengthExpected)
	return toReturn;

    }

    public FileChecked fileFsCheck(String path, double fileLengthExpected ){

	File file = new File(path);
        long fileLength = file.length();
/*	Date lastModified = new Date(file.lastModified());
 *	System.out.println(lastModified);
 */	
	FileChecked toReturn = new FileChecked( fileLength, fileLengthExpected);
	return toReturn;

	}

}

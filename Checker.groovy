/*package checker*/

import org.htmlparser.*
import org.htmlparser.util.*
import org.htmlparser.filters.*
import org.htmlparser.tags.*

public class Checker{


    private String url;
    private Integer size;

    private float errorPercent=0.05;
    public  ConfigObject config;

    public setErrorPercent(float percent){
	errorPercent=percent;
    }	
    
    public float getErrorPercent(){
	return errorPercent;
    }	

    public Checker(){
 	load();	
    }
    
    public static FILE_NOT_FOUND_OR_EMPTY = -1;
    public static FILE_SIZE_VALID = -3;
    public static FILE_SIZE_EXPECTED_INCORRECT = -2;

    public void setUrl(String _url){
    	url=_url;
    }
    public String getUrl(){
    	return url;
    }
    public void setSize(Integer _size){
    	size=_size;
    }

    public static String roundMe(long bytesNumber){

        def unites=["b","K","M","G","T"]
        def iter=0;
        while (bytesNumber > 1024){
            bytesNumber= Math.round(bytesNumber/1024)
            iter++;
        }
        return bytesNumber.toString() + unites[iter];
    }

    public Object getConfig(){
	return config;
    }


    public static void main(String[] args){

        def checker = new Checker();

	for (e in checker.config){
		checker.setUrl(e.getValue().get('url'));
		checker.setSize(e.getValue().get('size'));

		int res=0;
		switch (res=checker.fileCheck()){
			case FILE_NOT_FOUND_OR_EMPTY :
	            		System.out.println("File Not Found or empty : "+checker.getUrl());
				break;
			case FILE_SIZE_EXPECTED_INCORRECT :
	            		System.out.println("Expected File Size is invalid : "+checker.getUrl());
				break;
			case FILE_SIZE_VALID :
	            		System.out.println("CONFORME : "+checker.getUrl());
				break;
			default :
				System.out.println("FILE SIZE NOT CONFORME, error=" + res +"% : "+checker.getUrl() );
		}
    	}
    }

    private void load(){

	config = new ConfigSlurper().parse(new File('SizeConfig.groovy').toURL())
    }

    private void save(){

	new File("SizeConfig.groovy").withWriter { writer ->config.writeTo(writer)}
    }

    public void diskCheck(){
        //File f = new File("c:/");
        File f = new File("F:/");
        // prints the volume size in bytes.
        System.out.println("Total Space="+Checker.roundMe(f.getTotalSpace()));
        // prints the total free bytes for the volume in bytes.
        System.out.println("Free Space="+Checker.roundMe(f.getFreeSpace()));
        // prints an accurate estimate of the total free (and available) bytes
        // on the volume. This method may return the same result as 'getFreeSpace()' on
        // some platforms.
        
        System.out.println("Usable Space="+Checker.roundMe(f.getUsableSpace()));
    }


    private int fileCheck(){

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

            String prefix=url.substring(url.lastIndexOf('/')+1,url.lastIndexOf('*'));
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
		System.out.println(files);
		System.out.println("was prefix "+ prefix);

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
			return "";
		}
	    }
    }

    public int fileHttpCheck( httpurl, fileLengthExpected ){

	if(fileLengthExpected<1){
		return FILE_SIZE_EXPECTED_INCORRECT;
	}

        URL url                  = new URL(httpurl);
        URLConnection connection = url.openConnection();

        String fileType          = connection.getContentType();
        long fileLength           = connection.getContentLength();
/*	Date lastModified = new Date(connection.getLastModified());
 *	System.out.println(lastModified);
 *	Date date = new Date(connection.getDate());
 *	System.out.println(date);
 */
	
 	System.out.println("size="+fileLength);
	return fileIntCheck(fileLength, fileLengthExpected);

    }

    public int fileFsCheck(String path, fileLengthExpected ){

	if(fileLengthExpected<1){
		return FILE_SIZE_EXPECTED_INCORRECT 
	}
	File file = new File(path);
        long fileLength = file.length();
/*	Date lastModified = new Date(file.lastModified());
 *	System.out.println(lastModified);
 */	
	

	return fileIntCheck(fileLength, fileLengthExpected);

	}



   private int fileIntCheck(long fileLength, long fileLengthExpected){

        if (fileLength <= 0){
            return FILE_NOT_FOUND_OR_EMPTY;
        }
	System.out.println((fileLengthExpected * (1-errorPercent)) +">="+ fileLength +"="+ ((fileLengthExpected * (1-errorPercent)) >= fileLength));
	System.out.println(fileLength +"<="+ (fileLengthExpected * (1+errorPercent)) +"="+ (fileLength <= (fileLengthExpected * (1+errorPercent))));
	
	if ( ((fileLengthExpected * (1-errorPercent)) >= fileLength  ) && ( fileLength <= (fileLengthExpected * (1+errorPercent))) ){
		System.out.println("VALID");
	   return FILE_SIZE_VALID;		
	}

   	double error= Math.round(fileLengthExpected/fileLength*100);
	System.out.println("ERROR="+ error);
    	return error;  

    }




}

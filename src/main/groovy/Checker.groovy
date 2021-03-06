///*package checker*/

import org.htmlparser.*
import org.htmlparser.util.*
import org.htmlparser.filters.*
import org.htmlparser.tags.*
import FileChecked;


public class Checker{


    private String url;
    private double size;
    private double delta;
   // public  ConfigObject config;
    public List<FileToCheck> filesToCheck = new ArrayList<FileToCheck>() 

    private String DEFAULT_CONF_DIR = "src/main/ressources/"
    
    private String DEFAULT_CONF_FILE = "checker.conf"
			
    private static String DEFAULT_PERCENT="5%";

    public setErrorPercent(float percent){
	errorPercent=percent;
    }	
    
    public float getErrorPercent(){
	return errorPercent;
    }	

    public Checker(String[] args){
 	load(manageConfFile(args));	
    }
    
    public void setUrl(String _url){
    	url=_url;
    }
    public String getUrl(){
    	return url;
    }
    public void setSize(Double _size){
    	size=_size;
    }

    public Object getConfig(){
	return config;
    }

    public void setDelta(Double _delta){
    	delta=_delta;
    }

    public double getDelta(){
	return delta;
    }


    public static void main(String[] args){

        def checker = new Checker(args);

	checker.filesToCheck.each() { fileToCheck ->  

		checker.setUrl(fileToCheck.getUrl())
		checker.setSize(fileToCheck.getSize())
		checker.setDelta(fileToCheck.getDelta())
		
		FileChecked fileChecked=checker.fileCheck();
		int status=fileChecked.getErrorStatus();
		switch(status){
			case FileChecked.FILE_NOT_FOUND_OR_EMPTY :
	            		System.out.println("File Not Found or empty : "+checker.getUrl());
				break;
			case FileChecked.FILE_SIZE_EXPECTED_INCORRECT :
	            		System.out.println("Expected File Size is invalid : "+checker.getUrl());
				break;
			case FileChecked.FILE_SIZE_INVALID :
	            		System.out.println("NOT VALID : "+checker.getUrl()+" "+fileChecked);
				break;

			case FileChecked.FILE_SIZE_VALID :
	            		System.out.println("VALID : "+checker.getUrl());
				break;
			default :
				System.out.println("FILE SIZE NOT VALID, error=" + fileChecked.getErrorPercent() +"% : "+checker.getUrl() );
		}
    	}
    }

    private List<String> manageConfFile(String[] files){
	List<String> toReturn= new ArrayList<String>()
	File f
	if(files!=null ){
		files.each(){
			f=new File(it)
			if(f.exists()){
				toReturn.add(it)
			}else{
			    f=new File(DEFAULT_CONF_DIR+it)
			    if(f.exists()){
				toReturn.add(DEFAULT_CONF_DIR+it)
			    }
			}
		}
		if(toReturn.size()==0){
			f=new File(DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
			//println f
			if(f.exists()){
				toReturn.add(""+DEFAULT_CONF_DIR+DEFAULT_CONF_FILE)
			}else{
				println("No files to check, please provide a checker.conf")
			}
		}
	}
	return toReturn
    }

    private load(List<String> listOfFiles){
	listOfFiles.each(){
	    File conf= new File(it)
    	    conf.eachLine() { String file ->
		if(!file.startsWith("#")){
			String[] args = file.split(" ")
			FileToCheck target = new FileToCheck();
			target.setUrl(args[0])
			target.setSize(args[1])
			if(args.size()>2){
				target.setDelta(args[2])
			}else{
				target.setDelta(DEFAULT_PERCENT);
			}
			filesToCheck.add(target);
		}
	    }
        }
    }
    	
    private FileChecked fileCheck(){

	if(url.contains("*")){
		url=fileLastMatch(url);
	}
	if(url!=null && url.size()>0 ) {		
		if(url.startsWith("http")){
			return fileHttpCheck(url,size,delta);
		}else{
			return fileFsCheck(url,size,delta);
		}
	}
    }

    private String fileLastMatch(String url){

	if(!url.contains("*")){
		return url;
	}		
	
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

    public FileChecked fileHttpCheck( String httpurl, double fileLengthExpected,double delta ){


        URL url                  = new URL(httpurl);
        URLConnection connection = url.openConnection();

        String fileType          = connection.getContentType();
        long fileLength           = connection.getContentLength();
/*	Date lastModified = new Date(connection.getLastModified());
 *	System.out.println(lastModified);
 *	Date date = new Date(connection.getDate());
 *	System.out.println(date);
 */
	
	FileChecked toReturn = new FileChecked((double)fileLength,fileLengthExpected, delta)
	return toReturn;

    }

    public FileChecked fileFsCheck(String path, double fileLengthExpected, Double delta ){

	File file = new File(path);
        long fileLength = file.length();
/*	Date lastModified = new Date(file.lastModified());
 *	System.out.println(lastModified);
 */	
	FileChecked toReturn = new FileChecked( (Double)fileLength, fileLengthExpected, delta);
	return toReturn;

	}

}

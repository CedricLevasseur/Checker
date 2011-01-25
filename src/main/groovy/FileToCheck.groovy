public class FileToCheck{

	private String url;
	private double size;
	private double delta=0.05;

	public void setUrl(String _url){
		url=_url
	}
	
	public String getUrl(){
		return url
	}

	public void setSize(double _size){
		url=url
	}
	
	public double getSize(){
		return size
	}

	public void setDelta(double _delta){
		delta=_delta
	}
	public void setDelta(String _deltaStr){
		_deltaStr=_deltaStr.trim()
		if(_deltaStr.charAt(_deltaStr.size()-1)=='%'){
			_deltaStr="0."+_deltaStr.substring(0,_deltaStr.size()-2)	
		}
		delta=Double.parseDouble(_deltaStr)
		//delta=0.05//_delta
	}
	
	public double getDelta(){
		return delta
	}



}

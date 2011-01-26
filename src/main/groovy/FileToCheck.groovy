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

	public void setSize(String _size){
		size=_size
	}
	
	public double getSize(){
		return size
	}

	public void setDelta(double _delta){
		delta=_delta
	}
	public void setDelta(String _deltaStr){
		_deltaStr=_deltaStr.trim()
		char last=_deltaStr.charAt(_deltaStr.size()-1)
		if(last=='%'){
			_deltaStr="0."+_deltaStr.substring(0,_deltaStr.size()-2)	
			double deltaPercent=Double.parseDouble(_deltaStr)
			delta=size*(1-deltaPercent)
		}
		if(last in SizeFormatter.unites){
			//implement here the delta in size, not percent
			//delta=SizeFormatter.convertInBytes(_deltaStr)
		}
	}
	
	public double getDelta(){
		return delta
	}



}

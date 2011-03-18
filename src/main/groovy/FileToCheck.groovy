public class FileToCheck{

	private String url;
	private double size;
	private double delta=0.05;
	private String deltaStr;

	public void setUrl(String _url){
		url=_url
	}
	
	public String getUrl(){
		return url
	}

	public void setSize(String _size){
		size=SizeFormatter.convertToBytes(_size)
	}
	public void setSize(double _size){
		size=_size
	}

	public double getSize(){
		return size
	}

	public void setDelta(double _delta){
		delta=_delta
	}
	public void setDelta(String _deltaStr){
		if(_deltaStr!=null && _deltaStr.trim().length()>0){
			char last=_deltaStr.trim().charAt(_deltaStr.length()-1)
			int indexOfLast = _deltaStr.lastIndexOf((String)last)
			if(last=='%'){
				deltaStr=_deltaStr
				if(_deltaStr.size()==2){
					_deltaStr="0.0"+_deltaStr.substring(0,indexOfLast)	
				}else{
					_deltaStr="0."+_deltaStr.substring(0,indexOfLast)
				}
				//double deltaPercent=Double.parseDouble(_deltaStr)
				double deltaPercent=new Double(_deltaStr)
				//hum, be aware to set size before deltapercent 
				delta=size*(1-deltaPercent)
			}else{
				if(last in SizeFormatter.unites){
					//implement here the delta in size, not percent
					//delta=SizeFormatter.convertToBytes(_deltaStr)
				}else{
					delta= new Double (_deltaStr)
				}
			}
			// arrondi
			delta=(int)Math.floor(delta + 0.5f)
		}
	}
	
	public double getDelta(){
		return delta
	}



}

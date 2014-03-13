package utils;

public class P2sReqConstruct {
	
	private String version = "P2P-CI/1.0";
	private String crlf = "\r\n";
	
	public String construct(String method,Integer rfcNum,String hostName,Integer portNum,String title)
	{
		String result;
		
		result  = method+"RFC "+rfcNum.toString()+ " " + version + crlf;
		result += "Host: " + hostName + crlf;
		result += "Port: " + portNum.toString() + crlf;
		result += "Title: " + title + crlf;
		
		return result;
	}

}

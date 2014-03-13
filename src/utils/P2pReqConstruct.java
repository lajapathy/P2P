package utils;

public class P2pReqConstruct {
	
	private String version = "P2P-CI/1.0";
	private String crlf = "\r\n";
	
	public String construct(Integer reqRfcNum, String hostName) {
		String result;		
		
		result  = "GET RFC " + reqRfcNum.toString() + " " + version + crlf;
		result += "Host: " + hostName + crlf;
		result += "OS: " + System.getProperty("os.name") + crlf;
		
		return result;
	}
}

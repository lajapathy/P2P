package utils;

public class P2sResConstruct {
	
	private String version = "P2P-CI/1.0";
	private String crlf = "\r\n";
	private String response;
	
	public void constructHdr(Integer statusCode)
	{
		String[] statusPhrase = {"OK","Bad Request","Not Found","P2P-CI Version Not Supported" };
		String phrase;
		if (statusCode == 200) { 
			phrase = statusPhrase[0];
		} else if (statusCode == 400) {
			phrase = statusPhrase[1];
		} else if (statusCode == 404) {
			phrase = statusPhrase[2];
		} else {
			phrase = statusPhrase[3];
		}
		response = version + " " + statusCode.toString() + " " + phrase + crlf;
	}
	
	public boolean constructData(Integer rfcNum, String title, String hostName, Integer portNum)
	{
		if (response.length()==0) {
			return false;
		}
		
		response += "RFC " + rfcNum.toString() + title + hostName + portNum.toString() + crlf;
		
		return true;
	}
	
	public String getResponseMsg()
	{
		return response;
	}
}

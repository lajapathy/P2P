package utils;

import java.text.*;
import java.util.*;

public class P2pResHdrConstruct {
	
	private String version = "P2P-CI/1.0";
	private String crlf = "\r\n";
	private String result;
	
	public void construct(Integer statusCode,String lastModDate,Integer contentLen)
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
				
		String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		result  = version + " " + statusCode.toString() +" " + phrase + crlf;
		result += "Date: " + date + crlf;
		result += "OS: " + System.getProperty("os.name") + crlf;
		result += "Last-Modified: " + lastModDate + crlf;
		result += "Content-Length: " + contentLen.toString() + crlf;
		result += "Content-Type: " + "text/text" + crlf + crlf;
	}
	
	public String getHdrMsg() { return result; }

}

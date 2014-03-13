package utils;

import java.util.*;

public class P2pResParse {
	
	private String version = "P2P-CI/1.0";
	private String hostName;
	private String os;
	private String date;
	private String lastModDate;
	private Integer contentLen;
	private Integer dataOffset = 0;
	private boolean status = false;
	private Integer statusCode;
	private String  statusPhrase;
	
	public boolean setInput(String input) {
		
		Scanner scanner = new Scanner(input);		
		
		// 1st line
		if (!scanner.hasNextLine()) { return false; }
		String line = scanner.nextLine();
		dataOffset += line.length();
		String[] tokens = line.split(" ");
		statusCode = Integer.parseInt(tokens[1]);
		if ((statusCode!=200) && (statusCode!=400) && (statusCode!=404) && (statusCode!=505)) { return false;}
		for (int idx=1;idx<tokens.length;idx++) {
			statusPhrase+=tokens[idx];
		}
		// 2nd line
		if (!scanner.hasNextLine()) { return false; }
		line = scanner.nextLine();
		dataOffset += line.length();
		//3rd line
		if (!scanner.hasNextLine()) { return false; }
		line = scanner.nextLine();
		dataOffset += line.length();
		//4th line
		if (!scanner.hasNextLine()) { return false; }
		line = scanner.nextLine();
		dataOffset += line.length();
		//5th line
		if (!scanner.hasNextLine()) { return false; }
		line = scanner.nextLine();
		dataOffset += line.length();
		tokens = line.split(": ");
		if (!("Content-Length" == tokens[0])) { return false; };
		contentLen = Integer.parseInt(tokens[1]);
		//6th line
		if (!scanner.hasNextLine()) { return false; }
		line = scanner.nextLine();
		dataOffset += line.length();
			
		scanner.close();
		
		status = true;
		return status;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public Integer getContentLength() {
		return contentLen;
	}
	
	public Integer getDataOffset() {
		return dataOffset;
	}
	
	public boolean isOK() {
		return statusCode==200;
	}
	
	public String getErrorMsg() {
		return statusPhrase;
	}
}

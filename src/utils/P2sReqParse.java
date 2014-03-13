package utils;

import java.io.*;
import java.util.Scanner;

public class P2sReqParse {
	
	private String version = "P2P-CI/1.0";
	private String hostName;
	private Integer rfcNum;
	private Integer portNum;
	private String method;
	private String title;
	private boolean status = false;
	private int statusCode;
	
	public boolean setInput(InputStreamReader input) {
		
		Scanner scanner = new Scanner(input);		
		
		// 1st line
		if (!scanner.hasNextLine()) { return false; }
		String line = scanner.nextLine();		
		String[] tokens = line.split(" ");
		if (4 != tokens.length) { return false; }
		method = tokens[0];
		if ((method!="ADD") && (method!="LOOKUP") && (method!="LIST")) {
			statusCode = 400;
			return false; 
		}
		if ("RFC"!=tokens[1]) { 
			statusCode = 400; 
			return false;
		}
		rfcNum = Integer.parseInt(tokens[2]);
		if (version!=tokens[3]) { 
			statusCode = 505;
			return false; 
		}
		
		// 2nd line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false; 
		}
		line = scanner.nextLine();
		tokens = line.split(": ");
		if (tokens[0]!="Host") { 
			statusCode = 400;
			return false;
		}
		hostName = tokens[1];
		
		//3rd line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false; 
		}
		line = scanner.nextLine();
		tokens = line.split(": ");
		if (tokens[0]!="Port") { 
			statusCode = 400;
			return false;
		}
		portNum = Integer.parseInt(tokens[1]);
		
		//4th line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false; 
		}
		line = scanner.nextLine();
		tokens = line.split(": ");
		if (tokens[0]!="Title") { 
			statusCode = 400;
			return false;
		}
		title = tokens[1];
				
		scanner.close();
		
		status = true;
		statusCode = 200;
		
		return status;
	}
	
	public int getStausCode() {
		return statusCode;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public Integer getRfcNum() {
		return rfcNum;
	}
	
	public Integer getPortNum() {
		return portNum;
	}
	
	public String getTitle() {
		return title;
	}
}

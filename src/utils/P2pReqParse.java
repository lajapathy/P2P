package utils;

import java.io.*;
import java.util.*;

public class P2pReqParse {
	
	private String version = "P2P-CI/1.0";
	private String crlf = "\r\n";
	private Integer rfcNum;
	private String hostName;
	private String os;
	private boolean status = false;
	private int statusCode;
	
	public boolean setInput(InputStreamReader input) {
		
		Scanner scanner = new Scanner(input);
		
		// 1st line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false;			
		}
		String line = scanner.nextLine();
		String[] tokens = line.split(" ");
		if (!("GET" == tokens[0])) { 
			statusCode = 400;
			return false; 
		};
		if (!("RFC" == tokens[1])) { 
			statusCode = 400;
			return false; 
		};
		rfcNum = Integer.parseInt(tokens[2]);
		if (!(version == tokens[3])) { 
			statusCode = 505;
			return false; 
		};
		
		// 2nd line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false; 
		}
		line = scanner.nextLine();
		tokens = line.split(": ");
		if (!("Host" == tokens[0])) { 
			statusCode = 400;
			return false; 
		};
		hostName = tokens[1];
		
		//3rd line
		if (!scanner.hasNextLine()) { 
			statusCode = 400;
			return false; 
		}
		line = scanner.nextLine();
		tokens = line.split(": ");
		if (!("OS" == tokens[0])) { 
			statusCode = 400;
			return false; 
		};
		os = tokens[1];
		
		scanner.close();
		
		status = true;
		statusCode = 200;
		return status;
	}
	
	public Integer getRfcNum() {
		return rfcNum;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public int getStatusCode(){
		return statusCode;
	}
}

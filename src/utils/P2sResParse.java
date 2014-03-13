package utils;

import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Iterator;

class P2sResRecord {
	Integer rfcNum;
	String  title;
	String  hostName;
	Integer portNum;
}

public class P2sResParse {
	
	private String  version = "P2P-CI/1.0";
	private boolean status = false;
	private Integer statusCode = 400;
	private String  statusPhrase;
	private MyArrayQueue<P2sResRecord> recQ;
	private Iterator<P2sResRecord> iter;
	
	public boolean setInput(InputStreamReader input) {
		
		Scanner scanner = new Scanner(input);		
		
		// 1st line
		if (!scanner.hasNextLine()) { return false; }
		String line = scanner.nextLine();
		String[] tokens = line.split(" ");
		if (version!=tokens[0]) { return false; }
		statusCode = Integer.parseInt(tokens[1]);
		if ((statusCode!=200) && (statusCode!=400) && (statusCode!=404) && (statusCode!=505)) { return false;}
		for (int idx=1;idx<tokens.length;idx++) {
			statusPhrase+=tokens[idx];
		}
		
		//subsequent lines
		while(scanner.hasNextLine())
		{
			line = scanner.nextLine();
			tokens = line.split(" ");
			int len = tokens.length;
			
			P2sResRecord rec = new P2sResRecord();
			rec.rfcNum   = Integer.parseInt(tokens[1]);
			rec.portNum  = Integer.parseInt(tokens[len-1]);
			rec.hostName = tokens[len-2];
			for (int idx=2;idx<len-2;idx++) {
				rec.title+=tokens[idx];
			}
			recQ.enqueue(rec);
		}
		
		scanner.close();
		
		iter = recQ.iterator();
		
		status = true;
		return status;
	}
	
	public boolean isOK() {
		return statusCode==200;
	}
	
	public String getErrorMsg() {
		return statusPhrase;
	}
	
	public boolean hasNextRecord() {
		return iter.hasNext();
	}
	
	public P2sResRecord getRecord() {
		return iter.next();
	}
}

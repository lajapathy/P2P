package peerClient;

import utils.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PeerClient {

	/**
	 * @param args
	 */
	private static LinkedList<PeerInfo> getRfcRecordFromCentralServer(String serverHostName,int rfcNum, String title) {
		LinkedList<PeerInfo> list = new LinkedList<PeerInfo>();
		
		Socket client;
		InetAddress ipAddr;
		String hostName;
		try {
			ipAddr = InetAddress.getByName(serverHostName);
			hostName = InetAddress.getLocalHost().getHostName();
			client = new Socket(ipAddr,7734);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
			System.out.println("Unable to get IP address of server");
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to connect to server");
			return list;
		}
		
		P2sReqConstruct ReqMsg = new P2sReqConstruct();		
		String msg = ReqMsg.construct("LOOKUP", rfcNum, hostName, client.getLocalPort(), title);
		DataOutputStream msgStream;
		try {
			msgStream = new DataOutputStream(client.getOutputStream());
			msgStream.writeChars(msg);
		} catch (IOException e) {			
			e.printStackTrace();
			return list;
		}
		
		P2sResParse res = new P2sResParse(); 
		try {
			InputStreamReader serverStream = new InputStreamReader(client.getInputStream());
			if ( false == res.setInput(serverStream)) {
				System.out.println("Could not read server response");
				return list;
			}
			if (false == res.isOK()) {
				System.out.println("server response is not OK");
				System.out.println(res.getErrorMsg());
				return list;
			}
			
			while (res.hasNextRecord()) {
				res.getRecord();
			}
		} catch (IOException e) {			
			e.printStackTrace();
			return list;
		}
		
	}
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Wrong usage: <*.Java> <File containing rfc numbers> <host name of server> <peerServer port number>");
			return;
		}
		
		Scanner configFile;
		try {
			configFile = new Scanner (new BufferedReader( new FileReader (args[0])));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to read the input config file");
			return;
		} 
		
		while (configFile.hasNextLine()) {
			String line = configFile.nextLine();
			String[] tokens =  line.split(" ");
			String title=null;
			int rfcNum = Integer.parseInt(tokens[0]);
			for (int idx=1;idx<tokens.length;idx++) {
				title += tokens[idx];
			}
			
			LinkedList<PeerInfo> list = getRfcRecordFromCentralServer(args[1],rfcNum, title);
		}
	}

}

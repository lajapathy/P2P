package peerServer;

import java.net.Socket;
import java.util.LinkedList;
import java.io.*;
import java.text.SimpleDateFormat;

import utils.*;

public class PeerHandler extends Thread  {
	
	private Socket      clientSock;
	
	private PeerHandler(){
	}
	
	public PeerHandler(Socket clientSockIn){
		clientSock = clientSockIn;	
	}
	
	public void serviceClient() { start(); } 
	
	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
	
	public void run() {
		
		while(true)
		{
			try {
				P2pReqParse req = new P2pReqParse();
				
				InputStreamReader clientStream = new InputStreamReader(clientSock.getInputStream());
				if ( false == req.setInput(clientStream)) {
					System.out.println("Error occurred while parsing the P2P req");
					P2pResHdrConstruct response = new P2pResHdrConstruct(); 
					response.construct(req.getStatusCode(),"junkdate",0);
					String msg = response.getHdrMsg();
					DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
					msgStream.writeChars(msg);
					continue;
				}
				Integer rfcNum = req.getRfcNum();
				String rfcFileName = rfcNum.toString() + ".txt"; 
				System.out.println(req.getHostName() + "is requesting for RFC " + rfcFileName);
				String msgData;
				try{
					msgData=readFile(rfcFileName);
				}catch (IOException e){
					System.out.println("Requested Filename not found " + e);
					P2pResHdrConstruct response = new P2pResHdrConstruct();
					response.construct(404,"JunkDate",0);
					continue;
				} 
				File rfcFile = new File(rfcFileName);
				//TODO - change the date format
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				String lastModDate = sdf.format(rfcFile.lastModified());
				P2pResHdrConstruct response = new P2pResHdrConstruct(); 
				response.construct(req.getStatusCode(),lastModDate,0);
				String msg = response.getHdrMsg();
				msg += msgData;
				DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
				msgStream.writeChars(msg);
								
			} catch 
			(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
	}
}

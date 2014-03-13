package centralServer;

import java.net.Socket;
import java.util.LinkedList;
import java.io.*;

import utils.*;

public class ClientHandler extends Thread  {
	
	private PeerRecords peerRec;
	private RfcRecords  rfcRec;
	private Socket      clientSock;
	
	private ClientHandler(){
	}
	
	public ClientHandler(Socket clientSockIn, PeerRecords peerRecIn, RfcRecords rfcRecIn){
		peerRec    = peerRecIn;
		rfcRec     = rfcRecIn;
		clientSock = clientSockIn;	
	}
	
	public void serviceClient() { start(); } 
	
	public void run() {
		
		while(true)
		{
			try {
				P2sReqParse req = new P2sReqParse();
				
				InputStreamReader clientStream = new InputStreamReader(clientSock.getInputStream());
				if ( false == req.setInput(clientStream)) {
					System.out.println("Error occurred while parsing the P2S req");
					P2sResConstruct response = new P2sResConstruct(); 
					response.constructHdr(req.getStausCode());
					String msg = response.getResponseMsg();
					DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
					msgStream.writeChars(msg);
					continue;
				}
				
				String method = req.getMethod();
				if (method=="ADD") {
					PeerInfo peer = new PeerInfo();
					peer.setHostName(req.getHostName());
					peer.setPortNum(req.getPortNum());
					peerRec.addPeer(peer);					
					//construct response
					P2sResConstruct response = new P2sResConstruct(); 
					response.constructHdr(200);
					response.constructData(req.getRfcNum(), req.getTitle(), req.getHostName(), req.getPortNum());
					String msg = response.getResponseMsg();
					DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
					msgStream.writeChars(msg);
				} else if (method=="LOOKUP") {
					P2sResConstruct response = new P2sResConstruct(); 
					LinkedList<RfcInfo> result = rfcRec.search(req.getRfcNum());					
					//construct response
					if (result.isEmpty()) {
						response.constructHdr(404);
					} else {
						response.constructHdr(200);
					}
					for (RfcInfo r: result) {
						PeerInfo info = peerRec.search(r.getHostName());
						response.constructData(r.getRfcNum(), r.getTitle(), r.getHostName(),info.getPortNum());
					}
					String msg = response.getResponseMsg();
					DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
					msgStream.writeChars(msg);
				} else if (method=="LIST") {
					P2sResConstruct response = new P2sResConstruct(); 
					LinkedList<RfcInfo> result = rfcRec.listAll();					
					//construct response
					if (result.isEmpty()) {
						response.constructHdr(404);
					} else {
						response.constructHdr(200);
					}
					for (RfcInfo r: result) {
						PeerInfo info = peerRec.search(r.getHostName());
						response.constructData(r.getRfcNum(), r.getTitle(), r.getHostName(),info.getPortNum());
					}
					String msg = response.getResponseMsg();
					DataOutputStream msgStream = new DataOutputStream(clientSock.getOutputStream());
					msgStream.writeChars(msg);
				} else {
					System.out.println("Error error error");
				}
				
			} catch 
			(IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
	}
}

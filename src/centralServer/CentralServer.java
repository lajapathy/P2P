package centralServer;

import java.io.*;
import java.net.*;
import utils.*;

public class CentralServer {
    
    public static void main(String args[]) {
    
    	ServerSocket serverSock;
    	Socket clientSock;
    	PeerRecords peerRec = new PeerRecords();
    	RfcRecords rfcRec = new RfcRecords();
    	
    	try{
            serverSock = new ServerSocket(7734);
    	} catch (IOException e){
            System.out.println(e);
            return;
        }
        
    	while(true){
            try {
				clientSock = serverSock.accept();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			ClientHandler client = new ClientHandler(clientSock,peerRec,rfcRec);
			client.serviceClient();            
        }
    }
 }
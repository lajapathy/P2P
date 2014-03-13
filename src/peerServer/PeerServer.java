package peerServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length!=1){
			System.out.println("Usage Error, <executable> <PortNum> ");
			return;
		}
		ServerSocket serverSock ;
		Socket clientSock;
		
		try {
			serverSock = new ServerSocket(Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
    	while(true){
            try {
				clientSock = serverSock.accept();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
    	
	}

  }
}

package utils;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class PeerRecords {
	
	private LinkedList<PeerInfo> peers;
	private final ReentrantLock lock = new ReentrantLock();
	
	public boolean addPeer(PeerInfo inPeer) {
		
		// synchronize
		lock.lock();
		
		// Add new peer to linkedList
		peers.add(inPeer);
		
		lock.unlock();
		
		return true;
	}
	
	public boolean deletePeer(PeerInfo inPeer) {
		boolean status;
		
		// synchronize
		lock.lock();
		
		// search for matching hostname and portnum in linkedlist
		status = peers.remove(inPeer);
		
		lock.unlock();
		
		return status;
	}
	
	public PeerInfo search(String hostName) {
		
		// synchronize
		lock.lock();
		
		// search for matching hostname and portnum in linkedlist
		PeerInfo reqPeer = null;		
		for (PeerInfo s: peers) 
		{
			if (s.getHostName() == hostName)
			{
				reqPeer = s;
				break;
			}
		}
		
		lock.unlock();
		
		return reqPeer;
	}
}

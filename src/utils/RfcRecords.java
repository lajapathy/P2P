package utils;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class RfcRecords {
	
	private LinkedList<RfcInfo> peerRfcInfo;
	private final ReentrantLock lock = new ReentrantLock();
	
	public boolean addRfcInfo(RfcInfo inRfcInfo) {
		
		// synchronize
		lock.lock();
		
		// Add new peer to linkedList
		peerRfcInfo.add(inRfcInfo);
		
		lock.unlock();
		
		return true;
	}
	
	public boolean deleteRfcInfo(RfcInfo inRfcInfo) {
		boolean status;
		
		// synchronize
		lock.lock();
		
		// search for matching hostname and portnum in linkedlist
		status = peerRfcInfo.remove(inRfcInfo);		
		
		lock.unlock();
		
		return status;
	}
	
	public LinkedList<RfcInfo> search(int inRfcNum) {
		
		LinkedList<RfcInfo> result = new LinkedList<RfcInfo>();
		
		// synchronize
		lock.lock();
		
		// search for matching hostname and portnum in linkedlist				
		for (RfcInfo s: peerRfcInfo) 
		{
			if (s.getRfcNum() == inRfcNum)
			{
				result.add(s);
			}
		}
		
		lock.unlock();
		
		return result;
	}
	
	public LinkedList<RfcInfo> listAll() {
		
		LinkedList<RfcInfo> result = new LinkedList<RfcInfo>();
		
		// synchronize
		lock.lock();
		
		// search for matching hostname and portnum in linkedlist				
		for (RfcInfo s: peerRfcInfo) 
		{
			result.add(s);
		}
		
		lock.unlock();
		
		return result;
	} 
}
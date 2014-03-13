package utils;

public class PeerInfo {
	private String hostName;
	private int    portNum;
	
	public void setHostName(String inHostName) { hostName = inHostName; }
	public void setPortNum(int inPortNum) { portNum = inPortNum; }
	public String getHostName() { return hostName;}
	public int getPortNum() { return portNum;}
}

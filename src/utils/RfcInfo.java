package utils;

public class RfcInfo {
	private int    rfcNum;
	private String title;
	private String hostName;
	
	public void setRfcNum(int inRfcNum) { rfcNum = inRfcNum; }
	public void setTitle(String inTitle) { title = inTitle; }
	public void setHostName(String inHostName) { hostName = inHostName;}
	public int getRfcNum() { return rfcNum;}
	public String getTitle() { return title;}
	public String getHostName() { return hostName; }
}

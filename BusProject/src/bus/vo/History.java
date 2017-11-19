package bus.vo;

public class History {
	private String userId;
	private int busOrStnId;
	private String busOrStnType;
	private String indate;
	
	/*
	 * Constructor
	 */
	public History() {}
	
	public History(String userId, int busOrStnId, String busOrStnType, String indate) {
		this.userId = userId;
		this.busOrStnId = busOrStnId;
		this.busOrStnType = busOrStnType;
		this.indate = indate;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBusOrStnId() {
		return busOrStnId;
	}
	public void setBusOrStnId(int busOrStnId) {
		this.busOrStnId = busOrStnId;
	}
	public String getBusOrStnType() {
		return busOrStnType;
	}
	public void setBusOrStnType(String busOrStnType) {
		this.busOrStnType = busOrStnType;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	
	public void setTypeBus() {
		this.busOrStnType = "B";
	}
	public void setTypeStation() {
		this.busOrStnType = "S";
	}
}

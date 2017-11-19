package bus.vo;

public class Favorite {
	private String userId;
	private int busOrStnId;
	private String busOrStnType;
	
	/*
	 * Constructor
	 */
	public Favorite() {}
	
	public Favorite(String userId) {
		this.userId = userId;
	}
	public Favorite(String userId, int busOrStnId) {
		this.userId = userId;
		this.busOrStnId = busOrStnId;
	}
	
	public Favorite(String userId, int busOrStnId, String busOrStnType) {
		this.userId = userId;
		this.busOrStnId = busOrStnId;
		this.busOrStnType = busOrStnType;
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
	
	public void setTypeBus() {
		this.busOrStnType = "B";
	}
	public void setTypeStation() {
		this.busOrStnType = "S";
	}
}

package bus.vo;

public class Station {
	private int stnId;		// 정류장 id
	private String arsId;	// 정류장 고유 번호
	private String stnName; // 정류장 이름
	
	/*
	 * Constructor
	 */
	public Station() {}
	
	public Station(int stationId, String arsId, String stnName) {
		this.stnId = stationId;
		this.arsId = arsId;
		this.stnName = stnName;
	}
	
	/*
	 * Getters and setters
	 */
	public int getStnId() {
		return stnId;
	}

	public void setStnId(int stationId) {
		this.stnId = stationId;
	}

	public String getArsId() {
		return arsId;
	}

	public void setArsId(String arsId) {
		this.arsId = arsId;
	}

	public String getStnName() {
		return stnName;
	}

	public void setStnName(String stnName) {
		this.stnName = stnName;
	}
}

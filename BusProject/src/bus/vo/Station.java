package bus.vo;

public class Station {
	private int stationId;
	private String arsId;
	private String stnName;
	
	/*
	 * Constructor
	 */
	public Station() {}
	
	public Station(int stationId, String arsId, String stnName) {
		this.stationId = stationId;
		this.arsId = arsId;
		this.stnName = stnName;
	}
	
	/*
	 * Getters and setters
	 */
	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
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

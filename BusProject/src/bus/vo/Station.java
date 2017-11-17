package bus.vo;

public class Station {
	private int stationId;
	private String arsId;
	private String stationName;
	
	/*
	 * Constructor
	 */
	public Station() {}
	
	public Station(int stationId, String arsId, String stationName) {
		this.stationId = stationId;
		this.arsId = arsId;
		this.stationName = stationName;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}

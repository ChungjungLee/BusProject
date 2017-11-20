package bus.vo;

public class RealTimeStation extends Station {
	private int busType;
	private String plainNo;
	
	/*
	 * Constructor
	 */
	public RealTimeStation() {
		super();
	}
	
	// 해당 노선의 버스가 현재 위치하지 않는 정류소에 대한 생성자
	public RealTimeStation(int stationId, String arsId, String stnName) {
		super(stationId, arsId, stnName);
		busType = -1;
		plainNo = null;
	}
	
	// 해당 노선의 버스가 현재 도착 예정인 정류소에 대한 생성자
	public RealTimeStation(int stationId, String arsId, String stnName, int busType, String plainNo) {
		super(stationId, arsId, stnName);
		this.busType = busType;
		this.plainNo = plainNo;
	}

	/*
	 * Getters and setters
	 */
	public int getBusType() {
		return busType;
	}
	public void setBusType(int busType) {
		this.busType = busType;
	}
	public String getPlainNo() {
		return plainNo;
	}
	public void setPlainNo(String plainNo) {
		this.plainNo = plainNo;
	}
	
	
}

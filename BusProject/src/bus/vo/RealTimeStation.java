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
	
	// �ش� �뼱�� ������ ���� ��ġ���� �ʴ� �����ҿ� ���� ������
	public RealTimeStation(int stationId, String arsId, String stnName) {
		super(stationId, arsId, stnName);
		busType = -1;
		plainNo = null;
	}
	
	// �ش� �뼱�� ������ ���� ���� ������ �����ҿ� ���� ������
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

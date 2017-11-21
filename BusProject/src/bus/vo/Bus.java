package bus.vo;

public class Bus {
	private int routId;			// 버스 ID
	private String routName;	// 버스 번호
	private String routType;	// 버스 타입(간선, 지선, ...)
	
	/*
	 * Constructor
	 */
	public Bus() {}
	
	public Bus(int routId, String routName, String routType) {
		this.routId = routId;
		this.routName = routName;
		this.routType = routType;
	}
	
	/*
	 * Getters and setters
	 */
	public int getRoutId() {
		return routId;
	}
	public void setRoutId(int routId) {
		this.routId = routId;
	}
	public String getRoutName() {
		return routName;
	}
	public void setRoutName(String routName) {
		this.routName = routName;
	}
	public String getRoutType() {
		return routType;
	}
	public void setRoutType(String routType) {
		this.routType = routType;
	}
	
}

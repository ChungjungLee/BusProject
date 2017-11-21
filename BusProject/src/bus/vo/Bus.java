package bus.vo;

public class Bus {
	private int routId;			// ���� ID
	private String routName;	// ���� ��ȣ
	private String routType;	// ���� Ÿ��(����, ����, ...)
	
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

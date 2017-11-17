package bus.vo;

public class Bus {
	private int routId;			// 버스 ID
	private String routName;	// 버스 번호
	private String routType;	// 버스 타입(간선, 지선, ...)
	private String stnFirst;	// 기점
	private String stnLast;		// 종점
	private String timeFirst;	// 첫차 시각
	private String timeLast;	// 막차 시각
	private String satTimeFirst;// 토요일 첫차 시각
	private String satTimeLast;	// 토요일 막차 시각
	private String holTimeFirst;// 공휴일 첫차 시각
	private String holTimeLast;	// 공휴일 막차 시각
	private String norTerms;	// 평일 배차 간격
	private String satTerms;	// 토요일 배차 간격
	private String holTerms;	// 공휴일 배차 간격
	private String companyNm;	// 운수회사
	private String telNo;		// 회사 전화번호
	private String faxNo;		// 회사 팩스번호
	private String email;		// 회사 이메일주소
	
	/*
	 * Constructor
	 */
	public Bus() {}
	
	public Bus(int routId, String routName, String routType, String stnFirst, String stnLast,
			String timeFirst, String timeLast, String satTimeFirst, String satTimeLast, String holTimeFirst,
			String holTimeLast, String norTerms, String satTerms, String holTerms, String companyNm, String telNo,
			String faxNo, String email) {
		this.routId = routId;
		this.routName = routName;
		this.routType = routType;
		this.stnFirst = stnFirst;
		this.stnLast = stnLast;
		this.timeFirst = timeFirst;
		this.timeLast = timeLast;
		this.satTimeFirst = satTimeFirst;
		this.satTimeLast = satTimeLast;
		this.holTimeFirst = holTimeFirst;
		this.holTimeLast = holTimeLast;
		this.norTerms = norTerms;
		this.satTerms = satTerms;
		this.holTerms = holTerms;
		this.companyNm = companyNm;
		this.telNo = telNo;
		this.faxNo = faxNo;
		this.email = email;
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
	public String getStnFirst() {
		return stnFirst;
	}
	public void setStnFirst(String stnFirst) {
		this.stnFirst = stnFirst;
	}
	public String getStnLast() {
		return stnLast;
	}
	public void setStnLast(String stnLast) {
		this.stnLast = stnLast;
	}
	public String getTimeFirst() {
		return timeFirst;
	}
	public void setTimeFirst(String timeFirst) {
		this.timeFirst = timeFirst;
	}
	public String getTimeLast() {
		return timeLast;
	}
	public void setTimeLast(String timeLast) {
		this.timeLast = timeLast;
	}
	public String getSatTimeFirst() {
		return satTimeFirst;
	}
	public void setSatTimeFirst(String satTimeFirst) {
		this.satTimeFirst = satTimeFirst;
	}
	public String getSatTimeLast() {
		return satTimeLast;
	}
	public void setSatTimeLast(String satTimeLast) {
		this.satTimeLast = satTimeLast;
	}
	public String getHolTimeFirst() {
		return holTimeFirst;
	}
	public void setHolTimeFirst(String holTimeFirst) {
		this.holTimeFirst = holTimeFirst;
	}
	public String getHolTimeLast() {
		return holTimeLast;
	}
	public void setHolTimeLast(String holTimeLast) {
		this.holTimeLast = holTimeLast;
	}
	public String getNorTerms() {
		return norTerms;
	}
	public void setNorTerms(String norTerms) {
		this.norTerms = norTerms;
	}
	public String getSatTerms() {
		return satTerms;
	}
	public void setSatTerms(String satTerms) {
		this.satTerms = satTerms;
	}
	public String getHolTerms() {
		return holTerms;
	}
	public void setHolTerms(String holTerms) {
		this.holTerms = holTerms;
	}
	public String getCompanyNm() {
		return companyNm;
	}
	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

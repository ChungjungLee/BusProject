package bus.vo;

public class Bus {
	private int routId;
	private String routName;
	private String routType;
	private String stnFirst;
	private String stnLast;
	private String busIntervals;
	private String timeFirst;
	private String timeLast;
	private String satTimeFirst;
	private String satTimeLast;
	private String holTimeFirst;
	private String holTimeLast;
	private String norTerms;
	private String satTerms;
	private String holTerms;
	private String companyNm;
	private String telNo;
	private String faxNo;
	private String email;
	
	/*
	 * Constructor
	 */
	public Bus() {}
	
	public Bus(int routId, String routName, String routType, String stnFirst, String stnLast, String busIntervals,
			String timeFirst, String timeLast, String satTimeFirst, String satTimeLast, String holTimeFirst,
			String holTimeLast, String norTerms, String satTerms, String holTerms, String companyNm, String telNo,
			String faxNo, String email) {
		this.routId = routId;
		this.routName = routName;
		this.routType = routType;
		this.stnFirst = stnFirst;
		this.stnLast = stnLast;
		this.busIntervals = busIntervals;
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
	public String getBusIntervals() {
		return busIntervals;
	}
	public void setBusIntervals(String busIntervals) {
		this.busIntervals = busIntervals;
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

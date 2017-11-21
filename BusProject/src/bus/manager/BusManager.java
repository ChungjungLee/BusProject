package bus.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bus.dao.BusDAO;
import bus.vo.Account;
import bus.vo.Bus;
import bus.vo.Favorite;
import bus.vo.History;
import bus.vo.RealTimeStation;
import bus.vo.Station;

public class BusManager {
	
	// ���������� API Ȱ���� ���� ���� ����Ű
	private final String serviceKey =
			"9qJ%2FGyciiKC2bdjQYHAWCxxYmnJ0KmYn1ZySk6y8SJdOgcffBFBpTOxUgobyps504QppRIpzOrPbIkZoJWJhtg%3D%3D";
	
	BusDAO busDao;
	
	public BusManager() {
		busDao = new BusDAO();
	}
	
	
	/**
	 * ID�� ��й�ȣ�� �Է¹޾� �α����� �õ��Ѵ�.
	 * @param userId ���̵�
	 * @param userPw ��й�ȣ
	 * @return loginResult 0: �α��� ����, 1: ��й�ȣ ����, 2: ���� �������� ����
	 */
	public int userLogIn(String userId, String userPw) {
		Account accLoggedIn = busDao.selectAccount(userId);
		
		int loginResult = 0;
		
		if (accLoggedIn == null) {
			loginResult = 2;
		} else if (!accLoggedIn.getPw().equals(userPw)) {
			loginResult = 1;
		}
		
		return loginResult;
	}

	
	/**
	 * ID�� ��й�ȣ�� �Է¹޾� ���ο� ������ �����.
	 * @param userId ���̵�
	 * @param userPw ��й�ȣ
	 */
	public void signIn(String userId, String userPw) {
		// ȸ������
		Account account = new Account(userId, userPw);
		
		if (busDao.insertAccount(account)) {
			
		} else {
			
		}
	}
	
	
	/**
	 * �Է¹��� ���ڸ� ���� �ش� ���ڸ� �����ϴ� ������ �˻��Ѵ�.
	 * @param busNum �˻��ϰ��� �ϴ� ����
	 * @return �Է��� ���ڸ� �����ϴ� ���� ���
	 */
	public List<Bus> searchBuses(String busNum) {
		// DB���� �޾ƿ� ��
		List<Bus> busList = busDao.srchBusContainsNum(busNum);
		
		return busList;
	}
	
	
	/**
	 * Ȯ���Ϸ��� ������ ��ü �뼱���� �ǽð� ��ġ�� �޾ƿ´�.
	 * @param busId Ȯ���Ϸ��� ������ ID
	 * @return �뼱���� �ǽð� ��ġ
	 */
	public List<RealTimeStation> getRouteMap(int busRouteId) {
		
		String urlGetRouteMap = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey + 
				"&busRouteId=" + busRouteId;
		
		String urlGetRealTimeBus = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=" + serviceKey +
				"&busRouteId=" + busRouteId;
		
		Document docRouteMap = getDocumentByUrl(urlGetRouteMap);
		Document docRealTimeBus = getDocumentByUrl(urlGetRealTimeBus);
		
		if (docRouteMap == null || docRealTimeBus == null) {
			return null;
		}
		
		NodeList itemListRoute = docRouteMap.getElementsByTagName("itemList");
		NodeList itemListRealTimeBus = docRealTimeBus.getElementsByTagName("itemList");
		
		List<RealTimeStation> routeMap = new ArrayList<>();	// ���� ��ȯ�� ����Ʈ
		
		for (int indexRoute = 0; indexRoute < itemListRoute.getLength(); indexRoute++) {
			
			RealTimeStation station = new RealTimeStation();
			// ���� �뼱�� ������ �ϳ� �о���δ�.
			for (Node node = itemListRoute.item(indexRoute).getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node.getNodeName().equals("stNm")) {
					station.setStnName(node.getTextContent());
				} else if (node.getNodeName().equals("arsId")) {
					station.setArsId(node.getTextContent());
				} else if (node.getNodeName().equals("stId")) {
					station.setStnId(Integer.parseInt(node.getTextContent()));
				}
			}
			
			routeMap.add(station);
		}
		
		for (int indexRealTimeBus = 0; indexRealTimeBus < itemListRealTimeBus.getLength(); indexRealTimeBus++) {
			int tempBusType = 0;
			int tempLastStId = 0;
			String tempPlainNo = null;
			
			for (Node node = itemListRealTimeBus.item(indexRealTimeBus).getFirstChild(); node != null; node = node.getNextSibling()) {
				if (node.getNodeName().equals("busType")) {
					tempBusType = Integer.parseInt(node.getTextContent());
				} else if (node.getNodeName().equals("lastStnId")) {
					tempLastStId = Integer.parseInt(node.getTextContent());
				} else if (node.getNodeName().equals("plainNo")) {
					tempPlainNo = node.getTextContent();
				}
			}
			
			for (RealTimeStation station : routeMap) {
				if (station.getStnId() == tempLastStId) {
					station.setBusType(tempBusType);
					station.setPlainNo(tempPlainNo);
					break;
				}
			}
		}
		
		return routeMap;
	}
	
	
	/**
	 * �Է¹��� ���ڸ� ���� �ش� ���ڸ� �����ϴ� �������� �˻��Ѵ�.
	 * @param keyword �˻��ϰ��� �ϴ� ����
	 * @return �Է��� ���ڸ� �����ϴ� ������ ���
	 */
	public List<Station> searchStations(String keyword) {
		
		String urlString;
		try {
			urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?serviceKey=" + serviceKey +
						"&stSrch=" + URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("���ڵ� ������ �������� �ʴ� ����");
			e.printStackTrace();
			return null;
		}
		
		Document doc = getDocumentByUrl(urlString);
		
		if (doc == null) {
			return null;
		}
		
		List<Station> srchStnList = new ArrayList<>();
		
		NodeList itemList = doc.getElementsByTagName("itemList");
		
		for (int i = 0; i < itemList.getLength(); i++) {
			
			Station station = new Station();
			for (Node node = itemList.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
				
				if (node.getNodeName().equals("stNm")) {
					station.setStnName(node.getTextContent());
				} else if (node.getNodeName().equals("arsId")) {
					station.setArsId(node.getTextContent());
				} else if (node.getNodeName().equals("stId")) {
					station.setStnId(Integer.parseInt(node.getTextContent()));
				}
			}
			
			srchStnList.add(station);
		}
		
		// DB�� ����
		busDao.insertStations(srchStnList);
		
		return srchStnList;
	}
	
	
	/**
	 * Ȯ���Ϸ��� �������� �ǽð� ���� ���� ������ �޾ƿ´�.
	 * @param arsId Ȯ���Ϸ��� ������ ���� ��ȣ
	 * @return �ǽð� ���� ���� ����
	 */
	public List<HashMap<String, Object>> getBuses(String arsId) {
		
		String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?serviceKey=" + serviceKey +
				"&arsId=" + arsId;
		
		Document doc = getDocumentByUrl(urlString);
		
		if (doc == null) {
			return null;
		}
		
		List<HashMap<String, Object>> busArriveList = new ArrayList<HashMap<String, Object>>();
		
		NodeList itemList = doc.getElementsByTagName("itemList");
		for (int i = 0; i < itemList.getLength(); i++) {
			
			HashMap<String, Object> busArrive = new HashMap<String, Object>();
			for (Node node = itemList.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
				
				if (node.getNodeName().equals("stNm")) {
					busArrive.put("stationName", node.getTextContent());
				} else if (node.getNodeName().equals("arsId")) {
					busArrive.put("arsId", node.getTextContent());
				} else if (node.getNodeName().equals("adirection")) {
					busArrive.put("direction", node.getTextContent());
				} else if (node.getNodeName().equals("nxtStn")) {
					busArrive.put("nextStnName", node.getTextContent());
				} else if (node.getNodeName().equals("rtNm")) {
					busArrive.put("busNumber", node.getTextContent());
				} else if (node.getNodeName().equals("busType1")) {
					busArrive.put("firstBusType", node.getTextContent());
				} else if (node.getNodeName().equals("stationNm1")) {
					busArrive.put("firstBusStn", node.getTextContent());
				} else if (node.getNodeName().equals("arrmsg1")) {
					busArrive.put("firstBusMsg", node.getTextContent());
				} else if (node.getNodeName().equals("traTime1")) {
					busArrive.put("firstBusTime", node.getTextContent());
				} else if (node.getNodeName().equals("rerideNum1")) {
					busArrive.put("firstBusPeople", node.getTextContent());
				} else if (node.getNodeName().equals("busType2")) {
					busArrive.put("secondBusType", node.getTextContent());
				} else if (node.getNodeName().equals("stationNm2")) {
					busArrive.put("secondBusStn", node.getTextContent());
				} else if (node.getNodeName().equals("arrmsg2")) {
					busArrive.put("secondBusMsg", node.getTextContent());
				} else if (node.getNodeName().equals("traTime2")) {
					busArrive.put("secondBusTime", node.getTextContent());
				} else if (node.getNodeName().equals("rerideNum2")) {
					busArrive.put("secondBusPeople", node.getTextContent());
				}
			}
			
			busArriveList.add(busArrive);
		} // for loop
		
		return busArriveList;
	}
	
	
	/**
	 * ������� ���ã�� ��Ͽ� �ش� ���� Ȥ�� ������ ������ �� �ִ��� Ȯ���Ѵ�.
	 * @param userId ����� ID
	 * @param busOrStn Ȯ������ �ϴ� ���� ��ü
	 * @return boolean �̹� ������ true, ������ false
	 */
	public boolean searchFavorite(String userId, Object favObj) {
		
		Favorite favorite = new Favorite(userId);
		
		if (favObj.getClass() == Bus.class) {
			Bus bus = (Bus) favObj;
			favorite.setBusOrStnId(bus.getRoutId());
			
		} else if (favObj.getClass() == Station.class) {
			Station station = (Station) favObj;
			favorite.setBusOrStnId(station.getStnId());
		}
		
		List<Favorite> favList = busDao.selectFavoriteInfo(userId);
		
		for (Favorite favInList : favList) {
			if (favorite.equals(favInList)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * ���ã�⿡ ����, ������ ID�� �߰��Ѵ�.
	 * @param userId ����� ID
	 * @param favObj �߰��ϰ��� �ϴ� ������ ��ü 
	 * @return �߰� ���
	 */
	public boolean setFavorite(String userId, Object favObj) {
		boolean result = true;
		
		Favorite favorite = new Favorite(userId);
		
		if (favObj.getClass() == Bus.class) {
			Bus bus = (Bus) favObj;
			favorite.setBusOrStnId(bus.getRoutId());
			favorite.setTypeBus();
			
		} else if (favObj.getClass() == Station.class) {
			Station station = (Station) favObj;
			favorite.setBusOrStnId(station.getStnId());
			favorite.setTypeStation();
			
		}
		
		if (!busDao.insertFavorite(favorite)) {
			result = false;
		}
		
		return result;
	}
	
	
	/**
	 * ������ ���ã�⿡ ��ϵǾ� �ִ� ���� ������ ��ȯ�Ѵ�.
	 * @param userId ������� ID
	 * @return ���ã�� �� ������ ����Ʈ
	 */
	public List<Bus> getFavoriteBusList(String userId) {
		
		return busDao.selectFavoriteBus(userId);
	}
	
	
	/**
	 * ������ ���ã�⿡ ��ϵǾ� �ִ� ������ ������ ��ȯ�Ѵ�.
	 * @param userId ������� ID
	 * @return ���ã�� �� �������� ����Ʈ
	 */
	public List<Station> getFavoriteStnList(String userId) {
		
		return busDao.selectFavoriteStn(userId);
	}
	
	
	/**
	 * ������ ���ã�⿡ ��ϵǾ� �ִ� ����, ������ ������ ��ȯ�Ѵ�.
	 * @param userId ������� ID
	 * @return ������ ������ ������ ���ʷ� ������ ��
	 */
	public Map<String, Object> getFavoriteAll(String userId) {
		
		return busDao.selectFavoriteAll(userId);
	}
	
	
	/**
	 * ������ ���ã�⿡ ��ϵǾ� �ִ� ���� �����ϰ� ���� ���� �����Ѵ�.
	 * @param userId ������� ID
	 * @param busOrStn ���� Ȥ�� ������ ��ü
	 * @return ���� ���
	 */
	public boolean deleteFavorite(String userId, Object busOrStn) {
		Favorite favorite = null;
		
		if (busOrStn.getClass() == Bus.class) {
			Bus bus = (Bus) busOrStn;
			favorite = new Favorite(userId, bus.getRoutId());
			favorite.setTypeBus();
			
		} else if (busOrStn.getClass() == Station.class) {
			Station station = (Station) busOrStn;
			favorite = new Favorite(userId, station.getStnId());
			favorite.setTypeStation();
		}
		
		return busDao.deleteFavorite(favorite);
	}
	
	
	/**
	 * ������� �ֱ� �˻� ��Ͽ� �ش� ���� Ȥ�� ������ ������ �� �ִ��� Ȯ���Ѵ�.
	 * @param userId ����� ID
	 * @param busOrStn Ȯ������ �ϴ� ���� ��ü
	 * @return boolean �̹� ������ true, ������ false
	 */
	public boolean searchHistory(String userId, Object busOrStn) {
		
		History history = new History(userId);
		
		if (busOrStn.getClass() == Bus.class) {
			Bus bus = (Bus) busOrStn;
			history.setBusOrStnId(bus.getRoutId());
			
		} else if (busOrStn.getClass() == Station.class) {
			Station station = (Station) busOrStn;
			history.setBusOrStnId(station.getStnId());
		}
		
		List<History> hisList = busDao.selectHistoryInfo(userId);
		
		for (History hisInList : hisList) {
			if (history.equals(hisInList)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * �̹� ����Ǿ� �ִ� �˻� ����� �ð��� �ٲ� ������Ʈ �Ѵ�.
	 * @param userId ����� ID
	 * @param busOrStn �ð� �����Ϸ��� ���� ��ü
	 * @return ���� ���
	 */
	public boolean updateHistory(String userId, Object busOrStn) {
		boolean result = true;
		
		History history = new History(userId);
		
		if (busOrStn.getClass() == Bus.class) {
			Bus bus = (Bus) busOrStn;
			history.setBusOrStnId(bus.getRoutId());
			
		} else if (busOrStn.getClass() == Station.class) {
			Station station = (Station) busOrStn;
			history.setBusOrStnId(station.getStnId());
		}
		
		if (!busDao.updateHistoryInfo(history)) {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * ������� �˻� ����� �����Ѵ�.
	 * @param userId ����� ID
	 * @param hisObj �����Ϸ��� ���� Ȥ�� ������ ��ü
	 * @return �߰� ���
	 */
	public boolean setHistory(String userId, Object hisObj) {
		
		boolean result = true;
		
		History history = new History(userId);
		
		if (hisObj.getClass() == Bus.class) {
			Bus bus = (Bus) hisObj;
			history.setBusOrStnId(bus.getRoutId());
			history.setTypeBus();
			
		} else if (hisObj.getClass() == Station.class) {
			Station station = (Station) hisObj;
			history.setBusOrStnId(station.getStnId());
			history.setTypeStation();
			
		}
		
		if (!busDao.insertHistory(history)) {
			result = false;
		}
		
		return result;
	}
	
	
	/**
	 * ������ �ֱ� �˻���Ͽ� ����Ǿ� �ִ� ����, ������ ������ ��ȯ�Ѵ�. 
	 * @param userId ����� ID
	 * @return ������ ������ ������ ������ ��
	 */
	public List<Object> getHistory(String userId) {
		// List<Object> ���·� ����
		// �޴� ������ Bus, Station, History Ÿ�Կ� ���� �ٸ� �ൿ
		
		/*
		List<Object> hisList = busDao.selectHistoryAll(userId);
		for (int i = 0; i < hisList.size(); i += 2) {
			History history = (History) hisList.get(i);
			System.out.print("[" + (i+1) + "] " + history.getIndate());
			
			Object busOrStn = hisList.get(i + 1);
			if (busOrStn.getClass() == Bus.class) {
				Bus bus = (Bus) busOrStn;
				System.out.print(" bus: " + bus.getRoutName());
			} else if (busOrStn.getClass() == Station.class) {
				Station station = (Station) busOrStn;
				System.out.print(" station: " + station.getStnName());
			}
		}
		*/
		
		return busDao.selectHistoryAll(userId);
	}
	
	
	/**
	 * ��� ������ ������ ���Ϸκ��� �о� DB�� �����Ѵ�.
	 * @return ���� ���� ����
	 */
	public boolean databaseUpdate() {
		File file = new File("AllBusesList.txt");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String str = "";
		
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				str += line;
			}
		} catch (FileNotFoundException e) {
			System.out.println("DB ������Ʈ ������ ã�� �� �����ϴ�.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("DB ������Ʈ ���Ͽ� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
		
		if (!busDao.insertBuses(parseJSONBuses(str))) {
			System.out.println("DB ������Ʈ ���� ������ �߻��߽��ϴ�.");
			return false;
		}
		
		return true;
	}
	
	
	/*
	 * ==================================== Parsing Methods ====================================
	 */
	
	/**
	 * URL�κ��� ������ �޾� �Ľ��� �� ��ȯ�Ѵ�.
	 * @param urlString ������ �޾ƿ� URL ���ڿ�
	 * @return �Ľ̵� Document
	 */
	private Document getDocumentByUrl(String urlString) {
		URL url = null;
		Document doc = null;
		URLConnection connection = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("�߸��� URL �Է�");
			e.printStackTrace();
		}
		
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			System.out.println("URL ���� ����");
			e.printStackTrace();
		}
		
		// ������� XML������ �Ľ��ϴ� �κ�
		try {
			doc = parseXML(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("connection���κ��� inputstream�޾ƿ��� ����");
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * �Ѱܹ��� ������ XML ������ �������� �Ľ��Ѵ�.
	 * @param stream �Ľ��� ����
	 * @return XML �������� �Ľ̵� ����
	 */
	private Document parseXML(InputStream stream) {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
			
			doc = objDocumentBuilder.parse(stream);
			
		} catch (ParserConfigurationException e) {
			System.out.println("objDocumentBuilder �������� ����");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.out.println("doc stream parse���� ����");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("doc stream parse���� ����");
			e.printStackTrace();
			return null;
		}
		
		return doc;
	}
	
	/**
	 * ���� ����� JSON �����͸� �޾� Bus ��ü�� ����Ʈ�� �Ľ��Ѵ�.
	 * @param jsonData ���Ϸκ��� �о� ���� ���ڿ�
	 * @return Bus ��ü�� ����Ʈ
	 */
	private List<Bus> parseJSONBuses(String jsonData) {
		List<Bus> busesList = new ArrayList<>();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				JSONObject busJSONObject = (JSONObject) busArrayList.get(i);
				
				Bus bus = new Bus();
				bus.setRoutId(Integer.parseInt((String)busJSONObject.get("routId")));
				bus.setRoutName((String) busJSONObject.get("routName"));
				bus.setRoutType((String) busJSONObject.get("routType"));
				bus.setStnFirst((String) busJSONObject.get("stnFirst"));
				bus.setStnLast((String) busJSONObject.get("stnLast"));
				bus.setTimeFirst((String) busJSONObject.get("timeFirst"));
				bus.setTimeLast((String) busJSONObject.get("timeLast"));
				bus.setSatTimeFirst((String) busJSONObject.get("satTimeFirst"));
				bus.setSatTimeLast((String) busJSONObject.get("satTimeLast"));
				bus.setHolTimeFirst((String) busJSONObject.get("holTimeFirst"));
				bus.setHolTimeLast((String) busJSONObject.get("holTimeLast"));
				bus.setNorTerms((String) busJSONObject.get("norTerms"));
				bus.setSatTerms((String) busJSONObject.get("satTerms"));
				bus.setHolTerms((String) busJSONObject.get("holTerms"));
				bus.setCompanyNm((String) busJSONObject.get("companyNm"));
				bus.setTelNo((String) busJSONObject.get("telNo"));
				bus.setFaxNo((String) busJSONObject.get("faxNo"));
				bus.setEmail((String) busJSONObject.get("email"));
				
				busesList.add(bus);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return busesList;
	}
	
}


































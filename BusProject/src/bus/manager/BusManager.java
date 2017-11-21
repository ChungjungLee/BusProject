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
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
	
	// 공공데이터 API 활용을 위한 서비스 인증키
	private final String serviceKey =
			"9qJ%2FGyciiKC2bdjQYHAWCxxYmnJ0KmYn1ZySk6y8SJdOgcffBFBpTOxUgobyps504QppRIpzOrPbIkZoJWJhtg%3D%3D";
	
	// 구글 geocode API 활용을 위한 서비스 인증키
	private final String geocodeKey = 
			"AIzaSyAJ4G_Bsn0dBdkXBjn7CpfN1L3WDxumBu0";
	
	BusDAO busDao;
	
	public BusManager() {
		busDao = new BusDAO();
	}
	
	
	/**
	 * ID와 비밀번호를 입력받아 로그인을 시도한다.
	 * @param userId 아이디
	 * @param userPw 비밀번호
	 * @return loginResult 0: 로그인 성공, 1: 비밀번호 오류, 2: 계정 존재하지 않음
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
	 * ID와 비밀번호를 입력받아 새로운 계정을 만든다.
	 * @param userId 아이디
	 * @param userPw 비밀번호
	 */
	public void signIn(String userId, String userPw) {
		// 회원가입
		Account account = new Account(userId, userPw);
		
		if (busDao.insertAccount(account)) {
			
		} else {
			
		}
	}
	
	
	/**
	 * 입력받은 숫자를 통해 해당 숫자를 포함하는 버스를 검색한다.
	 * @param busNum 검색하고자 하는 숫자
	 * @return 입력한 숫자를 포함하는 버스 목록
	 */
	public List<Bus> searchBuses(String busNum) {
		// DB에서 받아올 것
		List<Bus> busList = busDao.srchBusContainsNum(busNum);
		
		return busList;
	}
	
	
	/**
	 * 확인하려는 버스의 전체 노선도와 실시간 위치를 받아온다.
	 * @param busId 확인하려는 버스의 ID
	 * @return 노선도와 실시간 위치
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
		
		List<RealTimeStation> routeMap = new ArrayList<>();	// 실제 반환할 리스트
		
		for (int indexRoute = 0; indexRoute < itemListRoute.getLength(); indexRoute++) {
			
			RealTimeStation station = new RealTimeStation();
			// 먼저 노선도 정보를 하나 읽어들인다.
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
	 * 입력받은 문자를 통해 해당 문자를 포함하는 정류장을 검색한다.
	 * @param keyword 검색하고자 하는 문자
	 * @return 입력한 문자를 포함하는 정류장 목록
	 */
	public List<Station> searchStations(String keyword) {
		
		String urlString;
		try {
			urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?serviceKey=" + serviceKey +
						"&stSrch=" + URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("인코딩 설정을 지원하지 않는 오류");
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
		
		// DB에 저장
		busDao.insertStations(srchStnList);
		
		return srchStnList;
	}
	
	
	/**
	 * 주소를 입력 받아 근처의 버스 정류장을 검색한다.
	 * @param keyword 검색할 주소
	 * @param radius 검색할 반경 범위(m)
	 * @return 정류장의 목록 
	 */
	public List<Station> searchNearStations(String keyword, int radius) {
		
		double[] gpsLoc = getGpsLocation(keyword);
		
		if (gpsLoc[0] == 0 && gpsLoc[0] == 0) {
			return null;
		}
		
		String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?serviceKey=" + serviceKey +
				"&tmX=" + gpsLoc[1] +
				"&tmY=" + gpsLoc[0] +
				"&radius=" + radius;
		
		Document doc = getDocumentByUrl(urlString);
		
		if (doc == null) {
			return null;
		}
		
		List<Station> nearStnList = new ArrayList<>();
		
		NodeList itemList = doc.getElementsByTagName("itemList");
		
		for (int i = 0; i < itemList.getLength(); i++) {
			
			Station station = new Station();
			for (Node node = itemList.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
				
				if (node.getNodeName().equals("stationNm")) {
					station.setStnName(node.getTextContent());
				} else if (node.getNodeName().equals("arsId")) {
					station.setArsId(node.getTextContent());
				} else if (node.getNodeName().equals("stationId")) {
					station.setStnId(Integer.parseInt(node.getTextContent()));
				}
			}
			
			nearStnList.add(station);
		}
		
		// DB에 저장
		busDao.insertStations(nearStnList);
		
		return nearStnList;
	}
	
	
	/**
	 * 확인하려는 정류장의 실시간 버스 도착 정보를 받아온다.
	 * @param arsId 확인하려는 정류장 고유 번호
	 * @return 실시간 버스 도착 정보
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
	 * 사용자의 즐겨찾기 목록에 해당 버스 혹은 정류장 정보가 들어가 있는지 확인한다.
	 * @param userId 사용자 ID
	 * @param busOrStn 확인코자 하는 정보 객체
	 * @return boolean 이미 있으면 true, 없으면 false
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
	 * 즐겨찾기에 버스, 정류장 ID를 추가한다.
	 * @param userId 사용자 ID
	 * @param favObj 추가하고자 하는 데이터 객체 
	 * @return 추가 결과
	 */
	public boolean setFavorite(String userId, Object favObj) {
		boolean result = true;
		
		Favorite favorite = new Favorite(userId);
		
		if (favObj.getClass() == Bus.class) {
			Bus bus = (Bus) favObj;
			if (busDao.selectBus(bus.getRoutId()) == null) {
				// 데이터베이스에 없는 버스를 즐겨찾기 하려는 상황
				return false;
			}
			
			favorite.setBusOrStnId(bus.getRoutId());
			favorite.setTypeBus();
			
		} else if (favObj.getClass() == Station.class) {
			Station station = (Station) favObj;
			if (busDao.selectStation(station.getStnId()) == null) {
				// 데이터베이스에 없는 버스를 즐겨찾기 하려는 상황
				return false;
			}
			
			favorite.setBusOrStnId(station.getStnId());
			favorite.setTypeStation();
			
		}
		
		if (!busDao.insertFavorite(favorite)) {
			result = false;
		}
		
		return result;
	}
	
	
	/**
	 * 유저의 즐겨찾기에 등록되어 있는 버스 정보를 반환한다.
	 * @param userId 사용자의 ID
	 * @return 즐겨찾기 된 버스의 리스트
	 */
	public List<Bus> getFavoriteBusList(String userId) {
		
		return busDao.selectFavoriteBus(userId);
	}
	
	
	/**
	 * 유저의 즐겨찾기에 등록되어 있는 정류장 정보를 반환한다.
	 * @param userId 사용자의 ID
	 * @return 즐겨찾기 된 정류장의 리스트
	 */
	public List<Station> getFavoriteStnList(String userId) {
		
		return busDao.selectFavoriteStn(userId);
	}
	
	
	/**
	 * 유저의 즐겨찾기에 등록되어 있는 버스, 정류장 정보를 반환한다.
	 * @param userId 사용자의 ID
	 * @return 버스와 정류장 정보를 차례로 가지는 맵
	 */
	public Map<String, Object> getFavoriteAll(String userId) {
		
		return busDao.selectFavoriteAll(userId);
	}
	
	
	/**
	 * 유저의 즐겨찾기에 등록되어 있는 것중 삭제하고 싶은 것을 삭제한다.
	 * @param userId 사용자의 ID
	 * @param busOrStn 버스 혹은 정류장 객체
	 * @return 삭제 결과
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
	 * 사용자의 최근 검색 목록에 해당 버스 혹은 정류장 정보가 들어가 있는지 확인한다.
	 * @param userId 사용자 ID
	 * @param busOrStn 확인코자 하는 정보 객체
	 * @return boolean 이미 있으면 true, 없으면 false
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
	 * 이미 저장되어 있는 검색 기록을 시간만 바꿔 업데이트 한다.
	 * @param userId 사용자 ID
	 * @param busOrStn 시간 수정하려는 정보 객체
	 * @return 수정 결과
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
	 * 사용자의 검색 기록을 저장한다.
	 * @param userId 사용자 ID
	 * @param hisObj 저장하려는 버스 혹은 정류장 객체
	 * @return 추가 결과
	 */
	public boolean setHistory(String userId, Object hisObj) {
		
		boolean result = true;
		
		History history = new History(userId);
		
		// hisObj가 데이터베이스에 저장되어 있지 않은 경우는 dao.insertHistory()를 부르지 않고 return false;
		if (hisObj.getClass() == Bus.class) {
			Bus bus = (Bus) hisObj;
			if (busDao.selectBus(bus.getRoutId()) == null) {
				// 데이터베이스에 없는 버스를 즐겨찾기 하려는 상황
				return false;
			}
			
			history.setBusOrStnId(bus.getRoutId());
			history.setTypeBus();
			
		} else if (hisObj.getClass() == Station.class) {
			Station station = (Station) hisObj;
			if (busDao.selectStation(station.getStnId()) == null) {
				// 데이터베이스에 없는 버스를 즐겨찾기 하려는 상황
				return false;
			}
			
			history.setBusOrStnId(station.getStnId());
			history.setTypeStation();
			
		}
		
		if (!busDao.insertHistory(history)) {
			result = false;
		}
		
		return result;
	}
	
	
	/**
	 * 유저의 최근 검색기록에 저장되어 있는 버스, 정류장 정보를 반환한다. 
	 * @param userId 사용자 ID
	 * @return 버스와 정류장 정보를 가지는 맵
	 */
	public List<Object> getHistory(String userId) {
		// List<Object> 형태로 리턴
		// 받는 곳에서 Bus, Station, History 타입에 따라 다른 행동
		
		return busDao.selectHistoryAll(userId);
	}
	
	
	/**
	 * 모든 버스의 정보를 파일로부터 읽어 DB에 저장한다.
	 * @return 저장 성공 여부
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
			System.out.println("DB 업데이트 파일을 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("DB 업데이트 파일에 오류가 발생했습니다.");
			e.printStackTrace();
		}
		
		if (!busDao.insertBuses(parseJSONBuses(str))) {
			System.out.println("DB 업데이트 도중 오류가 발생했습니다.");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 문자열 주소를 입력 받아 좌표 위치를 반환한다.
	 * @param address 변환할 주소 문자열
	 * @return 정수형 배열; [0]에는 gpsY, [1]에는 gpsX 값이 들어가 있다.
	 */
	private double[] getGpsLocation(String address) {
		
		String encodedAddress = null;
		try {
			encodedAddress = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String urlString = "https://maps.googleapis.com/maps/api/geocode/json?region=kr"
							+ "&address=" + encodedAddress
							+ "&key=" + geocodeKey;
		
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("잘못된 URL 입력");
			e.printStackTrace();
		}
		
		// read from the URL
	    Scanner scan = null;
		try {
			scan = new Scanner(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    String str = new String();
	    
	    while (scan.hasNext()) {
	        str += scan.nextLine();
	    }
	    scan.close();
	 
	    // build a JSON object
	    JSONParser jsonParser = new JSONParser();
	    
	    JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
		double[] gpsLoc = {0.0, 0.0};
		
		if (! jsonObject.get("status").equals("OK")) {
			System.out.println("google geocode api response error");
			
		} else {
			JSONObject resultObject = (JSONObject) ((JSONArray) jsonObject.get("results")).get(0);    
			
			JSONObject geometryObject = (JSONObject) resultObject.get("geometry");
			JSONObject locationObject = (JSONObject) geometryObject.get("location");
			
			gpsLoc[0] = Double.parseDouble((String) locationObject.get("lat").toString());	// gpsY
			gpsLoc[1] = Double.parseDouble((String) locationObject.get("lng").toString());	// gpsX
		}
		
		return gpsLoc;
	}
	
	
	/*
	 * ==================================== Parsing Methods ====================================
	 */
	
	/**
	 * URL로부터 정보를 받아 파싱한 후 반환한다.
	 * @param urlString 정보를 받아올 URL 문자열
	 * @return 파싱된 Document
	 */
	private Document getDocumentByUrl(String urlString) {
		URL url = null;
		Document doc = null;
		URLConnection connection = null;
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("잘못된 URL 입력");
			e.printStackTrace();
		}
		
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			System.out.println("URL 접속 오류");
			e.printStackTrace();
		}
		
		// 응답받은 XML정보를 파싱하는 부분
		try {
			doc = parseXML(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("connection으로부터 inputstream받아오기 오류");
			e.printStackTrace();
		}
		
		return doc;
	}
	
	/**
	 * 넘겨받은 정보를 XML 형식을 바탕으로 파싱한다.
	 * @param stream 파싱할 정보
	 * @return XML 형식으로 파싱된 파일
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
			System.out.println("objDocumentBuilder 생성에서 오류");
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			System.out.println("doc stream parse에서 오류");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("doc stream parse에서 오류");
			e.printStackTrace();
			return null;
		}
		
		return doc;
	}
	
	/**
	 * 버스 목록의 JSON 데이터를 받아 Bus 객체의 리스트로 파싱한다.
	 * @param jsonData 파일로부터 읽어 들인 문자열
	 * @return Bus 객체의 리스트
	 */
	private List<Bus> parseJSONBuses(String jsonData) {
		List<Bus> busesList = new ArrayList<>();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
			return busesList;
		}
		
		JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
		
		for (int i = 0; i < busArrayList.size(); i++) {
			JSONObject busJSONObject = (JSONObject) busArrayList.get(i);
			
			Bus bus = new Bus();
			bus.setRoutId(Integer.parseInt((String)busJSONObject.get("routId")));
			bus.setRoutName((String) busJSONObject.get("routName"));
			bus.setRoutType((String) busJSONObject.get("routType"));
			
			busesList.add(bus);
		}
		
		return busesList;
	}
	
}



































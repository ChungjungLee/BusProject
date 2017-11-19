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
import bus.vo.Bus;
import bus.vo.Station;

public class BusManager {
	
	// 공공데이터 API 활용을 위한 서비스 인증키
	private final String serviceKey =
			"9qJ%2FGyciiKC2bdjQYHAWCxxYmnJ0KmYn1ZySk6y8SJdOgcffBFBpTOxUgobyps504QppRIpzOrPbIkZoJWJhtg%3D%3D";
	
	BusDAO busDao;
	
	public BusManager() {
		busDao = new BusDAO();
	}
	
	/**
	 * 입력받은 숫자를 통해 해당 숫자를 포함하는 버스를 검색한다.
	 * @param busNum 검색하고자 하는 숫자
	 * @return 입력한 숫자를 포함하는 버스 목록
	 */
	public List<Bus> searchBuses(String busNum) {
		// DB에서 받아올 것
		
		return null;
	}
	
	
	/**
	 * 확인하려는 버스의 전체 노선도와 실시간 위치를 받아온다.
	 * @param busId 확인하려는 버스의 ID
	 * @return 노선도와 실시간 위치
	 */
	public List<Station> getRouteMap(int busRouteId) {
		String urlString = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?serviceKey=" + serviceKey + 
				"&busRouteId=" + busRouteId;
		
		Document doc = getDocumentByUrl(urlString);
		
		if (doc == null) {
			return null;
		}
		
		List<Station> routeMap = new ArrayList<>();
		
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
			
			routeMap.add(station);
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
		
		return srchStnList;
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
	 * 즐겨찾기에 등록되어 있는 버스, 정류장 ID를 반환한다.
	 * @param 
	 * @return
	 */
	public boolean getFavorite(String busNum) {
		return true;
	}
	
	
	/**
	 * 즐겨찾기에 버스, 정류장 ID를 추가한다.
	 * @param userId, favObj
	 * @return
	 */
	public boolean setFavorite(String userId, Object favObj) {
		
		if (favObj.getClass() == Bus.class) {
			
		} else if (favObj.getClass() == Station.class) {
			
		}
		
		return true;
	}
	
	/**
	 * 유저의 최근 검색 목록 전부를 반환한다.
	 * @param type
	 * @param throwId
	 * @return
	 */
	public List<Integer> recentSearch(String userId, Object srchedObj) {
		
		if (srchedObj.getClass() == Bus.class) {
			
		} else if (srchedObj.getClass() == Station.class) {
			
		}
		
		return null;
	}
	
	
	public int userLogIn(String userId, String userPw) {
		// 로그인
		return 0;
		
	}


	public void signIn(String userId, String userPw) {
		// 회원가입
		
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
			System.out.println("[Error] DB 업데이트 파일을 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[Error] DB 업데이트 도중 오류가 발생했습니다.");
			e.printStackTrace();
		}
		
		if (!busDao.insertBuses(parseJSONBuses(str))) {
			System.out.println("[Error] DB 업데이트 도중 오류가 발생했습니다.");
			return false;
		}
		
		return true;
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
	public List<Bus> parseJSONBuses(String jsonData) {
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


































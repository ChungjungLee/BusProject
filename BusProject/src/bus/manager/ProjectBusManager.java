package bus.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bus.dao.BusDAO;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusManager {

	ServerDataManager serverManager = new ServerDataManager();
	BusDAO busDao = new BusDAO();
	
	/**
	 * 사용자가 입력한 숫자를 포함하는 버스의 목록을 검색한다.
	 * @param busNum 찾고자 하는 숫자
	 * @return 숫자를 포함하는 버스의 목록
	 */
	public List<Bus> searchBuses(String busNum) {
		serverManager.sendRequestBusesByNum(busNum);
		
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Bus> busesList = parseJSONBusesByNum(busesJSON);
		
		busDao.insertBuses(busesList);
		
		// TODO: 그 결과를 받아 리턴하기
		
		return busesList;
	}

	/**
	 * 사용자가 입력한 문자를 포함하는 정류장의 목록을 검색한다.
	 * @param stnName 검색하고자 하는 문자열
	 * @return 문자열을 포함하는 정류장의 목록
	 */
	public List<Station> searchStations(String keyword) {
		serverManager.sendRequestStationsByWord(keyword);
		
		String stationsJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Station> stationsList = parseJSONStationsByWord(stationsJSON);
		
		// TODO: 생성된 객체를 Dao 측에 넘기기
		//busDao.insertBuses(stationsList);
		
		// TODO: 그 결과를 받아 리턴하기
		
		return stationsList;
	}

	/**
	 * 특정 버스의 노선도를 받아 넘겨준다.
	 * @param busId 노선도를 확인하려는 버스의 id
	 * @return 노선도
	 */
	public List<Station> getRouteMap(int busId) {
		serverManager.sendRequestStationsByBus(busId);
		
		String routeMapJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Station> routeMapList = parseJSONStationsByBus(routeMapJSON);
		
		// TODO: 생성된 객체를 Dao 측에 넘기기
		//busDao.insertRouteMap(routeMapList);
		
		return routeMapList;
	}
	
	/**
	 * 특정 정류장을 지나는 버스의 목록을 넘겨준다.
	 * @param arsId 정류장의 번호
	 * @return 정류장을 지나는 버스들
	 */
	public List<Bus> getBuses(String arsId) {
		serverManager.sendRequestBusesByStation();
		
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		//List<Bus> busesList = parseJSONBusesByBus(busesJSON);
		
		//return busesList;
		return null;
	}

	/**
	 * 즐겨찾기에 등록되어 있는 것들을 출력한다.
	 * @param busNum
	 * @return
	 */
	public boolean getFavorite(String busNum) {
		
		return false;
	}
	
	/*
	 * ================================= JSON Parser ===================================
	 */
	
	/**
	 * 입력받은 문자를 포함하는 버스 목록의 JSON 데이터를 받아 Bus 객체를 생성한다.
	 * @param jsonData 서버로부터 받은 버스 목록
	 * @return 입력받은 문자를 포함하는 버스의 리스트
	 */
	public List<Bus> parseJSONBusesByNum(String jsonData) {
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
	
	/**
	 * 버스 노선도 JSON 데이터를 받아 Station 객체를 생성한다.
	 * @param jsonData 응답받은 특정 버스의 노선도
	 * @return 가공된 JSON 문자열
	 */
	public List<Station> parseJSONStationsByBus(String jsonData) {
		List<Station> routeMapList = new ArrayList<>();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray routeMapArrayList = (JSONArray) jsonObject.get("resultList");
			
			for (int i = 0; i < routeMapArrayList.size(); i++) {
				JSONObject routeMapJSONObject = (JSONObject) routeMapArrayList.get(i);
				
				Station station = new Station();
				station.setStationId(Integer.parseInt((String)routeMapJSONObject.get("stationId")));
				station.setArsId((String) routeMapJSONObject.get("arsId"));
				station.setStnName((String) routeMapJSONObject.get("stnName"));
				
				routeMapList.add(station);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return routeMapList;
	}
	
	/**
	 * 특정 문자열을 포함하는 정류장 목록을 받아 JSON 데이터를 추출한다.
	 * @param jsonData 응답받은 정류장들의 목록
	 * @return 가공된 JSON 문자열
	 */
	public List<Station> parseJSONStationsByWord(String jsonData) {
		List<Station> stationsList = new ArrayList<Station>();
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray stationsArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < stationsArrayList.size(); i++) {
				JSONObject stationJSONObject = (JSONObject) stationsArrayList.get(i);
				
				Station station = new Station();
				station.setStationId(Integer.parseInt((String) stationJSONObject.get("stnId")));
				station.setArsId((String) stationJSONObject.get("arsId"));
				station.setStnName((String) stationJSONObject.get("stnName"));
				
				stationsList.add(station);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stationsList;
	}
}

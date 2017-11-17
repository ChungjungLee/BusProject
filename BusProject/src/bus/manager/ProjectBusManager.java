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
	 * ����ڰ� �Է��� ���ڸ� �����ϴ� ������ ����� �˻��Ѵ�.
	 * @param busNum ã���� �ϴ� ����
	 * @return ���ڸ� �����ϴ� ������ ���
	 */
	public List<Bus> searchBuses(String busNum) {
		serverManager.sendRequestBusesByNum(busNum);
		
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Bus> busesList = parseJSONBusesByNum(busesJSON);
		
		busDao.insertBuses(busesList);
		
		// TODO: �� ����� �޾� �����ϱ�
		
		return busesList;
	}

	/**
	 * ����ڰ� �Է��� ���ڸ� �����ϴ� �������� ����� �˻��Ѵ�.
	 * @param stnName �˻��ϰ��� �ϴ� ���ڿ�
	 * @return ���ڿ��� �����ϴ� �������� ���
	 */
	public List<Station> searchStations(String keyword) {
		serverManager.sendRequestStationsByWord(keyword);
		
		String stationsJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Station> stationsList = parseJSONStationsByWord(stationsJSON);
		
		// TODO: ������ ��ü�� Dao ���� �ѱ��
		//busDao.insertBuses(stationsList);
		
		// TODO: �� ����� �޾� �����ϱ�
		
		return stationsList;
	}

	/**
	 * Ư�� ������ �뼱���� �޾� �Ѱ��ش�.
	 * @param busId �뼱���� Ȯ���Ϸ��� ������ id
	 * @return �뼱��
	 */
	public List<Station> getRouteMap(int busId) {
		serverManager.sendRequestStationsByBus(busId);
		
		String routeMapJSON = serverManager.getJSON(serverManager.getResponse());
		
		List<Station> routeMapList = parseJSONStationsByBus(routeMapJSON);
		
		// TODO: ������ ��ü�� Dao ���� �ѱ��
		//busDao.insertRouteMap(routeMapList);
		
		return routeMapList;
	}
	
	/**
	 * Ư�� �������� ������ ������ ����� �Ѱ��ش�.
	 * @param arsId �������� ��ȣ
	 * @return �������� ������ ������
	 */
	public List<Bus> getBuses(String arsId) {
		serverManager.sendRequestBusesByStation();
		
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		//List<Bus> busesList = parseJSONBusesByBus(busesJSON);
		
		//return busesList;
		return null;
	}

	/**
	 * ���ã�⿡ ��ϵǾ� �ִ� �͵��� ����Ѵ�.
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
	 * �Է¹��� ���ڸ� �����ϴ� ���� ����� JSON �����͸� �޾� Bus ��ü�� �����Ѵ�.
	 * @param jsonData �����κ��� ���� ���� ���
	 * @return �Է¹��� ���ڸ� �����ϴ� ������ ����Ʈ
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
	 * ���� �뼱�� JSON �����͸� �޾� Station ��ü�� �����Ѵ�.
	 * @param jsonData ������� Ư�� ������ �뼱��
	 * @return ������ JSON ���ڿ�
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
	 * Ư�� ���ڿ��� �����ϴ� ������ ����� �޾� JSON �����͸� �����Ѵ�.
	 * @param jsonData ������� ��������� ���
	 * @return ������ JSON ���ڿ�
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

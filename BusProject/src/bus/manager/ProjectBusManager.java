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
		
		// TODO: ������ ��ü�� Dao ���� �ѱ��
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
		// TODO: �����κ��� ������ ����� �޾ƿ���
		serverManager.sendRequestStationsByWord(keyword);
		
		// TODO: ���� ������ JSON parser�� �����ϱ�
		String stationsJSON = serverManager.getJSON(serverManager.getResponse());
		
		// TODO: ������ JSON ���ڿ��� ���� Bus ��ü �����ϱ�
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
		// TODO: �����κ��� �ش� ������ �뼱���� �޾ƿ���
		serverManager.sendRequestStationsByBus(busId);
		
		// TODO: ���� ������ JSON parser�� �����ϱ�
		String routeMapJSON = serverManager.getJSON(serverManager.getResponse());
		
		// TODO: ������ JSON ���ڿ��� ���� Bus ��ü �����ϱ�
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
		List<Bus> busesList = new ArrayList<Bus>();
		
		return busesList;
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
		List<Station> stations = new ArrayList<Station>();
		
		String result = "";
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				result += "=== ������ " + (i + 1) + "===\n";
				JSONObject bus = (JSONObject) busArrayList.get(i);
				result += "stnId: " + bus.get("stnId") + "\n";
				result += "layerGubn: " +bus.get("layerGubn") + "\n";
				result += "stnName: " +bus.get("stnName") + "\n";
				result += "stationCd: " +bus.get("stationCd") + "\n";
				result += "stationCdNm: " +bus.get("stationCdNm") + "\n";
				result += "posX: " +bus.get("posX") + "\n";
				result += "posY: " +bus.get("posY") + "\n";
				result += "startNodeId: " +bus.get("startNodeId") + "\n";
				result += "endNodeId: " +bus.get("endNodeId") + "\n";
				result += "utmXpos: " +bus.get("utmXpos") + "\n";
				result += "utmYpos: " +bus.get("utmYpos") + "\n";
				result += "arsId: " +bus.get("arsId") + "\n";
				result += "code: " +bus.get("code") + "\n";
				result += "isBitStation: " +bus.get("isBitStation") + "\n";
				result += "rnum: " +bus.get("rnum") + "\n";
				
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return stations;
	}
}

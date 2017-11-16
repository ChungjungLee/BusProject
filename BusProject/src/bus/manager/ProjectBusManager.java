package bus.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusManager {
	
	ServerDataManager serverManager = new ServerDataManager();
	
	/**
	 * ����ڰ� �Է��� ���ڸ� �����ϴ� ������ ����� �޾� �Ѱ��ش�.
	 * @param busNum ã���� �ϴ� ����
	 * @return ���ڸ� �����ϴ� ������ ���
	 */
	public List<Bus> getBuses(String busNum) {
		// TODO: �����κ��� ������ ����� �޾ƿ���
		serverManager.sendRequestBusesByNum(busNum);
		
		// TODO: ���� ������ JSON parser�� �����ϱ�
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		// TODO: ������ JSON ���ڿ��� ���� Bus ��ü �����ϱ�
		List<Bus> busesList = parseJSONBusesByNum(busesJSON);
		
		// TODO: ������ ��ü�� Dao ���� �ѱ��
		
		
		// TODO: �� ����� �޾� �����ϱ�
		
		
		return busesList;
	}

	public List<Station> getStations(String stnName) {
		
		return null;
	}

	public List<Bus> getRouteMap(String busNum) {
		
		return null;
	}

	public boolean getFavorite(String busNum) {
		
		return false;
	}
	
	/**
	 * ���ڸ� ������ ���� ����� JSON �����͸� �޾� Bus ��ü�� �����Ѵ�.
	 * @param jsonData ������� Ư�� ������ �뼱��
	 * @return ������ JSON ���ڿ�
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
				bus.setRoutName((String) busJSONObject.get("routName"));
				bus.setRoutType((String) busJSONObject.get("routType"));
				bus.setStnFirst((String) busJSONObject.get("stnFirst"));
				bus.setStnLast((String) busJSONObject.get("stnLast"));
				bus.setBusIntervals((String) busJSONObject.get("busIntervals"));
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

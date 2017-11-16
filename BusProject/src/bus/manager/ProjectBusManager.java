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
	 * 사용자가 입력한 숫자를 포함하는 버스의 목록을 받아 넘겨준다.
	 * @param busNum 찾고자 하는 숫자
	 * @return 숫자를 포함하는 버스의 목록
	 */
	public List<Bus> getBuses(String busNum) {
		// TODO: 서버로부터 버스의 목록을 받아오기
		serverManager.sendRequestBusesByNum(busNum);
		
		// TODO: 받은 정보를 JSON parser로 가공하기
		String busesJSON = serverManager.getJSON(serverManager.getResponse());
		
		// TODO: 가공된 JSON 문자열을 통해 Bus 객체 생성하기
		List<Bus> busesList = parseJSONBusesByNum(busesJSON);
		
		// TODO: 생성된 객체를 Dao 측에 넘기기
		
		
		// TODO: 그 결과를 받아 리턴하기
		
		
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
	 * 숫자를 포함한 버스 목록의 JSON 데이터를 받아 Bus 객체를 생성한다.
	 * @param jsonData 응답받은 특정 버스의 노선도
	 * @return 가공된 JSON 문자열
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

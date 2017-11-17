package bus.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ServerDataManager {
	
	// 정보를 받아올 서버의 주소를 저장
	Socket socket;
	
	/*
	 * Constructor
	 */
	public ServerDataManager() {
		try {
			socket = new Socket("topis.seoul.go.kr", 80);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 특정 번호가 포함된 버스 목록을 요청한다.
	 * @param number 검색할 버스 번호
	 */
	public void sendRequestBusesByNum(String number) {
		try {
			String param = "totalSearch=" + number +
							"&jsFunction=fn_searchBus";
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "UTF-8"));
			
			bw.write("POST /map/busMap/selectBusList.do HTTP/1.1\r\n" + 
					"Host: " + socket.getInetAddress() + "\r\n" +
					"Content-Length: " + param.length() + "\r\n" +
					"Accept: application/json, text/javascript, */*; q=0.01\r\n" +
					"Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" + 
					"\r\n" +
					param);
			
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 숫자를 포함한 버스 목록을 받아 JSON 데이터를 추출한다.
	 * @param jsonData 응답받은 특정 버스의 노선도
	 * @return 가공된 JSON 문자열
	 */
	public String parseJSONBusesByNum(String jsonData) {
		String result = "";
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				JSONObject bus = (JSONObject) busArrayList.get(i);
				result += "routId: " +bus.get("routId") + "\n";
				result += "routName: " +bus.get("routName") + "\n";
				result += "rtCd: " +bus.get("rtCd") + "\n";
				result += "routType: " +bus.get("routType") + "\n";
				result += "tmpOrder: " +bus.get("tmpOrder") + "\n";
				result += "stnFirst: " +bus.get("stnFirst") + "\n";
				result += "stnLast: " +bus.get("stnLast") + "\n";
				result += "busIntervals: " +bus.get("busIntervals") + "\n";
				result += "timeFirst: " +bus.get("timeFirst") + "\n";
				result += "timeLast: " +bus.get("timeLast") + "\n";
				result += "satTimeFirst: " +bus.get("satTimeFirst") + "\n";
				result += "satTimeLast: " +bus.get("satTimeLast") + "\n";
				result += "holTimeFirst: " +bus.get("holTimeFirst") + "\n";
				result += "holTimeLast: " +bus.get("holTimeLast") + "\n";
				result += "routeDistance: " +bus.get("routeDistance") + "\n";
				result += "busFee: " +bus.get("busFee") + "\n";
				result += "busRunStatusCd: " +bus.get("busRunStatusCd") + "\n";
				result += "busRunStatus: " +bus.get("busRunStatus") + "\n";
				result += "turnPlaceStationId: " +bus.get("turnPlaceStationId") + "\n";
				result += "normalDayFirstBusTime: " +bus.get("normalDayFirstBusTime") + "\n";
				result += "normalDayLastBusTime: " +bus.get("normalDayLastBusTime") + "\n";
				result += "normalLastRouteAt: " +bus.get("normalLastRouteAt") + "\n";
				result += "normalLastBusId: " +bus.get("normalLastBusId") + "\n";
				result += "lowerDayFirstBusTime: " +bus.get("lowerDayFirstBusTime") + "\n";
				result += "lowerDayLastBusTime: " +bus.get("lowerDayLastBusTime") + "\n";
				result += "lowerLastRouteAt: " +bus.get("lowerLastRouteAt") + "\n";
				result += "lowerLastBusId: " +bus.get("lowerLastBusId") + "\n";
				result += "timegap: " + bus.get("timegap") + "\n";
				result += "norTerms: " +bus.get("norTerms") + "\n";
				result += "satTerms: " +bus.get("satTerms") + "\n";
				result += "holTerms: " +bus.get("holTerms") + "\n";
				result += "routNo: " +bus.get("routNo") + "\n";
				result += "companyNm: " +bus.get("companyNm") + "\n";
				result += "faxNo: " +bus.get("faxNo") + "\n";
				result += "telNo: " +bus.get("telNo") + "\n";
				result += "email: " +bus.get("email") + "\n";
				result += "etcDesc: " +bus.get("etcDesc") + "\n";
				result += "rn" +bus.get("rn") + "\n";
				result += "rnum: " +bus.get("rnum") + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 정류장 번호를 통해 해당 정류장을 이용하는 버스 목록을 요청한다.
	 */
	public void sendRequestBusesByStation() {
		try {
			String param = "url=http%3A%2F%2F210.96.13.82%3A8099%2Fapi%2Frest%2Fstationinfo%2F" +
							"getStationByUid&arsId=" + 24143;
			
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "UTF-8"));
			
			//아래처럼 하면 디비 내 모든 버스의 정보를 받아온다
			//bw.write("POST /map/busMap/selectBusList.do HTTP/1.1\r\n" + 
			bw.write("POST /map/getBusStn.do HTTP/1.1\r\n" +
					"Host: " + socket.getInetAddress() + "\r\n" +
					"Content-Length: " + param.length() + "\r\n" +
					"Accept: application/json, text/javascript, */*; q=0.01\r\n" +
					"Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" + 
					"\r\n" +
					param);
			
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 정류장을 지나는 버스 목록을 받아 JSON 데이터를 추출한다.
	 * @param jsonData 응답받은 정류장을 지나는 버스 목록
	 * @return 가공된 JSON 문자열
	 */
	public String parseJSONBusesByStation(String jsonData) {
		String result = "";
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				result += "===" + (i+1) + "번째 버스 번호===\n";
				JSONObject bus = (JSONObject) busArrayList.get(i);
				result += "posY: " + bus.get("posY") + "\n";
				result += "traSpd1: " +bus.get("traSpd1") + "\n";
				result += "posX: " +bus.get("posX") + "\n";
				result += "traSpd2: " +bus.get("traSpd2") + "\n";
				result += "rerdieDiv1: " +bus.get("rerdieDiv1") + "\n";
				result += "stationTp: " +bus.get("stationTp") + "\n";
				result += "rerdieDiv2: " +bus.get("rerdieDiv2") + "\n";
				result += "nxtStn: " +bus.get("nxtStn") + "\n";
				result += "sectNm: " +bus.get("sectNm") + "\n";
				result += "adirection: " +bus.get("adirection") + "\n";
				result += "sectOrd1: " +bus.get("sectOrd1") + "\n";
				result += "arrmsg1: " +bus.get("arrmsg1") + "\n";
				result += "arrmsg2: " +bus.get("arrmsg2") + "\n";
				result += "routeType: " +bus.get("routeType") + "\n";
				result += "gpsX: " +bus.get("gpsX") + "\n";
				result += "gpsY: " +bus.get("gpsY") + "\n";
				result += "sectOrd2: " +bus.get("sectOrd2") + "\n";
				result += "isArrive1: " +bus.get("isArrive1") + "\n";
				result += "isArrive2: " +bus.get("isArrive2") + "\n";
				result += "lastTm: " +bus.get("lastTm") + "\n";
				result += "stNm: " +bus.get("stNm") + "\n";
				result += "traTime2: " +bus.get("traTime2") + "\n";
				result += "staOrd: " +bus.get("staOrd") + "\n";
				result += "traTime1: " +bus.get("traTime1") + "\n";
				result += "stationNm1: " +bus.get("stationNm1") + "\n";
				result += "stationNm2: " +bus.get("stationNm2") + "\n";
				result += "busRouteId: " +bus.get("busRouteId") + "\n";
				result += "arrmsgSec2: " +bus.get("arrmsgSec2") + "\n";
				result += "arrmsgSec1: " +bus.get("arrmsgSec1") + "\n";
				result += "rtNm: " +bus.get("rtNm") + "\n";
				result += "nextBus: " +bus.get("nextBus") + "\n";
				result += "arsId: " +bus.get("arsId") + "\n";
				result += "vehId2: " +bus.get("vehId2") + "\n";
				result += "isFullFlag1: " +bus.get("isFullFlag1") + "\n";
				result += "plainNo2: " +bus.get("plainNo2") + "\n";
				result += "isFullFlag2: " +bus.get("isFullFlag2") + "\n";
				result += "plainNo1: " +bus.get("plainNo1") + "\n";
				result += "rerideNum2: " +bus.get("rerideNum2") + "\n";
				result += "vehId1: " +bus.get("vehId1") + "\n";
				result += "rerideNum1: " +bus.get("rerideNum1") + "\n";
				result += "term: " +bus.get("term") + "\n";
				result += "isLast1: " +bus.get("isLast1") + "\n";
				result += "busType1: " +bus.get("busType1") + "\n";
				result += "isLast2: " +bus.get("isLast2") + "\n";
				result += "busType2: " +bus.get("busType2") + "\n";
				result += "firstTm: " +bus.get("firstTm") + "\n";
				result += "stId: " +bus.get("stId") + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 버스 번호를 통해 해당 버스의 전체 노선도를 요청한다.
	 */
	public void sendRequestStationsByBus(String busNum) {
		try {
			String param = "routId=" + busNum;
			
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "UTF-8"));
			
			bw.write("POST /map/busMap/selectBusLineStnList.do HTTP/1.1\r\n" + 
					"Host: " + socket.getInetAddress() + "\r\n" +
					"Content-Length: " + param.length() + "\r\n" +
					"Accept: application/json, text/javascript, */*; q=0.01\r\n" +
					"Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" + 
					"\r\n" + 
					param);
			
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 버스 노선도를 받아 JSON 데이터를 추출한다.
	 * @param jsonData 응답받은 특정 버스의 노선도
	 * @return 가공된 JSON 문자열
	 */
	public String parseJSONStationsByBus(String jsonData) {
		String result = "";
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("resultList");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				result += "===" + (i+1) + "번째 정류장===\n";
				JSONObject bus = (JSONObject) busArrayList.get(i);
				result += "stationCd: " + bus.get("stationCd") + "\n";
				result += "layerGubn: " + bus.get("layerGubn") + "\n";
				result += "stnId: " + bus.get("stnId") + "\n";
				result += "arsId: " +bus.get("arsId") + "\n";
				result += "code: " + bus.get("code") + "\n";
				result += "stnName: " +bus.get("stnName") + "\n";
				result += "name: " + bus.get("name") + "\n";
				result += "posX: " + bus.get("posX") + "\n";
				result += "posY: " + bus.get("posY") + "\n";
				result += "routeId: " +bus.get("routeId") + "\n";
				result += "stationOrd: " +bus.get("stationOrd") + "\n";
				result += "stationId: " +bus.get("stationId") + "\n";
				result += "guganId: " +bus.get("guganId") + "\n";
				result += "guganNm: " + bus.get("guganNm") + "\n";
				result += "guganLen: " + bus.get("guganLen") + "\n";
				result += "routNm: " +bus.get("routNm") + "\n";
				result += "dir: " +bus.get("dir") + "\n";
				result += "turnPlaceStationId: " + bus.get("turnPlaceStationId") + "\n";
				result += "turnPlaceStationYn: " +bus.get("turnPlaceStationYn") + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 특정 문자열을 가지고 있는 정류장을 요청한다.
	 */
	public void sendRequestStationsByWord() {
		try {
			String param = "totalSearch=" + " " +
					"&jsFunction=fn_searchBusStn";
			
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), "UTF-8"));
			
			bw.write("POST /map/busMap/selectBusStnList.do HTTP/1.1\r\n" + 
					"Host: " + socket.getInetAddress() + "\r\n" +
					"Content-Length: " + param.length() + "\r\n" +
					"Accept: application/json, text/javascript, */*; q=0.01\r\n" +
					"Content-Type: application/x-www-form-urlencoded; charset=UTF-8\r\n" + 
					"\r\n" + 
					param);
			
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 특정 문자열을 포함하는 정류장 목록을 받아 JSON 데이터를 추출한다.
	 * @param jsonData 응답받은 정류장들의 목록
	 * @return 가공된 JSON 문자열
	 */
	public String parseJSONStationsByWord(String jsonData) {
		String result = "";
		
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
			JSONArray busArrayList = (JSONArray) jsonObject.get("rows");
			
			for (int i = 0; i < busArrayList.size(); i++) {
				result += "=== 정류장 " + (i + 1) + "===\n";
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
			
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 응답받은 문자열을 반환한다.
	 * @return 요청의 응답을 문자열로 변환한 것
	 */
	public String getResponse() {
		String result = "";
		
		try {
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			String line;
			
			while(true) {
				line = br.readLine();
				//System.out.println(line);
				if (line.equals("0")) {
					break;
				}
				
				result += line + "\n";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 웹으로부터 응답받은 것으로부터 JSON 데이터를 추출한다.
	 * @param input 응답받은 문자열
	 * @return 추출된 JSON 데이터
	 */
	public String getJSON(String input) {
		String result = "";
		String[] data = input.split("\n\n")[1].split("\n");
		
		for (int i = 1; i < data.length; i += 2) {
			result += data[i];
		}
		
		return result;
	}
	
}























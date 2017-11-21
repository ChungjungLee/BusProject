package api.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ApiTest {
	
	// ���������� API Ȱ���� ���� ���� ����Ű
	private final String serviceKey =
			"9qJ%2FGyciiKC2bdjQYHAWCxxYmnJ0KmYn1ZySk6y8SJdOgcffBFBpTOxUgobyps504QppRIpzOrPbIkZoJWJhtg%3D%3D";
	
	// ���� ���� API Ȱ���� ���� ���� Ű
	private final String geocodingKey = 
			"AIzaSyAJ4G_Bsn0dBdkXBjn7CpfN1L3WDxumBu0";
	
	private String busNumber;
	Socket socket;
	
	/*
	 * Constructor
	 */
	public ApiTest() {
		try {
			//socket = new Socket("topis.seoul.go.kr", 80);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	public void start() {
		// URL�κ��� XML������ �Է� �޴� �κ�
		//String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?serviceKey=" + serviceKey + "&stSrch=" + "���տ��";
		String urlString = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?serviceKey=" + serviceKey + "&stSrch=" + "341";
		
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("�߸��� URL �Է�");
			e.printStackTrace();
		}
		
		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			System.out.println("URL ���� ����");
			e.printStackTrace();
		}
		
		
		// ������� XML������ �Ľ��ϴ� �κ�
		Document doc = null;
		try {
			//System.out.println(connection.getInputStream());
			doc = parseXML(connection.getInputStream());
		} catch (IOException e) {
			System.out.println("connection���κ��� inputstream�޾ƿ��� ����");
			e.printStackTrace();
		}
		
		NodeList header = doc.getElementsByTagName("msgHeader");
		for (int i = 0; i < header.getLength(); i++) {
			for (Node node = header.item(i).getFirstChild(); 
					node != null; node = node.getNextSibling()) {
				
				if (node.getNodeName().equals("headerCd")) {
					System.out.println("headerCd:" + node.getTextContent());
				} else if (node.getNodeName().equals("headerMsg")) {
					System.out.println("headerMsg: " + node.getTextContent());
				} else if (node.getNodeName().equals("itemCount")) {
					System.out.println("itemCount: " + node.getTextContent());
				}
			}
		}
		
		NodeList itemList = doc.getElementsByTagName("itemList");
		//System.out.println("size of nodeList: " + itemList.getLength());
		for (int i = 0; i < itemList.getLength(); i++) {
			for (Node node = itemList.item(i).getFirstChild(); 
					node != null; node = node.getNextSibling()) {
				
				if (node.getNodeName().equals("stNm")) {
					System.out.println("stnNm:" + node.getTextContent());
				} else if (node.getNodeName().equals("arsId")) {
					System.out.println("arsId: " + node.getTextContent());
				} else if (node.getNodeName().equals("stId")) {
					System.out.println("stnId: " + node.getTextContent());
				}
			}
		}
		
	}
	*/
	
	public void start() {
		
		String testStr1 = "abcdefghi";
		String testStr2 = "abcdefghijklmnopqr";
		
		System.out.println(String.format("%-20s%s", testStr1, "zkzkzk"));
		System.out.println(String.format("%-20s%s", testStr2, "zkzkzk"));
		
		/*
		String keyword = "�Ｚ�� �ڿ���";
		
		String encodedKeyword = null;
		try {
			encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String urlString = "https://maps.googleapis.com/maps/api/geocode/json?"
							+ "address=" + encodedKeyword
							+ "&key=" + geocodingKey;
		
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("�߸��� URL �Է�");
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
	    
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	 
	    // build a JSON object
	    JSONParser jsonParser = new JSONParser();
	    
	    JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
		if (! jsonObject.get("status").equals("OK")) {
			System.out.println("google geocode api response error");
		}
		
		JSONObject resultObject = (JSONObject) ((JSONArray) jsonObject.get("results")).get(0);    
		
		JSONObject geometryObject = (JSONObject) resultObject.get("geometry");
		JSONObject locationObject = (JSONObject) geometryObject.get("location");
		
		System.out.println(resultObject.get("formatted_address"));
		int lat = Integer.parseInt((String) locationObject.get("lat"));
		int lng = Integer.parseInt((String) locationObject.get("lng"));
		*/
	}
	
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
	
	
	// 5�� ���� ���� ��û�� �����ϴ� �Լ�
	public void sendRequest() {
		try {
			String param = "pageIndex=1&pageSize=5&totalSearch=" + busNumber +
							"&recordPerPage=5&jsFunction=fn_searchBus";
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
	 * Ư�� ���ڿ��� �����ϴ� ������ ����� �޾� JSON �����͸� �����Ѵ�.
	 * @param jsonData ������� ��������� ���
	 * @return ������ JSON ���ڿ�
	 */
	public String parseJSONStationsByWord(String jsonData) {
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
			
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * ������� ���ڿ��� ��ȯ�Ѵ�.
	 * @return ��û�� ������ ���ڿ��� ��ȯ�� ��
	 */
	public String getResponse() {
		String result = "";
		
		try {
			BufferedReader br = 
					new BufferedReader(
							//new InputStreamReader(socket2.getInputStream(), "EUC-KR"));
							new InputStreamReader(socket.getInputStream(), "UTF-8"));
			
			String line;
			
			while(true) {
				line = br.readLine();
				System.out.println(line);
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
	 * �����κ��� ������� �����κ��� JSON �����͸� �����Ѵ�.
	 * @param input ������� ���ڿ�
	 * @return ����� JSON ������
	 */
	public String getJSON(String input) {
		System.out.println("enter getJSON()");
		String result = "";
		String[] data = input.split("\n\n")[1].split("\n");
		
		for (int i = 1; i < data.length; i += 2) {
			result += data[i];
			System.out.println(i + ": ");
			System.out.println(data[i]);
		}
		
		return result;
	}
	
}























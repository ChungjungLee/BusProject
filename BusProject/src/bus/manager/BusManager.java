package bus.manager;

import java.io.IOException;
import java.io.InputStream;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bus.vo.Bus;
import bus.vo.Station;

public class BusManager {
	
	// ���������� API Ȱ���� ���� ���� ����Ű
	private final String serviceKey =
			"9qJ%2FGyciiKC2bdjQYHAWCxxYmnJ0KmYn1ZySk6y8SJdOgcffBFBpTOxUgobyps504QppRIpzOrPbIkZoJWJhtg%3D%3D";
	
	public BusManager() {
		
	}
	
	
	/**
	 * �Է¹��� ���ڸ� ���� �ش� ���ڸ� �����ϴ� ������ �˻��Ѵ�.
	 * @param busNum �˻��ϰ��� �ϴ� ����
	 * @return �Է��� ���ڸ� �����ϴ� ���� ���
	 */
	public List<Bus> searchBuses(String busNum) {
		// DB���� �޾ƿ� ��
		
		return null;
	}
	
	
	/**
	 * Ȯ���Ϸ��� ������ ��ü �뼱���� �ǽð� ��ġ�� �޾ƿ´�.
	 * @param busId Ȯ���Ϸ��� ������ ID
	 * @return �뼱���� �ǽð� ��ġ
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
	 * ���ã�⿡ ��ϵǾ� �ִ� ����, ������ ID�� ��ȯ�Ѵ�.
	 * @param 
	 * @return
	 */
	public boolean getFavorite(String busNum) {
		return true;
	}
	
	/**
	 * ���ã�⿡ ���� ID�� �߰��Ѵ�.
	 * @param busId
	 * @return
	 */
	public boolean setFavoriteBus(String userId, int busId) {
		return true;
	}
	
	/**
	 * ���ã�⿡ ������ ID�� �߰��Ѵ�.
	 * @param userId 
	 * @param stnId
	 * @return
	 */
	public boolean setFavoriteStn(String userId, int stnId) {
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


	public List<Integer> recentSearch(String userId, int type, int throwId) {
		// ���� ���̵��, ���� Type�̸� 0, ������ Type�̸� 1�� �Ѱܹް�, ����id �Ǵ� ������id�� �Ѱܹ޴´�.
		return null;
	}
	
	public boolean databaseUpdate() {
		return true;
	}


	public int userLogIn(String userId, String userPw) {
		// �α���
		return 0;
		
	}


	public List<String> signIn(List<String> usersInfo) {
		// ȸ������
		return null;
	}
}


































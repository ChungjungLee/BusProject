package bus.test;

public class Main {

	public static void main(String[] args) {
		BusList busList = new BusList("341");
		
		// 특정 번호를 포함하는 버스의 목록을 5개만 받아옴
		/*
		busList.sendRequest();
		
		String busListResult = 
				busList.getJSON(busList.getResponse());
		new FileSave().saveFile("getText.txt", busListResult);
		*/
		
		
		// 특정 번호를 포함하는 버스의 목록을 전부 받아옴
		/*
		busList.sendRequestBusesByNum();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByNum(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList341_.txt", busListResult);
		*/
		
		// 버스 번호를 통해 해당 버스의 전체 노선도를 받아옴
		/*
		busList.sendRequestStationsByBus();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByBus(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList3417.txt", busListResult);
		*/
		
		// 특정 문자를 포함하는 정류장의 목록을 전부 받아옴
		
		busList.sendRequestStationsByWord();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByWord(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList_.txt", busListResult);
		
		
		// 정류장 번호를 통해 해당 정류장을 지나는 버스 목록을 받아옴
		/*
		busList.sendRequestBusesByStation();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByStation(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList종합운동장사거리.txt", busListResult);
		*/
	}

}

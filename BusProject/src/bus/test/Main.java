package bus.test;

public class Main {

	public static void main(String[] args) {
		BusList busList = new BusList("341");
		
		// Ư�� ��ȣ�� �����ϴ� ������ ����� 5���� �޾ƿ�
		/*
		busList.sendRequest();
		
		String busListResult = 
				busList.getJSON(busList.getResponse());
		new FileSave().saveFile("getText.txt", busListResult);
		*/
		
		
		// Ư�� ��ȣ�� �����ϴ� ������ ����� ���� �޾ƿ�
		/*
		busList.sendRequestBusesByNum();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByNum(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList341_.txt", busListResult);
		*/
		
		// ���� ��ȣ�� ���� �ش� ������ ��ü �뼱���� �޾ƿ�
		/*
		busList.sendRequestStationsByBus();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByBus(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList3417.txt", busListResult);
		*/
		
		// Ư�� ���ڸ� �����ϴ� �������� ����� ���� �޾ƿ�
		
		busList.sendRequestStationsByWord();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByWord(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList_.txt", busListResult);
		
		
		// ������ ��ȣ�� ���� �ش� �������� ������ ���� ����� �޾ƿ�
		/*
		busList.sendRequestBusesByStation();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByStation(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList���տ���Ÿ�.txt", busListResult);
		*/
	}

}

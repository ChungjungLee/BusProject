package bus.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		
		BusList busList = new BusList("341");
		/*
		busList.sendRequestBusesByStation("23419");
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByStation(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList23419.txt", busListResult);
		*/
		
		File file = new File("AllBusesList.txt");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String str = "";
		
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			String line = null;
			while ((line = br.readLine()) != null) {
				str += line;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(busList.parseJSONBusesByNum(str));
		
		
		/*
		busList.sendRequestStationsByWord();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByWord(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList_.txt", busListResult);
		*/
		
		// Ư�� ��ȣ�� �����ϴ� ������ ����� ���� �޾ƿ�
		/*
		busList.sendRequestBusesByNum();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByNum(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList34_.txt", busListResult);
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
		/*
		busList.sendRequestStationsByWord();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByWord(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList_.txt", busListResult);
		*/
		
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

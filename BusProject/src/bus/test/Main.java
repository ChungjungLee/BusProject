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
		
		// 특정 번호를 포함하는 버스의 목록을 전부 받아옴
		/*
		busList.sendRequestBusesByNum();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONBusesByNum(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("BusesList34_.txt", busListResult);
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
		/*
		busList.sendRequestStationsByWord();
		String busListResult = 
				//busList.getJSON(busList.getResponse());
				busList.parseJSONStationsByWord(busList.getJSON(busList.getResponse()));
		new FileSave().saveFile("StationsList_.txt", busListResult);
		*/
		
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

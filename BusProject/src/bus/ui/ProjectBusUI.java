package bus.ui;

import java.util.List;
import java.util.Scanner;

import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);		// Scanner 선언
	Bus bus = new Bus();								// projectbus.dao.bus
		
	/**
	 * 		Bus UI
	 */
	public ProjectBusUI() {
	
		boolean loop = true;
		
		while(loop){
			
			int option = 0;
			
			printMainMenu();
			
			option = getIntFromUser();
						
			switch (option) {
			
				case 1:			// 검색
					
					search();
					break;
	
				case 2:			// 즐겨찾기
					
					favorite();
					break;
					
				case 3:			// 최근검색
					
					recentSearch();
					break;
					
				case 0:			// 프로그램 종료
					
					System.out.println("[System] 프로그램을 종료합니다.");
					loop = false;
					break;
					
				default:
					break;
			}
		}
		
	}	// ProjectBusUI();

	/**
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. 검색");
		System.out.println("2. 즐겨찾기");
		System.out.println("3. 최근 검색");
		System.out.println("0. 프로그램 종료");
		System.out.println();
		System.out.println("실행하실 메뉴를 선택하세요.");
				
	}
	
	
	/**
	 * 		Search - 버스 / 정류장 검색
	 */
	private void search() {
		
		sc.nextLine();
		
		System.out.println("--- < 검  색  > ---");
		System.out.println("1. 버스 번호로 검색");
		System.out.println("2. 정류장으로 검색");
		System.out.println("0. 메인메뉴");
		System.out.println();
		System.out.print(">> ");
		
		int option = 0;					// 유저로부터 입력받을 숫자 1, 2, 0
		
		option = getIntFromUser();		// 유저로부터 숫자만 입력받게 하는 메소드
		
		int busNum = 0;					// 입력받은 버스 번호를 담을 그릇 
		
		String stnName = null;			// 입력받은 정류장 이름을 담을 그릇
		
		boolean loop = true;			// while 반복문용.
		
		while(loop) {
			
			switch (option) {
			
				case 1:		// 버스 번호로 검색
					
					System.out.println("--- < Bus Info > ---");
					System.out.println("검색하고 싶은 버스 번호를 입력하세요.");
													
					busNum = getIntFromUser();
					
					List<Bus> busList = ProjectBusManager.getBuses(busNum);
					
					for (Bus bus : busList) {
						System.out.println(bus); 	// TODO: [ VO 보고, 수정필요! ]
					}
						// TODO: 즐겨찾기 여부 확인 후 저장
					break;
		
				case 2:		// 정류장으로 검색
					
					System.out.println("--- < Station Info > ---");
					System.out.println("검색하고 싶은 정류장을 입력하세요.");
					
					stnName = getTextFromUser();
					
					List<Station> stnList = ProjectBusManager.getStations(stnName);
						
						// TODO: manager에서 Station ID로 변환, 해당되는 정류소를 다시 넘겨받아야 함.
						// TODO: 즐겨찾기 여부 확인 후 저장
										
					break;
									
				case 0:		// 메소드 종료
					
					System.out.println("[System] 메인 메뉴로 돌아갑니다.\n");
					loop = false;
					break;
					
				default:
					
					System.out.println("[Error] 잘못 입력하셨습니다.");
					break;
			}
		}
	} // search();
	
	/**
	 * 		유저로부터 숫자만 받아오게 한다.
	 * 		@return Integer
	 */
	private int getIntFromUser() {
		
		int option = 0;
		
		do {
			
			System.out.print(">> ");
			
			try {
				option = sc.nextInt();
			}
			catch (Exception e) {
				// e.printStackTrace();
				System.out.println("[Error] 숫자를 입력해 주십시오");
				
				// 에러가 났을 경우 이미 입력받은 문자를 제거해 줘야 되므로 실행
				sc.nextLine();
			}
		}
		
		while (option <= 0);
		
		return option;
	}
	
	/**
	 * 		유저로부터 입력받은 텍스트 메소드
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// 입력받은 정류장 이름을 담을 그릇
		boolean loop = true;		// while 반복문용.
		
		while (loop) {
			
			System.out.print(">>  ");
			inputText = sc.next();
			
			if (inputText == null || inputText.length() < 2) {
				System.out.println("[Error] 최소 두 글자 이상 입력하셔야 합니다.");
			} else { 
				loop = false;		// while문 종료
			}
		}
		
		return inputText;
			
	}
	
	/**
	 * 		즐겨찾기
	 */
	private void favorite() {
		// TODO: 1. 유저로부터 입력받은 버스번호 또는 정류장 검색에서 즐겨찾기 저장 여부 확인
		
		System.out.println("--- < 즐  겨  찾  기 > ---");
		// TODO: 2. 이 메소드에서는 즐겨찾기한 목록 출력만 하도록.
		
	}
	
	/**
	 *  	최근 검색
	 */
	private void recentSearch() {
		// TODO: 1. 위의 search() 에서 검색된 버스 또는 정류장이 있다면 그자리에서 바로 count를 올리는 방식.
		// TODO: 2. 이 메소드에서는 가장 최근에 검색한 값이 가장 상단에 노출되도록 출력만 함 
		
	}
}

package bus.ui;

import java.util.List;
import java.util.Scanner;

import bus.vo.Bus;
import bus.manager.ProjectBusManager;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);		// Scanner 선언
	ProjectBusManager manager = new ProjectBusManager();
	/**
	 * 		Bus UI
	 */
	public ProjectBusUI() {
	
		boolean loop = true;
		
		while(loop){
			
			printMainMenu();
			
			int option = getIntFromUser();
						
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
					
					System.out.println("[Error] 잘못 입력하셨습니다.\n");
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
		
		String busNum = null;				// 입력받은 버스 번호를 담을 그릇 
		String stnName = null;				// 입력받은 정류장 이름을 담을 그릇
		List<Bus> busRoute = null;			// 노선도 저장용
		boolean loop = true;				// while 반복문용.
		boolean flag = true;				// 내부 메소드 while 반복문용.
		
		System.out.println("\n--- < 검  색  > ---");
		System.out.println("1. 버스 번호로 검색");
		System.out.println("2. 정류장으로 검색");
		System.out.println("0. 메인메뉴\n");
					
		int option = getIntFromUser();		// 유저로부터 숫자만 입력받게 하는 메소드
		
		while(loop) {
			
			switch (option) {
			
				case 1:		// 버스 번호로 검색 -> 노선도 확인 -> 즐겨찾기 여부 확인
					
					System.out.println("--- < Bus Info > ---");
					System.out.println("검색하고 싶은 버스 번호를 입력하세요.");
					
					// 두 글자 이상, 네 글자 이하만 받도록 검사
					busNum = getTextFromUser();
						
					// manager에서 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
					System.out.println("> 입력하신 숫자에 해당되는 버스 목록입니다. <");
					List<Bus> busList = manager.getBuses(busNum);
										
					// 불러온 버스 목록의 배열에 Numbering 해서 출력
					System.out.println();
					for (int i = 0; i < busList.size(); i++) {
						System.out.println((i + 1) + ". " + busList.get(i) + "\n");
					}
					System.out.println();
					
					// 선택지 이상의 숫자를 입력하면 error 출력
					
					System.out.println("> 확인하고 싶은 버스를 선택해주세요. <");
					while(flag) {
						System.out.println("^^");
						sc.nextLine();
						option = getIntFromUser();
						
						if (option > busList.size() || option <= 0) {
							System.out.println("[Error] 목록 내의 숫자를 입력하세요.");
						} else {
							flag = false;
						}
					}
					
					// TODO: 노선도 출력
					busRoute = manager.getRouteMap(busNum);
					
					for (Bus route : busRoute) {
						System.out.println(route);		// TODO: 필요한 정보만 출력하도록 수정필요
					}
					
					// TODO: 즐겨찾기 여부 확인 후 저장
					
					boolean canSaveFavorite = manager.getFavorite(busNum);
					
					if (canSaveFavorite) {
						System.out.println("[Error] 이미 저장된 정보입니다.");
					} else {
						// TODO: 즐겨찾기 저장
						System.out.println("[System] 정상적으로 저장되었습니다.\n");
						loop = false; 	// 메인메뉴로 돌아감
					}
					
					break;
		
				case 2:		// 정류장으로 검색 -> 지나다니는 버스 확인 -> 즐겨찾기 여부 확인
					
					System.out.println("--- < Station Info > ---");
					System.out.println("검색하고 싶은 정류장을 입력하세요.");
					
					stnName = getTextFromUser();
					
					System.out.println("> 입력하신 숫자에 해당되는 정류장 목록입니다. <");
					List<Station> stnList = manager.getStations(stnName);
					
					// 배열에 Numbering 해서 출력
					System.out.println();
					
					for (int i = 0; i < stnList.size(); i++) {
						System.out.println(i + 1 + ". " + stnList.get(i) + "\n");
					}
					
					System.out.println();
					
					// 선택지 이상의 숫자를 입력하면 false 반환
					System.out.println("> 확인하고 싶은 정류장을 선택해주세요. <");
					
					while(flag) {
						sc.nextLine();
						option = getIntFromUser();
						
						if (option > stnList.size() || option <= 0) {
							System.out.println("[Error] 목록 내의 숫자를 입력하세요.");
							
						} else {
							flag = false;
						}
					}
					
					//stnList = manager.getBuses(stnList.get(option - 1).getStnId());
					
					// TODO: 해당 정류장을 지나가는 버스 목록 불러오기
						
					
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
	 */
	private int getIntFromUser() {
		
		int option = 0;
		
		do 	{
			
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
		
		while (option < 0);
		
		return option;
	}
	
	/**
	 * 		유저로부터 입력받은 텍스트 메소드
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// 입력받은 정류장 이름을 담을 그릇
				
		do 	{
				System.out.print(">>  ");
			
			try {
				inputText = sc.next();
				
			} catch (Exception e) {
				System.out.println("[Error] 최소 두 글자 이상 입력하셔야 합니다.");
				sc.nextLine();
			}
		} 
		
		while(inputText == null || inputText.length() < 2);
		
		return inputText;
			
	}
	
	/**
	 * 		즐겨찾기
	 */
	private void favorite() {
		System.out.println("--- < 즐  겨  찾  기 > ---");
		// TODO: 1. 유저로부터 입력받은 버스번호 또는 정류장 검색에서 즐겨찾기 저장 여부 확인
		
		// TODO: 2. 이 메소드에서는 즐겨찾기한 목록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
		// TODO: 3. 해당 버스 또는 정류장의 즐겨찾기 취소
	}
	
	/**
	 *  	최근 검색
	 */
	private void recentSearch() {
		// TODO: 1. 위의 search() 에서 검색된 버스 또는 정류장이 있다면 그자리에서 바로 count를 올리는 방식.
		// TODO: 2. 이 메소드에서는 가장 최근에 검색한 값이 가장 상단에 노출되도록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
	}
}

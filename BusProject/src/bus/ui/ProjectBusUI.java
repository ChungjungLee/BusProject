package bus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);			// Scanner 선언
	ProjectBusManager manager = new ProjectBusManager();	// Manager class 연결
	

	/**__________________________________________________________________________________________________
	 * 
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. 검색");
		System.out.println("2. 즐겨찾기");
		System.out.println("3. 최근 검색");
		System.out.println("9. 프로그램 종료\n");
		System.out.println("실행하실 메뉴를 선택하세요.");
		
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Bus UI
	 */
	
	public ProjectBusUI() {
	
		boolean loop = true;
		
		while(loop){
			
			int option = 0;
			
			printMainMenu();			// Main 호출
			option = getIntFromUser();	// 유저로부터 선택받기
			
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
					
				case 9:			// 프로그램 종료
					
					System.out.println("[System] 프로그램을 종료합니다.");
					loop = false;
					break;
					
				default:
					
					System.out.println("[Error] 잘못 입력하셨습니다.\n");
					break;
			}
		}
		
	}	// ProjectBusUI();
	
	/**__________________________________________________________________________________________________
	 * 	
	 * 		Search - 버스 / 정류장 검색
	 * 		: Main - switch 1
	 */
	private void search() {
		
		boolean loop = true;				// while 반복문용.
		List<Station> busRoute = null;		// 노선도 저장용
		
		while(loop) {
			
			System.out.println("\n--- < 검  색  > ---");
			System.out.println("1. 버스 번호로 검색");
			System.out.println("2. 정류장으로 검색");
			System.out.println("9. 메인메뉴\n");

			int option = 0;	
			boolean flag = true;				// 내부 메소드 while 반복문용.
			
			while(flag) {
				
				try {
					option = getIntFromUser();
				} catch (Exception e) {
					System.out.println("\n[Error] 숫자만 입력해주세요.");
					sc.nextLine();
					continue;
				}
				
				if (option != 1 && option != 2 && option != 9) {
					System.out.println("\n[Error] 출력된 메뉴만 선택해주세요.");
					sc.nextLine();
					continue;
				} else {
					flag = false;
				}
			}
			
			switch (option) {
			
				case 1:		// 버스 번호로 검색 -> 노선도 확인 -> 즐겨찾기 여부 확인
					
					List<Bus> busNumList = searchBusList();
					if (busNumList.isEmpty() || busNumList == null) {
						break;
					}
					
					// 불러온 버스 목록의 배열에 Numbering 해서 출력
					System.out.println();
					for (int i = 0; i < busNumList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " 
								+ busNumList.get(i).getRoutName() + "  " 
								+ busNumList.get(i).getRoutType());
					}						
					
					// 선택지 이상의 숫자를 입력하면 error 출력
					System.out.println("\n> 확인하고 싶은 버스를 선택해주세요. <");
					
					int input = 0;
					int busListSize = busNumList.size();
					
					input = selectNum(busListSize);
					
					// 노선도 출력
					int busId = 0;
					busId = busNumList.get(input - 1).getRoutId();
					
					busRoute = manager.getRouteMap(busId);
					
					for (Station route : busRoute) {
						System.out.println("| 정류장 이름 : " + route.getStnName() + 
								"    ( 정류장 ID : " + route.getArsId() + " )" + "\n");		
					}
					
					// TODO: 즐겨찾기 여부 확인 후 저장
				/*
					boolean canSaveFavorite = manager.getFavorite(busNum);
					
					if (canSaveFavorite) {
						System.out.println("\n[Error] 이미 저장된 정보입니다.");
					} else {
						// TODO: 즐겨찾기 저장
						System.out.println("[System] 정상적으로 저장되었습니다.\n");
						loop = false; 	// 메인메뉴로 돌아감
					}
				*/
					break;
		
				case 2:		// 정류장으로 검색 -> 지나다니는 버스 확인 -> 즐겨찾기 여부 확인
					
					List<Station> foundBusList = searchStnList();
					if (foundBusList.isEmpty() || foundBusList == null) {
						loop = false;
					}					
					
					// 배열에 Numbering 해서 출력
					for (int i = 0; i < foundBusList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
								+ "    ( 정류장 ID : " + foundBusList.get(i).getArsId() + " )" + "\n");
					}
										
					// 선택지 이상의 숫자를 입력하면 false 반환
					System.out.println("\n> 확인하고 싶은 정류장을 선택해주세요. <");
					
					int input1 = 0;
					int stationListSize = foundBusList.size();
					
					input1 = selectNum(stationListSize);
					
					// TODO: 해당 정류장을 지나가는 버스 목록 불러오기
					List<HashMap<String, Object>> busInfoLists 
						= manager.getBuses(foundBusList.get(input1 - 1).getArsId());
					
					for (HashMap<String, Object> busInfo : busInfoLists) {
						System.out.println("<" + busInfo.get("busNumber") + ">");
						
						System.out.print("다음 차:");
						System.out.println(busInfo.get("firstBusTime"));
						
						System.out.print("다다음 차:");
						System.out.println(busInfo.get("secondBusTime"));
					}
					
					// TODO: 즐겨찾기 여부 확인 후 저장
										
					break;
									
				case 9:		// 메소드 종료
					
					System.out.println("[System] 메인 메뉴로 돌아갑니다.\n");
					loop = false;
					break;
					
				default:
					
					System.out.println("\n[Error] 잘못 입력하셨습니다.");
					break;
			}
			
		}
	} // search();
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		즐겨찾기
	 * 		: Main - switch 2
	 */
	private void favorite() {
		System.out.println("--- < 즐  겨  찾  기 > ---");
		// TODO: 1. 유저로부터 입력받은 버스번호 또는 정류장 검색에서 즐겨찾기 저장 여부 확인
		
		// TODO: 2. 이 메소드에서는 즐겨찾기한 목록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
		// TODO: 3. 해당 버스 또는 정류장의 즐겨찾기 취소
	} // favorite();
	
	/**__________________________________________________________________________________________________
	 * 
	 *  	최근 검색
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		// TODO: 1. 위의 search() 에서 검색된 버스 또는 정류장이 있다면 그자리에서 바로 count를 올리는 방식.
		// TODO: 2. 이 메소드에서는 가장 최근에 검색한 값이 가장 상단에 노출되도록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
	} // recentSearch();
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		유저로부터 숫자만 받아오게 한다.
	 * 		@return int
	 */
	private int getIntFromUser() {
		
		int option = 0;
		boolean flag = true;
		
		while(flag) {
			
			System.out.print(">> ");
			
			try {
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("[Error] 숫자를 입력해 주십시오");
				continue;
			}
			
			if (option < 0) {
				System.out.println("[Error] 음수는 입력하실 수 없습니다.");
				continue;
				
			} else {
				flag = false;
			}
		}
		
		return option;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		유저로부터 입력받은 텍스트 메소드
	 * 		@return String
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// 입력받은 정류장 이름을 담을 그릇
		boolean loop = true;
		
		while(loop) {
			
			System.out.print(">> ");
			
			try {
				inputText = sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
				continue;
			}
			
			if (inputText.length() < 2) {
				System.out.println("[Error] 최소 두 글자 이상 입력하셔야 합니다.");
				continue;
			} else {
				loop = false;
			}
		}
		
		return inputText;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Bus List
	 * 		@return busList
	 */
	private List<Bus> searchBusList(){
		
		boolean flag = true;
		String busNum = null;
		List<Bus> busList = null;
		
		while(flag) {
			
			System.out.println("--- < Bus Info > ---");
			System.out.println("검색하고 싶은 버스 번호를 입력하세요.");
			
			// 두 글자 이상, 네 글자 이하만 받도록 검사
			busNum = getTextFromUser();
				
			// manager에서 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
			
			busList = manager.searchBuses(busNum);
			
			if (busList.isEmpty() || busList == null) {
				System.out.println("\n[Error] 검색 결과가 없습니다.\n");
				flag = false;
				
			} else {
				System.out.println("\n> 입력하신 숫자에 해당되는 버스 목록입니다. <\n");
				flag = false;
			}
		}
		
		return busList;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Station List
	 * 		@return stnList
	 */
	private List<Station> searchStnList(){
		
		boolean flag = true;
		String stnName = null;
		List<Station> stnList = null;
		
		while(flag) {
			
			System.out.println("--- < Station Info > ---");
			System.out.println("검색하고 싶은 정류장 이름를 입력하세요.");
			
			// 두 글자 이상, 네 글자 이하만 받도록 검사
			stnName = getTextFromUser();
				
			// manager에서 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
			
			stnList = manager.searchStations(stnName);
			
			if (stnList.isEmpty() || stnList == null) {
				System.out.println("\n[Error] 검색 결과가 없습니다.\n");
				flag = false;
			
			} else {
				System.out.println("\n> 입력하신 정류장을 지나가는 버스 목록입니다. <\n");
				flag = false;
			}
		}
		
		return stnList;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		목록 내의 숫자만 입력받게 한다.
	 * 		@param int
	 * 		@return int
	 */
	private int selectNum(int size){
		
		boolean loop = true;
		int input = 0;
		
		while(loop) {
			sc.nextLine();
			input = getIntFromUser();
			
			if (input > size || input <= 0) {
				System.out.println("\n[Error] 목록 내의 숫자를 입력하세요.");
				
			} else {
				loop = false;
			}
		}
		
		return input;
	}
		
}

package bus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import bus.manager.BusManager;
import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);			// Scanner 선언
	ProjectBusManager manager = new ProjectBusManager();	// Manager class 연결(진행 되면서 지워야 함)
	BusManager busManager = new BusManager();				// Manager class 연결
	private String userId = null;							// User 각각의 다른 정보 저장을 위해 입력받는 id
		
	/**__________________________________________________________________________________________________
	 * 
	 * 		Bus UI
	 */
	
	public ProjectBusUI() {
	
		logIn();
		
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
					
				case 4:			// database update
					
					databaseUpdate();
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
		
	}	// ProjectBusUI(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 *		Log-in
	 *		@param 	유저로부터 아이디와 비밀번호를 입력받는다. 
	 * 		
	 */
	private void logIn() {
		
		int usersInfo = 0;
		boolean canLogIn = true;
		
		while(canLogIn) {
			
			System.out.println("ID : ");
			userId = getTextFromUser(2);
			
			System.out.println("PW : ");
			String userPw = getTextFromUser(4);
			
			usersInfo = busManager.userLogIn(userId, userPw); // 0, 1, 2 로 넘겨받는다.
			
			switch (usersInfo) {
			
				case 0:		// ID, PW 둘 다 저장된 정보와 일치 -> login 완료
					
					System.out.println("[System] 로그인 완료\n");
					canLogIn = false;
					break;
		
				case 1:		// ID는 일치하지만 PW가 불일치 -> 다시 입력받는다
					
					System.out.println("[Error] 해당 ID의 저장된 비밀번호와 일치하지 않습니다.");
					System.out.println("[System] 다시 로그인 해주세요.");
					break;
					
				case 2:		// ID가 존재하지 않음 -> 회원가입 받기
					
					System.out.println("[Error] ID가 존재하지 않습니다.");
					System.out.println("\n입력하신 ID로 회원가입 하시겠습니까?");
					System.out.println("1. 예\n2. 아니오");
					
					int isChoiceSignIn = getIntFromUser();
					
					if (isChoiceSignIn == 1) {
						System.out.println("[System] 해당 아이디로 로그인 완료되었습니다.");
					
					} else if (isChoiceSignIn == 2){
						System.out.println("[System] 다시 로그인 해주세요.");
					}
					
					break;
				
			}
		}
		
	} // logIn(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. 검색");
		System.out.println("2. 즐겨찾기");
		System.out.println("3. 최근 검색");
		System.out.println("4. 데이터베이스 업데이트");
		System.out.println("9. 프로그램 종료\n");
		System.out.println("실행하실 메뉴를 선택하세요.");
		
	} // printMainManu(); method end
	
	/**__________________________________________________________________________________________________
	 * 	
	 * 		Search - 버스 / 정류장 검색
	 * 		: Main - switch 1
	 */
	private void search() {
		
		boolean loop = true;	// while 반복문용.
		
		while(loop) {
			
			System.out.println("\n--- < 검  색  > ---");
			System.out.println("1. 버스 번호로 검색");
			System.out.println("2. 정류장으로 검색");
			System.out.println("9. 메인메뉴\n");
			
			int option = 0;
			
			while(true) {
				option = getIntFromUser();
				
				if (option == 1 || option == 2 || option == 9) {
					break;
				}
				
				System.out.println("[Error] 출력된 메뉴만 선택해주세요.\n");
			}
			
			switch (option) {
			
				case 1:		// 버스 번호로 검색 -> 노선도 확인 -> 즐겨찾기 여부 확인
					
					List<Bus> busNumList = searchBusList();
					
					if (busNumList.isEmpty() || busNumList == null) {
						System.out.println("\n[Error] 검색 결과가 없습니다.\n");
						break;
					} 
					
					// 불러온 버스 목록의 배열에 Numbering 해서 출력
					System.out.println("\n> 입력하신 숫자에 해당되는 버스 목록입니다. <\n");
					
					for (int i = 0; i < busNumList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " 
								+ busNumList.get(i).getRoutName() + "  " 
								+ busNumList.get(i).getRoutType());
					}						
					
					// 선택지 이상의 숫자를 입력하면 error 출력
					System.out.println("\n> 확인하고 싶은 버스를 선택해주세요. <");
					
					int inputToGetRouteMap = selectNum(busNumList.size());
					
					int throwBusId = busNumList.get(inputToGetRouteMap - 1).getRoutId();
					
					System.out.println();
					
					List<Station> busRoute = 
							busManager.getRouteMap(throwBusId);
					
					int x = 1; // 정류장 이름 앞에 숫자 붙여서 출력하는 용도
					for (Station route : busRoute) {
						System.out.println();
						System.out.println("| " + (x++) + " | " + route.getStnName() + 
								"    ( 정류장 ID : " + route.getArsId() + " )");
					}
					
					// 최근검색기록에 해당 버스를 저장한다.
					busManager.recentSearch(userId, 0, throwBusId);
					
					// 즐겨찾기 여부 확인 후 저장
					boolean canSaveBusFav = true;
					
					while(canSaveBusFav) {
						
						System.out.println("\n| 해당 버스를 즐겨찾기 하시겠습니까? |");
						System.out.println("1. 예\n2. 아니오");
						
						int usersSelect1 = getIntFromUser();	// 예, 아니오 판별용
						
						if (usersSelect1 == 1) {
							busManager.setFavoriteBus(userId, throwBusId); 	// manager에 버스 id를 넘겨주고, 즐겨찾기에 저장시킨다.
							
							System.out.println("[System] 저장이 정상적으로 완료되었습니다.\n");
							canSaveBusFav = false;
							loop = false;	// 메인메뉴로 돌아감
							
						} else if (usersSelect1 == 2) {
							System.out.println("[System] 저장하지 않고 메인 메뉴로 돌아갑니다.\n");
							canSaveBusFav = false;
							loop = false;	// 메인메뉴로 돌아감
							
						} else {
							System.out.println("[Error] 출력된 메뉴만 선택해주세요.");
						}
					}
				
					break;
		
				case 2:		// 정류장으로 검색 -> 지나다니는 버스 확인 -> 즐겨찾기 여부 확인
					
					List<Station> foundBusList = searchStnList();
					if (foundBusList.isEmpty() || foundBusList == null) {
						System.out.println("\n[Error] 검색 결과가 없습니다.\n");
						break;
					}
					
					// 배열에 Numbering 해서 출력
					for (int i = 0; i < foundBusList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
								+ "    ( 정류장 ID : " + foundBusList.get(i).getArsId() + " )" + "\n");
					}
					
					// 선택지 이상의 숫자를 입력하면 false 반환
					System.out.println("\n> 확인하고 싶은 정류장을 선택해주세요. <");
					
					int inputToGetBuses = selectNum(foundBusList.size());
					
					int throwStnId = foundBusList.get(inputToGetBuses - 1).getStnId();
					
					List<HashMap<String, Object>> busArriveList =
							busManager.getBuses(foundBusList.get(inputToGetBuses - 1).getArsId());
					
					for (HashMap<String, Object> busInfo : busArriveList) {
						System.out.println();
						System.out.println("<" + busInfo.get("busNumber") + ">");
						
						System.out.print("다음 차:");
						System.out.println(busInfo.get("firstBusMsg"));
						
						System.out.print("다다음 차:");
						System.out.println(busInfo.get("secondBusMsg"));
					}
					
					// 최근검색기록에 해당 정류장을 저장한다.
					busManager.recentSearch(userId, 1, throwStnId);
					
					// 즐겨찾기 여부 확인 후 저장
					boolean canSaveStnFav = true;
					
					while(canSaveStnFav) {
						
						System.out.println("해당 정류장을 즐겨찾기 하시겠습니까?");
						System.out.println("1. 예\n2. 아니오");
						
						int usersSelect2 = getIntFromUser();	// 예, 아니오 판별용
						
						if (usersSelect2 == 1) {
							busManager.setFavoriteStn(userId, throwStnId);	// manager에 정류장 id를 넘겨주고 즐겨찾기에 저장시킨다.
							
							System.out.println("[System] 저장이 정상적으로 완료되었습니다.\n");
							canSaveStnFav = false;
							loop = false;	// 메인메뉴로 돌아감
							
						} else if (usersSelect2 == 2) {
							System.out.println("[System] 저장하지 않고 메인 메뉴로 돌아갑니다.\n");
							canSaveStnFav = false;
							loop = false;	// 메인메뉴로 돌아감
						}
					}
										
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
	} // search(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		즐겨찾기
	 * 		: Main - switch 2
	 */
	private void favorite() {
		// 1. 위의 버스번호 또는 정류장 검색에서 즐겨찾기 저장 여부 확인 받음
		
		// TODO: 2. 이 메소드에서는 즐겨찾기한 목록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		System.out.println("--- < 즐  겨  찾  기 > ---");
		
		//busManager.getFavorite();
		
		// TODO: 3. 해당 버스 또는 정류장의 즐겨찾기 취소
		
	} // favorite(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 *  	최근 검색
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		System.out.println("--- < 최  근  검  색 > ---");
		// TODO: 1. 위의 search() 에서 검색된 버스 또는 정류장이 있다면 그자리에서 바로 count를 올리는 방식.
		// TODO: 2. 이 메소드에서는 가장 최근에 검색한 값이 가장 상단에 노출되도록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
	} // recentSearch(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		유저로부터 숫자만 받아오게 한다.
	 * 		@return int
	 */
	private int getIntFromUser() {
		
		int inputInt = 0;
		
		while(true) {
			String inputText = getTextFromUser(1);
			
			if (!isNumeric(inputText)) {
				System.out.println("[Error] 숫자를 입력해 주십시오.\n");
				continue;
			}
			
			inputInt = Integer.parseInt(inputText);
			
			if (inputInt < 0) {
				System.out.println("[Error] 음수는 입력하실 수 없습니다.\n");
				continue;
			} 
			
			break;
		}
		
		return inputInt;
		
	} // getIntFromUser(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		유저로부터 입력받은 텍스트 메소드
	 * 		@return String 공백을 제외한 최소 두 글자 이상의 입력받은 문자열
	 */
	private String getTextFromUser(int minSize) {
		
		String inputText = null;	// 입력받은 정류장 이름을 담을 그릇
		
		while(true) {
			System.out.print(">> ");
			
			try {
				inputText = sc.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			if (minSize <= inputText.trim().length()) {
				break;
			}
			
			if (minSize != 1) { 
				System.out.println("[Error] 최소 " + minSize + " 글자 이상 입력하셔야 합니다.\n");
			}
		}
		
		return inputText;
		
	} // getTextFromUser(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Bus List
	 * 		@return 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
	 */
	private List<Bus> searchBusList(){
		
		String busNum = null;
		List<Bus> busList = null;
		
		System.out.println("\n--- < Bus Info > ---");
		System.out.println("검색하고 싶은 버스 번호를 입력하세요.");
		
		// 두 글자 이상만 받도록 검사
		busNum = getTextFromUser(2);
		
		// manager에서 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
		busList = manager.searchBuses(busNum);
		
		return busList;
		
	} // searchBusList(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Station List
	 * 		@return 입력받은 숫자가 포함된 정류장의 목록을 불러온다.
	 */
	private List<Station> searchStnList(){
		
		String stnName = null;
		List<Station> stnList = null;
		
		System.out.println("\n--- < Station Info > ---");
		System.out.println("검색하고 싶은 정류장 이름을 입력하세요.");
		
		// 두 글자 이상만 받도록 검사
		stnName = getTextFromUser(2);
			
		// manager에서 입력받은 숫자가 포함된 정류장의 목록을 불러온다.
		stnList = busManager.searchStations(stnName);
		
		return stnList;
		
	} // searchStnList(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		목록 내의 숫자만 입력받게 한다.
	 * 		@param int
	 * 		@return int
	 */
	private int selectNum(int size){
		int input = 0;
		
		while(true) {
			input = getIntFromUser();
			
			if (0 < input && input <= size) {
				break;
			}
			
			System.out.println("\n[Error] 목록 내의 숫자를 입력하세요.");
		}
		
		return input;
		
	} // selectNum(); method end
	
	/**__________________________________________________________________________________________________
	 * 
     * 		입력받은 문자열이 숫자로만 이루어져 있는지 판별한다.
     * 		@param text 입력받은  문자열
     * 		@return 숫자인  문자로만  되어있으면  true, 아니면  false
     */
	private boolean isNumeric(String checkStr) {
		if (checkStr == null || checkStr.trim().length() == 0) {
			return false;
		}
		
		String trimmedStr = checkStr.trim();
		
		char leadChar = trimmedStr.charAt(0);
		if (!Character.isDigit(leadChar) && leadChar != '-' && leadChar != '+') {
			// +, - 기호가 아닌 다른 기호가 첫 글자라면 false
			return false;
		}
		
		for (int i = 1; i < trimmedStr.length(); i++) {
			if(!Character.isDigit(trimmedStr.charAt(i))) {
				return false;
			}
		}
		
		return true;
		
	} // isNumeric(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Database를 Update하게 해 주는 메소드
	 * 
	 */
	private void databaseUpdate() {
		
		boolean canUpdate = busManager.databaseUpdate();
		
		if (canUpdate) {
			System.out.println("[System] 정상적으로 업데이트 되었습니다.");
		} else {
			System.out.println("[Error] 업데이트에 실패하였습니다.");
		}
		
	} // databaseUpdate(); method end
	
}


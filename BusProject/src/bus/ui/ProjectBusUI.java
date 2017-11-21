package bus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bus.manager.BusManager;
import bus.vo.Bus;
import bus.vo.History;
import bus.vo.RealTimeStation;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);			// Scanner 선언
	private BusManager busManager = new BusManager();		// Manager class 연결
	private String userId = null;							// User 각각의 다른 정보 저장을 위해 입력받는 id
	
	
	/**
	 * 
	 * 		Bus UI
	 */
	
	public ProjectBusUI() {
	
		
		logIn();	// 유저로부터 사용자 정보를 입력받아 로그인 시킨다. 로그인 완료시 다음 메소드로 넘어간다.
		
		boolean canUpdate = false;		// 업데이트가 되어있지 않다면 실행할 수 없도록 만들어둔 장치
		
		boolean loop = true;			// while 반복문용
		
		while(loop){
			
			int option = 0;
			
			printMainMenu();			// Main 호출
			option = getIntFromUser();	// 유저로부터 메뉴 선택받기
			
			switch (option) {
			
				case 1:			// 검색
					
					if (canUpdate == true) {
						search();
					} else {
						System.out.println("[Error] DB 업데이트가 필요합니다.\n");
					}
					
					break;
	
				case 2:			// 즐겨찾기
					
					if (canUpdate == true) {
						favorite();
					} else {
						System.out.println("[Error] DB 업데이트가 필요합니다.\n");
					}
					
					break;
					
				case 3:			// 최근검색
					
					if (canUpdate == true) {
						recentSearch();
					} else {
						System.out.println("[Error] DB 업데이트가 필요합니다.\n");
					}
					
					break;
					
				case 4:			// database update
					
					databaseUpdate();
					canUpdate = true;	// DB 업데이트 완료
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
	
	
	/**
	 * 
	 *		Log-in
	 *		@param 	유저로부터 아이디와 비밀번호를 입력받는다. 
	 * 		
	 */
	private void logIn() {
		
		int usersInfo = 0;
		boolean canLogIn = true;
		
		while(canLogIn) {
			
			System.out.println("--- < Bus Program > ---\n");
			System.out.println("- Log-in & Sign-in -");
			
			System.out.print("ID를 입력해주세요.\n");
			userId = getTextFromUser(2);
			
			System.out.print("비밀번호를 입력해주세요.\n");
			String userPw = getTextFromUser(4);
			
			usersInfo = busManager.userLogIn(userId, userPw); // 매니저에서 ID, PW 검사 후 [0, 1, 2] 로 넘겨받는다.
			
			// ID, PW 검사
			switch (usersInfo) {
			
				case 0:		// ID, PW 둘 다 저장된 정보와 일치 -> login 완료
					
					System.out.println("[System] 로그인 완료\n");
					canLogIn = false;
					break;
		
				case 1:		// ID는 일치하지만 PW가 불일치 -> 다시 입력받는다
					
					System.out.println("[Error] 해당 ID의 저장된 비밀번호와 일치하지 않습니다.");
					System.out.println("[System] 다시 로그인 해주세요.\n");
					break;
					
				case 2:		// ID가 존재하지 않음 -> 회원가입 받기
					
					System.out.println("[Error] ID가 존재하지 않습니다.");
					System.out.println("\n입력하신 ID로 회원가입 하시겠습니까?");
					System.out.println("1. 예\n2. 아니오");
					
					int isChoiceSignIn = selectNum(2);	// 1 또는 2 입력받음
					
					if (isChoiceSignIn == 1) { 			 // 예
						
						busManager.signIn(userId, userPw);	// 입력받은 ID, PW로 회원가입 완료
						
						System.out.println("[System] 해당 아이디로 회원가입 및 로그인이 완료되었습니다.\n");
						canLogIn = false;
					
					} else if (isChoiceSignIn == 2) {	 // 아니오
						
						System.out.println("[System] 다시 로그인 해주세요.");
					}
					
					break;
				
			}
			
		}
		
	} // logIn(); method end
	
	
	/**
	 * 
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Main Menu ] ===");
		System.out.println("* 첫 실행시 DB 업데이트 이후 사용하세요. *\n");
		System.out.println("1. 검색");
		System.out.println("2. 즐겨찾기");
		System.out.println("3. 최근 검색");
		System.out.println("4. 데이터베이스 업데이트");
		System.out.println("9. 프로그램 종료\n");
		System.out.println("실행하실 메뉴를 선택하세요.");
		
	} // printMainManu(); method end
	
	
	/**
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
			System.out.println("3. 주소로 주변 정류장 검색");
			System.out.println("9. 메인메뉴\n");
			
			int option = 0;
			
			while(true) {
				
				option = getIntFromUser();		// 유저로부터 메뉴 선택받음
				
				if (option == 1 || option == 2 || option == 3 || option == 9) {
					break;
				}
				
				System.out.println("[Error] 출력된 메뉴만 선택해주세요.\n"); // 1, 2, 9가 아닐 경우 다시 선택받음
			}
			
			switch (option) {
			
				case 1:		// 버스 번호로 검색 -> 노선도 확인 -> 즐겨찾기 여부 확인
					
					List<Bus> busNumList = searchBusList();					// 입력받은 숫자가 포함된 버스들의 목록을 불러온다.
					
					if (busNumList.isEmpty() || busNumList == null) { 		// 해당 버스가 없는 경우
						System.out.println("\n[Error] 검색 결과가 없습니다.\n");
						break;
					} 
					
					// 불러온 버스 목록의 배열에 Numbering 해서 출력
					System.out.println("\n- 입력하신 숫자에 해당되는 버스 목록입니다. -");
					
					Bus throwBus = catchBusList(busNumList);
										
					boolean canSaveFavBus = favAndHistory(throwBus);
				
					// 모든 과정이 끝나면 메인 메뉴로 돌아감
					if (canSaveFavBus) {
						loop = false;
					}
					
					break;
		
				case 2:		// 정류장으로 검색 -> 지나다니는 버스 확인 -> 즐겨찾기 여부 확인
					
					List<Station> foundBusList = searchStnList();	 // 입력받은 숫자가 포함된 정류장의 목록을 불러온다.
					
					if (foundBusList.isEmpty() || foundBusList == null) {
						System.out.println("\n[Error] 검색 결과가 없습니다.\n");
						break; // 검색 결과가 없으면 검색 메뉴로 다시 돌아간다.
					}
					
					// 불러온 정류장 목록의 배열에 Numbering 해서 출력
					Station throwStn = catchStnList(foundBusList);
					
					boolean canSaveFavStn = favAndHistory(throwStn);
					
					// 모든 과정이 끝나면 메인 메뉴로 돌아감
					if (canSaveFavStn) {
						loop = false;
					}
					break;
				
				case 3:		// 주소로 주변 정류장 검색
					
					System.out.println("- 정류장을 찾고싶은 주소를 입력해주세요. -");
					
					List<Station> stnList = busManager.searchNearStations( , );
					
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
	
	
	/**
	 * 
	 * 		즐겨찾기
	 * 		: Main - switch 2
	 */
	private void favorite() {
			
		boolean loop = true;		// while 반복문용
			
		while(loop) {

			System.out.println("\n--- < 즐  겨  찾  기  목  록 > ---\n");
			
			Map<String, Object> favList = busManager.getFavoriteAll(userId);
			
			List<Bus> busFavList = (List<Bus>) favList.get("Bus");
			
			int i = 0;
			
			for (i = 0; i < busFavList.size(); i++) {
			
				System.out.print("| " + (i + 1) + " | ");
				
				System.out.print(" 버스 번호 : " + busFavList.get(i).getRoutName() + "  ( " 
											+ busFavList.get(i).getRoutType() + " )\n");
			}
								
			List<Station> stnFavList = (List<Station>) favList.get("Station");
			
			for (int j = 0; j < stnFavList.size(); j++) {
				
				System.out.print("| " + (i + 1) + " | ");
				
				i++;
				
				System.out.print(" 정류장 이름 : " 	+ stnFavList.get(j).getStnName() + "  ( " 
												+ stnFavList.get(j).getArsId() + " )\n");
			}
						
			int selectMenuFromFav = 0;
			
			boolean flag = true;
			
			while(flag) {
				
				System.out.println("\n--- < 메   뉴  > ---");
				System.out.println("1. 즐겨찾기 검색");
				System.out.println("2. 즐겨찾기 해제");
				System.out.println("9. 메인메뉴로 돌아가기\n");
				
				selectMenuFromFav = getIntFromUser(); 	// 유저로부터 항목을 선택받는다.
				
				if (selectMenuFromFav != 1 && selectMenuFromFav != 2 && selectMenuFromFav != 9) {
					System.out.println("[Error] 출력된 메뉴만 선택해주세요.");
				} else {
					flag = false;
				}
			}
			
			switch (selectMenuFromFav) {
			
				case 1:		// 목록 검색
					
					// 유저로부터 검색하고 싶은 숫자 입력받기
					System.out.println("\n- 즐겨찾기 목록에서 검색을 원하시는 번호를 선택해주세요.");
					
					int selectFavList = selectNum(favList.size() + 1);
					
					if (selectFavList < busFavList.size() + 1) {
						Bus getBus = busFavList.get(selectFavList - 1);
						printBusOrStnList(getBus);
						
					} else if (selectFavList > busFavList.size()){
						Station getStn = stnFavList.get((selectFavList - 1) - busFavList.size());
						printBusOrStnList(getStn);
					}
											
					boolean out = loopFavMenu();
					
					if (out) { 
						loop = false; 
					}
					
					break;
		
				case 2:		// 저장된 즐겨찾기 취소
						
						/* 해당 버스 또는 정류장의 즐겨찾기 취소 (사용자 id, 취소하려고 하는 버스 또는 정류장의 id)
					  		1.	버스 및 정류장 목록 모두 출력받음
							2.	출력받은 목록에 넘버링
							3.	넘버링한 숫자를 유저로부터 선택받아 해당 버스 또는 정류장을 즐겨찾기 해제
						*/
					
					System.out.println("\n- 즐겨찾기를 취소하실 번호를 선택해주세요. -");
					
					int inputToDelete = selectNum(i);
					
					if (inputToDelete < busFavList.size() + 1) {
						// 버스 객체를 넘겨준다
						Bus deleteBus = busFavList.get(inputToDelete - 1);
						boolean canDeleteBus = busManager.deleteFavorite(userId, deleteBus);
						
						if (canDeleteBus) {
							System.out.println("\n[System] 정상적으로 해제되었습니다.");
						} else {
							System.out.println("\n[Error] 즐겨찾기 해제가 되지 않았습니다.");
						}
						
					} else if (inputToDelete > busFavList.size()) {
						// 정류장 객체를 넘겨준다
						Station deleteStn = stnFavList.get(inputToDelete - 1);
						boolean canDeleteStn = busManager.deleteFavorite(userId, deleteStn);
						
						if (canDeleteStn) {
							System.out.println("\n[System] 정상적으로 해제되었습니다.");
						} else {
							System.out.println("\n[Error] 즐겨찾기 해제가 되지 않았습니다.");
						}
						
					} else {
						System.out.println("[System] 삭제하지 않고 돌아갑니다.");
					}
					
					break;
								
				case 9:		// 메인메뉴로 돌아가기
					
					System.out.println("[System] 메인 메뉴로 돌아갑니다.\n");
					loop = false;
					break;
					
				default:	// 잘못 입력받을 경우
					
					System.out.println("[Error] 출력된 메뉴만 선택해주세요.");
					break;
			}
		}
	
	} // favorite(); method end
	
	
	/**
	 * 
	 *  	최근 검색
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		
		//  1. 위의 search() 에서 검색된 버스 또는 정류장이 있다면 그자리에서 바로 count를 올리는 방식.
		//  2. 이 메소드에서는 가장 최근에 검색한 값이 가장 상단에 노출되도록 출력, 선택받아 해당 버스 또는 정류장 정보 출력
		
		System.out.println("\n--- < 최  근  검  색 > ---\n");
		
		List<Object> history = busManager.getHistory(userId);	// 해당 ID에 맞는 최근 검색 기록 호출 (userId = 첫번째 인자)
		
		int sum = 1;				// Numbering용
		
		Object busOrStn = null;		// 버스 또는 정류장
		
		for (int i = 0; i < history.size(); i += 2) {
			
			History busOrStnHistory = (History) history.get(i); // 호출된 배열에서 불러낼 객체
						
			System.out.print("| " + (sum++) + " | "+ busOrStnHistory.getIndate() + " | ");
						
			busOrStn = history.get(i + 1);						// 두 번째 인자 = 버스 또는 정류장
			
			if (busOrStn.getClass() == Bus.class) {				// 버스일 경우 호출될 문구
				
				Bus busHistory = (Bus) busOrStn;
				System.out.println(" 버스번호 : " 	+ busHistory.getRoutName() + " ( " 
												+ busHistory.getRoutType() + " )");
				
			} else if (busOrStn.getClass() == Station.class) {	// 정류장일 경우 호출될 문구
				
				Station stnHistory = (Station) busOrStn;
				System.out.println(" 정류장 이름 : " + stnHistory.getStnName() + " ( "
													+ stnHistory.getArsId() + " ) ");
			}
		}
		System.out.println();
		
		System.out.println("- 확인하고 싶은 목록의 번호를 입력하세요. -");
		
		int inputNumInHistory = selectNum(sum - 1);		// 유저로부터 확인하고 싶은 목록의 번호를 입력받는다.
				
		Object busOrStn1 = history.get(inputNumInHistory + (inputNumInHistory - 1));
		
		busManager.updateHistory(userId, busOrStn1);	// 검색 기록을 저장한다.
		
		printBusOrStnList(busOrStn1);		// 해당 버스 또는 정류장 정보 출력
		
		System.out.println("\n[System] 메인 메뉴로 돌아갑니다.\n");
	
	} // recentSearch(); method end
	
	
	/**
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
	
	
	/**
	 * 
	 * 		유저로부터 입력받은 텍스트 메소드
	 * 		@param int : 최소 크기
	 * 		@return String : 공백을 제외한 최소 두 글자 이상의 입력받은 문자열
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
		busList = busManager.searchBuses(busNum);
		
		return busList;
		
	} // searchBusList(); method end
	
	
	/**
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
	
	
	/**
	 * 
	 * 		목록 내의 숫자만 입력받게 한다.
	 * 		@param 리스트의 크기
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
	
	
	/**
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
	
	
	/**
	 * 
	 * 		Database를 Update하게 해 주는 메소드
	 * 
	 */
	private void databaseUpdate() {
		
		boolean canUpdate = busManager.databaseUpdate();
		
		if (canUpdate) {
			System.out.println("[System] 정상적으로 업데이트 되었습니다.\n");
		} else {
			System.out.println("[Error] 업데이트에 실패하였습니다.\n");
		}
		
	} // databaseUpdate(); method end
	
	
	/**
	 * 
	 * 		버스 또는 정류장의 객체에 맞게 해당 문구를 출력한다.
	 * 		@param busOrStn
	 */
	private void canSaveFav(Object busOrStn) {
		
		boolean loopFav = true;
		
		while(loopFav) {
			
			if (busOrStn instanceof Bus) {
				System.out.println("\n| 해당 버스를 즐겨찾기 하시겠습니까? |");
				
			} else {
				System.out.println("\n| 해당 정류장을 즐겨찾기 하시겠습니까? |");
			}
			
			System.out.println("1. 예\n2. 아니오");
			
			int usersSelect = getIntFromUser();	// 예, 아니오 판별용
			
			// manager에 id를 넘겨주고, 즐겨찾기에 저장시킨다.
			if (usersSelect == 1) {
				busManager.setFavorite(userId, busOrStn); 
				
				System.out.println("[System] 저장이 정상적으로 완료되었습니다.\n");
				loopFav = false;
						
			} else if (usersSelect == 2) {
				System.out.println("[System] 저장하지 않고 메인 메뉴로 돌아갑니다.\n");
				loopFav = false;
								
			} else {
				System.out.println("[Error] 출력된 메뉴만 선택해주세요.");	// while문 반복
			}
		}
		
	} // printFavMenu(); method end
	
	
	/**
	 * 		
	 * 		버스 리스트를 받아, 각각 넘버링 해서 넘겨준다.
	 * 		@param 	List<Bus> busList
	 * 		@return 선택된 Bus 객체
	 */
	private Bus catchBusList(List<Bus> busList) {
		
		Bus throwBus = null;	// 최근검색, 즐겨찾기에 넘겨줄 객체
		
		for (int i = 0; i < busList.size(); i++) {
			Bus list = busList.get(i);
			
			System.out.println();
			System.out.println("| " + (i + 1) + " | " 
					+ list.getRoutName() + " ( " + list.getRoutType() + " )");
		}
		
		System.out.println("\n확인하고 싶은 버스를 선택해주세요.");
		
		int selectBus = selectNum(busList.size());	// 유저로부터 넘버링된 숫자를 입력받는다
				
		throwBus = busList.get(selectBus - 1);  // 최근검색, 즐겨찾기에 넘겨줄 객체
		
		printBusOrStnList(throwBus);
		
		return throwBus;
		
	} // catchBusList(); method end
	
	
	/**
	 * 
	 * 		정류장 리스트를 받아, 각각 넘버링 해서 넘겨준다.
	 * 		@param 	List<Station> foundBusList
	 * 		@return 선택된 Station 객체
	 */
	private Station catchStnList(List<Station> foundBusList) {
		
		Station throwStn = null;
		
		for (int i = 0; i < foundBusList.size(); i++) {
			System.out.println("| " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
					+ "    ( 정류장 번호 : " + foundBusList.get(i).getArsId() + " )" + "\n");
		}
		
		// 선택지 이상의 숫자를 입력하면 false 반환
		System.out.println("\n- 확인하고 싶은 정류장을 선택해주세요. -");
		
		int inputToGetBuses = selectNum(foundBusList.size());	// 사용자로부터 출력을 원하는 정류장 선택받기
		
		throwStn = foundBusList.get(inputToGetBuses - 1);	// 최근검색, 즐겨찾기에 넘겨줄 객체
		
		printBusOrStnList(throwStn);
		
		return throwStn;
		
	} // catchStnList(); method end
	
	
	/**
	 * 
	 * 		즐겨찾기 내부에서 즐겨찾기 메뉴로 돌아갈건지, 메인메뉴로 돌아갈건지 묻는 메소드
	 * 
	 */
	private boolean loopFavMenu() {
		
		boolean loopInsideMenu = true;
		boolean out = true;
		
		while(loopInsideMenu) {
			
			System.out.println("\n돌아갈 메뉴를 선택해주세요.");
			System.out.println("1. 즐겨찾기 메뉴");
			System.out.println("2. 메인 메뉴");
			
			int backToTheMenu = getIntFromUser();
		
			if (backToTheMenu != 1 && backToTheMenu != 2) {
				System.out.println("[Error] 출력된 메뉴만 선택해주세요.");
				
			} else if (backToTheMenu == 1) {
				out = false;
				break;
				
			} else if (backToTheMenu == 2) {
				loopInsideMenu = false;
				
			}
		}
				
		return out;
		
	} // loopFavMenu(); method end
	
	
	/**
	 * 
	 * 		버스일 경우 출력되는 문구, 정류장일 경우 출력되는 문구
	 * 		@param Bus 또는 Station 객체 형태
	 */
	private void printBusOrStnList(Object busOrStn) {
					
		if (busOrStn.getClass() == Bus.class) {		// 객체의 클래스가 버스 타입일 경우
			
			Bus callBusRoute = (Bus) busOrStn;		// 버스로 down-casting
			
			int callBusId = callBusRoute.getRoutId();	// 버스 ID를 불러온다
			
			List<RealTimeStation> busRoute = busManager.getRouteMap(callBusId);		// 버스ID에 맞는 노선도 호출
			
			int x = 1; // 정류장 이름 앞에 숫자 붙여서 출력하는 용도
			
			for (RealTimeStation route : busRoute) {		// 출력
				System.out.println();
				System.out.print(String.format("%-55s", "| " + (x++) + " | " + route.getStnName() + 
						"    ( 정류장 ID : " + route.getArsId() + " )"));
				
				if (route.getPlainNo() != null) {
					System.out.print("  	|  ★  출 발 ★    |  " + route.getPlainNo());
				}
			}
			
			System.out.println();
							
		} else {
			
			Station callStnsBusList = (Station) busOrStn;	// 객체의 클래스가 정류장 타입일 경우

			
			// 정류장이 가지고 있는 ID로 해당 정류장을 지나가는 버스 리스트 호출
			List<HashMap<String, Object>> busArriveList =
					busManager.getBuses(callStnsBusList.getArsId());	
			
			for (HashMap<String, Object> busInfo : busArriveList) {		// 출력
				System.out.println();
				System.out.println("<" + busInfo.get("busNumber") + ">");
				
				System.out.print("다음 차:");
				System.out.println(busInfo.get("firstBusMsg"));
				
				System.out.print("다다음 차:");
				System.out.println(busInfo.get("secondBusMsg"));
			}
			
			System.out.println();
		}
	} // printBusOrStnList(); method end
	
	
	/**
	 * 
	 * 		최근 검색 기록과 즐겨찾기 추가 메소드
	 * 		@param busOrStn
	 * 		@return true
	 */
	private boolean favAndHistory(Object busOrStn) {
		
		if (busOrStn.getClass() == Bus.class) {
			
			// 최근검색기록에 해당 버스를 저장한다.
			boolean canSaveBusHistory = busManager.searchHistory(userId, busOrStn);
			
			if (canSaveBusHistory) {
				busManager.updateHistory(userId, busOrStn);
			} else {
				busManager.setHistory(userId, busOrStn);
			}
			
			// 즐겨찾기 여부 확인 후 저장
			boolean canSaveBusFav = busManager.searchFavorite(userId, busOrStn);
			
			if (canSaveBusFav == true) {
				System.out.println("\n[Error] 이미 등록된 버스 정보입니다.");
				System.out.println("[System] 메인 메뉴로 돌아갑니다.\n");
			
			} else {
				canSaveFav(busOrStn);
			}
			
		} else if (busOrStn.getClass() == Station.class) {
			
			// 최근검색기록에 해당 정류장을 저장한다.
			boolean canSaveStnHistory = busManager.searchHistory(userId, busOrStn);
		
			if (canSaveStnHistory) {
				busManager.updateHistory(userId, busOrStn);
			} else {
				busManager.setHistory(userId, busOrStn);
			}
			
			// 즐겨찾기 여부 확인 후 저장
			boolean canSaveStnFav = busManager.searchFavorite(userId, busOrStn);
								
			if (canSaveStnFav == true) {
				System.out.println("[Error] 이미 등록된 정류장 정보입니다.");
				System.out.println("[System] 메인 메뉴로 돌아갑니다.");
				
			} else {
				canSaveFav(busOrStn);
			}
		}
		
		return true;
	}
}


package bus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import bus.manager.BusManager;
import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);			// Scanner ����
	ProjectBusManager manager = new ProjectBusManager();	// Manager class ����(���� �Ǹ鼭 ������ ��)
	BusManager busManager = new BusManager();				// Manager class ����
	private String userId = null;							// User ������ �ٸ� ���� ������ ���� �Է¹޴� id
		
	/**__________________________________________________________________________________________________
	 * 
	 * 		Bus UI
	 */
	
	public ProjectBusUI() {
	
		
		logIn();	// �����κ��� ����� ������ �Է¹޾� �α��� ��Ų��. �α��� �Ϸ�� ���� �޼ҵ�� �Ѿ��.
		
		boolean canUpdate = false;		// ������Ʈ�� �Ǿ����� �ʴٸ� ������ �� ������ ������ ��ġ
		
		boolean loop = true;			// while �ݺ�����
		
		while(loop){
			
			int option = 0;
			
			printMainMenu();			// Main ȣ��
			option = getIntFromUser();	// �����κ��� �޴� ���ùޱ�
			
			switch (option) {
			
				case 1:			// �˻�
					
					if (canUpdate == true) {
						search();
					} else {
						System.out.println("[Error] DB ������Ʈ�� �ʿ��մϴ�.\n");
					}
					
					break;
	
				case 2:			// ���ã��
					
					if (canUpdate == true) {
						favorite();
					} else {
						System.out.println("[Error] DB ������Ʈ�� �ʿ��մϴ�.\n");
					}
					
					break;
					
				case 3:			// �ֱٰ˻�
					
					if (canUpdate == true) {
						recentSearch();
					} else {
						System.out.println("[Error] DB ������Ʈ�� �ʿ��մϴ�.\n");
					}
					break;
					
				case 4:			// database update
					
					databaseUpdate();
					canUpdate = true;	// DB ������Ʈ �Ϸ�
					break;
					
				case 9:			// ���α׷� ����
					
					System.out.println("[System] ���α׷��� �����մϴ�.");
					loop = false;
					break;
					
				default:
					
					System.out.println("[Error] �߸� �Է��ϼ̽��ϴ�.\n");
					break;
			}
		}
		
	}	// ProjectBusUI(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 *		Log-in
	 *		@param 	�����κ��� ���̵�� ��й�ȣ�� �Է¹޴´�. 
	 * 		
	 */
	private void logIn() {
		
		int usersInfo = 0;
		boolean canLogIn = true;
		
		while(canLogIn) {
			
			System.out.println("--- < Bus Program > ---\n");
			System.out.println("- Log-in & Sign-in -");
			
			System.out.print("ID�� �Է����ּ���.\n");
			userId = getTextFromUser(2);
			
			System.out.print("��й�ȣ�� �Է����ּ���.\n");
			String userPw = getTextFromUser(4);
			
			usersInfo = busManager.userLogIn(userId, userPw); // 0, 1, 2 �� �Ѱܹ޴´�.
			
			// ID, PW �˻�
			switch (usersInfo) {
			
				case 0:		// ID, PW �� �� ����� ������ ��ġ -> login �Ϸ�
					
					System.out.println("[System] �α��� �Ϸ�\n");
					canLogIn = false;
					break;
		
				case 1:		// ID�� ��ġ������ PW�� ����ġ -> �ٽ� �Է¹޴´�
					
					System.out.println("[Error] �ش� ID�� ����� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					System.out.println("[System] �ٽ� �α��� ���ּ���.\n");
					break;
					
				case 2:		// ID�� �������� ���� -> ȸ������ �ޱ�
					
					System.out.println("[Error] ID�� �������� �ʽ��ϴ�.");
					System.out.println("\n�Է��Ͻ� ID�� ȸ������ �Ͻðڽ��ϱ�?");
					System.out.println("1. ��\n2. �ƴϿ�");
					
					int isChoiceSignIn = getIntFromUser();	// 1 �Ǵ� 2 �Է¹���
					
					if (isChoiceSignIn == 1) { 			 // ��
						
						busManager.signIn(userId, userPw);	// �Է¹��� ID, PW�� ȸ������ �Ϸ�
						
						System.out.println("[System] �ش� ���̵�� ȸ������ �� �α����� �Ϸ�Ǿ����ϴ�.\n");
						canLogIn = false;
					
					} else if (isChoiceSignIn == 2){	 // �ƴϿ�
						
						System.out.println("[System] �ٽ� �α��� ���ּ���.");
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
		
		System.out.println("=== [ Main Menu ] ===");
		System.out.println("* ù ����� DB ������Ʈ ���� ����ϼ���. *\n");
		System.out.println("1. �˻�");
		System.out.println("2. ���ã��");
		System.out.println("3. �ֱ� �˻�");
		System.out.println("4. �����ͺ��̽� ������Ʈ");
		System.out.println("9. ���α׷� ����\n");
		System.out.println("�����Ͻ� �޴��� �����ϼ���.");
		
	} // printMainManu(); method end
	
	/**__________________________________________________________________________________________________
	 * 	
	 * 		Search - ���� / ������ �˻�
	 * 		: Main - switch 1
	 */
	private void search() {
		
		boolean loop = true;	// while �ݺ�����.
		
		while(loop) {
			
			System.out.println("\n--- < ��  ��  > ---");
			System.out.println("1. ���� ��ȣ�� �˻�");
			System.out.println("2. ���������� �˻�");
			System.out.println("9. ���θ޴�\n");
			
			int option = 0;
			
			while(true) {
				
				option = getIntFromUser();		// �����κ��� �޴� ���ù���
				
				if (option == 1 || option == 2 || option == 9) {
					break;
				}
				
				System.out.println("[Error] ��µ� �޴��� �������ּ���.\n"); // 1, 2, 9�� �ƴ� ��� �ٽ� ���ù���
			}
			
			switch (option) {
			
				case 1:		// ���� ��ȣ�� �˻� -> �뼱�� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Bus> busNumList = searchBusList();					// �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
					
					if (busNumList.isEmpty() || busNumList == null) { 		// �ش� ������ ���� ���
						System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
						break;
					} 
					
					// �ҷ��� ���� ����� �迭�� Numbering �ؼ� ���
					System.out.println("\n> �Է��Ͻ� ���ڿ� �ش�Ǵ� ���� ����Դϴ�. <\n");
					
					for (int i = 0; i < busNumList.size(); i++) {
						Bus list = busNumList.get(i);
						
						System.out.println(" | " + (i + 1) + " | " 
								+ list.getRoutName() + "  " + list.getRoutType());
					}						
					
					// ������ �̻��� ���ڸ� �Է��ϸ� error ���
					System.out.println("\n> Ȯ���ϰ� ���� ������ �������ּ���. <");
					
					int inputToGetRouteMap = selectNum(busNumList.size());	// ����ڷκ��� ����� ���ϴ� ���� ���ùޱ�
					
					int throwBusId = busNumList.get(inputToGetRouteMap - 1).getRoutId(); // ���� ��Ʈ ��¿�
					
					Bus throwBus = busNumList.get(inputToGetRouteMap - 1); // �ֱٰ˻�, ���ã�⿡ �Ѱ��� ��ü
					
					System.out.println();
					
					List<Station> busRoute = busManager.getRouteMap(throwBusId);
					
					int x = 1; // ������ �̸� �տ� ���� �ٿ��� ����ϴ� �뵵
					for (Station route : busRoute) {
						System.out.println();
						System.out.println("| " + (x++) + " | " + route.getStnName() + 
								"    ( ������ ID : " + route.getArsId() + " )");
					}
					
					// �ֱٰ˻���Ͽ� �ش� ������ �����Ѵ�.
					busManager.recentSearch(userId, throwBus);
					
					// ���ã�� ���� Ȯ�� �� ����
					boolean canSaveBusFav = true;
					
					while(canSaveBusFav) {
						
						System.out.println("\n| �ش� ������ ���ã�� �Ͻðڽ��ϱ�? |");
						System.out.println("1. ��\n2. �ƴϿ�");
						
						int usersSelect1 = getIntFromUser();	// ��, �ƴϿ� �Ǻ���
						
						if (usersSelect1 == 1) {
							busManager.setFavorite(userId, throwBus); 	// manager�� ���� id�� �Ѱ��ְ�, ���ã�⿡ �����Ų��.
							
							System.out.println("[System] ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
							canSaveBusFav = false;
							loop = false;	// ���θ޴��� ���ư�
							
						} else if (usersSelect1 == 2) {
							System.out.println("[System] �������� �ʰ� ���� �޴��� ���ư��ϴ�.\n");
							canSaveBusFav = false;
							loop = false;	// ���θ޴��� ���ư�
							
						} else {
							System.out.println("[Error] ��µ� �޴��� �������ּ���.");	// while�� �ݺ�
						}
					}
				
					break;
		
				case 2:		// ���������� �˻� -> �����ٴϴ� ���� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Station> foundBusList = searchStnList();	 // �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
					
					if (foundBusList.isEmpty() || foundBusList == null) {
						System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
						break;
					}
					
					// �ҷ��� ������ ����� �迭�� Numbering �ؼ� ���
					for (int i = 0; i < foundBusList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
								+ "    ( ������ ��ȣ : " + foundBusList.get(i).getArsId() + " )" + "\n");
					}
					
					// ������ �̻��� ���ڸ� �Է��ϸ� false ��ȯ
					System.out.println("\n> Ȯ���ϰ� ���� �������� �������ּ���. <");
					
					int inputToGetBuses = selectNum(foundBusList.size());	// ����ڷκ��� ����� ���ϴ� ������ ���ùޱ�
					
					Station throwStn = foundBusList.get(inputToGetBuses - 1);	// �ֱٰ˻�, ���ã�⿡ �Ѱ��� ��ü
					
					// open-api �������� �� �����忡 ������ �������� ���� �ð��� �ҷ��� ���
					List<HashMap<String, Object>> busArriveList =
							busManager.getBuses(foundBusList.get(inputToGetBuses - 1).getArsId());
					
					for (HashMap<String, Object> busInfo : busArriveList) {
						System.out.println();
						System.out.println("<" + busInfo.get("busNumber") + ">");
						
						System.out.print("���� ��:");
						System.out.println(busInfo.get("firstBusMsg"));
						
						System.out.print("�ٴ��� ��:");
						System.out.println(busInfo.get("secondBusMsg"));
					}
					
					// �ֱٰ˻���Ͽ� �ش� �������� �����Ѵ�.
					busManager.recentSearch(userId, throwStn);
					
					// ���ã�� ���� Ȯ�� �� ����
					boolean canSaveStnFav = true;
					
					while(canSaveStnFav) {
						
						System.out.println("\n�ش� �������� ���ã�� �Ͻðڽ��ϱ�?");
						System.out.println("1. ��\n2. �ƴϿ�");
						
						int usersSelect2 = getIntFromUser();	// ��, �ƴϿ� �Ǻ���
						
						if (usersSelect2 == 1) {
							busManager.setFavorite(userId, throwStn);	// manager�� ������ id�� �Ѱ��ְ� ���ã�⿡ �����Ų��.
							
							System.out.println("[System] ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
							canSaveStnFav = false;
							loop = false;	// ���θ޴��� ���ư�
							
						} else if (usersSelect2 == 2) {
							System.out.println("[System] �������� �ʰ� ���� �޴��� ���ư��ϴ�.\n");
							canSaveStnFav = false;
							loop = false;	// ���θ޴��� ���ư�
						}
					}
										
					break;
									
				case 9:		// �޼ҵ� ����
					
					System.out.println("[System] ���� �޴��� ���ư��ϴ�.\n");
					loop = false;
					break;
					
				default:
					
					System.out.println("\n[Error] �߸� �Է��ϼ̽��ϴ�.");
					break;
			}
			
		}
	} // search(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		���ã��
	 * 		: Main - switch 2
	 */
	private void favorite() {
						
		int selectMenuFromFav = 0; 	// �����κ��� �׸��� ���ù޴´�.
		
		boolean loop = true;		// while �ݺ�����
				
		while(loop) {
			
			System.out.println("\n--- < ��  ��  ã  �� > ---");
			System.out.println("1. ����� ���� ���");
			System.out.println("2. ����� ������ ���");
			System.out.println("3. ����� ���ã�� ���");
			System.out.println("9. ���θ޴��� ���ư���");
			
			selectMenuFromFav = getIntFromUser();
			
			switch (selectMenuFromFav) {
			
				case 1:		// ���� ��� ���
					
					// ���� ID�� �ش��ϴ� ���ã�� ��� ���
					List<Bus> favoriteBusList = busManager.getFavoriteBusList(userId);
					
					for (int i = 0; i < favoriteBusList.size(); i++) {
						Bus list = favoriteBusList.get(i);
						
						System.out.println();
						System.out.println(" | " + (i + 1) + " | " 
								+ list.getRoutName() + " ( " + list.getRoutType() + ")");
					}
					
					System.out.println("Ȯ���ϰ� ���� ������ �������ּ���.");
					
					int selectBusFromFav = selectNum(favoriteBusList.size());
					
					int throwBusId = favoriteBusList.get(selectBusFromFav - 1).getRoutId();
					
					List<Station> busRoute = busManager.getRouteMap(throwBusId);
					
					int x = 1; // ������ �̸� �տ� ���� �ٿ��� ����ϴ� �뵵
					for (Station route : busRoute) {
						System.out.println();
						System.out.println("| " + (x++) + " | " + route.getStnName() + 
								"    ( ������ ID : " + route.getArsId() + " )");
					}
					
					boolean loopInsideMenu = true;
					
					while(loopInsideMenu) {
						
						System.out.println("���ư� �޴��� �������ּ���.");
						System.out.println("1. ���ã�� �޴�");
						System.out.println("2. ���� �޴�");
						
						int backToTheMenu = getIntFromUser();
					
						if (backToTheMenu != 1 && backToTheMenu != 2) {
							System.out.println("[Error] ��µ� �޴��� �������ּ���.");
							
						} else if (backToTheMenu == 1) {
							break;
							
						} else if (backToTheMenu == 2) {
							loopInsideMenu = false;
							loop = false;
						}
					}
					
					break;
		
				case 2:		// ������ ��� ���
					
					// ���� ID�� �ش��ϴ� ���ã�� ��� ���
					List<Station> favoriteStnList = busManager.getFavoriteStnList(userId);
					
					for (int i = 0; i < favoriteStnList.size(); i++) {
						Station list = favoriteStnList.get(i);
						
						System.out.println();
						System.out.println(" | " + (i + 1) + " | "
								+ list.getStnName() + " ( ������ ��ȣ : " + list.getArsId() + ")");
					}
					
					System.out.println("Ȯ���ϰ� ���� �������� �������ּ���.");
					
					break;
					
				case 3: 	// ����� ���ã�� ���
					
					// TODO: �ش� ���� �Ǵ� �������� ���ã�� ��� (����� id, ����Ϸ��� �ϴ� ���� �Ǵ� �������� id)
										
					
					break;
					
				case 9:		// ���θ޴��� ���ư���
					
					System.out.println("[System] ���� �޴��� ���ư��ϴ�.\n");
					loop = false;
					break;
					
				default:	// �߸� �Է¹��� ���
					
					System.out.println("[Error] ��µ� �޴��� �������ּ���.");
					break;
			}
		}
							
				
		
	} // favorite(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 *  	�ֱ� �˻�
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		System.out.println("--- < ��  ��  ��  �� > ---");
		// TODO: 1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		// TODO: 2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
	} // recentSearch(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		�����κ��� ���ڸ� �޾ƿ��� �Ѵ�.
	 * 		@return int
	 */
	private int getIntFromUser() {
		
		int inputInt = 0;
		
		while(true) {
			String inputText = getTextFromUser(1);
			
			if (!isNumeric(inputText)) {
				System.out.println("[Error] ���ڸ� �Է��� �ֽʽÿ�.\n");
				continue;
			}
			
			inputInt = Integer.parseInt(inputText);
			
			if (inputInt < 0) {
				System.out.println("[Error] ������ �Է��Ͻ� �� �����ϴ�.\n");
				continue;
			} 
			
			break;
		}
		
		return inputInt;
		
	} // getIntFromUser(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		�����κ��� �Է¹��� �ؽ�Ʈ �޼ҵ�
	 * 		@return String ������ ������ �ּ� �� ���� �̻��� �Է¹��� ���ڿ�
	 */
	private String getTextFromUser(int minSize) {
		
		String inputText = null;	// �Է¹��� ������ �̸��� ���� �׸�
		
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
				System.out.println("[Error] �ּ� " + minSize + " ���� �̻� �Է��ϼž� �մϴ�.\n");
			}
		}
		
		return inputText;
		
	} // getTextFromUser(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Bus List
	 * 		@return �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
	 */
	private List<Bus> searchBusList(){
		
		String busNum = null;
		List<Bus> busList = null;
		
		System.out.println("\n--- < Bus Info > ---");
		System.out.println("�˻��ϰ� ���� ���� ��ȣ�� �Է��ϼ���.");
		
		// �� ���� �̻� �޵��� �˻�
		busNum = getTextFromUser(2);
		
		// manager���� �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
		busList = busManager.searchBuses(busNum);
		
		return busList;
		
	} // searchBusList(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Station List
	 * 		@return �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
	 */
	private List<Station> searchStnList(){
		
		String stnName = null;
		List<Station> stnList = null;
		
		System.out.println("\n--- < Station Info > ---");
		System.out.println("�˻��ϰ� ���� ������ �̸��� �Է��ϼ���.");
		
		// �� ���� �̻� �޵��� �˻�
		stnName = getTextFromUser(2);
			
		// manager���� �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
		stnList = busManager.searchStations(stnName);
		
		return stnList;
		 
	} // searchStnList(); method end
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		��� ���� ���ڸ� �Է¹ް� �Ѵ�.
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
			
			System.out.println("\n[Error] ��� ���� ���ڸ� �Է��ϼ���.");
		}
		
		return input;
		
	} // selectNum(); method end
	
	/**__________________________________________________________________________________________________
	 * 
     * 		�Է¹��� ���ڿ��� ���ڷθ� �̷���� �ִ��� �Ǻ��Ѵ�.
     * 		@param text �Է¹���  ���ڿ�
     * 		@return ������  ���ڷθ�  �Ǿ�������  true, �ƴϸ�  false
     */
	private boolean isNumeric(String checkStr) {
		if (checkStr == null || checkStr.trim().length() == 0) {
			return false;
		}
		
		String trimmedStr = checkStr.trim();
		
		char leadChar = trimmedStr.charAt(0);
		if (!Character.isDigit(leadChar) && leadChar != '-' && leadChar != '+') {
			// +, - ��ȣ�� �ƴ� �ٸ� ��ȣ�� ù ���ڶ�� false
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
	 * 		Database�� Update�ϰ� �� �ִ� �޼ҵ�
	 * 
	 */
	private void databaseUpdate() {
		
		boolean canUpdate = busManager.databaseUpdate();
		
		if (canUpdate) {
			System.out.println("[System] ���������� ������Ʈ �Ǿ����ϴ�.\n");
		} else {
			System.out.println("[Error] ������Ʈ�� �����Ͽ����ϴ�.\n");
		}
		
	} // databaseUpdate(); method end
	
}


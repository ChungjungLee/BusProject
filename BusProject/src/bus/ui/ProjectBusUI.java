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

	private Scanner sc = new Scanner(System.in);			// Scanner ����
	private BusManager busManager = new BusManager();		// Manager class ����
	private String userId = null;							// User ������ �ٸ� ���� ������ ���� �Է¹޴� id
	
	
	/**
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
	
	
	/**
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
			
			usersInfo = busManager.userLogIn(userId, userPw); // �Ŵ������� ID, PW �˻� �� [0, 1, 2] �� �Ѱܹ޴´�.
			
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
					
					int isChoiceSignIn = selectNum(2);	// 1 �Ǵ� 2 �Է¹���
					
					if (isChoiceSignIn == 1) { 			 // ��
						
						busManager.signIn(userId, userPw);	// �Է¹��� ID, PW�� ȸ������ �Ϸ�
						
						System.out.println("[System] �ش� ���̵�� ȸ������ �� �α����� �Ϸ�Ǿ����ϴ�.\n");
						canLogIn = false;
					
					} else if (isChoiceSignIn == 2) {	 // �ƴϿ�
						
						System.out.println("[System] �ٽ� �α��� ���ּ���.");
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
		System.out.println("* ù ����� DB ������Ʈ ���� ����ϼ���. *\n");
		System.out.println("1. �˻�");
		System.out.println("2. ���ã��");
		System.out.println("3. �ֱ� �˻�");
		System.out.println("4. �����ͺ��̽� ������Ʈ");
		System.out.println("9. ���α׷� ����\n");
		System.out.println("�����Ͻ� �޴��� �����ϼ���.");
		
	} // printMainManu(); method end
	
	
	/**
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
			System.out.println("3. �ּҷ� �ֺ� ������ �˻�");
			System.out.println("9. ���θ޴�\n");
			
			int option = 0;
			
			while(true) {
				
				option = getIntFromUser();		// �����κ��� �޴� ���ù���
				
				if (option == 1 || option == 2 || option == 3 || option == 9) {
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
					System.out.println("\n- �Է��Ͻ� ���ڿ� �ش�Ǵ� ���� ����Դϴ�. -");
					
					Bus throwBus = catchBusList(busNumList);
										
					boolean canSaveFavBus = favAndHistory(throwBus);
				
					// ��� ������ ������ ���� �޴��� ���ư�
					if (canSaveFavBus) {
						loop = false;
					}
					
					break;
		
				case 2:		// ���������� �˻� -> �����ٴϴ� ���� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Station> foundBusList = searchStnList();	 // �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
					
					if (foundBusList.isEmpty() || foundBusList == null) {
						System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
						break; // �˻� ����� ������ �˻� �޴��� �ٽ� ���ư���.
					}
					
					// �ҷ��� ������ ����� �迭�� Numbering �ؼ� ���
					Station throwStn = catchStnList(foundBusList);
					
					boolean canSaveFavStn = favAndHistory(throwStn);
					
					// ��� ������ ������ ���� �޴��� ���ư�
					if (canSaveFavStn) {
						loop = false;
					}
					break;
				
				case 3:		// �ּҷ� �ֺ� ������ �˻�
					
					System.out.println("- �������� ã����� �ּҸ� �Է����ּ���. -");
					
					List<Station> stnList = busManager.searchNearStations( , );
					
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
	
	
	/**
	 * 
	 * 		���ã��
	 * 		: Main - switch 2
	 */
	private void favorite() {
			
		boolean loop = true;		// while �ݺ�����
			
		while(loop) {

			System.out.println("\n--- < ��  ��  ã  ��  ��  �� > ---\n");
			
			Map<String, Object> favList = busManager.getFavoriteAll(userId);
			
			List<Bus> busFavList = (List<Bus>) favList.get("Bus");
			
			int i = 0;
			
			for (i = 0; i < busFavList.size(); i++) {
			
				System.out.print("| " + (i + 1) + " | ");
				
				System.out.print(" ���� ��ȣ : " + busFavList.get(i).getRoutName() + "  ( " 
											+ busFavList.get(i).getRoutType() + " )\n");
			}
								
			List<Station> stnFavList = (List<Station>) favList.get("Station");
			
			for (int j = 0; j < stnFavList.size(); j++) {
				
				System.out.print("| " + (i + 1) + " | ");
				
				i++;
				
				System.out.print(" ������ �̸� : " 	+ stnFavList.get(j).getStnName() + "  ( " 
												+ stnFavList.get(j).getArsId() + " )\n");
			}
						
			int selectMenuFromFav = 0;
			
			boolean flag = true;
			
			while(flag) {
				
				System.out.println("\n--- < ��   ��  > ---");
				System.out.println("1. ���ã�� �˻�");
				System.out.println("2. ���ã�� ����");
				System.out.println("9. ���θ޴��� ���ư���\n");
				
				selectMenuFromFav = getIntFromUser(); 	// �����κ��� �׸��� ���ù޴´�.
				
				if (selectMenuFromFav != 1 && selectMenuFromFav != 2 && selectMenuFromFav != 9) {
					System.out.println("[Error] ��µ� �޴��� �������ּ���.");
				} else {
					flag = false;
				}
			}
			
			switch (selectMenuFromFav) {
			
				case 1:		// ��� �˻�
					
					// �����κ��� �˻��ϰ� ���� ���� �Է¹ޱ�
					System.out.println("\n- ���ã�� ��Ͽ��� �˻��� ���Ͻô� ��ȣ�� �������ּ���.");
					
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
		
				case 2:		// ����� ���ã�� ���
						
						/* �ش� ���� �Ǵ� �������� ���ã�� ��� (����� id, ����Ϸ��� �ϴ� ���� �Ǵ� �������� id)
					  		1.	���� �� ������ ��� ��� ��¹���
							2.	��¹��� ��Ͽ� �ѹ���
							3.	�ѹ����� ���ڸ� �����κ��� ���ù޾� �ش� ���� �Ǵ� �������� ���ã�� ����
						*/
					
					System.out.println("\n- ���ã�⸦ ����Ͻ� ��ȣ�� �������ּ���. -");
					
					int inputToDelete = selectNum(i);
					
					if (inputToDelete < busFavList.size() + 1) {
						// ���� ��ü�� �Ѱ��ش�
						Bus deleteBus = busFavList.get(inputToDelete - 1);
						boolean canDeleteBus = busManager.deleteFavorite(userId, deleteBus);
						
						if (canDeleteBus) {
							System.out.println("\n[System] ���������� �����Ǿ����ϴ�.");
						} else {
							System.out.println("\n[Error] ���ã�� ������ ���� �ʾҽ��ϴ�.");
						}
						
					} else if (inputToDelete > busFavList.size()) {
						// ������ ��ü�� �Ѱ��ش�
						Station deleteStn = stnFavList.get(inputToDelete - 1);
						boolean canDeleteStn = busManager.deleteFavorite(userId, deleteStn);
						
						if (canDeleteStn) {
							System.out.println("\n[System] ���������� �����Ǿ����ϴ�.");
						} else {
							System.out.println("\n[Error] ���ã�� ������ ���� �ʾҽ��ϴ�.");
						}
						
					} else {
						System.out.println("[System] �������� �ʰ� ���ư��ϴ�.");
					}
					
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
	
	
	/**
	 * 
	 *  	�ֱ� �˻�
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		
		//  1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		//  2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
		System.out.println("\n--- < ��  ��  ��  �� > ---\n");
		
		List<Object> history = busManager.getHistory(userId);	// �ش� ID�� �´� �ֱ� �˻� ��� ȣ�� (userId = ù��° ����)
		
		int sum = 1;				// Numbering��
		
		Object busOrStn = null;		// ���� �Ǵ� ������
		
		for (int i = 0; i < history.size(); i += 2) {
			
			History busOrStnHistory = (History) history.get(i); // ȣ��� �迭���� �ҷ��� ��ü
						
			System.out.print("| " + (sum++) + " | "+ busOrStnHistory.getIndate() + " | ");
						
			busOrStn = history.get(i + 1);						// �� ��° ���� = ���� �Ǵ� ������
			
			if (busOrStn.getClass() == Bus.class) {				// ������ ��� ȣ��� ����
				
				Bus busHistory = (Bus) busOrStn;
				System.out.println(" ������ȣ : " 	+ busHistory.getRoutName() + " ( " 
												+ busHistory.getRoutType() + " )");
				
			} else if (busOrStn.getClass() == Station.class) {	// �������� ��� ȣ��� ����
				
				Station stnHistory = (Station) busOrStn;
				System.out.println(" ������ �̸� : " + stnHistory.getStnName() + " ( "
													+ stnHistory.getArsId() + " ) ");
			}
		}
		System.out.println();
		
		System.out.println("- Ȯ���ϰ� ���� ����� ��ȣ�� �Է��ϼ���. -");
		
		int inputNumInHistory = selectNum(sum - 1);		// �����κ��� Ȯ���ϰ� ���� ����� ��ȣ�� �Է¹޴´�.
				
		Object busOrStn1 = history.get(inputNumInHistory + (inputNumInHistory - 1));
		
		busManager.updateHistory(userId, busOrStn1);	// �˻� ����� �����Ѵ�.
		
		printBusOrStnList(busOrStn1);		// �ش� ���� �Ǵ� ������ ���� ���
		
		System.out.println("\n[System] ���� �޴��� ���ư��ϴ�.\n");
	
	} // recentSearch(); method end
	
	
	/**
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
	
	
	/**
	 * 
	 * 		�����κ��� �Է¹��� �ؽ�Ʈ �޼ҵ�
	 * 		@param int : �ּ� ũ��
	 * 		@return String : ������ ������ �ּ� �� ���� �̻��� �Է¹��� ���ڿ�
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
	
	
	/**
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
	
	
	/**
	 * 
	 * 		��� ���� ���ڸ� �Է¹ް� �Ѵ�.
	 * 		@param ����Ʈ�� ũ��
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
	
	
	/**
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
	
	
	/**
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
	
	
	/**
	 * 
	 * 		���� �Ǵ� �������� ��ü�� �°� �ش� ������ ����Ѵ�.
	 * 		@param busOrStn
	 */
	private void canSaveFav(Object busOrStn) {
		
		boolean loopFav = true;
		
		while(loopFav) {
			
			if (busOrStn instanceof Bus) {
				System.out.println("\n| �ش� ������ ���ã�� �Ͻðڽ��ϱ�? |");
				
			} else {
				System.out.println("\n| �ش� �������� ���ã�� �Ͻðڽ��ϱ�? |");
			}
			
			System.out.println("1. ��\n2. �ƴϿ�");
			
			int usersSelect = getIntFromUser();	// ��, �ƴϿ� �Ǻ���
			
			// manager�� id�� �Ѱ��ְ�, ���ã�⿡ �����Ų��.
			if (usersSelect == 1) {
				busManager.setFavorite(userId, busOrStn); 
				
				System.out.println("[System] ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
				loopFav = false;
						
			} else if (usersSelect == 2) {
				System.out.println("[System] �������� �ʰ� ���� �޴��� ���ư��ϴ�.\n");
				loopFav = false;
								
			} else {
				System.out.println("[Error] ��µ� �޴��� �������ּ���.");	// while�� �ݺ�
			}
		}
		
	} // printFavMenu(); method end
	
	
	/**
	 * 		
	 * 		���� ����Ʈ�� �޾�, ���� �ѹ��� �ؼ� �Ѱ��ش�.
	 * 		@param 	List<Bus> busList
	 * 		@return ���õ� Bus ��ü
	 */
	private Bus catchBusList(List<Bus> busList) {
		
		Bus throwBus = null;	// �ֱٰ˻�, ���ã�⿡ �Ѱ��� ��ü
		
		for (int i = 0; i < busList.size(); i++) {
			Bus list = busList.get(i);
			
			System.out.println();
			System.out.println("| " + (i + 1) + " | " 
					+ list.getRoutName() + " ( " + list.getRoutType() + " )");
		}
		
		System.out.println("\nȮ���ϰ� ���� ������ �������ּ���.");
		
		int selectBus = selectNum(busList.size());	// �����κ��� �ѹ����� ���ڸ� �Է¹޴´�
				
		throwBus = busList.get(selectBus - 1);  // �ֱٰ˻�, ���ã�⿡ �Ѱ��� ��ü
		
		printBusOrStnList(throwBus);
		
		return throwBus;
		
	} // catchBusList(); method end
	
	
	/**
	 * 
	 * 		������ ����Ʈ�� �޾�, ���� �ѹ��� �ؼ� �Ѱ��ش�.
	 * 		@param 	List<Station> foundBusList
	 * 		@return ���õ� Station ��ü
	 */
	private Station catchStnList(List<Station> foundBusList) {
		
		Station throwStn = null;
		
		for (int i = 0; i < foundBusList.size(); i++) {
			System.out.println("| " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
					+ "    ( ������ ��ȣ : " + foundBusList.get(i).getArsId() + " )" + "\n");
		}
		
		// ������ �̻��� ���ڸ� �Է��ϸ� false ��ȯ
		System.out.println("\n- Ȯ���ϰ� ���� �������� �������ּ���. -");
		
		int inputToGetBuses = selectNum(foundBusList.size());	// ����ڷκ��� ����� ���ϴ� ������ ���ùޱ�
		
		throwStn = foundBusList.get(inputToGetBuses - 1);	// �ֱٰ˻�, ���ã�⿡ �Ѱ��� ��ü
		
		printBusOrStnList(throwStn);
		
		return throwStn;
		
	} // catchStnList(); method end
	
	
	/**
	 * 
	 * 		���ã�� ���ο��� ���ã�� �޴��� ���ư�����, ���θ޴��� ���ư����� ���� �޼ҵ�
	 * 
	 */
	private boolean loopFavMenu() {
		
		boolean loopInsideMenu = true;
		boolean out = true;
		
		while(loopInsideMenu) {
			
			System.out.println("\n���ư� �޴��� �������ּ���.");
			System.out.println("1. ���ã�� �޴�");
			System.out.println("2. ���� �޴�");
			
			int backToTheMenu = getIntFromUser();
		
			if (backToTheMenu != 1 && backToTheMenu != 2) {
				System.out.println("[Error] ��µ� �޴��� �������ּ���.");
				
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
	 * 		������ ��� ��µǴ� ����, �������� ��� ��µǴ� ����
	 * 		@param Bus �Ǵ� Station ��ü ����
	 */
	private void printBusOrStnList(Object busOrStn) {
					
		if (busOrStn.getClass() == Bus.class) {		// ��ü�� Ŭ������ ���� Ÿ���� ���
			
			Bus callBusRoute = (Bus) busOrStn;		// ������ down-casting
			
			int callBusId = callBusRoute.getRoutId();	// ���� ID�� �ҷ��´�
			
			List<RealTimeStation> busRoute = busManager.getRouteMap(callBusId);		// ����ID�� �´� �뼱�� ȣ��
			
			int x = 1; // ������ �̸� �տ� ���� �ٿ��� ����ϴ� �뵵
			
			for (RealTimeStation route : busRoute) {		// ���
				System.out.println();
				System.out.print(String.format("%-55s", "| " + (x++) + " | " + route.getStnName() + 
						"    ( ������ ID : " + route.getArsId() + " )"));
				
				if (route.getPlainNo() != null) {
					System.out.print("  	|  ��  �� �� ��    |  " + route.getPlainNo());
				}
			}
			
			System.out.println();
							
		} else {
			
			Station callStnsBusList = (Station) busOrStn;	// ��ü�� Ŭ������ ������ Ÿ���� ���

			
			// �������� ������ �ִ� ID�� �ش� �������� �������� ���� ����Ʈ ȣ��
			List<HashMap<String, Object>> busArriveList =
					busManager.getBuses(callStnsBusList.getArsId());	
			
			for (HashMap<String, Object> busInfo : busArriveList) {		// ���
				System.out.println();
				System.out.println("<" + busInfo.get("busNumber") + ">");
				
				System.out.print("���� ��:");
				System.out.println(busInfo.get("firstBusMsg"));
				
				System.out.print("�ٴ��� ��:");
				System.out.println(busInfo.get("secondBusMsg"));
			}
			
			System.out.println();
		}
	} // printBusOrStnList(); method end
	
	
	/**
	 * 
	 * 		�ֱ� �˻� ��ϰ� ���ã�� �߰� �޼ҵ�
	 * 		@param busOrStn
	 * 		@return true
	 */
	private boolean favAndHistory(Object busOrStn) {
		
		if (busOrStn.getClass() == Bus.class) {
			
			// �ֱٰ˻���Ͽ� �ش� ������ �����Ѵ�.
			boolean canSaveBusHistory = busManager.searchHistory(userId, busOrStn);
			
			if (canSaveBusHistory) {
				busManager.updateHistory(userId, busOrStn);
			} else {
				busManager.setHistory(userId, busOrStn);
			}
			
			// ���ã�� ���� Ȯ�� �� ����
			boolean canSaveBusFav = busManager.searchFavorite(userId, busOrStn);
			
			if (canSaveBusFav == true) {
				System.out.println("\n[Error] �̹� ��ϵ� ���� �����Դϴ�.");
				System.out.println("[System] ���� �޴��� ���ư��ϴ�.\n");
			
			} else {
				canSaveFav(busOrStn);
			}
			
		} else if (busOrStn.getClass() == Station.class) {
			
			// �ֱٰ˻���Ͽ� �ش� �������� �����Ѵ�.
			boolean canSaveStnHistory = busManager.searchHistory(userId, busOrStn);
		
			if (canSaveStnHistory) {
				busManager.updateHistory(userId, busOrStn);
			} else {
				busManager.setHistory(userId, busOrStn);
			}
			
			// ���ã�� ���� Ȯ�� �� ����
			boolean canSaveStnFav = busManager.searchFavorite(userId, busOrStn);
								
			if (canSaveStnFav == true) {
				System.out.println("[Error] �̹� ��ϵ� ������ �����Դϴ�.");
				System.out.println("[System] ���� �޴��� ���ư��ϴ�.");
				
			} else {
				canSaveFav(busOrStn);
			}
		}
		
		return true;
	}
}


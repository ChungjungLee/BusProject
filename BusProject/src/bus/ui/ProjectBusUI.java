package bus.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import bus.manager.BusManager;
import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);			// Scanner ����
	ProjectBusManager manager = new ProjectBusManager();	// Manager class ����(���� �Ǹ鼭 ������ ��)
	BusManager busManager = new BusManager();				// Manager class ����

	/**__________________________________________________________________________________________________
	 * 
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. �˻�");
		System.out.println("2. ���ã��");
		System.out.println("3. �ֱ� �˻�");
		System.out.println("4. �����ͺ��̽� ������Ʈ");
		System.out.println("9. ���α׷� ����\n");
		System.out.println("�����Ͻ� �޴��� �����ϼ���.");
		
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Bus UI
	 */
	
	public ProjectBusUI() {
	
		boolean loop = true;
		
		while(loop){
			
			int option = 0;
			
			printMainMenu();			// Main ȣ��
			option = getIntFromUser();	// �����κ��� ���ùޱ�
			
			switch (option) {
			
				case 1:			// �˻�
					
					search();
					break;
	
				case 2:			// ���ã��
					
					favorite();
					break;
					
				case 3:			// �ֱٰ˻�
					
					recentSearch();
					break;
					
				case 4:			// database update
					
					databaseUpdate();
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
		
	}	// ProjectBusUI();
	

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
				option = getIntFromUser();
				
				if (option == 1 || option == 2 || option == 9) {
					break;
				}
				
				System.out.println("[Error] ��µ� �޴��� �������ּ���.\n");
			}
			
			switch (option) {
			
				case 1:		// ���� ��ȣ�� �˻� -> �뼱�� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Bus> busNumList = searchBusList();
					
					if (busNumList.isEmpty() || busNumList == null) {
						System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
						break;
					} 
					
					// �ҷ��� ���� ����� �迭�� Numbering �ؼ� ���
					System.out.println("\n> �Է��Ͻ� ���ڿ� �ش�Ǵ� ���� ����Դϴ�. <\n");
					
					for (int i = 0; i < busNumList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " 
								+ busNumList.get(i).getRoutName() + "  " 
								+ busNumList.get(i).getRoutType());
					}						
					
					// ������ �̻��� ���ڸ� �Է��ϸ� error ���
					System.out.println("\n> Ȯ���ϰ� ���� ������ �������ּ���. <");
					
					int inputToGetRouteMap = selectNum(busNumList.size());
					
					int throwBusId = busNumList.get(inputToGetRouteMap - 1).getRoutId();
					
					System.out.println();
					
					List<Station> busRoute = 
							busManager.getRouteMap(throwBusId);
					
					int x = 1; // ������ �̸� �տ� ���� �ٿ��� ����ϴ� �뵵
					for (Station route : busRoute) {
						System.out.println();
						System.out.println("| " + (x++) + " | " + route.getStnName() + 
								"    ( ������ ID : " + route.getArsId() + " )");
					}
					
					// �ֱٰ˻���Ͽ� �ش� ������ �����Ѵ�.
					busManager.recentSearch(0, throwBusId);
					
					// ���ã�� ���� Ȯ�� �� ����
					boolean canSaveBusFav = true;
					
					while(canSaveBusFav) {
						
						System.out.println("\n| �ش� ������ ���ã�� �Ͻðڽ��ϱ�? |");
						System.out.println("1. ��\n2. �ƴϿ�");
						
						int usersSelect1 = getIntFromUser();	// ��, �ƴϿ� �Ǻ���
						
						if (usersSelect1 == 1) {
							busManager.setFavoriteBus(throwBusId);
							System.out.println("[System] ������ ���������� �Ϸ�Ǿ����ϴ�.\n");
							canSaveBusFav = false;
							loop = false;	// ���θ޴��� ���ư�
							
						} else if (usersSelect1 == 2) {
							System.out.println("[System] �������� �ʰ� ���� �޴��� ���ư��ϴ�.\n");
							canSaveBusFav = false;
							loop = false;
							
						} else {
							System.out.println("[Error] ��µ� �޴��� �������ּ���.");
						}
					}
				
					break;
		
				case 2:		// ���������� �˻� -> �����ٴϴ� ���� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Station> foundBusList = searchStnList();
					if (foundBusList.isEmpty() || foundBusList == null) {
						System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
						break;
					}
					
					// �迭�� Numbering �ؼ� ���
					for (int i = 0; i < foundBusList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
								+ "    ( ������ ID : " + foundBusList.get(i).getArsId() + " )" + "\n");
					}
					
					// ������ �̻��� ���ڸ� �Է��ϸ� false ��ȯ
					System.out.println("\n> Ȯ���ϰ� ���� �������� �������ּ���. <");
					
					int inputToGetBuses = selectNum(foundBusList.size());
					
					int throwStnId = foundBusList.get(inputToGetBuses - 1).getStnId();
					
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
					
					// TODO: �ֱٰ˻���Ͽ� �ش� �������� �����Ѵ�.
					busManager.recentSearch(1, throwStnId);
					
					// TODO: ���ã�� ���� Ȯ�� �� ����
					boolean canSaveStnFav = true;
					
					while(canSaveStnFav) {
						
						System.out.println("�ش� �������� ���ã�� �Ͻðڽ��ϱ�?");
						System.out.println("1. ��\n2. �ƴϿ�");
						
						int usersSelect2 = getIntFromUser();
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
	} // search();
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		���ã��
	 * 		: Main - switch 2
	 */
	private void favorite() {
		System.out.println("--- < ��  ��  ã  �� > ---");
		// TODO: 1. �����κ��� �Է¹��� ������ȣ �Ǵ� ������ �˻����� ���ã�� ���� ���� Ȯ��
		
		// TODO: 2. �� �޼ҵ忡���� ���ã���� ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
		// TODO: 3. �ش� ���� �Ǵ� �������� ���ã�� ���
	} // favorite();
	
	/**__________________________________________________________________________________________________
	 * 
	 *  	�ֱ� �˻�
	 *  	: Main - switch 3
	 */
	private void recentSearch() {
		System.out.println("--- < ��  ��  ��  �� > ---");
		// TODO: 1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		// TODO: 2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
	} // recentSearch();
	
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
	}
	
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
	}
	
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
		busList = manager.searchBuses(busNum);
		
		return busList;
	}
	
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
	}
	
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
	}
	
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
	}
	
	private void databaseUpdate() {
		
		boolean canUpdate = busManager.databaseUpdate();
		
		if (canUpdate) {
			System.out.println("[System] ���������� ������Ʈ �Ǿ����ϴ�.");
		} else {
			System.out.println("[Error] ������Ʈ�� �����Ͽ����ϴ�.");
		}
		
	}
	
}


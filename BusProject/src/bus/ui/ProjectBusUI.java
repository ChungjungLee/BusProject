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
	ProjectBusManager manager = new ProjectBusManager();	// Manager class ����
	BusManager busManager = new BusManager();

	/**__________________________________________________________________________________________________
	 * 
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. �˻�");
		System.out.println("2. ���ã��");
		System.out.println("3. �ֱ� �˻�");
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
		
		boolean loop = true;				// while �ݺ�����.
		List<Station> busRoute = null;		// �뼱�� �����
		
		while(loop) {
			
			System.out.println("\n--- < ��  ��  > ---");
			System.out.println("1. ���� ��ȣ�� �˻�");
			System.out.println("2. ���������� �˻�");
			System.out.println("9. ���θ޴�\n");

			int option = 0;	
			boolean flag = true;				// ���� �޼ҵ� while �ݺ�����.
			
			while(flag) {
				
				try {
					option = getIntFromUser();
				} catch (Exception e) {
					System.out.println("\n[Error] ���ڸ� �Է����ּ���.");
					sc.nextLine();
					continue;
				}
				
				if (option != 1 && option != 2 && option != 9) {
					System.out.println("\n[Error] ��µ� �޴��� �������ּ���.");
					sc.nextLine();
					continue;
				} else {
					flag = false;
				}
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
					
					int busListSize = busNumList.size();
					
					int input = selectNum(busListSize);
					
					// �뼱�� ���
					int busId = busNumList.get(input - 1).getRoutId();
					
					busRoute = busManager.getRouteMap(busId);
					
					for (Station route : busRoute) {
						System.out.println("| ������ �̸� : " + route.getStnName() + 
								"    ( ������ ID : " + route.getArsId() + " )" + "\n");
					}
					
					// TODO: ���ã�� ���� Ȯ�� �� ����
				/*
					boolean canSaveFavorite = manager.getFavorite(busNum);
					
					if (canSaveFavorite) {
						System.out.println("\n[Error] �̹� ����� �����Դϴ�.");
					} else {
						// TODO: ���ã�� ����
						System.out.println("[System] ���������� ����Ǿ����ϴ�.\n");
						loop = false; 	// ���θ޴��� ���ư�
					}
				*/
					break;
		
				case 2:		// ���������� �˻� -> �����ٴϴ� ���� Ȯ�� -> ���ã�� ���� Ȯ��
					
					List<Station> foundBusList = searchStnList();
					if (foundBusList.isEmpty() || foundBusList == null) {
						loop = false;
					}					
					
					// �迭�� Numbering �ؼ� ���
					for (int i = 0; i < foundBusList.size(); i++) {
						System.out.println(" | " + (i + 1) + " | " + foundBusList.get(i).getStnName() 
								+ "    ( ������ ID : " + foundBusList.get(i).getArsId() + " )" + "\n");
					}
										
					// ������ �̻��� ���ڸ� �Է��ϸ� false ��ȯ
					System.out.println("\n> Ȯ���ϰ� ���� �������� �������ּ���. <");
					
					int input1 = 0;
					int stationListSize = foundBusList.size();
					
					input1 = selectNum(stationListSize);
					
					// TODO: �ش� �������� �������� ���� ��� �ҷ�����
					List<HashMap<String, Object>> busArriveList =
							busManager.getBuses(foundBusList.get(input1 - 1).getArsId());
					
					for (HashMap<String, Object> busInfo : busArriveList) {
						System.out.println();
						System.out.println("<" + busInfo.get("busNumber") + ">");
						
						System.out.print("���� ��:");
						System.out.println(busInfo.get("firstBusMsg"));
						
						System.out.print("�ٴ��� ��:");
						System.out.println(busInfo.get("secondBusMsg"));
					}
					
					// TODO: ���ã�� ���� Ȯ�� �� ����
										
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
		// TODO: 1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		// TODO: 2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
	} // recentSearch();
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		�����κ��� ���ڸ� �޾ƿ��� �Ѵ�.
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
				System.out.println("[Error] ���ڸ� �Է��� �ֽʽÿ�");
				continue;
			}
			
			if (option < 0) {
				System.out.println("[Error] ������ �Է��Ͻ� �� �����ϴ�.");
				continue;
				
			} else {
				flag = false;
			}
		}
		
		return option;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		�����κ��� �Է¹��� �ؽ�Ʈ �޼ҵ�
	 * 		@return String ������ ������ �ּ� �� ���� �̻��� �Է¹��� ���ڿ�
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// �Է¹��� ������ �̸��� ���� �׸�
		
		while(true) {
			
			System.out.print(">> ");
			
			try {
				inputText = sc.nextLine();
				
			} catch (Exception e) {
				//sc.nextLine();
				e.printStackTrace();
				continue;
			}
			
			if (2 <= inputText.trim().length()) {
				break;
			}
			
			System.out.println("[Error] �ּ� �� ���� �̻� �Է��ϼž� �մϴ�.");
			System.out.println();
		}
		
		return inputText;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		Search Bus List
	 * 		@return busList
	 */
	private List<Bus> searchBusList(){
		
		String busNum = null;
		List<Bus> busList = null;
		
		System.out.println("--- < Bus Info > ---");
		System.out.println("�˻��ϰ� ���� ���� ��ȣ�� �Է��ϼ���.");
		
		// �� ���� �̻� �޵��� �˻�
		busNum = getTextFromUser();
		
		// manager���� �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
		busList = manager.searchBuses(busNum);
		
		if (busList.isEmpty() || busList == null) {
			System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
			
		} else {
			System.out.println("\n> �Է��Ͻ� ���ڿ� �ش�Ǵ� ���� ����Դϴ�. <\n");
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
			System.out.println("�˻��ϰ� ���� ������ �̸��� �Է��ϼ���.");
			
			// �� ���� �̻� �޵��� �˻�
			stnName = getTextFromUser();
				
			// manager���� �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
			stnList = busManager.searchStations(stnName);
			
			if (stnList.isEmpty() || stnList == null) {
				System.out.println("\n[Error] �˻� ����� �����ϴ�.\n");
				flag = false;
			
			} else {
				System.out.println("\n> �Է��Ͻ� �������� �������� ���� ����Դϴ�. <\n");
				flag = false;
			}
		}
		
		return stnList;
	}
	
	/**__________________________________________________________________________________________________
	 * 
	 * 		��� ���� ���ڸ� �Է¹ް� �Ѵ�.
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
				System.out.println("\n[Error] ��� ���� ���ڸ� �Է��ϼ���.");
				
			} else {
				loop = false;
			}
		}
		
		return input;
	}
		
}

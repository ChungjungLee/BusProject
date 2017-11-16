package bus.ui;

import java.util.List;
import java.util.Scanner;

import bus.vo.Bus;
import bus.manager.ProjectBusManager;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);		// Scanner ����
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
			
				case 1:			// �˻�
					
					search();
					break;
	
				case 2:			// ���ã��
					
					favorite();
					break;
					
				case 3:			// �ֱٰ˻�
					
					recentSearch();
					break;
					
				case 0:			// ���α׷� ����
					
					System.out.println("[System] ���α׷��� �����մϴ�.");
					loop = false;
					break;
					
				default:
					
					System.out.println("[Error] �߸� �Է��ϼ̽��ϴ�.\n");
					break;
			}
		}
		
	}	// ProjectBusUI();

	/**
	 * 		Main Menu
	 */
	private void printMainMenu() {
		
		System.out.println("=== [ Bus Program ] ===");
		System.out.println("1. �˻�");
		System.out.println("2. ���ã��");
		System.out.println("3. �ֱ� �˻�");
		System.out.println("0. ���α׷� ����");
		System.out.println();
		System.out.println("�����Ͻ� �޴��� �����ϼ���.");
				
	}
	
	
	/**
	 * 		Search - ���� / ������ �˻�
	 */
	private void search() {
		
		sc.nextLine();
		
		String busNum = null;				// �Է¹��� ���� ��ȣ�� ���� �׸� 
		String stnName = null;				// �Է¹��� ������ �̸��� ���� �׸�
		List<Bus> busRoute = null;			// �뼱�� �����
		boolean loop = true;				// while �ݺ�����.
		boolean flag = true;				// ���� �޼ҵ� while �ݺ�����.
		
		System.out.println("\n--- < ��  ��  > ---");
		System.out.println("1. ���� ��ȣ�� �˻�");
		System.out.println("2. ���������� �˻�");
		System.out.println("0. ���θ޴�\n");
					
		int option = getIntFromUser();		// �����κ��� ���ڸ� �Է¹ް� �ϴ� �޼ҵ�
		
		while(loop) {
			
			switch (option) {
			
				case 1:		// ���� ��ȣ�� �˻� -> �뼱�� Ȯ�� -> ���ã�� ���� Ȯ��
					
					System.out.println("--- < Bus Info > ---");
					System.out.println("�˻��ϰ� ���� ���� ��ȣ�� �Է��ϼ���.");
					
					// �� ���� �̻�, �� ���� ���ϸ� �޵��� �˻�
					busNum = getTextFromUser();
						
					// manager���� �Է¹��� ���ڰ� ���Ե� �������� ����� �ҷ��´�.
					System.out.println("> �Է��Ͻ� ���ڿ� �ش�Ǵ� ���� ����Դϴ�. <");
					List<Bus> busList = manager.getBuses(busNum);
										
					// �ҷ��� ���� ����� �迭�� Numbering �ؼ� ���
					System.out.println();
					for (int i = 0; i < busList.size(); i++) {
						System.out.println((i + 1) + ". " + busList.get(i) + "\n");
					}
					System.out.println();
					
					// ������ �̻��� ���ڸ� �Է��ϸ� error ���
					
					System.out.println("> Ȯ���ϰ� ���� ������ �������ּ���. <");
					while(flag) {
						System.out.println("^^");
						sc.nextLine();
						option = getIntFromUser();
						
						if (option > busList.size() || option <= 0) {
							System.out.println("[Error] ��� ���� ���ڸ� �Է��ϼ���.");
						} else {
							flag = false;
						}
					}
					
					// TODO: �뼱�� ���
					busRoute = manager.getRouteMap(busNum);
					
					for (Bus route : busRoute) {
						System.out.println(route);		// TODO: �ʿ��� ������ ����ϵ��� �����ʿ�
					}
					
					// TODO: ���ã�� ���� Ȯ�� �� ����
					
					boolean canSaveFavorite = manager.getFavorite(busNum);
					
					if (canSaveFavorite) {
						System.out.println("[Error] �̹� ����� �����Դϴ�.");
					} else {
						// TODO: ���ã�� ����
						System.out.println("[System] ���������� ����Ǿ����ϴ�.\n");
						loop = false; 	// ���θ޴��� ���ư�
					}
					
					break;
		
				case 2:		// ���������� �˻� -> �����ٴϴ� ���� Ȯ�� -> ���ã�� ���� Ȯ��
					
					System.out.println("--- < Station Info > ---");
					System.out.println("�˻��ϰ� ���� �������� �Է��ϼ���.");
					
					stnName = getTextFromUser();
					
					System.out.println("> �Է��Ͻ� ���ڿ� �ش�Ǵ� ������ ����Դϴ�. <");
					List<Station> stnList = manager.getStations(stnName);
					
					// �迭�� Numbering �ؼ� ���
					System.out.println();
					
					for (int i = 0; i < stnList.size(); i++) {
						System.out.println(i + 1 + ". " + stnList.get(i) + "\n");
					}
					
					System.out.println();
					
					// ������ �̻��� ���ڸ� �Է��ϸ� false ��ȯ
					System.out.println("> Ȯ���ϰ� ���� �������� �������ּ���. <");
					
					while(flag) {
						sc.nextLine();
						option = getIntFromUser();
						
						if (option > stnList.size() || option <= 0) {
							System.out.println("[Error] ��� ���� ���ڸ� �Է��ϼ���.");
							
						} else {
							flag = false;
						}
					}
					
					//stnList = manager.getBuses(stnList.get(option - 1).getStnId());
					
					// TODO: �ش� �������� �������� ���� ��� �ҷ�����
						
					
					// TODO: ���ã�� ���� Ȯ�� �� ����
										
					break;
									
				case 0:		// �޼ҵ� ����
					
					System.out.println("[System] ���� �޴��� ���ư��ϴ�.\n");
					loop = false;
					break;
					
				default:
					
					System.out.println("[Error] �߸� �Է��ϼ̽��ϴ�.");
					break;
			}
		}
	} // search();
	
	/**
	 * 		�����κ��� ���ڸ� �޾ƿ��� �Ѵ�.
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
				System.out.println("[Error] ���ڸ� �Է��� �ֽʽÿ�");
				
				// ������ ���� ��� �̹� �Է¹��� ���ڸ� ������ ��� �ǹǷ� ����
				sc.nextLine();
			}
		}
		
		while (option < 0);
		
		return option;
	}
	
	/**
	 * 		�����κ��� �Է¹��� �ؽ�Ʈ �޼ҵ�
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// �Է¹��� ������ �̸��� ���� �׸�
				
		do 	{
				System.out.print(">>  ");
			
			try {
				inputText = sc.next();
				
			} catch (Exception e) {
				System.out.println("[Error] �ּ� �� ���� �̻� �Է��ϼž� �մϴ�.");
				sc.nextLine();
			}
		} 
		
		while(inputText == null || inputText.length() < 2);
		
		return inputText;
			
	}
	
	/**
	 * 		���ã��
	 */
	private void favorite() {
		System.out.println("--- < ��  ��  ã  �� > ---");
		// TODO: 1. �����κ��� �Է¹��� ������ȣ �Ǵ� ������ �˻����� ���ã�� ���� ���� Ȯ��
		
		// TODO: 2. �� �޼ҵ忡���� ���ã���� ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
		// TODO: 3. �ش� ���� �Ǵ� �������� ���ã�� ���
	}
	
	/**
	 *  	�ֱ� �˻�
	 */
	private void recentSearch() {
		// TODO: 1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		// TODO: 2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ���, ���ù޾� �ش� ���� �Ǵ� ������ ���� ���
		
	}
}

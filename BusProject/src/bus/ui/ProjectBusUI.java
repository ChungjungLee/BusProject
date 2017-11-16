package bus.ui;

import java.util.List;
import java.util.Scanner;

import bus.manager.ProjectBusManager;
import bus.vo.Bus;
import bus.vo.Station;

public class ProjectBusUI {

	private Scanner sc = new Scanner(System.in);		// Scanner ����
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
		
		System.out.println("--- < ��  ��  > ---");
		System.out.println("1. ���� ��ȣ�� �˻�");
		System.out.println("2. ���������� �˻�");
		System.out.println("0. ���θ޴�");
		System.out.println();
		System.out.print(">> ");
		
		int option = 0;					// �����κ��� �Է¹��� ���� 1, 2, 0
		
		option = getIntFromUser();		// �����κ��� ���ڸ� �Է¹ް� �ϴ� �޼ҵ�
		
		int busNum = 0;					// �Է¹��� ���� ��ȣ�� ���� �׸� 
		
		String stnName = null;			// �Է¹��� ������ �̸��� ���� �׸�
		
		boolean loop = true;			// while �ݺ�����.
		
		while(loop) {
			
			switch (option) {
			
				case 1:		// ���� ��ȣ�� �˻�
					
					System.out.println("--- < Bus Info > ---");
					System.out.println("�˻��ϰ� ���� ���� ��ȣ�� �Է��ϼ���.");
													
					busNum = getIntFromUser();
					
					List<Bus> busList = ProjectBusManager.getBuses(busNum);
					
					for (Bus bus : busList) {
						System.out.println(bus); 	// TODO: [ VO ����, �����ʿ�! ]
					}
						// TODO: ���ã�� ���� Ȯ�� �� ����
					break;
		
				case 2:		// ���������� �˻�
					
					System.out.println("--- < Station Info > ---");
					System.out.println("�˻��ϰ� ���� �������� �Է��ϼ���.");
					
					stnName = getTextFromUser();
					
					List<Station> stnList = ProjectBusManager.getStations(stnName);
						
						// TODO: manager���� Station ID�� ��ȯ, �ش�Ǵ� �����Ҹ� �ٽ� �Ѱܹ޾ƾ� ��.
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
				System.out.println("[Error] ���ڸ� �Է��� �ֽʽÿ�");
				
				// ������ ���� ��� �̹� �Է¹��� ���ڸ� ������ ��� �ǹǷ� ����
				sc.nextLine();
			}
		}
		
		while (option <= 0);
		
		return option;
	}
	
	/**
	 * 		�����κ��� �Է¹��� �ؽ�Ʈ �޼ҵ�
	 */
	private String getTextFromUser() {
		
		String inputText = null;	// �Է¹��� ������ �̸��� ���� �׸�
		boolean loop = true;		// while �ݺ�����.
		
		while (loop) {
			
			System.out.print(">>  ");
			inputText = sc.next();
			
			if (inputText == null || inputText.length() < 2) {
				System.out.println("[Error] �ּ� �� ���� �̻� �Է��ϼž� �մϴ�.");
			} else { 
				loop = false;		// while�� ����
			}
		}
		
		return inputText;
			
	}
	
	/**
	 * 		���ã��
	 */
	private void favorite() {
		// TODO: 1. �����κ��� �Է¹��� ������ȣ �Ǵ� ������ �˻����� ���ã�� ���� ���� Ȯ��
		
		System.out.println("--- < ��  ��  ã  �� > ---");
		// TODO: 2. �� �޼ҵ忡���� ���ã���� ��� ��¸� �ϵ���.
		
	}
	
	/**
	 *  	�ֱ� �˻�
	 */
	private void recentSearch() {
		// TODO: 1. ���� search() ���� �˻��� ���� �Ǵ� �������� �ִٸ� ���ڸ����� �ٷ� count�� �ø��� ���.
		// TODO: 2. �� �޼ҵ忡���� ���� �ֱٿ� �˻��� ���� ���� ��ܿ� ����ǵ��� ��¸� �� 
		
	}
}

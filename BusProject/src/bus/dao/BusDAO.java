package bus.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bus.vo.Account;
import bus.vo.Bus;
import bus.vo.Station;

public class BusDAO {
	SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
	/**
	 * ���� ������ �����ͺ��̽��� �����Ѵ�.
	 * @param account ������ ���� ����
	 * @return ���� ���
	 */
	public boolean insertAccount(Account account) {
		SqlSession session = null;
		boolean result = true;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			if (mapper.insertAccount(account) != 1) {
				result = false;
			}
			
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * ID�� ������ �ش� ���� ������ �����´�.
	 * @param userId ã���� �ϴ� ������ ID
	 * @return ID�� ��ġ�ϴ� ����
	 */
	public Account selectAccount(String userId) {
		SqlSession session = null;
		Account result = null;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			result = mapper.selectAccount(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * ���� ����� �Ѱ� �޾� �����ͺ��̽��� �����Ѵ�.
	 * @param busesList ������ ���� ����Ʈ
	 * @return ���� ���
	 */
	public boolean insertBuses(List<Bus> busesList) {
		SqlSession session = null;
		boolean result = true;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			for (Bus bus : busesList) {
				if (mapper.insertBus(bus) == 0) {
					result = false;
				}
			}
			
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return result;
	}
	
	/**
	 * Ư�� ��ȣ�� �����ϴ� ��� ������ �����´�.
	 * @param busNum �˻��Ϸ��� ��ȣ
	 * @return Ư�� ��ȣ�� �����ϴ� ����
	 */
	public List<Bus> srchBusContainsNum(String busNum) {
		SqlSession session = null;
		List<Bus> busList = null;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			busList = mapper.selectBusContainsNum(busNum);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return busList;
	}
	
	/**
	 * ������ ������ ����� �Ѱ� �޾� �����ͺ��̽��� �����Ѵ�.
	 * @param stationsList ������ ������ ����Ʈ
	 * @return ���� ���
	 */
	public boolean insertStations(List<Station> stationsList) {
		SqlSession session = null;
		boolean result = true;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			for (Station station : stationsList) {
				if(mapper.insertStation(station) == 0) {
					result = false;
				}
			}
			
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return result;
	}
	
	
}

package bus.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bus.vo.Bus;
import bus.vo.Station;

public class BusDAO {
	SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
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

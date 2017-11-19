package bus.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bus.vo.Bus;
import bus.vo.Station;

public class BusDAO {
	SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
	/**
	 * 버스 목록을 넘겨 받아 데이터베이스에 저장한다.
	 * @param busesList 저장할 버스 리스트
	 * @return 저장 결과
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
	 * 저장할 정류장 목록을 넘겨 받아 데이터베이스에 저장한다.
	 * @param stationsList 저장할 정류장 리스트
	 * @return 저장 결과
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

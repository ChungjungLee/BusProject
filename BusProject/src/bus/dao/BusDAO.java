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
	 * 계정 정보를 데이터베이스에 저장한다.
	 * @param account 저장할 계정 정보
	 * @return 저장 결과
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
	 * ID를 가지고 해당 계정 정보를 가져온다.
	 * @param userId 찾고자 하는 계정의 ID
	 * @return ID와 일치하는 계정
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
	 * 특정 번호를 포함하는 모든 버스를 가져온다.
	 * @param busNum 검색하려는 번호
	 * @return 특정 번호를 포함하는 버스
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

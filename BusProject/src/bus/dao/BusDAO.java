package bus.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bus.vo.Bus;

public class BusDAO {
	SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
	/**
	 * 
	 * @param busesList
	 * @return
	 */
	public boolean insertBuses(List<Bus> busesList) {
		SqlSession session = null;
		boolean result = true;
		
		try {
			session = factory.openSession();
			BusMapper mapper = session.getMapper(BusMapper.class);
			
			for (Bus bus : busesList) {
				mapper.insertBus(bus);
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

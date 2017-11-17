package bus.dao;

import java.util.List;

import bus.vo.Bus;
import bus.vo.Station;

public interface BusMapper {
	
	int insertBus(Bus bus);
	
	int insertStation(Station station);
	
	List<Integer> selectFavorites(int id);
}

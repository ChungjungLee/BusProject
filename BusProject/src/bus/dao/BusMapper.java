package bus.dao;

import bus.vo.Bus;
import bus.vo.Station;

public interface BusMapper {
	
	int insertBus(Bus bus);
	
	int insertStation(Station station);
}

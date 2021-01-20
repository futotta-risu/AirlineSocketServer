package org.SlavaLenin.SocketAirline.socket.echo.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.SlavaLenin.SocketAirline.socket.echo.server.data.AirlineFlight;

public class AirlineDBManager {

	private static HashMap<Integer, AirlineFlight> flights;
	
	private static AirlineDBManager instance;
	
	AirlineDBManager(){
		flights = new HashMap<Integer, AirlineFlight>();
		for(int i = 0; i < 10; i++) {
			AirlineFlight f = new AirlineFlight();
			flights.put(f.getFligthNumber(), f);
		}
	}
	
	public static AirlineDBManager getInstance() {
		if(instance == null)
			instance = new AirlineDBManager();
		return instance;
	}
	
	public AirlineFlight getFlight(String id) {
		int flightID = Integer.valueOf(id);
		if(flights.containsKey(flightID))
			return flights.get(flightID);
		return null;
	}
	
	public List<AirlineFlight> getFlights(){
		return new ArrayList<AirlineFlight>(flights.values());
	}
	
}

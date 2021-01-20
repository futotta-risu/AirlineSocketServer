package org.SlavaLenin.SocketAirline.socket.echo.server.data;


import java.io.Serializable;
import java.util.Date;

public class SocketAirlineFlightDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int fligthNumber;
	
	private Date dateDeparture, dateArrival;
	
	/**
	 * The String passed is the ICAO of the Airports
	 */
	private String airportDeparture, airportArrival;

	private int totalSeats, filledSeats, price;
	

	public SocketAirlineFlightDTO(int fligthNumber, Date dateDeparture, Date dateArrival, 
	        String airportDeparture, String airportArrival, int totalSeats, int filledSeats, int price) {
	    super();
	    this.fligthNumber = fligthNumber;
	    this.dateDeparture = dateDeparture;
	    this.dateArrival = dateArrival;
	    this.airportDeparture = airportDeparture;
	    this.airportArrival = airportArrival;
	    this.filledSeats = filledSeats;
	    this.price = price;
	}
	
	

	public int getFligthNumber() {
	    return fligthNumber;
	}
	
	public void setFligthNumber(int fligthNumber) {
	    this.fligthNumber = fligthNumber;
	}
	
	public Date getDateDeparture() {
	    return dateDeparture;
	}
	
	public void setDateDeparture(Date dateDeparture) {
	    this.dateDeparture = dateDeparture;
	}
	
	public Date getDateArrival() {
	    return dateArrival;
	}
	
	public void setDateArrival(Date dateArrival) {
	    this.dateArrival = dateArrival;
	}



	public String getAirportDeparture() {
		return airportDeparture;
	}



	public void setAirportDeparture(String airportDeparture) {
		this.airportDeparture = airportDeparture;
	}



	public String getAirportArrival() {
		return airportArrival;
	}



	public void setAirportArrival(String airportArrival) {
		this.airportArrival = airportArrival;
	}



	public int getTotalSeats() {
		return totalSeats;
	}



	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}



	public int getFilledSeats() {
		return filledSeats;
	}



	public void setFilledSeats(int filledSeats) {
		this.filledSeats = filledSeats;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}
	

}

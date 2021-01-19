package org.SlavaLenin.SocketAirline.socket.echo.server.data;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.SlavaLenin.SocketAirline.socket.echo.server.data.errors.NoMoreSeatsException;



public class AirlineFlight {
private final String[] airportCodes = {"BILB","BARC","MADR","BUDA","PARI","BTCH","COTI"};
	
	private static int flightID = 2;
	
	private int flightNumber;
	
	private Date dateDeparture, dateArrival;
	private String airportDeparture, airportArrival;

	
	private int seats=100, filledseats=0;
	
	public AirlineFlight() {
		this.flightNumber = ++flightID;
		Random rnd = new Random();
		this.dateDeparture = new Date(Math.abs(System.currentTimeMillis() - rnd.nextInt()));
		this.dateArrival = new Date(Math.abs(System.currentTimeMillis() - rnd.nextInt()));
		int airportDepartureCode = 1 + Math.abs(rnd.nextInt()) % 6 ; // Number between 1 to 7
		int permutationCode =  2 + Math.abs(rnd.nextInt()) % 5; // Number between 2 to 7
		// Number between 1 to 7 such that airportDepartureCode != airportArrivalCode
		int airportArrivalCode =  (airportDepartureCode * permutationCode) % 7;
		airportDeparture = airportCodes[airportDepartureCode - 1];
		airportArrival = airportCodes[airportArrivalCode - 1];
	}

	public int getFligthNumber() {
		return flightNumber;
	}

	public void setFligthNumber(int fligthNumber) {
		this.flightNumber = fligthNumber;
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

	

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getFilledseats() {
		return filledseats;
	}

	public void setFilledseats(int filledseats) throws NoMoreSeatsException{
		if(filledseats > this.seats) 
			throw new NoMoreSeatsException("El maximo numero de asiento sobrepasado.");
		this.filledseats = filledseats;
	}
	
	public void fillSeat(int filledseats) throws NoMoreSeatsException{
		if(filledseats + this.filledseats > this.seats) 
			throw new NoMoreSeatsException("El maximo numero de asiento sobrepasado.");
		this.filledseats = filledseats;
	}
	
	public void fillSeat() throws NoMoreSeatsException{
		if(1 + this.filledseats > this.seats) 
			throw new NoMoreSeatsException("El maximo numero de asiento sobrepasado.");
		this.filledseats = filledseats + 1;
	}
	
	public SocketAirlineFlightDTO assemble() {
        
		return new SocketAirlineFlightDTO(this.flightNumber, this.dateDeparture, this.dateArrival,
		        this.airportDeparture, this.airportArrival, this.seats, this.filledseats);
	}

	public int getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
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

	@Override
	public String toString() {
		return "AirlineFlight [airportCodes=" + Arrays.toString(airportCodes) + ", flightNumber=" + flightNumber
				+ ", dateDeparture=" + dateDeparture + ", dateArrival=" + dateArrival + ", airportDeparture="
				+ airportDeparture + ", airportArrival=" + airportArrival + ", seats=" + seats + ", filledseats="
				+ filledseats + "]";
	}
}

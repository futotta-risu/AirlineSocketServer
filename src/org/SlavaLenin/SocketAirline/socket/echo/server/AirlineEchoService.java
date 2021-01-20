package org.SlavaLenin.SocketAirline.socket.echo.server;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.SlavaLenin.SocketAirline.socket.echo.server.data.AirlineFlight;
import org.SlavaLenin.SocketAirline.socket.echo.server.data.SocketAirlineFlightDTO;
import org.SlavaLenin.SocketAirline.socket.echo.server.data.errors.NoMoreSeatsException;


public class AirlineEchoService extends Thread{
	private DataInputStream in;
	private ObjectOutputStream out;
	private Socket tcpSocket;

	public AirlineEchoService(Socket socket) {
		try {
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.start();
			
		} catch (Exception e) {
			System.out.println("# EchoService - TCPConnection IO error:" + e.getMessage());
		}
		
	}
	
	public void reservar(String id) throws Exception{
		AirlineFlight flight = AirlineDBManager.getInstance().getFlight(id);
		if(flight == null)
			throw new Exception("ID not found in AirFrance Airline");
		
		try {
			flight.fillSeat();
		} catch (NoMoreSeatsException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelarReserva(String id) throws Exception{
		AirlineFlight flight = AirlineDBManager.getInstance().getFlight(id);
		if(flight == null)
			throw new Exception("ID not found in AirFrance Airline");
		try {
			flight.fillSeat(-1);
		} catch (NoMoreSeatsException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public SocketAirlineFlightDTO buscarVuelo(String id) {
		System.out.println("La id de buscar " + id);
		
		AirlineFlight flight = AirlineDBManager.getInstance().getFlight(id);
			
		System.out.println("- Se ha buscado y devuelto " + flight + " viajes");
		return flight.assemble();
	}

	public List<SocketAirlineFlightDTO> buscar(String id){
		System.out.println("La id de buscar " + id);
		
		List<SocketAirlineFlightDTO> flightList = new LinkedList<SocketAirlineFlightDTO>();
		
		for(AirlineFlight f : AirlineDBManager.getInstance().getFlights()) 
			if(f.getFligthNumber() > Integer.valueOf(id))
				flightList.add(f.assemble());
		
		System.out.println("- Se han buscado y devuelto " + flightList.size() + " viajes");
		return flightList;
	}

	public void run() {
		System.out.println(" - Comenzando el proceso de run en el service.");
		List<SocketAirlineFlightDTO> resultSearch = new ArrayList<SocketAirlineFlightDTO>();
		//Echo server
		try {
			//Read request from the client
			String data = this.in.readUTF();			
			System.out.println("   - EchoService - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");		
			String[] values = data.split(" ");
			
			
			switch(values[0]) {
				case("BUSCAR"):
					System.out.println(" - Ejecutando la secuencia de comandos de buscar");
					resultSearch = buscar(values[1]);
					if(!resultSearch.isEmpty()) {
						this.out.writeObject(resultSearch);
					}else{
						this.out.writeUTF("NULLSEARCH");			
					}
					break;
				case("RESERVAR"):
					System.out.println(" - Ejecutando la secuencia de comandos de reservar");
					try {
						reservar(values[1]);
						this.out.writeUTF("OK_RESERVA " + values[1]);
					}catch (Exception e) {
						this.out.writeUTF("FALLO_RESERVA");			
					}
					break;
					
				case("CANCELAR"):
					System.out.println(" - Ejecutando la secuencia de comandos de cancelar");
					try {
						cancelarReserva(values[1]);
						this.out.writeUTF("OK_CANCELAR_RESERVA " + values[1]);
					}catch (Exception e) {
						this.out.writeUTF("FALLO_CANCELAR_RESERVA");			
					}
					break;
				case("BUSCARVUELO"):
					System.out.println(" - Ejecutando la secuencia de comandos de buscarvuelo");
					this.out.writeObject(buscarVuelo(values[1])); 
					break;
			}
					
			System.out.println("   - EchoService - Sent data to '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data.toUpperCase() + "'");
		} catch (Exception e) {
			System.out.println("   # EchoService error" + e.getMessage());
		} finally {
			try {
				tcpSocket.close();
			} catch (Exception e) {
				System.out.println("   # EchoService error:" + e.getMessage());
			}
		}
	}
}

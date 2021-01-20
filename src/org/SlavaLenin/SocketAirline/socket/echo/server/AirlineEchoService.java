package org.SlavaLenin.SocketAirline.socket.echo.server;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.SlavaLenin.SocketAirline.socket.echo.server.data.AirlineFlight;
import org.SlavaLenin.SocketAirline.socket.echo.server.data.SocketAirlineFlightDTO;
import org.SlavaLenin.SocketAirline.socket.echo.server.data.errors.NoMoreSeatsException;


public class AirlineEchoService extends Thread{
	private DataInputStream in;
	private ObjectOutputStream out;
	private Socket tcpSocket;
	private HashMap<Integer, AirlineFlight> flights;


	public AirlineEchoService(Socket socket) {
		try {
			
			this.tcpSocket = socket;
		    this.in = new DataInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.start();
			
			flights = new HashMap<Integer, AirlineFlight>();
			for(int i = 0; i < 10; i++) {
				AirlineFlight f = new AirlineFlight();
				flights.put(f.getFligthNumber(), f);
			}
			
		} catch (Exception e) {
			System.out.println("# EchoService - TCPConnection IO error:" + e.getMessage());
		}
	}
	
	public void reservar(String id) throws Exception{
		if(!flights.containsKey(Integer.valueOf(id)))
			throw new Exception("ID not found in KoreanAirline");
		
		try {
			flights.get(Integer.valueOf(id)).fillSeat();
		} catch (NoMoreSeatsException e) {
			e.printStackTrace();
		}
	}

	public List<SocketAirlineFlightDTO> buscar(String id){
		System.out.println("La id de buscar " + id);
		System.out.println("Tiene el tamanio " + flights.size());
		List<SocketAirlineFlightDTO> flightList = new LinkedList<SocketAirlineFlightDTO>();
		for(AirlineFlight f : flights.values()) 
			if(f.getFligthNumber() > Integer.valueOf(id))
				flightList.add(f.assemble());
		
		System.out.println("- Se han buscado y devuelto " + flightList.size() + " viajes");
		return flightList;
	}

	public void run() {
		
		List<SocketAirlineFlightDTO> resultSearch = new ArrayList<SocketAirlineFlightDTO>();
		//Echo server
		try {
			//Read request from the client
			String data = this.in.readUTF();			
			System.out.println("   - EchoService - Received data from '" + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort() + "' -> '" + data + "'");		
			String[] values = data.split(" ");
			
			
			switch(values[0]) {
				case("BUSCAR"):
					resultSearch = buscar(values[1]);
					if(!resultSearch.isEmpty()) {
						this.out.writeObject(resultSearch);
					}else{
						this.out.writeUTF("NULLSEARCH");			
					}
				
				case("RESERVAR"):
					try {
						reservar(values[1]);
						this.out.writeUTF("OK_RESERVA " + values[1]);
					}catch (Exception e) {
						this.out.writeUTF("FALLO_RESERVA");			
					}
				
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

package org.SlavaLenin.SocketAirline.socket.echo.server.data.errors;

public class NoMoreSeatsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoMoreSeatsException(String errorMessage) {
        super(errorMessage);
    }
}
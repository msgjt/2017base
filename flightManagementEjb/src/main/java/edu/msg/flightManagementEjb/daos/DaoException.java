package edu.msg.flightManagementEjb.daos;

public class DaoException extends RuntimeException{

	private static final long serialVersionUID = -7943752204133908484L;
	
	public DaoException() {
		super();
		
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DaoException(String message) {
		super(message);
		
	}

	public DaoException(Throwable cause) {
		super(cause);
		
	}

}

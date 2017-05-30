package edu.msg.jbugs.business;

public class RemoteException extends RuntimeException{

	private static final long serialVersionUID = -7008231173289984474L;
	
	
	public RemoteException() {
		super();
		
	}

	public RemoteException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public RemoteException(String message) {
		super(message);
		
	}

	public RemoteException(Throwable cause) {
		super(cause);
		
	}

}

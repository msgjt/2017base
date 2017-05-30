package edu.msg.jbugs.persistence.repositories;

public class RepositoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9165790234751635373L;

	public RepositoryException(String message, Exception e) {
		super(message, e);
	}

	public RepositoryException(String message) {
		super(message);
	}

}

package edu.msg.jbugs.util;

import javax.ejb.Stateless;

@Stateless
public abstract class CsvFactory {

	public abstract boolean importCsv(String filename);
	
	public abstract boolean exportCsv();
	
	public static CsvFactory getInstance(final String type) {
		switch (type) {
		case "User":
			return new UserCsv();
		default:
			return null;
		}
	}
}

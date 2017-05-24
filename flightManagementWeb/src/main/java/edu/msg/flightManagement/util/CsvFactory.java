package edu.msg.flightManagement.util;

import javax.ejb.Stateless;

@Stateless
public abstract class CsvFactory {

	public abstract boolean importCsv(String filename);
	
	public abstract boolean exportCsv();
	
	public static CsvFactory getInstance(final String type) {
		switch (type) {
		case "Plane":
			return new PlaneCsv();
		case "User":
			return new UserCsv();
		case "Airport":
			return new AirportCsv();
		case "Flight":
			return new FlightCsv();
		case "Itinerary":
			return new ItineraryCsv();
		default:
			return null;
		}
	}
}

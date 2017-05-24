package edu.msg.flightManagementEjbClient.dtos;

public class FlightHistoryDTO {

	private int flightHistoryId;
	
	private String description;
	
	private FlightDTO flight;

	public int getFlightHistoryId() {
		return flightHistoryId;
	}

	public void setFlightHistoryId(int flightHistoryId) {
		this.flightHistoryId = flightHistoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}
	
	

}

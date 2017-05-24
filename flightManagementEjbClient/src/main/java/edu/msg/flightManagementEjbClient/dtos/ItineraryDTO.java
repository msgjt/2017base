package edu.msg.flightManagementEjbClient.dtos;

import java.util.List;

public class ItineraryDTO {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + itineraryId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItineraryDTO other = (ItineraryDTO) obj;
		if (itineraryId != other.itineraryId)
			return false;
		return true;
	}

	private int itineraryId;

	private String itineraryName;

	private List<FlightDTO> flights;

	private AirportDTO airport1;

	private AirportDTO airport2;

	private CompanyDTO company;

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public int getItineraryId() {
		return itineraryId;
	}

	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	public List<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightDTO> flights) {
		this.flights = flights;
	}

	public AirportDTO getAirport1() {
		return airport1;
	}

	public void setAirport1(AirportDTO airport1) {
		this.airport1 = airport1;
	}

	public AirportDTO getAirport2() {
		return airport2;
	}

	public void setAirport2(AirportDTO airport2) {
		this.airport2 = airport2;
	}

}

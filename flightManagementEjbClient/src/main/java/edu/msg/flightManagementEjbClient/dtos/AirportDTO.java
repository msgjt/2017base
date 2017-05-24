package edu.msg.flightManagementEjbClient.dtos;

import java.util.List;

public class AirportDTO {

	private int airportId;

	private String address;

	private String airportName;

	private String city;

	private String country;

	private boolean status;

	private List<FlightDTO> flights1;

	private List<FlightDTO> flights2;

	private List<FlighttemplateDTO> flighttemplates1;

	private List<FlighttemplateDTO> flighttemplates2;

	private List<ItineraryDTO> itinerarys1;

	private List<ItineraryDTO> itinerarys2;

	public int getAirportId() {
		return airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<FlightDTO> getFlights1() {
		return flights1;
	}

	public void setFlights1(List<FlightDTO> flights1) {
		this.flights1 = flights1;
	}

	public List<FlightDTO> getFlights2() {
		return flights2;
	}

	public void setFlights2(List<FlightDTO> flights2) {
		this.flights2 = flights2;
	}

	public List<FlighttemplateDTO> getFlighttemplates1() {
		return flighttemplates1;
	}

	public void setFlighttemplates1(List<FlighttemplateDTO> flighttemplates1) {
		this.flighttemplates1 = flighttemplates1;
	}

	public List<FlighttemplateDTO> getFlighttemplates2() {
		return flighttemplates2;
	}

	public void setFlighttemplates2(List<FlighttemplateDTO> flighttemplates2) {
		this.flighttemplates2 = flighttemplates2;
	}

	public List<ItineraryDTO> getItinerarys1() {
		return itinerarys1;
	}

	public void setItinerarys1(List<ItineraryDTO> itinerarys1) {
		this.itinerarys1 = itinerarys1;
	}

	public List<ItineraryDTO> getItinerarys2() {
		return itinerarys2;
	}

	public void setItinerarys2(List<ItineraryDTO> itinerarys2) {
		this.itinerarys2 = itinerarys2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airportId;
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
		AirportDTO other = (AirportDTO) obj;
		if (airportId != other.airportId)
			return false;
		return true;
	}

}

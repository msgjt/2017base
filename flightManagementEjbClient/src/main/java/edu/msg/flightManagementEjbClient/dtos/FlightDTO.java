package edu.msg.flightManagementEjbClient.dtos;

import java.util.Date;
import java.util.List;

public class FlightDTO {

	private int flightId;

	private Date endDate;

	private Date startDate;

	private List<UserDTO> users;

	private AirportDTO airport1;

	private AirportDTO airport2;

	private CompanyDTO company;

	private PlaneDTO plane;

	private List<ItineraryDTO> itineraries;

	private boolean status;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getFlightId() {
		return flightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightId;
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
		FlightDTO other = (FlightDTO) obj;
		if (flightId != other.flightId)
			return false;
		return true;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public PlaneDTO getPlane() {
		return plane;
	}

	public void setPlane(PlaneDTO plane) {
		this.plane = plane;
	}

	public List<ItineraryDTO> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<ItineraryDTO> itineraries) {
		this.itineraries = itineraries;
	}

}

package edu.msg.flightManagementEjbClient.dtos;

import java.util.List;

public class PlaneDTO {

	private int planeId;

	private int capacity;

	private String model;

	private boolean status;

	private CompanyDTO company;
	
	private List<FlightDTO> flights;

	private List<FlighttemplateDTO> flighttemplates;

	public int getPlaneId() {
		return planeId;
	}

	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightDTO> flights) {
		this.flights = flights;
	}

	public List<FlighttemplateDTO> getFlighttemplates() {
		return flighttemplates;
	}

	public void setFlighttemplates(List<FlighttemplateDTO> flighttemplates) {
		this.flighttemplates = flighttemplates;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + planeId;
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
		PlaneDTO other = (PlaneDTO) obj;
		if (planeId != other.planeId)
			return false;
		return true;
	}

}

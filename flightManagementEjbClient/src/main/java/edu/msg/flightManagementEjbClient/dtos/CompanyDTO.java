package edu.msg.flightManagementEjbClient.dtos;

import java.util.ArrayList;
import java.util.List;

public class CompanyDTO {

	private int companyId;

	private String address;

	private String companyName;

	private String email;

	private String phone;

	private boolean status;

	private List<FlightDTO> flights;

	private List<FlighttemplateDTO> flighttemplates;

	private List<UserDTO> users;

	private List<PlaneDTO> planes;

	private List<ItineraryDTO> itineraries;

	public List<ItineraryDTO> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<ItineraryDTO> itineraries) {
		this.itineraries = itineraries;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public UserDTO addUser(UserDTO user) {
		getUsers().add(user);
		user.setCompany(this);

		return user;
	}

	public UserDTO removeUser(UserDTO user) {
		getUsers().remove(user);
		user.setCompany(null);

		return user;
	}

	public List<PlaneDTO> getPlanes() {
		return planes;
	}

	public void setPlanes(List<PlaneDTO> planes) {
		this.planes = planes;
	}

	public PlaneDTO addPlane(PlaneDTO plane) {
		getPlanes().add(plane);
		plane.setCompany(this);

		return plane;
	}

	public PlaneDTO removePlane(PlaneDTO plane) {
		getPlanes().remove(plane);
		plane.setCompany(null);

		return plane;
	}

	public List<PlaneDTO> getAvailablePlanes() {
		ArrayList<PlaneDTO> a = new ArrayList<PlaneDTO>();
		for (PlaneDTO planeDTO : getPlanes()) {
			if(planeDTO.getStatus() == true)
				a.add(planeDTO);
		}
		return a;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + companyId;
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
		CompanyDTO other = (CompanyDTO) obj;
		if (companyId != other.companyId)
			return false;
		return true;
	}

}

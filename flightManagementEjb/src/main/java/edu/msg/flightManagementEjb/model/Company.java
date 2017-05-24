package edu.msg.flightManagementEjb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int companyId;

	private String address;

	private String companyName;

	private String email;

	private String phone;

	private boolean status;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "company")
	private List<Flight> flights;

	// bi-directional many-to-one association to Flighttemplate
	@OneToMany(mappedBy = "company")
	private List<Flighttemplate> flighttemplates;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "company")
	private List<User> users;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "company")
	private List<Itinerary> itineraries;

	public List<Itinerary> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<Itinerary> itineraries) {
		this.itineraries = itineraries;
	}

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "company")
	private List<Plane> planes;

	public Company() {
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public Flight addFlight(Flight flight) {
		getFlights().add(flight);
		flight.setCompany(this);

		return flight;
	}

	public Flight removeFlight(Flight flight) {
		getFlights().remove(flight);
		flight.setCompany(null);

		return flight;
	}

	public List<Flighttemplate> getFlighttemplates() {
		return this.flighttemplates;
	}

	public void setFlighttemplates(List<Flighttemplate> flighttemplates) {
		this.flighttemplates = flighttemplates;
	}

	public Flighttemplate addFlighttemplate(Flighttemplate flighttemplate) {
		getFlighttemplates().add(flighttemplate);
		flighttemplate.setCompany(this);

		return flighttemplate;
	}

	public Flighttemplate removeFlighttemplate(Flighttemplate flighttemplate) {
		getFlighttemplates().remove(flighttemplate);
		flighttemplate.setCompany(null);

		return flighttemplate;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setCompany(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setCompany(null);

		return user;
	}

	public List<Plane> getPlanes() {
		return this.planes;
	}

	public void setPlanes(List<Plane> planes) {
		this.planes = planes;
	}

	public Plane addPlane(Plane plane) {
		getPlanes().add(plane);
		plane.setCompany(this);

		return plane;
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
		Company other = (Company) obj;
		if (companyId != other.companyId)
			return false;
		return true;
	}

	public Plane removePlane(Plane plane) {
		getPlanes().remove(plane);
		plane.setCompany(null);

		return plane;
	}

}
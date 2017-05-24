package edu.msg.flightManagementEjb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the plane database table.
 * 
 */
@Entity
@NamedQuery(name = "Plane.findAll", query = "SELECT p FROM Plane p")
public class Plane implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int planeId;

	private int capacity;

	private String model;

	private boolean status;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "plane")
	private List<Flight> flights;

	// bi-directional many-to-one association to Flighttemplate
	@OneToMany(mappedBy = "plane")
	private List<Flighttemplate> flighttemplates;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Plane() {
	}

	public int getPlaneId() {
		return this.planeId;
	}

	public void setPlaneId(int planeId) {
		this.planeId = planeId;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
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
		flight.setPlane(this);

		return flight;
	}

	public Flight removeFlight(Flight flight) {
		getFlights().remove(flight);
		flight.setPlane(null);

		return flight;
	}

	public List<Flighttemplate> getFlighttemplates() {
		return this.flighttemplates;
	}

	public void setFlighttemplates(List<Flighttemplate> flighttemplates) {
		this.flighttemplates = flighttemplates;
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
		Plane other = (Plane) obj;
		if (planeId != other.planeId)
			return false;
		return true;
	}

	public Flighttemplate addFlighttemplate(Flighttemplate flighttemplate) {
		getFlighttemplates().add(flighttemplate);
		flighttemplate.setPlane(this);

		return flighttemplate;
	}

	public Flighttemplate removeFlighttemplate(Flighttemplate flighttemplate) {
		getFlighttemplates().remove(flighttemplate);
		flighttemplate.setPlane(null);

		return flighttemplate;
	}

}
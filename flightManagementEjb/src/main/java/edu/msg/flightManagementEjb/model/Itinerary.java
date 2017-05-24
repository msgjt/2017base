package edu.msg.flightManagementEjb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the itinerary database table.
 * 
 */
@Entity
@NamedQuery(name = "Itinerary.findAll", query = "SELECT i FROM Itinerary i")
public class Itinerary implements Serializable {
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
		Itinerary other = (Itinerary) obj;
		if (itineraryId != other.itineraryId)
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private int itineraryId;

	private String itineraryName;

	// bi-directional many-to-many association to Flight
	@ManyToMany
	@JoinTable(name = "flightitinerary", joinColumns = { @JoinColumn(name = "itineraryId") }, inverseJoinColumns = {
			@JoinColumn(name = "flightId") })
	private List<Flight> flights;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	// bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name = "startLocationId")
	private Airport airport1;

	// bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name = "endLocationId")
	private Airport airport2;

	public Itinerary() {
	}

	public int getItineraryId() {
		return this.itineraryId;
	}

	public void setItineraryId(int itineraryId) {
		this.itineraryId = itineraryId;
	}

	public String getItineraryName() {
		return this.itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public Airport getAirport1() {
		return this.airport1;
	}

	public void setAirport1(Airport airport1) {
		this.airport1 = airport1;
	}

	public Airport getAirport2() {
		return this.airport2;
	}

	public void setAirport2(Airport airport2) {
		this.airport2 = airport2;
	}

}
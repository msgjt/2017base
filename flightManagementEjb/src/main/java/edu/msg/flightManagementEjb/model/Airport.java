package edu.msg.flightManagementEjb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the airport database table.
 * 
 */
@Entity
@NamedQuery(name = "Airport.findAll", query = "SELECT a FROM Airport a")
public class Airport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int airportId;

	private String address;

	private String airportName;

	private String city;

	private String country;

	private boolean status;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "airport1")
	private List<Flight> flights1;

	// bi-directional many-to-one association to Flight
	@OneToMany(mappedBy = "airport2")
	private List<Flight> flights2;

	// bi-directional many-to-one association to Flighttemplate
	@OneToMany(mappedBy = "airport1")
	private List<Flighttemplate> flighttemplates1;

	// bi-directional many-to-one association to Flighttemplate
	@OneToMany(mappedBy = "airport2")
	private List<Flighttemplate> flighttemplates2;

	// bi-directional many-to-one association to Itinerary
	@OneToMany(mappedBy = "airport1")
	private List<Itinerary> itineraries1;

	// bi-directional many-to-one association to Itinerary
	@OneToMany(mappedBy = "airport2")
	private List<Itinerary> itineraries2;

	public Airport() {
		/* constructor */
	}

	public int getAirportId() {
		return this.airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAirportName() {
		return this.airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Flight> getFlights1() {
		return this.flights1;
	}

	public void setFlights1(List<Flight> flights1) {
		this.flights1 = flights1;
	}

	public Flight addFlights1(Flight flights1) {
		getFlights1().add(flights1);
		flights1.setAirport1(this);

		return flights1;
	}

	public Flight removeFlights1(Flight flights1) {
		getFlights1().remove(flights1);
		flights1.setAirport1(null);

		return flights1;
	}

	public List<Flight> getFlights2() {
		return this.flights2;
	}

	public void setFlights2(List<Flight> flights2) {
		this.flights2 = flights2;
	}

	public Flight addFlights2(Flight flights2) {
		getFlights2().add(flights2);
		flights2.setAirport2(this);

		return flights2;
	}

	public Flight removeFlights2(Flight flights2) {
		getFlights2().remove(flights2);
		flights2.setAirport2(null);

		return flights2;
	}

	public List<Flighttemplate> getFlighttemplates1() {
		return this.flighttemplates1;
	}

	public void setFlighttemplates1(List<Flighttemplate> flighttemplates1) {
		this.flighttemplates1 = flighttemplates1;
	}

	public Flighttemplate addFlighttemplates1(Flighttemplate flighttemplates1) {
		getFlighttemplates1().add(flighttemplates1);
		flighttemplates1.setAirport1(this);

		return flighttemplates1;
	}

	public Flighttemplate removeFlighttemplates1(Flighttemplate flighttemplates1) {
		getFlighttemplates1().remove(flighttemplates1);
		flighttemplates1.setAirport1(null);

		return flighttemplates1;
	}

	public List<Flighttemplate> getFlighttemplates2() {
		return this.flighttemplates2;
	}

	public void setFlighttemplates2(List<Flighttemplate> flighttemplates2) {
		this.flighttemplates2 = flighttemplates2;
	}

	public Flighttemplate addFlighttemplates2(Flighttemplate flighttemplates2) {
		getFlighttemplates2().add(flighttemplates2);
		flighttemplates2.setAirport2(this);

		return flighttemplates2;
	}

	public Flighttemplate removeFlighttemplates2(Flighttemplate flighttemplates2) {
		getFlighttemplates2().remove(flighttemplates2);
		flighttemplates2.setAirport2(null);

		return flighttemplates2;
	}

	public List<Itinerary> getItineraries1() {
		return this.itineraries1;
	}

	public void setItineraries1(List<Itinerary> itineraries1) {
		this.itineraries1 = itineraries1;
	}

	public Itinerary addItineraries1(Itinerary itineraries1) {
		getItineraries1().add(itineraries1);
		itineraries1.setAirport1(this);

		return itineraries1;
	}

	public Itinerary removeItineraries1(Itinerary itineraries1) {
		getItineraries1().remove(itineraries1);
		itineraries1.setAirport1(null);

		return itineraries1;
	}

	public List<Itinerary> getItineraries2() {
		return this.itineraries2;
	}

	public void setItineraries2(List<Itinerary> itineraries2) {
		this.itineraries2 = itineraries2;
	}

	public Itinerary addItineraries2(Itinerary itineraries2) {
		getItineraries2().add(itineraries2);
		itineraries2.setAirport2(this);

		return itineraries2;
	}

	public Itinerary removeItineraries2(Itinerary itineraries2) {
		getItineraries2().remove(itineraries2);
		itineraries2.setAirport2(null);

		return itineraries2;
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
		Airport other = (Airport) obj;
		if (airportId != other.airportId)
			return false;
		return true;
	}

}
package edu.msg.flightManagementEjb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="FlightHistory.findAll", query="SELECT f FROM FlightHistory f")
public class FlightHistory implements Serializable{
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightHistoryId;
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
		FlightHistory other = (FlightHistory) obj;
		if (flightHistoryId != other.flightHistoryId)
			return false;
		return true;
	}

	private static final long serialVersionUID = 3004146879639887295L;

	@Id
	@GeneratedValue
	private int flightHistoryId;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="flightId")
	private Flight flight;
	
	public FlightHistory() {
		
	}

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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	
}

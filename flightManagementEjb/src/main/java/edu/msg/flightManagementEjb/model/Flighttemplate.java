package edu.msg.flightManagementEjb.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the flighttemplate database table.
 * 
 */
@Entity
@NamedQuery(name = "Flighttemplate.findAll", query = "SELECT f FROM Flighttemplate f")
public class Flighttemplate implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightTemplateId;
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
		Flighttemplate other = (Flighttemplate) obj;
		if (flightTemplateId != other.flightTemplateId)
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private int flightTemplateId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	// bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name = "departureAirportId")
	private Airport airport1;

	// bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name = "destinationAirportId")
	private Airport airport2;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;

	// bi-directional many-to-one association to Plane
	@ManyToOne
	@JoinColumn(name = "planeId")
	private Plane plane;

	public Flighttemplate() {
	}

	public int getFlightTemplateId() {
		return this.flightTemplateId;
	}

	public void setFlightTemplateId(int flightTemplateId) {
		this.flightTemplateId = flightTemplateId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

}
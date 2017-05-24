package edu.msg.flightManagementEjbClient.dtos;

import java.util.Date;

public class FlighttemplateDTO {

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
		FlighttemplateDTO other = (FlighttemplateDTO) obj;
		if (flightTemplateId != other.flightTemplateId)
			return false;
		return true;
	}

	private int flightTemplateId;

	private Date endDate;

	private Date startDate;

	private AirportDTO airport1;

	private AirportDTO airport2;

	private CompanyDTO company;

	private PlaneDTO plane;

	public int getFlightTemplateId() {
		return flightTemplateId;
	}

	public void setFlightTemplateId(int flightTemplateId) {
		this.flightTemplateId = flightTemplateId;
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

}

package edu.msg.flightManagementEjb.assemblers;

import edu.msg.flightManagementEjb.model.Flighttemplate;
import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;

public class FlightTemplateAssembler {

	private AirportAssembler airportAssembler;
	private CompanyAssembler companyAssembler;
	private PlaneAssembler planeAssembler;
	
	public FlightTemplateAssembler() {
		this.airportAssembler = new AirportAssembler();
		this.companyAssembler = new CompanyAssembler();
		this.planeAssembler = new PlaneAssembler();
	}
	
	public Flighttemplate dtoToModel(FlighttemplateDTO dto) {
		if (dto == null) {
			return null;
		} else {
			
			Flighttemplate flightTemplate = new Flighttemplate();
			flightTemplate.setFlightTemplateId(dto.getFlightTemplateId());
			flightTemplate.setAirport1(airportAssembler.dtoToModel(dto.getAirport1()));
			flightTemplate.setAirport2(airportAssembler.dtoToModel(dto.getAirport2()));
			flightTemplate.setCompany(companyAssembler.dtoToModel(dto.getCompany()));
			flightTemplate.setStartDate(dto.getStartDate());
			flightTemplate.setEndDate(dto.getEndDate());
			flightTemplate.setPlane(planeAssembler.dtoToModel(dto.getPlane()));
			
			return flightTemplate;
		}
	}

	public FlighttemplateDTO modelToDto(Flighttemplate flightTemplate) {
		if (flightTemplate == null) {
			return null;
		} else {
			FlighttemplateDTO dto =  new FlighttemplateDTO();
			
			dto.setFlightTemplateId(flightTemplate.getFlightTemplateId());
			dto.setAirport1(airportAssembler.modelToDto(flightTemplate.getAirport1()));
			dto.setAirport2(airportAssembler.modelToDto(flightTemplate.getAirport2()));
			dto.setCompany(companyAssembler.modelToDto(flightTemplate.getCompany()));
			dto.setStartDate(flightTemplate.getStartDate());
			dto.setEndDate(flightTemplate.getEndDate());
			dto.setPlane(planeAssembler.modelToDto(flightTemplate.getPlane()));
			
			return dto;
		}

	}
}

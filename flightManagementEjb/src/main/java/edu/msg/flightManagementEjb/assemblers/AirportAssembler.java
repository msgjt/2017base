package edu.msg.flightManagementEjb.assemblers;

import edu.msg.flightManagementEjb.model.Airport;
import edu.msg.flightManagementEjbClient.dtos.AirportDTO;

public class AirportAssembler {
	
	public AirportAssembler() {
	}

	public Airport dtoToModel(AirportDTO dto) {
		if (dto == null) {
			return null;
		} else {
		Airport airport = new Airport();
		airport.setAirportId(dto.getAirportId());
		airport.setAddress(dto.getAddress());
		airport.setAirportName(dto.getAirportName());
		airport.setCity(dto.getCity());
		airport.setCountry(dto.getCountry());
		airport.setStatus(dto.getStatus());
		return airport;
		}
		/*List<Flight> flights1 = new LinkedList<Flight>();
		for (int i = 0; i < dto.getFlights1().size(); i++) {
			flights1.add(flightAssembler.dtoToModel(dto.getFlights1().get(i)));
			
		}
		airport.setFlights1(flights1);
		List<Flight> flights2 = new LinkedList<Flight>();
		for (int i = 0; i < dto.getFlights2().size(); i++) {
			flights2.add(flightAssembler.dtoToModel(dto.getFlights2().get(i)));
			
		}
		airport.setFlights2(flights2);*/
		
		
		//THIS STILL NEEDS TO BE IMPLEMENTED
/*		airport.setFlighttemplates1(dto.getFlighttemplates1());
		airport.setFlighttemplates2(dto.getFlighttemplates2());
		airport.setItineraries1(dto.getItinerarys1());
		airport.setItineraries2(dto.getItinerarys2());*/
	}

	public AirportDTO modelToDto(Airport airport) {
		if (airport == null) {
			return null;
		} else {
			AirportDTO dto = new AirportDTO();
			dto.setAddress(airport.getAddress());
			dto.setAirportName(airport.getAirportName());
			dto.setCity(airport.getCity());
			dto.setCountry(airport.getCountry());
			dto.setAirportId(airport.getAirportId());
			dto.setStatus(airport.getStatus());
		return dto;
		}
	}
}

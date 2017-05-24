package edu.msg.flightManagementEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Itinerary;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;

public class ItineraryAssembler {

	public Itinerary dtoToModel(ItineraryDTO itineraryDTO) {
		if (itineraryDTO != null) {
			Itinerary itinerary = new Itinerary();
			itinerary.setItineraryId(itineraryDTO.getItineraryId());
			itinerary.setItineraryName(itineraryDTO.getItineraryName());
			itinerary.setAirport1(new AirportAssembler().dtoToModel(itineraryDTO.getAirport1()));
			itinerary.setAirport2(new AirportAssembler().dtoToModel(itineraryDTO.getAirport2()));
			List<Flight> flightList = new ArrayList<>();
			FlightAssembler fa = new FlightAssembler();
			for (FlightDTO flight : itineraryDTO.getFlights()) {
				flightList.add(fa.dtoToModelSimple(flight));
			}
			itinerary.setFlights(flightList);
			itinerary.setCompany(new CompanyAssembler().dtoToModelSimple(itineraryDTO.getCompany()));
			return itinerary;
		} else {
			return null;
		}
	}

	public Itinerary dtoToModelSimple(ItineraryDTO itineraryDTO) {
		if (itineraryDTO != null) {
			Itinerary itinerary = new Itinerary();
			itinerary.setItineraryId(itineraryDTO.getItineraryId());
			itinerary.setItineraryName(itineraryDTO.getItineraryName());
			itinerary.setAirport1(new AirportAssembler().dtoToModel(itineraryDTO.getAirport1()));
			itinerary.setAirport2(new AirportAssembler().dtoToModel(itineraryDTO.getAirport2()));
			itinerary.setCompany(new CompanyAssembler().dtoToModelSimple(itineraryDTO.getCompany()));
			return itinerary;
		} else {
			return null;
		}
	}

	public ItineraryDTO modelToDto(Itinerary itinerary) {
		if (itinerary != null) {
			ItineraryDTO dto = new ItineraryDTO();
			dto.setItineraryId(itinerary.getItineraryId());
			dto.setItineraryName(itinerary.getItineraryName());
			dto.setAirport1(new AirportAssembler().modelToDto(itinerary.getAirport1()));
			dto.setAirport2(new AirportAssembler().modelToDto(itinerary.getAirport2()));
			List<FlightDTO> flightList = new ArrayList<>();
			FlightAssembler fa = new FlightAssembler();
			for (Flight flight : itinerary.getFlights()) {
				flightList.add(fa.modelToDtoSimple(flight));
			}
			dto.setFlights(flightList);
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(itinerary.getCompany()));
			return dto;
		} else {
			return null;
		}
	}

	public ItineraryDTO modelToDtoSimple(Itinerary itinerary) {
		if (itinerary != null) {
			ItineraryDTO dto = new ItineraryDTO();
			dto.setItineraryId(itinerary.getItineraryId());
			dto.setItineraryName(itinerary.getItineraryName());
			dto.setAirport1(new AirportAssembler().modelToDto(itinerary.getAirport1()));
			dto.setAirport2(new AirportAssembler().modelToDto(itinerary.getAirport2()));
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(itinerary.getCompany()));
			return dto;
		} else {
			return null;
		}
	}
}

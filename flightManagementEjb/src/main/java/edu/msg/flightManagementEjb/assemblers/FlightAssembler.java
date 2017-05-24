package edu.msg.flightManagementEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Itinerary;
import edu.msg.flightManagementEjb.model.User;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;

public class FlightAssembler {

	public Flight dtoToModelSimple(FlightDTO dto) {
		Flight flight = new Flight();
		flight.setFlightId(dto.getFlightId());
		flight.setStartDate(dto.getStartDate());
		flight.setEndDate(dto.getEndDate());
		flight.setAirport1(new AirportAssembler().dtoToModel(dto.getAirport1()));
		flight.setAirport2(new AirportAssembler().dtoToModel(dto.getAirport2()));
		flight.setPlane(new PlaneAssembler().dtoToModel(dto.getPlane()));
		flight.setCompany(new CompanyAssembler().dtoToModelSimple(dto.getCompany()));
		flight.setStatus(dto.getStatus());
		return flight;
	}

	public Flight dtoToModel(FlightDTO dto) {
		Flight flight = new Flight();
		flight.setFlightId(dto.getFlightId());
		flight.setStartDate(dto.getStartDate());
		flight.setEndDate(dto.getEndDate());
		flight.setAirport1(new AirportAssembler().dtoToModel(dto.getAirport1()));
		flight.setAirport2(new AirportAssembler().dtoToModel(dto.getAirport2()));
		flight.setCompany(new CompanyAssembler().dtoToModel(dto.getCompany()));
		flight.setPlane(new PlaneAssembler().dtoToModel(dto.getPlane()));
		flight.setStatus(dto.getStatus());
		List<User> userList = new ArrayList<>();
		UserAssembler ua = new UserAssembler();
		for (UserDTO user : dto.getUsers()) {
			userList.add(ua.dtoToModelSimple(user));
		}
		flight.setUsers(userList);
		List<Itinerary> itineraryList = new ArrayList<>();
		ItineraryAssembler ia = new ItineraryAssembler();
		for (ItineraryDTO itinerary : dto.getItineraries()) {
			itineraryList.add(ia.dtoToModelSimple(itinerary));
		}
		flight.setItineraries(itineraryList);
		return flight;
	}

	// advanced version (original)
	public FlightDTO modelToDto(Flight flight) {
		if (flight == null) {
			return null;
		} else {
			FlightDTO dto = new FlightDTO();
			dto.setFlightId(flight.getFlightId());
			dto.setStartDate(flight.getStartDate());
			dto.setEndDate(flight.getEndDate());
			dto.setAirport1(new AirportAssembler().modelToDto(flight.getAirport1()));
			dto.setAirport2(new AirportAssembler().modelToDto(flight.getAirport2()));
			dto.setCompany(new CompanyAssembler().modelToDto(flight.getCompany()));
			dto.setPlane(new PlaneAssembler().modelToDto(flight.getPlane()));
			dto.setStatus(flight.getStatus());
			List<UserDTO> userList = new ArrayList<>();
			UserAssembler ua = new UserAssembler();
			for (User user : flight.getUsers()) {
				userList.add(ua.modelToDtoSimple(user));
			}
			dto.setUsers(userList);
			List<ItineraryDTO> itineraryList = new ArrayList<>();
			ItineraryAssembler ia = new ItineraryAssembler();
			for (Itinerary itinerary : flight.getItineraries()) {
				itineraryList.add(ia.modelToDtoSimple(itinerary));
			}
			dto.setItineraries(itineraryList);
			return dto;
		}
	}

	// simplified version
	public FlightDTO modelToDtoSimple(Flight flight) {
		if (flight == null) {
			return null;
		} else {
			FlightDTO dto = new FlightDTO();
			dto.setFlightId(flight.getFlightId());
			dto.setStartDate(flight.getStartDate());
			dto.setEndDate(flight.getEndDate());
			dto.setAirport1(new AirportAssembler().modelToDto(flight.getAirport1()));
			dto.setAirport2(new AirportAssembler().modelToDto(flight.getAirport2()));
			dto.setPlane(new PlaneAssembler().modelToDto(flight.getPlane()));
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(flight.getCompany()));
			dto.setStatus(flight.getStatus());
			return dto;
		}
	}
}

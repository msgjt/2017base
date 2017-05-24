package edu.msg.flightManagementEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.User;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;

public class UserAssembler {

	public UserAssembler() {
	}

	public User dtoToModel(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();
			user.setUserId(dto.getUserId());
			user.setUserName(dto.getUserName());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setType(dto.getType());
			user.setStatus(dto.getStatus());
			user.setCompany(new CompanyAssembler().dtoToModelSimple(dto.getCompany()));
			List<Flight> flightList = new ArrayList<>();
			FlightAssembler fa = new FlightAssembler();

			for (FlightDTO flight : dto.getFlights()) {
				flightList.add(fa.dtoToModelSimple(flight));
			}
			user.ListFlights(flightList);

			user.setPassword(dto.getPassword());

			return user;
		}
	}

	public User dtoToModelSimple(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();
			user.setUserId(dto.getUserId());
			user.setUserName(dto.getUserName());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setType(dto.getType());
			user.setStatus(dto.getStatus());
			user.setCompany(new CompanyAssembler().dtoToModelSimple(dto.getCompany()));

			user.setPassword(dto.getPassword());

			return user;
		}
	}

	public UserDTO modelToDto(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO dto = new UserDTO();

			dto.setUserId(user.getUserId());
			dto.setUserName(user.getUserName());
			dto.setPassword(user.getPassword());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setStatus(user.getStatus());
			dto.setType(user.getType());
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(user.getCompany()));
			List<FlightDTO> flightList = new ArrayList<>();
			FlightAssembler fa = new FlightAssembler();

			for (Flight flight : user.getFlights()) {
				flightList.add(fa.modelToDtoSimple(flight));
			}
			dto.setFlights(flightList);

			return dto;
		}
	}

	// Simple version
	public UserDTO modelToDtoSimple(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO dto = new UserDTO();
			dto.setUserId(user.getUserId());
			dto.setUserName(user.getUserName());
			dto.setPassword(user.getPassword());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setStatus(user.getStatus());
			dto.setType(user.getType());
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(user.getCompany()));
			return dto;
		}
	}

}

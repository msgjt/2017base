package edu.msg.flightManagementEjb.assemblers;

import java.util.ArrayList;
import java.util.List;

import edu.msg.flightManagementEjb.model.Company;
import edu.msg.flightManagementEjb.model.Flight;
import edu.msg.flightManagementEjb.model.Plane;
import edu.msg.flightManagementEjb.model.User;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;

public class CompanyAssembler {

	public CompanyAssembler() {
	}

	public Company dtoToModelSimple(CompanyDTO dto) {
		if (dto == null) {
			return null;
		} else {
			Company company = new Company();
			company.setAddress(dto.getAddress());
			company.setCompanyId(dto.getCompanyId());
			company.setCompanyName(dto.getCompanyName());
			company.setEmail(dto.getEmail());
			company.setPhone(dto.getPhone());
			company.setStatus(dto.getStatus());
			return company;
		}
	}

	public Company dtoToModel(CompanyDTO dto) {
		if (dto == null) {
			return null;
		} else {
			Company company = new Company();
			company.setAddress(dto.getAddress());
			company.setCompanyId(dto.getCompanyId());
			company.setCompanyName(dto.getCompanyName());
			company.setEmail(dto.getEmail());
			company.setPhone(dto.getPhone());
			company.setStatus(dto.getStatus());
			if (null != dto.getUsers()) {
				List<User> userList = new ArrayList<>();
				UserAssembler ua = new UserAssembler();
				for (UserDTO user : dto.getUsers()) {
					userList.add(ua.dtoToModelSimple(user));
				}
				company.setUsers(userList);
			}
			if (null != dto.getFlights()) {
				List<Flight> flightList = new ArrayList<>();
				FlightAssembler fa = new FlightAssembler();
				for (FlightDTO flight : dto.getFlights()) {
					flightList.add(fa.dtoToModelSimple(flight));
				}
				company.setFlights(flightList);
			}
			if (null != dto.getPlanes()) {
				List<Plane> planeList = new ArrayList<>();
				PlaneAssembler fa = new PlaneAssembler();
				for (PlaneDTO plane : dto.getPlanes()) {
					planeList.add(fa.dtoToModel(plane));
				}
				company.setPlanes(planeList);
			}
			return company;
		}
	}

	public CompanyDTO modelToDtoSimple(Company company) {
		if (company == null) {
			return null;
		} else {
			CompanyDTO dto = new CompanyDTO();
			dto.setAddress(company.getAddress());
			dto.setCompanyId(company.getCompanyId());
			dto.setCompanyName(company.getCompanyName());
			dto.setEmail(company.getEmail());
			dto.setPhone(company.getPhone());
			dto.setStatus(company.getStatus());
			return dto;
		}

	}

	public CompanyDTO modelToDto(Company company) {
		if (company == null) {
			return null;
		} else {
			CompanyDTO dto = new CompanyDTO();
			dto.setAddress(company.getAddress());
			dto.setCompanyId(company.getCompanyId());
			dto.setCompanyName(company.getCompanyName());
			dto.setEmail(company.getEmail());
			dto.setPhone(company.getPhone());
			dto.setStatus(company.getStatus());
			List<UserDTO> userList = new ArrayList<>();
			UserAssembler ua = new UserAssembler();
			if (company.getUsers() != null)
				for (User user : company.getUsers()) {
					userList.add(ua.modelToDtoSimple(user));
				}
			dto.setUsers(userList);
			List<FlightDTO> flightList = new ArrayList<>();
			FlightAssembler fa = new FlightAssembler();
			if (company.getFlights() != null)
				for (Flight flight : company.getFlights()) {
					flightList.add(fa.modelToDtoSimple(flight));
				}
			dto.setFlights(flightList);
			List<PlaneDTO> planeList = new ArrayList<>();
			PlaneAssembler pa = new PlaneAssembler();
			if (company.getPlanes() != null)
				for (Plane plane : company.getPlanes()) {
					planeList.add(pa.modelToDtoSimple(plane));
				}
			dto.setPlanes(planeList);
			return dto;
		}

	}

}

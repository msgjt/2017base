package edu.msg.flightManagementEjb.assemblers;

import edu.msg.flightManagementEjb.model.FlightHistory;
import edu.msg.flightManagementEjbClient.dtos.FlightHistoryDTO;

public class FlightHistoryAssembler {

	public  FlightHistory dtoToModel( FlightHistoryDTO  dto) {
		if (dto != null) {
			FlightHistory fh = new FlightHistory();
			fh.setDescription(dto.getDescription());
			fh.setFlightHistoryId(dto.getFlightHistoryId());
			fh.setFlight(new FlightAssembler().dtoToModel(dto.getFlight()));
			return fh;
		}else {
			return null;
		}
	}
	
	public FlightHistoryDTO modelToDto(FlightHistory flightHistory) {
		if (flightHistory == null) {
			return null;
		}else {
			FlightHistoryDTO dto = new FlightHistoryDTO();
			dto.setDescription(flightHistory.getDescription());
			dto.setFlightHistoryId(flightHistory.getFlightHistoryId());
			dto.setFlight(new FlightAssembler().modelToDto(flightHistory.getFlight()));
			return dto;
		}
	}
}

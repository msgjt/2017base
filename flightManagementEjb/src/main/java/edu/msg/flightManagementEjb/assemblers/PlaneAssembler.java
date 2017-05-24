package edu.msg.flightManagementEjb.assemblers;

import edu.msg.flightManagementEjb.model.Plane;
import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;

public class PlaneAssembler {

	public PlaneAssembler() {
		
	}
	
	public Plane dtoToModel(PlaneDTO dto) {
		if (dto == null) {
			return null;
		} else {
			Plane plane = new Plane();
			plane.setPlaneId(dto.getPlaneId());
			plane.setModel(dto.getModel());
			plane.setCapacity(dto.getCapacity());
			plane.setStatus(dto.getStatus());
			plane.setCompany(new CompanyAssembler().dtoToModelSimple(dto.getCompany()));
			return plane;
		}
	}

	public PlaneDTO modelToDto(Plane plane) {
		if (plane == null) {
			return null;
		} else {
			PlaneDTO dto =  new PlaneDTO();
			dto.setPlaneId(plane.getPlaneId());
			dto.setModel(plane.getModel());
			dto.setCapacity(plane.getCapacity());
			dto.setStatus(plane.getStatus());
			dto.setCompany(new CompanyAssembler().modelToDtoSimple(plane.getCompany()));
			return dto;
		}

	}
	public PlaneDTO modelToDtoSimple(Plane plane) {
		if (plane == null) {
			return null;
		} else {
			PlaneDTO dto =  new PlaneDTO();
			dto.setPlaneId(plane.getPlaneId());
			dto.setModel(plane.getModel());
			dto.setCapacity(plane.getCapacity());
			dto.setStatus(plane.getStatus());
			return dto;
		}

	}
}

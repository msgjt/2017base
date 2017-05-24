package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.FlighttemplateDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightTemplateBeanInterface;

@ManagedBean(name = "flightTemplateService", eager = true)
@ApplicationScoped
public class FlightTemplateService {

	@EJB
	private FlightTemplateBeanInterface flightTemplateInterface;
	
	public FlighttemplateDTO getFlightTemplate(int id) {
		return flightTemplateInterface.findById(id);
	}
}

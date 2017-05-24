package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.FlightDTO;
import edu.msg.flightManagementEjbClient.interfaces.FlightBeanInterface;

/**
 * 
 * @author kelemeni Service used by the FlightConverter to retrieve a flight by
 *         it's ID
 */
@ManagedBean(name = "flightService", eager = true)
@ApplicationScoped
public class FlightService {

	@EJB
	private FlightBeanInterface fb;

	/**
	 * 
	 * @param id
	 * @return a flight from the database by IDs
	 */
	public FlightDTO getFlight(int id) {
		return fb.getFlightById(id);
	}
}

package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.AirportDTO;
import edu.msg.flightManagementEjbClient.interfaces.AirportBeanInterface;

/**
 * 
 * @author kelemeni Service used by the AirportConverter to retrieve an airport
 *         by it's ID
 */

@ManagedBean(name = "airportService", eager = true)
@ApplicationScoped
public class AirportService {

	@EJB
	private AirportBeanInterface abi;

	/**
	 * 
	 * @param id
	 * @return an airport from the database by IDs
	 */
	public AirportDTO getAirport(int id) {
		return abi.findById(id);
	}
}

package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.ItineraryDTO;
import edu.msg.flightManagementEjbClient.interfaces.ItineraryBeanInterface;

/**
 * 
 * @author kelemeni Service used by the ItineraryConverter to retrieve an
 *         itinerary by it's ID
 */
@ManagedBean(name = "itineraryService", eager = true)
@ApplicationScoped
public class ItineraryService {

	@EJB
	private ItineraryBeanInterface ib;

	/**
	 * 
	 * @param id
	 * @return an itinerary from the database by it's ID
	 */
	public ItineraryDTO getItinerary(int id) {
		return ib.findById(id);
	}
}

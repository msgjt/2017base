package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.PlaneDTO;
import edu.msg.flightManagementEjbClient.interfaces.PlaneBeanInterface;

/**
 * 
 * @author kelemeni Service used by the PlaneConverter to retrieve a plane
 *         by it's ID
 */
@ManagedBean(name = "planeService", eager = true)
@ApplicationScoped
public class PlaneService {

	@EJB
	private PlaneBeanInterface pbi;

	/**
	 * 
	 * @param id
	 * @return a plane from the database by it's ID
	 */
	public PlaneDTO getPlane(int id) {
		return pbi.getPlaneById(id);
	}

}

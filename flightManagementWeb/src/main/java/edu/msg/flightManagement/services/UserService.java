package edu.msg.flightManagement.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.flightManagementEjbClient.dtos.UserDTO;
import edu.msg.flightManagementEjbClient.interfaces.UserBeanInterface;

/**
 * 
 * @author kelemeni Service used by the UserConverter to retrieve a user by it's
 *         ID
 */
@ManagedBean(name = "userService", eager = true)
@ApplicationScoped
public class UserService {

	@EJB
	private UserBeanInterface ub;

	/**
	 * 
	 * @param id
	 * @return a plane from the database by it's ID
	 */
	public UserDTO getUser(int id) {
		return ub.findById(id);
	}
}

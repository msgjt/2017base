package edu.msg.jbugs.services;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.services.UserFacade;

@ManagedBean(name = "userService", eager = true)
@ApplicationScoped
public class UserService {

	@EJB
	private UserFacade ub;

	/**
	 * 
	 * @param id
	 * @return a plane from the database by it's ID
	 */
	public UserDTO getUser(int id) {
		return ub.findById(id);
	}
}

package edu.msg.jbugs.business;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.msg.jbugs.persistence.repositories.UserRepository;

/**
 * User handling.
 */
@Stateless
public class UserEjb implements UserService, Serializable {
	private static final long serialVersionUID = -5564537719566794885L;
	
	@EJB(beanName = "UserRepository")
	private UserRepository userRepository;

	/**
     * Default constructor. 
     */
    public UserEjb() {
    }

	@Override
	public String login(String userName, String password) {
		return userRepository.validateUserNameAndPassword(userName, password);
	}
}

/**
 *  Copyright (C) 2017 java training
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    	// Default constructor
    }

	@Override
	public String login(String userName, String password) {
		return userRepository.validateUserNameAndPassword(userName, password);
	}
}

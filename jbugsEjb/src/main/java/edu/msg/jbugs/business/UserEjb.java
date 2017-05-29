package edu.msg.jbugs.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.msg.jbugs.assemblers.UserAssembler;
import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.persistence.entities.User;
import edu.msg.jbugs.persistence.repositories.DaoException;
import edu.msg.jbugs.persistence.repositories.UserRepository;
import edu.msg.jbugs.services.RemoteException;
import edu.msg.jbugs.services.UserFacade;
import edu.msg.jbugs.util.PasswordEncrypter;

@Stateless
@LocalBean
public class UserEjb implements Serializable, UserFacade {

	private static Logger logger = LoggerFactory.getLogger(UserEjb.class);
	private static final long serialVersionUID = 6215916780406838825L;

	@EJB
	private UserRepository userDao;

	private UserAssembler userAssembler = new UserAssembler();

	@Override
	public String validateUserNameAndPassword(String userName, String password) throws RemoteException {
		try {
			return userDao.validateUserNameAndPassword(userName, password);
		} catch (DaoException e) {
			logger.error("Username and password validation error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void insertUser(UserDTO userDTO) throws RemoteException  {
		User user = userAssembler.dtoToModelSimple(userDTO);
		user.setPassword(PasswordEncrypter.getGeneratedHashedPassword(user.getPassword(), ""));
		try {
			userDao.insertUser(user);

		} catch (DaoException e) {
			logger.error("insert user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, boolean withPassword) throws RemoteException {
		User user = userAssembler.dtoToModel(userDTO);
		if (withPassword) {
			user.setPassword(PasswordEncrypter.getGeneratedHashedPassword(user.getPassword(), ""));
		}
		try {
			UserDTO updatedDTO = userAssembler.modelToDto(userDao.updateUser(user));
			return updatedDTO;
		} catch (DaoException e) {
			logger.error("update user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public UserDTO getByUserName(String userName) throws RemoteException {
		try {
			UserDTO getUser = userAssembler.modelToDto(userDao.getByUserName(userName));
			return getUser;
		} catch (DaoException e) {
			logger.error("getByUserName error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteUser(UserDTO userDTO) throws RemoteException {
		User user = userAssembler.dtoToModel(userDTO);
		try {
			userDao.deleteUser(user);
		} catch (DaoException e) {
			logger.error("delete user error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteUserById(int userId) throws RemoteException {
		try {
			userDao.deleteUserById(userId);
		} catch (DaoException e) {
			logger.error("deleteByUserId error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserDTO> getUsers() throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getUsers();
			for (User user : usersList) {
				UserDTO userDto = userAssembler.modelToDto(user);
				usersDTOlist.add(userDto);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("get all users error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public UserDTO findById(int userId) throws RemoteException {
		UserDTO user = new UserDTO();
		try {
			user = userAssembler.modelToDto(userDao.findById(userId));
			return user;
		} catch (DaoException e) {
			logger.error("findById error " + e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserDTO> getUsersByType(String type) throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getUsersByType(type);
			for (User user : usersList) {
				UserDTO userDto = userAssembler.modelToDto(user);
				usersDTOlist.add(userDto);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("getUsersByType error " + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}


	@Override
	public List<UserDTO> getAvailableUsers() throws RemoteException {
		List<UserDTO> usersDTOlist = new ArrayList<>();
		try {
			List<User> usersList = userDao.getAvailableUsers();
			for (User user : usersList) {
				UserDTO userDTO = userAssembler.modelToDto(user);
				usersDTOlist.add(userDTO);
			}
			return usersDTOlist;
		} catch (DaoException e) {
			logger.error("getAvailableUsers error " + e);
			throw new RemoteException(e.getMessage(),e);
		}
	}

}

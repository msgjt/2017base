package edu.msg.jbugs.services;

import java.util.List;

import javax.ejb.Remote;

import edu.msg.jbugs.dtos.UserDTO;

@Remote
public interface UserFacade {
	String validateUserNameAndPassword(String userName, String password);

	void insertUser(UserDTO userDTO) throws RemoteException;

	UserDTO updateUser(UserDTO userDTO, boolean hashPassword) throws RemoteException;

	UserDTO getByUserName(String userName) throws RemoteException;

	UserDTO findById(int userId) throws RemoteException;

	void deleteUser(UserDTO userDTO) throws RemoteException;

	void deleteUserById(int userId) throws RemoteException;

	List<UserDTO> getUsers() throws RemoteException;

	List<UserDTO> getUsersByType(String type) throws RemoteException;

	List<UserDTO> getAvailableUsers() throws RemoteException;
}

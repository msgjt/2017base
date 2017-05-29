package edu.msg.jbugs.assemblers;

import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.persistence.entities.User;

public class UserAssembler {

	public UserAssembler() {
	}

	public User dtoToModel(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();
			user.setUserId(dto.getUserId());
			user.setUserName(dto.getUserName());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setType(dto.getType());
			user.setStatus(dto.getStatus());
			user.setPassword(dto.getPassword());

			return user;
		}
	}

	public User dtoToModelSimple(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			User user = new User();
			user.setUserId(dto.getUserId());
			user.setUserName(dto.getUserName());
			user.setFirstName(dto.getFirstName());
			user.setLastName(dto.getLastName());
			user.setType(dto.getType());
			user.setStatus(dto.getStatus());

			user.setPassword(dto.getPassword());

			return user;
		}
	}

	public UserDTO modelToDto(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO dto = new UserDTO();

			dto.setUserId(user.getUserId());
			dto.setUserName(user.getUserName());
			dto.setPassword(user.getPassword());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setStatus(user.getStatus());
			dto.setType(user.getType());

			return dto;
		}
	}

	// Simple version
	public UserDTO modelToDtoSimple(User user) {
		if (user == null) {
			return null;
		} else {
			UserDTO dto = new UserDTO();
			dto.setUserId(user.getUserId());
			dto.setUserName(user.getUserName());
			dto.setPassword(user.getPassword());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setStatus(user.getStatus());
			dto.setType(user.getType());
			return dto;
		}
	}

}

package edu.msg.jbugs.assemblers;

import edu.msg.jbugs.dtos.UserDTO;
import edu.msg.jbugs.persistence.entities.UserEntity;

public class UserAssembler {

	public UserAssembler() {
	}

	public UserEntity dtoToModel(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			UserEntity user = new UserEntity();
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

	public UserEntity dtoToModelSimple(UserDTO dto) {
		if (dto == null) {
			return null;
		} else {
			UserEntity user = new UserEntity();
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

	public UserDTO modelToDto(UserEntity user) {
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
	public UserDTO modelToDtoSimple(UserEntity user) {
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

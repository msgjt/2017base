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

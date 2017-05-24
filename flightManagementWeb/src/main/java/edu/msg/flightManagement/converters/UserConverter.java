package edu.msg.flightManagement.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.msg.flightManagement.services.UserService;
import edu.msg.flightManagementEjbClient.dtos.UserDTO;

@FacesConverter("userConverter")
public class UserConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				UserService service = (UserService) fc.getExternalContext().getApplicationMap().get("userService");
				return service.getUser(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid user."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((UserDTO) object).getUserId());
		} else {
			return null;
		}
	}
}

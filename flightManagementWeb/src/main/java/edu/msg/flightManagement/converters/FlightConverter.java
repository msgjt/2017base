package edu.msg.flightManagement.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.msg.flightManagement.services.FlightService;
import edu.msg.flightManagementEjbClient.dtos.FlightDTO;

@FacesConverter("flightConverter")
public class FlightConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				FlightService service = (FlightService) fc.getExternalContext().getApplicationMap()
						.get("flightService");
				return service.getFlight(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid flight."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((FlightDTO) object).getFlightId());
		} else {
			return null;
		}
	}
}

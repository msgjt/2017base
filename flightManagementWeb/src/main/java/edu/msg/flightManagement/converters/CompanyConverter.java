package edu.msg.flightManagement.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import edu.msg.flightManagement.services.CompanyService;
import edu.msg.flightManagementEjbClient.dtos.CompanyDTO;

@FacesConverter("companyConverter")
public class CompanyConverter implements Converter {

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				CompanyService service = (CompanyService) fc.getExternalContext().getApplicationMap()
						.get("companyService");
				return service.getCompany(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid company."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((CompanyDTO) object).getCompanyId());
		} else {
			return null;
		}
	}

}

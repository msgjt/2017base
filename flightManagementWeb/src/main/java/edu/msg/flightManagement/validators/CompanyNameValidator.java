package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

@FacesValidator("CompanyNameValidator")
public class CompanyNameValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String name = value.toString();

		if (name.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_COMPANY_REQUIRED));
			throw new ValidatorException(message);
		}
		if (name.length() < 3) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_COMPANY_TOO_SHORT));
			throw new ValidatorException(message);
		}

		if (name.length() > 20) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_COMPANY_TOO_LONG));
			throw new ValidatorException(message);
		}

	}

}

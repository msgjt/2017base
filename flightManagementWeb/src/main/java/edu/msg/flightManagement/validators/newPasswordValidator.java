package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

@FacesValidator("newPasswordValidator")
public class newPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object pwd) throws ValidatorException {
		String newPassword = pwd.toString();

		if ((newPassword.length() > 0) && (newPassword.length() < 3)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PWORD_TOO_SHORT));
			throw new ValidatorException(message);
		}

		if (newPassword.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PWORD_TOO_LONG));
			throw new ValidatorException(message);
		}

	}

}

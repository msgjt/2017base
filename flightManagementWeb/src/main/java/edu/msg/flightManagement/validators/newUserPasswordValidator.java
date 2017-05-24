package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

//Validates password length Only

@FacesValidator("newUserPasswordValidator")
public class newUserPasswordValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object pwd) throws ValidatorException {
		String password = pwd.toString();

		if (password.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_PWORD_REQUIRED));
			throw new ValidatorException(message);
		} else {
			if (password.length() < 3) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PWORD_TOO_SHORT));
				throw new ValidatorException(message);
			}
		}

		if (password.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_PWORD_TOO_LONG));
			throw new ValidatorException(message);
		}

	}

}

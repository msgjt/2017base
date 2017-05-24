package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

//Validates username length ONLY!

@FacesValidator("userValidator")
public class userValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object userName) throws ValidatorException {
		String name = userName.toString();

		if (name.length() == 0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_USERNAME_REQUIRED));
			throw new ValidatorException(message);
		} else {
			if (name.length() < 3) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						LabelProvider.getLabel(Constants.ERROR),
						LabelProvider.getLabel(Constants.ERROR_USERNAME_TOO_SHORT));
				throw new ValidatorException(message);
			}
		}

		if (name.length() > 15) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_USERNAME_TOO_LONG));
			throw new ValidatorException(message);
		}

		if (name.contains(" ")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Username can't contain space");
			throw new ValidatorException(message);
		}
	}

}

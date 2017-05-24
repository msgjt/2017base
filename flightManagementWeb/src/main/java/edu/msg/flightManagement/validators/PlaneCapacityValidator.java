package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

@FacesValidator("planeCapacityValidator")
public class PlaneCapacityValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String capacity = value.toString();
		
		if (!capacity.matches("[0-9]+")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_INVALIDCAPACITY));
			throw new ValidatorException(message);
		}
		if (Integer.valueOf(capacity) < 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_SHORTCAPACITY));
			throw new ValidatorException(message);
		}
		if (Integer.valueOf(capacity) > 5000) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, LabelProvider.getLabel(Constants.ERROR),
					LabelProvider.getLabel(Constants.ERROR_LONGCAPACITY));
			throw new ValidatorException(message);
		}
	}
}
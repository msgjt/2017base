package edu.msg.flightManagement.validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

@FacesValidator("DateValidator")
public class DateValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date startValue = (Date) component.getAttributes().get("otherValue");
		Date endValue = (Date) value;

		if (startValue == null || endValue == null) {
			return;
		}

		if (endValue.before(startValue)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ENDDATEMUSTBEGREATER)));
		}
	}

}

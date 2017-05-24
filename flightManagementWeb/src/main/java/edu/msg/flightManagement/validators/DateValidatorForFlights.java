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

@FacesValidator("DateValidatorForFlights")
public class DateValidatorForFlights implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Date startValue = (Date) component.getAttributes().get("otherValue");
		Date endValue = (Date) value;

		if (startValue == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_STARTDATE_NULL)));
		}

		if (endValue == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_ENDDATE_NULL)));
		}

		if (endValue.before(startValue)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ENDDATEMUSTBEGREATER)));
		}

		Date date = new Date();
		if (startValue.compareTo(date) < 0 || endValue.compareTo(date) < 0) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					LabelProvider.getLabel(Constants.ERROR), LabelProvider.getLabel(Constants.ERROR_DATE_FUTURE)));
		}
	}

}
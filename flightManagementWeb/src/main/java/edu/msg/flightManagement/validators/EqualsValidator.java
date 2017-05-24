package edu.msg.flightManagement.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import edu.msg.flightManagement.i18n.LabelProvider;
import edu.msg.flightManagement.util.Constants;

@FacesValidator("EqualsValidator")
public class EqualsValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Object otherValue = component.getAttributes().get("otherValue");
        if (value == null || otherValue == null) 
        {
            return; 
        }
        if (value.equals(otherValue)) 
        {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, Constants.ERROR,
					LabelProvider.getLabel("error.sameAirport")));
        }
	}

}

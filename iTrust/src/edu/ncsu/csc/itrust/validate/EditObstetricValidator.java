package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditObstetricOfficeVisitAction;
import edu.ncsu.csc.itrust.beans.forms.EditObstetricForm;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * Used to validate updating an office visit, by {@link EditOfficeVisitAction}
 * 
 *  
 * 
 */
public class EditObstetricValidator extends BeanValidator<EditObstetricForm> {
	//private boolean validatePrescription = false;

	/**
	 * The default constructor.
	 */
	public EditObstetricValidator() {
	}

	/*public EditOfficeVisitValidator(boolean validatePrescription) {
		this.validatePrescription = validatePrescription;
	}*/

	/**
	 * Performs the act of validating the bean in question, which varies depending on the
	 * type of validator.  If the validation does not succeed, a {@link FormValidationException} is thrown.
	 * 
	 * @param p A bean of the type to be validated.
	 */
	@Override
	public void validate(EditObstetricForm form) throws FormValidationException {
		ErrorList errorList = new ErrorList();
		errorList.addIfNotNull(checkFormat("Weight", form.getWeight(), ValidationFormat.WEIGHT, false));
		errorList.addIfNotNull(checkFormat("Systolic Blood Pressure", form.getSBloodPressure(), ValidationFormat.SYSTOLIC_BLOOD_PRESSURE, false));
		errorList.addIfNotNull(checkFormat("Diastolic Blood Pressure", form.getDBloodPressure(), ValidationFormat.DIASTOLIC_BLOOD_PRESSURE, false));
		errorList.addIfNotNull(checkFormat("Fetal heart rate", form.getFHR(), ValidationFormat.FHR, false));
		errorList.addIfNotNull(checkDouble("Fundal height of the uterus", form.getFHU(), 0, 9999));
		if (errorList.hasErrors() || Double.valueOf(form.getFHU()) <= 0)
			throw new FormValidationException(errorList);
	}
}

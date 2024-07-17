package br.com.ecommerce.users.annotation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/* Unlike the Accounts-MS service, null/blank entries are allowed, as in this service the update knows
 * how to deal with empty entries and the flow cannot be interrupted by the absence of a valid phone number.
 */
public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String>{

	@Override
	public void initialize(PhoneNumberValidator constraintAnnotation) {}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			PhoneNumberUtil pnUtil = PhoneNumberUtil.getInstance();
			PhoneNumber pn;
			
			try {
				pn = pnUtil.parse(phoneNumber, "BR");
				
			} catch (NumberParseException e) {
				return false;
			}
			
			return pnUtil.isValidNumberForRegion(pn, "BR");
		}	
		return true;
	}
}
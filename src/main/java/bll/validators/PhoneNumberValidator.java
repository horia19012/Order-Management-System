package bll.validators;

import java.util.regex.Pattern;

import model.Client;

public class PhoneNumberValidator implements Validator<Client> {
	private static final String PHNUMBER_PATTERN = "^\\d+$";

	/**
	 * it validates the client t based on the phone number pattern. if there s no
	 * match it throws an exception
	 */
	public void validate(Client t) {
		Pattern pattern = Pattern.compile(PHNUMBER_PATTERN);
		if (!pattern.matcher(t.getPhoneNumber()).matches()) {
			throw new IllegalArgumentException("PhoneNumber is not a valid phone number!");
		}
	}
}

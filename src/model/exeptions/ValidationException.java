package model.exeptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

	private Map<String, String> errors = new HashMap<String, String>();

	public ValidationException(String msg) {
		super(msg);
	}

	public Map<String, String> getErrors(){
		return errors;
	}
	
	public void addError(String fildName, String errorMenssage) {
		errors.put(fildName, errorMenssage);
	}

}

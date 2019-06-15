package itroadlabs.rnd.validation.app.constraints;

import org.springframework.validation.Errors;

public class ValidationErrorsException extends RuntimeException {
    private final Errors errors;

    public ValidationErrorsException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}

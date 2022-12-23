package lab4Validation;

public class ValidationError extends RuntimeException {
    public ValidationError(String errorMessage) {
        super(errorMessage);
    }
}

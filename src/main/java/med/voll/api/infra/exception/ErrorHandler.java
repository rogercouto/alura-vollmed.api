package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handeEntityNotFountException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var fieldErrors = e.getFieldErrors();
        var validationErrors = fieldErrors.stream().map(DataValidationError::new).toList();
        return ResponseEntity.badRequest().body(validationErrors);
    }

    private record DataValidationError(String campo, String message) {

        public DataValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<?> handleDataValidationException(DataValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

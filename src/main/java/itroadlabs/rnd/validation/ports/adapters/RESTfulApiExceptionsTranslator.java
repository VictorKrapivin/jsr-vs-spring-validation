package itroadlabs.rnd.validation.ports.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import itroadlabs.rnd.validation.app.constraints.ValidationErrorsException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
class RESTfulApiExceptionsTranslator {
    @Autowired
    RESTfulApiExceptionsTranslator(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ProblemModule().withStackTraces(false));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Problem> handle(ConstraintViolationException e, HttpServletRequest request) {
        List<InvalidInputDataErrors.FieldError> errors = InvalidInputDataErrors.from(e).getErrors();
        return createProblemResponseEntity(request, (List<InvalidInputDataErrors.FieldError>) errors);
    }

    @ExceptionHandler({ValidationErrorsException.class})
    public ResponseEntity<Problem> handle(ValidationErrorsException e, HttpServletRequest request) {
        List<InvalidInputDataErrors.FieldError> errors = InvalidInputDataErrors.from(e).getErrors();
        return createProblemResponseEntity(request, errors);
    }

    private ResponseEntity<Problem> createProblemResponseEntity(HttpServletRequest request, List<InvalidInputDataErrors.FieldError> errors) {
        ThrowableProblem problem = Problem.builder()
                .withStatus(Status.UNPROCESSABLE_ENTITY)
                .withTitle("Invalid input data")
                .withType(URI.create("//itroadlabs/rnd/problems/invalid-input-data"))
                .withInstance(URI.create(request.getRequestURI()))
                .with("errors", errors).build();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .header("Content-Type", "application/problem+json")
                .body(problem);
    }

    @Getter
    public static class InvalidInputDataErrors {
        private final List<FieldError> errors;

        private InvalidInputDataErrors(List<FieldError> errors) {
            this.errors = errors;
        }

        static InvalidInputDataErrors from(ConstraintViolationException e) {
            return new InvalidInputDataErrors(e.getConstraintViolations().stream()
                    .map(constraintViolation -> new FieldError(cutOffMethodAndParameterName(constraintViolation),
                            constraintViolation.getMessage()))
                    .collect(Collectors.toList()));
        }

        private static String cutOffMethodAndParameterName(ConstraintViolation<?> violation) {
            // TODO: Are there any better approaches ?
            String pathAsString = violation.getPropertyPath().toString();
            if (violation.getExecutableParameters() != null) {
                int payloadPathStartIndex = pathAsString.indexOf('.', pathAsString.indexOf('.') + 1);
                return pathAsString.substring(payloadPathStartIndex + 1);
            } else {
                return pathAsString;
            }
        }

        static InvalidInputDataErrors from(ValidationErrorsException e) {
            Errors errors = e.getErrors();
            List<org.springframework.validation.FieldError> source = errors.getFieldErrors();
            List<FieldError> result = new ArrayList<>();
            for (org.springframework.validation.FieldError fieldError : source) {
                result.add(new FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            return new InvalidInputDataErrors(result);
        }

        @AllArgsConstructor
        @Getter
        @Setter
        public static class FieldError {
            private String field;
            private String message;
        }
    }
}

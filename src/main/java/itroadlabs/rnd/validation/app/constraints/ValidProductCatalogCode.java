package itroadlabs.rnd.validation.app.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidProductCatalogCodeValidator.class})
public @interface ValidProductCatalogCode {
    String message() default "Product '${validatedValue}' does not exist in the catalog";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

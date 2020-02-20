
package com.mycompany.bluefood.infrastructure.web.validator;

import com.mycompany.bluefood.util.FileType;
import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = UploadValidator.class)
public @interface UploadConstraint {
    
    String message() default "Arquivo invalido";
    FileType[] acceptedTypes();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

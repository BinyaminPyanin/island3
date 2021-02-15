package com.upgrade.island3.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ReservationWithinOneMonth
 *
 * @author Binyamin Pyanin
 * @since 20210215
 */
@Constraint(validatedBy = ReservationWithinMonthValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface ReservationWithinOneMonth {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message();
}

package org.example.catalogodecamisetasv2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotación de validación personalizada para el formato de temporada.
 * Acepta formato: YYYY-YYYY (ejemplo: 2024-2025)
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TemporadaValidator.class)
@Documented
public @interface ValidTemporada {
    String message() default "El formato de temporada debe ser YYYY-YYYY (ejemplo: 2024-2025)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


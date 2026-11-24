package org.example.catalogodecamisetasv2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Validador personalizado para el formato de temporada.
 * Formato aceptado: YYYY-YYYY (ejemplo: 2024-2025)
 */
public class TemporadaValidator implements ConstraintValidator<ValidTemporada, String> {

    private static final Pattern TEMPORADA_PATTERN = Pattern.compile("^\\d{4}-\\d{4}$");

    @Override
    public void initialize(ValidTemporada constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        // Validar formato YYYY-YYYY
        if (!TEMPORADA_PATTERN.matcher(value).matches()) {
            return false;
        }

        // Extraer los años
        String[] years = value.split("-");
        int yearStart = Integer.parseInt(years[0]);
        int yearEnd = Integer.parseInt(years[1]);

        // Validar que el segundo año sea exactamente 1 más que el primero
        return yearEnd == yearStart + 1;
    }
}



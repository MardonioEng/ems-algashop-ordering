package br.com.mardoniorodrigues.ordering.domain.valueObject;

import br.com.mardoniorodrigues.ordering.domain.validator.FieldValidations;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;

public record Email(String value) {

    public Email{
        FieldValidations.requiresValidEmail(value, VALIDATION_ERROR_EMAIL_IS_INVALID);

        if (value.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}

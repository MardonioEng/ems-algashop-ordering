package br.com.mardoniorodrigues.ordering.domain.valueObject;

import static br.com.mardoniorodrigues.ordering.domain.validator.FieldValidations.requiresNonBlank;

public record ProductName(String value) {

    public ProductName {
        requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

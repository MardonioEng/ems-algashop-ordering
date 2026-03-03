package br.com.mardoniorodrigues.ordering.domain.model.valueObject;

import static br.com.mardoniorodrigues.ordering.domain.model.validator.FieldValidations.requiresNonBlank;

public record ProductName(String value) {

    public ProductName {
        requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

package br.com.mardoniorodrigues.ordering.domain.valueObject;

import lombok.Builder;

import java.util.Objects;

import static br.com.mardoniorodrigues.ordering.domain.validator.FieldValidations.requiresNonBlank;

public record Address(
        String street,
        String complement,
        String neighborhood,
        String number,
        String city,
        String state,
        ZipCode zipCode
) {

    @Builder(toBuilder = true)
    public Address {
        requiresNonBlank(street);
        requiresNonBlank(neighborhood);
        requiresNonBlank(number);
        requiresNonBlank(city);
        requiresNonBlank(state);
        Objects.requireNonNull(zipCode);
    }
}

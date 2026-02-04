package br.com.mardoniorodrigues.ordering.domain.valueObject;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.com.mardoniorodrigues.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;

public record BirthDate(LocalDate value) {

    public BirthDate {
        Objects.requireNonNull(value);

        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }
    }

    public Integer age() {
        return Period.between(this.value, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return (value != null)
            ? value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            : "Date not defined";
    }
}

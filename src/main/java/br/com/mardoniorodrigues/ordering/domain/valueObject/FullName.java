package br.com.mardoniorodrigues.ordering.domain.valueObject;

import java.util.Objects;

public record FullName(String firstname, String lastName) {

    public FullName(String firstname, String lastName) {
        Objects.requireNonNull(firstname);
        Objects.requireNonNull(lastName);

        if (firstname.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (lastName.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.firstname = firstname.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstname + " " + lastName;
    }
}

package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                Customer customer = new Customer(
                    IdGenerator.generateTimeBasedUUID(),
                    "John Doe",
                    LocalDate.of(1991, 7, 5),
                    "invalid",
                    "475-356-2504",
                    "255-08-0578",
                    false,
                    OffsetDateTime.now()
                );
            });
    }

    @Test
    void given_invalidEmail_whenTryUpdadeCustomer_shouldGenerateException() {

        Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "John Doe",
            LocalDate.of(1991, 7, 5),
            "john.doe@hotmail.com",
            "475-356-2504",
            "255-08-0578",
            false,
            OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                customer.changeEmail("invalid");
            });
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@hotmail.com",
                "475-356-2504",
                "255-08-0578",
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
            c -> assertThat(c.fullName()).isEqualTo("Anonymous"),
            c -> assertThat(c.email()).isNotEqualTo("john.doe@hotmail.com"),
            c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
            c -> assertThat(c.document()).isEqualTo("000-000-0000"),
            c -> assertThat(c.birthDate()).isNull()
            );
    }

}
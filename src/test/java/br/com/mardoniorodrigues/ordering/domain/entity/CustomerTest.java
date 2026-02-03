package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.exception.CustomerArchivedException;
import br.com.mardoniorodrigues.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
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

        assertThatExceptionOfType(IllegalArgumentException.class)
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

        assertWith(customer,
            c -> assertThat(c.fullName()).isEqualTo("Anonymous"),
            c -> assertThat(c.email()).isNotEqualTo("john.doe@hotmail.com"),
            c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
            c -> assertThat(c.document()).isEqualTo("000-000-0000"),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
            );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Anonymous",
            null,
            "anonymous@anonymous.com",
            "000-000-0000",
            "000-000-0000",
            false,
            true,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            10
        );

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeEmail("email@email.com"));

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changePhone("123-123-1234"));

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::enablePromotionNotifications);

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {

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

        customer.addLoyaltyPoint(10);
        customer.addLoyaltyPoint(20);

        assertThat(customer.loyaltyPoints()).isEqualTo(30);
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {

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

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(0));

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(-10));
    }

}
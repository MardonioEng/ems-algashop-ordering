package br.com.mardoniorodrigues.ordering.domain.entity;

import br.com.mardoniorodrigues.ordering.domain.exception.CustomerArchivedException;
import br.com.mardoniorodrigues.ordering.domain.valueObject.*;
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
                Customer customer = Customer.brandNew(
                    new FullName("John", "Doe"),
                    new BirthDate(LocalDate.of(1991, 7, 5)),
                    new Email("invalid"),
                    new Phone("475-356-2504"),
                    new Document("255-08-0578"),
                    false,
                    Address.builder()
                        .street("Bourdon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114")
                        .build()
                );
            });
    }

    @Test
    void given_invalidEmail_whenTryUpdadeCustomer_shouldGenerateException() {

        Customer customer = Customer.brandNew(
            new FullName("John", "Doe"),
            new BirthDate(LocalDate.of(1991, 7, 5)),
            new Email("john.doe@hotmail.com"),
            new Phone("475-356-2504"),
            new Document("255-08-0578"),
            false,
            Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                customer.changeEmail(new Email("invalid"));
            });
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {

        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@hotmail.com"),
                new Phone("475-356-2504"),
                new Document("255-08-0578"),
                false,
                Address.builder()
                    .street("Bourdon Street")
                    .number("1134")
                    .neighborhood("North Ville")
                    .city("York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .complement("Apt. 114")
                    .build()
        );

        customer.archive();

        assertWith(customer,
            c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
            c -> assertThat(c.email()).isNotEqualTo(new Email("john.doe@hotmail.com")),
            c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
            c -> assertThat(c.document()).isEqualTo(new Document("000-000-0000")),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
            c -> assertThat(c.address()).isEqualTo(
                    Address.builder()
                            .street("Bourdon Street")
                            .number("Anonymized")
                            .neighborhood("North Ville")
                            .city("York")
                            .state("South California")
                            .zipCode(new ZipCode("12345"))
                            .complement(null)
                            .build())
            );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = Customer.existing(
            new CustomerId(),
            new FullName("Anonymous", "Anonymous"),
            null,
            new Email("anonymous@anonymous.com"),
            new Phone("000-000-0000"),
            new Document("000-000-0000"),
            false,
            true,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            new LoyaltyPoints(10),
            Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build()
        );

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::archive);

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changeEmail(new Email("email@email.com")));

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(() -> customer.changePhone(new Phone("123-123-1234")));

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::enablePromotionNotifications);

        assertThatExceptionOfType(CustomerArchivedException.class)
            .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {

        Customer customer = Customer.brandNew(
            new FullName("John", "Doe"),
            new BirthDate(LocalDate.of(1991, 7, 5)),
            new Email("john.doe@hotmail.com"),
            new Phone("475-356-2504"),
            new Document("255-08-0578"),
            false,
            Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build()
        );

        customer.addLoyaltyPoint(new LoyaltyPoints(10));
        customer.addLoyaltyPoint(new LoyaltyPoints(20));

        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {

        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@hotmail.com"),
                new Phone("475-356-2504"),
                new Document("255-08-0578"),
                false,
                Address.builder()
                    .street("Bourdon Street")
                    .number("1134")
                    .neighborhood("North Ville")
                    .city("York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .complement("Apt. 114")
                    .build()
        );

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(new LoyaltyPoints(0)));

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(new LoyaltyPoints(-10)));
    }

}
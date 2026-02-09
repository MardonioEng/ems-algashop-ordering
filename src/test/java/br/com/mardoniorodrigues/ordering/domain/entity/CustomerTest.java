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
            .isThrownBy(() -> Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("invalid"))
                .phone(new Phone("475-356-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .address(Address.builder()
                    .street("Bourdon Street")
                    .number("1134")
                    .neighborhood("North Ville")
                    .city("York")
                    .state("South California")
                    .zipCode(new ZipCode("12345"))
                    .complement("Apt. 114")
                    .build())
                .build()
            );
    }

    @Test
    void given_invalidEmail_whenTryUpdadeCustomer_shouldGenerateException() {

        Customer customer = Customer.brandNew()
            .fullName(new FullName("John", "Doe"))
            .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
            .email(new Email("john.doe@hotmail.com"))
            .phone(new Phone("475-356-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .address(Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build())
            .build();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                customer.changeEmail(new Email("invalid"));
            });
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {

        Customer customer = Customer.brandNew()
            .fullName(new FullName("John", "Doe"))
            .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
            .email(new Email("john.doe@hotmail.com"))
            .phone(new Phone("475-356-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .address(Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build())
            .build();

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
        Customer customer = Customer.existing()
            .id(new CustomerId())
            .fullName(new FullName("Anonymous", "Anonymous"))
            .birthDate(null)
            .email(new Email("anonymous@anonymous.com"))
            .phone(new Phone("000-000-0000"))
            .document(new Document("000-000-0000"))
            .promotionNotificationsAllowed(false)
            .archived(true)
            .registeredAt(OffsetDateTime.now())
            .archivedAt(OffsetDateTime.now())
            .loyaltyPoints(new LoyaltyPoints(10))
            .address(Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build())
            .build();

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

        Customer customer = Customer.brandNew()
            .fullName(new FullName("John", "Doe"))
            .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
            .email(new Email("john.doe@hotmail.com"))
            .phone(new Phone("475-356-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .address(Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build())
            .build();

        customer.addLoyaltyPoint(new LoyaltyPoints(10));
        customer.addLoyaltyPoint(new LoyaltyPoints(20));

        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {

        Customer customer = Customer.brandNew()
            .fullName(new FullName("John", "Doe"))
            .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
            .email(new Email("john.doe@hotmail.com"))
            .phone(new Phone("475-356-2504"))
            .document(new Document("255-08-0578"))
            .promotionNotificationsAllowed(false)
            .address(Address.builder()
                .street("Bourdon Street")
                .number("1134")
                .neighborhood("North Ville")
                .city("York")
                .state("South California")
                .zipCode(new ZipCode("12345"))
                .complement("Apt. 114")
                .build())
            .build();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(new LoyaltyPoints(0)));

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> customer.addLoyaltyPoint(new LoyaltyPoints(-10)));
    }

}
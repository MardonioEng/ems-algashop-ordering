package br.com.mardoniorodrigues.ordering;

import br.com.mardoniorodrigues.ordering.domain.entity.Customer;
import br.com.mardoniorodrigues.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {

    @Test
    public void testingCustomer() {
        Customer customer = new Customer(
            IdGenerator.generateTimeBasedUUID(),
            "Mardonio Rodrigues",
            LocalDate.of(1994, 7, 5),
            "mardoniorodrigues11@gmail.com",
            "475-345-2345",
            "234-456-8675",
            true,
            OffsetDateTime.now()
        );

        System.out.println(customer.id());
        System.out.println(IdGenerator.generateTimeBasedUUID());

        customer.addLoyaltyPoint(10);
    }
}

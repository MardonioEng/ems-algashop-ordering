package br.com.mardoniorodrigues.ordering.domain.valueObject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class QuantityTest {

    @Test
    void shouldGenerateWithValue() {
        Quantity quantity = new Quantity(5);

        assertThat(quantity.value()).isEqualTo(5);
    }

    @Test
    void shouldAddValue() {
        Quantity quantity = new Quantity(5);
        Quantity quantityUpdated = quantity.add(new Quantity(5));

        assertThat(quantityUpdated.value()).isEqualTo(10);
    }

    @Test
    void shouldThrowExceptionForInvalidValue() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                new Quantity(-1);
            });
    }
}

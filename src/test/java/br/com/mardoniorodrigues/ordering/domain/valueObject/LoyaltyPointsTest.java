package br.com.mardoniorodrigues.ordering.domain.valueObject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LoyaltyPointsTest {

    @Test
    void shouldGenerateWithValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        LoyaltyPoints loyaltyPointsUpdated = loyaltyPoints.add(5);

        assertThat(loyaltyPointsUpdated.value()).isEqualTo(15);
    }

    @Test
    void shouldNotAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> loyaltyPoints.add(-5));

        assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void shouldNotAddZeroValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltyPoints.add(0));

        assertThat(loyaltyPoints.value()).isEqualTo(10);
    }
}
package br.com.mardoniorodrigues.ordering.domain.valueObject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BirthDateTest {

    @Test
    void shouldGenerateWithValue() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1994, 6, 25));

        assertThat(birthDate.toString()).isEqualTo("25/06/1994");
    }

    @Test
    void givenNullDateShouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new BirthDate(null));
    }

    @Test
    void givenFutureDateShouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new BirthDate(LocalDate.of(2040, 6, 25)));
    }

    @Test
    void givenValidDateShouldBeAbleToGenerateAgeValue() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1994, 6, 25));

        Integer expectedAge = 31;

        assertThat(birthDate.age()).isEqualTo(expectedAge);
    }
}

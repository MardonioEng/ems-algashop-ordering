package br.com.mardoniorodrigues.ordering.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTest {

    @Test
    public void canChangeTo() {

        assertThat(OrderStatus.DRAFT.canChange(OrderStatus.PLACED)).isTrue();
        assertThat(OrderStatus.DRAFT.canChange(OrderStatus.CANCELED)).isTrue();

        assertThat(OrderStatus.PAID.canChange(OrderStatus.DRAFT)).isFalse();
    }

    @Test
    public void canNotChange() {

        assertThat(OrderStatus.PLACED.canNotChangeTo(OrderStatus.DRAFT)).isTrue();
    }
}